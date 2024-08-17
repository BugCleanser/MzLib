package mz.mzlib.util.delegator;

import mz.mzlib.asm.Type;
import mz.mzlib.asm.*;
import mz.mzlib.asm.tree.*;
import mz.mzlib.util.*;
import mz.mzlib.util.asm.AsmUtil;

import java.lang.annotation.Annotation;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class DelegatorClassInfo
{
    public Class<? extends Delegator> delegatorClass;
    public Annotation delegatorClassAnnotation;
    public Class<?> delegateClass;
    public Map<Method, Member> delegations = new ConcurrentHashMap<>();

    public DelegatorClassInfo(Class<? extends Delegator> delegatorClass)
    {
        this.delegatorClass = delegatorClass;
    }

    public Class<? extends Delegator> getDelegatorClass()
    {
        return delegatorClass;
    }

    public Class<?> getDelegateClass()
    {
        return delegateClass;
    }

    public static Map<Class<? extends Delegator>, WeakRef<DelegatorClassInfo>> cache = Collections.synchronizedMap(new WeakHashMap<>());

    public void analyse() throws InstantiationException, IllegalAccessException
    {
        ClassNotFoundException lastException = null;
        for (Annotation i : this.delegatorClass.getDeclaredAnnotations())
        {
            DelegatorClassFinderClass finder = i.annotationType().getDeclaredAnnotation(DelegatorClassFinderClass.class);
            if (finder != null)
            {
                try
                {
                    this.delegateClass = finder.value().newInstance().find(this.delegatorClass.getClassLoader(), i);
                }
                catch (ClassNotFoundException e)
                {
                    lastException = e;
                }
                if (this.delegateClass != null)
                {
                    this.delegatorClassAnnotation = i;
                    break;
                }
            }
        }
        if (this.delegateClass == null)
            throw new IllegalStateException("Delegate class not found: " + this.delegatorClass, lastException);
        for (Method i : this.delegatorClass.getDeclaredMethods())
        {
            if (Modifier.isAbstract(i.getModifiers()) && ElementSwitcher.isEnabled(i))
            {
                Class<?> returnType = i.getReturnType();
                if (Delegator.class.isAssignableFrom(returnType))
                {
                    returnType = DelegatorClassInfo.get(RuntimeUtil.cast(returnType)).getDelegateClass();
                }
                Class<?>[] argTypes = i.getParameterTypes();
                for (int j = 0; j < argTypes.length; j++)
                {
                    if (Delegator.class.isAssignableFrom(argTypes[j]))
                    {
                        argTypes[j] = DelegatorClassInfo.get(RuntimeUtil.cast(argTypes[j])).getDelegateClass();
                    }
                }
                for (Annotation j : i.getDeclaredAnnotations())
                {
                    DelegatorMemberFinderClass finder = j.annotationType().getDeclaredAnnotation(DelegatorMemberFinderClass.class);
                    if (finder != null)
                    {
                        try
                        {
                            Member m = finder.value().newInstance().find(delegateClass, j, returnType, argTypes);
                            if (m != null)
                            {
                                this.delegations.put(i, m);
                            }
                        }
                        catch (NoSuchMethodException |
                               NoSuchFieldException ignored)
                        {
                        }
                    }
                }
            }
        }
        for (Method i : this.getDelegatorClass().getMethods())
        {
            if (Modifier.isAbstract(i.getModifiers()) && ElementSwitcher.isEnabled(i) && i.getDeclaringClass() != this.getDelegatorClass() && Delegator.class.isAssignableFrom(i.getDeclaringClass()))
            {
                Member tar = DelegatorClassInfo.get(RuntimeUtil.cast(i.getDeclaringClass())).delegations.get(i);
                if (tar != null && !(tar instanceof Constructor))
                {
                    this.delegations.put(i, tar);
                }
            }
        }
    }

    public static DelegatorClassInfo get(Class<? extends Delegator> clazz)
    {
        return cache.computeIfAbsent(clazz, k ->
        {
            DelegatorClassInfo re = new DelegatorClassInfo(clazz);
            cache.put(clazz, new WeakRef<>(re));
            if (ElementSwitcher.isEnabled(clazz))
            {
                try
                {
                    re.analyse();
                }
                catch (Throwable e)
                {
                    throw RuntimeUtil.sneakilyThrow(e);
                }
            }
            ClassUtil.makeReference(clazz.getClassLoader(), re);
            return new WeakRef<>(re);
        }).get();
    }

    public volatile MethodHandle constructor = null;
    public MethodHandle constructorCache = null;

    public MethodHandle getConstructor()
    {
        MethodHandle result = constructorCache;
        if (result == null)
        {
            synchronized (this)
            {
                result = constructorCache = constructor;
                if (result == null)
                {
                    genAClassAndPhuckTheJvm();
                    result = constructorCache = constructor;
                }
            }
        }
        return result;
    }

    void genAClassAndPhuckTheJvm()
    {
        try
        {
            ClassNode cn = new ClassNode();
            cn.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "0MzDelegatorClass", null, AsmUtil.getType(AbsDelegator.class), new String[]{AsmUtil.getType(getDelegatorClass())});
            MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC, "<init>", AsmUtil.getDesc(void.class, Object.class), null, new String[0]);
            mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(), 0));
            mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 1));
            mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, AsmUtil.getType(AbsDelegator.class), mn.name, mn.desc, false));
            mn.instructions.add(AsmUtil.insnReturn(void.class));
            mn.visitEnd();
            cn.methods.add(mn);
            for (Method m : getDelegatorClass().getMethods())
            {
                if (!m.getName().equals("getDelegate") || m.getParameterCount() != 0 || Modifier.isStatic(m.getModifiers()) || !ElementSwitcher.isEnabled(m))
                {
                    continue;
                }
                mn = new MethodNode(Opcodes.ACC_PUBLIC, m.getName(), AsmUtil.getDesc(m), null, new String[0]);
                mn.instructions.add(AsmUtil.insnVarLoad(Delegator.class, 0));
                mn.visitMethodInsn(Opcodes.INVOKESPECIAL, AsmUtil.getType(AbsDelegator.class), "getDelegate", AsmUtil.getDesc(Object.class, new Class[0]), false);
                mn.instructions.add(AsmUtil.insnCast(m.getReturnType(), Object.class));
                mn.instructions.add(AsmUtil.insnReturn(m.getReturnType()));
                mn.visitEnd();
                cn.methods.add(mn);
            }
            for (Map.Entry<Method, Member> i : delegations.entrySet())
            {
                boolean isPublic = Modifier.isPublic(i.getValue().getDeclaringClass().getModifiers()) && Modifier.isPublic(i.getValue().getModifiers());
                Class<?>[] pts = i.getKey().getParameterTypes();
                mn = new MethodNode(Opcodes.ACC_PUBLIC, i.getKey().getName(), AsmUtil.getDesc(i.getKey()), null, new String[0]);
                if (i.getValue() instanceof Constructor)
                {
                    Class<?>[] ptsTar = ((Constructor<?>) i.getValue()).getParameterTypes();
                    if (isPublic)
                    {
                        mn.instructions.add(new TypeInsnNode(Opcodes.NEW, AsmUtil.getType(i.getValue().getDeclaringClass())));
                        mn.instructions.add(AsmUtil.insnDup(i.getValue().getDeclaringClass()));
                        for (int j = 0; j < pts.length; j++)
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(pts[j], 1 + j));
                            if (Delegator.class.isAssignableFrom(pts[j]))
                            {
                                mn.instructions.add(AsmUtil.insnGetDelegate());
                                mn.instructions.add(AsmUtil.insnCast(ptsTar[j], Object.class));
                            }
                            else
                            {
                                mn.instructions.add(AsmUtil.insnCast(ptsTar[j], pts[j]));
                            }
                        }
                        mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, AsmUtil.getType(i.getValue().getDeclaringClass()), "<init>", AsmUtil.getDesc((Constructor<?>) i.getValue())));
                    }
                    else
                    {
                        for (int j = 0; j < pts.length; j++)
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(pts[j], 1 + j));
                            if (Delegator.class.isAssignableFrom(pts[j]))
                            {
                                mn.instructions.add(AsmUtil.insnGetDelegate());
                                ptsTar[j] = Object.class;
                            }
                            else
                            {
                                ptsTar[j] = pts[j];
                            }
                        }
                        mn.visitInvokeDynamicInsn("new", AsmUtil.getDesc(Object.class, ptsTar), new Handle(Opcodes.H_INVOKESTATIC, AsmUtil.getType(ClassUtil.class), "getConstructorCallSite", AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Class.class, MethodType.class), false), Type.getType(i.getValue().getDeclaringClass()), Type.getMethodType(AsmUtil.getDesc(i.getValue())));
                    }
                    mn.instructions.add(AsmUtil.insnCreateDelegator(getDelegatorClass()));
                    mn.instructions.add(AsmUtil.insnReturn(getDelegatorClass()));
                }
                else if (i.getValue() instanceof Method)
                {
                    Class<?>[] ptsTar = ((Method) i.getValue()).getParameterTypes();
                    Class<?> rt = ((Method) i.getValue()).getReturnType();
                    if (isPublic)
                    {
                        if (!Modifier.isStatic(i.getValue().getModifiers()))
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(), 0));
                            mn.instructions.add(AsmUtil.insnGetDelegate());
                            mn.instructions.add(AsmUtil.insnCast(i.getValue().getDeclaringClass(), Object.class));
                        }
                        for (int j = 0; j < pts.length; j++)
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(), 1 + j));
                            if (Delegator.class.isAssignableFrom(pts[j]))
                            {
                                mn.instructions.add(AsmUtil.insnGetDelegate());
                                pts[j] = Object.class;
                            }
                            mn.instructions.add(AsmUtil.insnCast(ptsTar[j], pts[j]));
                        }
                        mn.instructions.add(new MethodInsnNode(Modifier.isStatic(i.getValue().getModifiers()) ? Opcodes.INVOKESTATIC : Modifier.isInterface(i.getValue().getDeclaringClass().getModifiers()) ? Opcodes.INVOKEINTERFACE : Opcodes.INVOKEVIRTUAL, AsmUtil.getType(i.getValue().getDeclaringClass()), i.getValue().getName(), AsmUtil.getDesc((Method) i.getValue())));
                    }
                    else
                    {
                        if (!Modifier.isStatic(i.getValue().getModifiers()))
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(), 0));
                            mn.instructions.add(AsmUtil.insnGetDelegate());
                        }
                        for (int j = 0; j < pts.length; j++)
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(pts[j], 1 + j));
                            if (Delegator.class.isAssignableFrom(pts[j]))
                            {
                                mn.instructions.add(AsmUtil.insnGetDelegate());
                                ptsTar[j] = Object.class;
                            }
                            else
                            {
                                ptsTar[j] = pts[j];
                            }
                        }
                        if (!Modifier.isStatic(i.getValue().getModifiers()))
                        {
                            ptsTar = CollectionUtil.addAll(CollectionUtil.newArrayList(Object.class), ptsTar).toArray(new Class[0]);
                        }
                        mn.visitInvokeDynamicInsn(i.getValue().getName(), AsmUtil.getDesc(rt, ptsTar), new Handle(Opcodes.H_INVOKESTATIC, AsmUtil.getType(ClassUtil.class), "getMethodCallSite", AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Class.class, MethodType.class, int.class), false), Type.getType(i.getValue().getDeclaringClass()), Type.getMethodType(AsmUtil.getDesc(i.getValue())), Modifier.isStatic(i.getValue().getModifiers()) ? 1 : 0);
                    }
                    if (Delegator.class.isAssignableFrom(i.getKey().getReturnType()))
                    {
                        mn.instructions.add(AsmUtil.insnCreateDelegator(RuntimeUtil.<Class<Delegator>>cast(i.getKey().getReturnType())));
                    }
                    else
                    {
                        mn.instructions.add(AsmUtil.insnCast(i.getKey().getReturnType(), rt));
                    }
                    mn.instructions.add(AsmUtil.insnReturn(i.getKey().getReturnType()));
                }
                else if (i.getValue() instanceof Field)
                {
                    Class<?> type = ((Field) i.getValue()).getType();
                    switch (pts.length)
                    {
                        case 0:
                            if (isPublic)
                            {
                                if (Modifier.isStatic(i.getValue().getModifiers()))
                                {
                                    mn.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC, AsmUtil.getType(i.getValue().getDeclaringClass()), i.getValue().getName(), AsmUtil.getDesc(type)));
                                }
                                else
                                {
                                    mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(), 0));
                                    mn.instructions.add(AsmUtil.insnGetDelegate());
                                    mn.instructions.add(AsmUtil.insnCast(i.getValue().getDeclaringClass(), Object.class));
                                    mn.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, AsmUtil.getType(i.getValue().getDeclaringClass()), i.getValue().getName(), AsmUtil.getDesc(type)));
                                }
                            }
                            else
                            {
                                if (Modifier.isStatic(i.getValue().getModifiers()))
                                {
                                    MethodType mt = MethodType.methodType(type.isPrimitive() ? type : Object.class);
                                    mn.visitInvokeDynamicInsn(i.getValue().getName(), AsmUtil.getDesc(mt), new Handle(Opcodes.H_INVOKESTATIC, AsmUtil.getType(ClassUtil.class), "getFieldGetterCallSite", AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Class.class, MethodType.class), false), Type.getType(i.getValue().getDeclaringClass()), Type.getMethodType(Type.getType(((Field) i.getValue()).getType())));
                                }
                                else
                                {
                                    mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(), 0));
                                    mn.instructions.add(AsmUtil.insnGetDelegate());
                                    MethodType mt = MethodType.methodType(type.isPrimitive() ? type : Object.class, Object.class);
                                    mn.visitInvokeDynamicInsn(i.getValue().getName(), AsmUtil.getDesc(mt), new Handle(Opcodes.H_INVOKESTATIC, AsmUtil.getType(ClassUtil.class), "getFieldGetterCallSite", AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Class.class, MethodType.class), false), Type.getType(i.getValue().getDeclaringClass()), Type.getMethodType(Type.getType(((Field) i.getValue()).getType()), Type.getType(i.getValue().getDeclaringClass())));
                                }
                            }
                            if (Delegator.class.isAssignableFrom(i.getKey().getReturnType()))
                            {
                                mn.instructions.add(AsmUtil.insnCreateDelegator(RuntimeUtil.<Class<Delegator>>cast(i.getKey().getReturnType())));
                            }
                            else
                            {
                                mn.instructions.add(AsmUtil.insnCast(i.getKey().getReturnType(), type));
                            }
                            mn.instructions.add(AsmUtil.insnReturn(i.getKey().getReturnType()));
                            break;
                        case 1:
                            Class<?> inputType = i.getKey().getParameterTypes()[0];
                            mn.instructions.add(AsmUtil.insnVarLoad(inputType, 1));
                            if (Delegator.class.isAssignableFrom(inputType))
                            {
                                mn.instructions.add(AsmUtil.insnGetDelegate());
                                inputType = Object.class;
                            }
                            if (isPublic && !Modifier.isFinal(i.getValue().getModifiers()))
                            {
                                mn.instructions.add(AsmUtil.insnCast(type, inputType));
                                if (Modifier.isStatic(i.getValue().getModifiers()))
                                {
                                    mn.instructions.add(new FieldInsnNode(Opcodes.PUTSTATIC, AsmUtil.getType(i.getValue().getDeclaringClass()), i.getValue().getName(), AsmUtil.getDesc(type)));
                                }
                                else
                                {
                                    mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(), 0));
                                    mn.instructions.add(AsmUtil.insnGetDelegate());
                                    mn.instructions.add(AsmUtil.insnCast(i.getValue().getDeclaringClass(), Object.class));
                                    mn.instructions.add(AsmUtil.insnSwap(i.getValue().getDeclaringClass(), type));
                                    mn.instructions.add(new FieldInsnNode(Opcodes.PUTFIELD, AsmUtil.getType(i.getValue().getDeclaringClass()), i.getValue().getName(), AsmUtil.getDesc(type)));
                                }
                            }
                            else
                            {
                                if (Modifier.isStatic(i.getValue().getModifiers()))
                                {
                                    MethodType mt = MethodType.methodType(void.class, inputType);
                                    mn.visitInvokeDynamicInsn(i.getValue().getName(), AsmUtil.getDesc(mt), new Handle(Opcodes.H_INVOKESTATIC, AsmUtil.getType(ClassUtil.class), "getFieldSetterCallSite", AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Class.class, MethodType.class), false), Type.getType(i.getValue().getDeclaringClass()), Type.getMethodType(Type.VOID_TYPE, Type.getType(((Field) i.getValue()).getType())));
                                }
                                else
                                {
                                    mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(), 0));
                                    mn.instructions.add(AsmUtil.insnGetDelegate());
                                    mn.instructions.add(AsmUtil.insnSwap(getDelegatorClass(), inputType));
                                    MethodType mt = MethodType.methodType(void.class, Object.class, inputType);
                                    mn.visitInvokeDynamicInsn(i.getValue().getName(), AsmUtil.getDesc(mt), new Handle(Opcodes.H_INVOKESTATIC, AsmUtil.getType(ClassUtil.class), "getFieldSetterCallSite", AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Class.class, MethodType.class), false), Type.getType(i.getValue().getDeclaringClass()), Type.getMethodType(Type.VOID_TYPE, Type.getType(i.getValue().getDeclaringClass()), Type.getType(((Field) i.getValue()).getType())));
                                }
                            }
                            mn.instructions.add(AsmUtil.insnReturn(void.class));
                            break;
                        default:
                            throw new AssertionError();
                    }
                }
                else
                {
                    throw new UnsupportedOperationException(Objects.toString(i.getValue()));
                }
                mn.visitEnd();
                cn.methods.add(mn);
            }
            this.delegatorClassAnnotation.annotationType().getDeclaredAnnotation(DelegatorClassFinderClass.class).value().newInstance().extra(this.delegatorClassAnnotation, cn);
            cn.visitEnd();
            ClassWriter cw = new ClassWriter(delegatorClass.getClassLoader());
            cn.accept(cw);
            Class<?> c = ClassUtil.defineClass(new SimpleClassLoader(this.delegatorClass.getClassLoader()), cn.name, cw.toByteArray());
            constructor = ClassUtil.unreflect(c.getDeclaredConstructor(Object.class)).asType(MethodType.methodType(Delegator.class, Object.class));

            cn = new ClassNode();
            new ClassReader(ClassUtil.getByteCode(delegatorClass)).accept(cn, 0);
            for (Method m : delegatorClass.getDeclaredMethods())
            {
                if (!m.isAnnotationPresent(DelegatorCreator.class))
                {
                    continue;
                }
                mn = AsmUtil.getMethodNode(cn, m.getName(), AsmUtil.getDesc(m));
                mn.instructions = new InsnList();
                mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
                mn.instructions.add(AsmUtil.insnCreateDelegator(delegatorClass));
                mn.instructions.add(AsmUtil.insnCast(m.getReturnType(), delegatorClass));
                mn.instructions.add(AsmUtil.insnReturn(m.getReturnType()));
                mn.visitEnd();
            }
            cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            cn.accept(cw);
            ClassUtil.defineClass(delegatorClass.getClassLoader(), cn.name, cw.toByteArray());
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
