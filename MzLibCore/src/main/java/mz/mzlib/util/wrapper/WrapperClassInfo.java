package mz.mzlib.util.wrapper;

import mz.mzlib.asm.Type;
import mz.mzlib.asm.*;
import mz.mzlib.asm.tree.*;
import mz.mzlib.util.*;
import mz.mzlib.util.asm.AsmUtil;

import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WrapperClassInfo
{
    public Class<? extends WrapperObject> wrapperClass;
    public Annotation WrapperClassAnnotation;
    public Class<?> wrappedClass;
    public Map<Method, Member> wrappedMembers = new ConcurrentHashMap<>();

    public WrapperClassInfo(Class<? extends WrapperObject> wrapperClass)
    {
        this.wrapperClass = wrapperClass;
    }

    public Class<? extends WrapperObject> getWrapperClass()
    {
        return this.wrapperClass;
    }

    public Class<?> getWrappedClass()
    {
        return wrappedClass;
    }

    public static Map<Class<? extends WrapperObject>, WeakRef<WrapperClassInfo>> cache = Collections.synchronizedMap(new WeakHashMap<>());

    public void analyse() throws InstantiationException, IllegalAccessException
    {
        if(!ElementSwitcher.isEnabled(this.getWrapperClass()))
            return;
        ClassNotFoundException lastException = null;
        for (Annotation i : this.wrapperClass.getDeclaredAnnotations())
        {
            WrappedClassFinderClass finder = i.annotationType().getDeclaredAnnotation(WrappedClassFinderClass.class);
            if (finder != null)
            {
                try
                {
                    this.wrappedClass = finder.value().newInstance().find(this.wrapperClass, i);
                }
                catch (ClassNotFoundException e)
                {
                    lastException = e;
                }
                if (this.wrappedClass != null)
                {
                    this.WrapperClassAnnotation = i;
                    break;
                }
            }
        }
        if (this.wrappedClass == null)
        {
            if (lastException != null)
                throw new IllegalStateException("Wrapped class not found: " + this.wrapperClass, lastException);
            return;
        }
        for (Method i : this.wrapperClass.getDeclaredMethods())
        {
            if (!Modifier.isAbstract(i.getModifiers()) || !ElementSwitcher.isEnabled(i))
                continue;
            Class<?> returnType = i.getReturnType();
            if (WrapperObject.class.isAssignableFrom(returnType))
            {
                returnType = WrapperClassInfo.get(RuntimeUtil.cast(returnType)).getWrappedClass();
            }
            Class<?>[] argTypes = i.getParameterTypes();
            for (int j = 0; j < argTypes.length; j++)
            {
                if (WrapperObject.class.isAssignableFrom(argTypes[j]))
                {
                    argTypes[j] = WrapperClassInfo.get(RuntimeUtil.cast(argTypes[j])).getWrappedClass();
                }
            }
            Exception lastException1 = null;
            for (Annotation j : i.getDeclaredAnnotations())
            {
                WrappedMemberFinderClass finder = j.annotationType().getDeclaredAnnotation(WrappedMemberFinderClass.class);
                if (finder != null)
                {
                    try
                    {
                        Member m = finder.value().newInstance().find(wrappedClass, j, returnType, argTypes);
                        if (m != null)
                            this.wrappedMembers.put(i, m);
                    }
                    catch (NoSuchMethodException|NoSuchFieldException e)
                    {
                        lastException1=e;
                    }
                }
            }
            if(lastException1!=null)
                throw RuntimeUtil.sneakilyThrow(new NoSuchElementException("Of wrapper: "+i).initCause(lastException1));
        }
        for (Method i : this.getWrapperClass().getMethods())
        {
            if (Modifier.isAbstract(i.getModifiers()) && ElementSwitcher.isEnabled(i) && i.getDeclaringClass() != this.getWrapperClass() && WrapperObject.class.isAssignableFrom(i.getDeclaringClass()))
            {
                Member tar = WrapperClassInfo.get(RuntimeUtil.cast(i.getDeclaringClass())).wrappedMembers.get(i);
                if (tar != null && !(tar instanceof Constructor))
                    this.wrappedMembers.put(i, tar);
            }
        }
    }

    public static WrapperClassInfo get(Class<? extends WrapperObject> clazz)
    {
        return cache.computeIfAbsent(clazz, k ->
        {
            WrapperClassInfo re = new WrapperClassInfo(clazz);
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
            cn.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, AsmUtil.getType(this.getWrapperClass())+"$0WrapperImpl", null, AsmUtil.getType(AbsWrapper.class), new String[]{AsmUtil.getType(getWrapperClass())});
            MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC, "<init>", AsmUtil.getDesc(void.class, Object.class), null, new String[0]);
            mn.instructions.add(AsmUtil.insnVarLoad(getWrapperClass(), 0));
            mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 1));
            mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, AsmUtil.getType(AbsWrapper.class), mn.name, mn.desc, false));
            mn.instructions.add(AsmUtil.insnReturn(void.class));
            mn.visitEnd();
            cn.methods.add(mn);
            for (Method m : this.getWrapperClass().getMethods())
            {
                if(!ElementSwitcher.isEnabled(m))
                    continue;
                if (m.getName().equals("getWrapped") && m.getParameterCount() == 0 && !Modifier.isStatic(m.getModifiers()))
                {
                    mn = new MethodNode(Opcodes.ACC_PUBLIC, m.getName(), AsmUtil.getDesc(m), null, new String[0]);
                    mn.instructions.add(AsmUtil.insnVarLoad(WrapperObject.class, 0));
                    mn.visitMethodInsn(Opcodes.INVOKESPECIAL, AsmUtil.getType(AbsWrapper.class), m.getName(), AsmUtil.getDesc(Object.class, new Class[0]), false);
                    mn.instructions.add(AsmUtil.insnCast(m.getReturnType(), Object.class));
                    mn.instructions.add(AsmUtil.insnReturn(m.getReturnType()));
                    mn.visitEnd();
                    cn.methods.add(mn);
                }
                SpecificImpl specificImpl = m.getDeclaredAnnotation(SpecificImpl.class);
                if(specificImpl!=null)
                {
                    Class<?>[] pts = m.getParameterTypes();
                    Method target = m.getDeclaringClass().getMethod(specificImpl.value(), pts);
                    if(AsmUtil.getMethodNode(cn, target.getName(), AsmUtil.getDesc(target))!=null)
                        throw new IllegalStateException("Multiple implementations for method: "+target);
                    mn=new MethodNode(Opcodes.ACC_PUBLIC, target.getName(), AsmUtil.getDesc(target), null, new String[0]);
                    mn.instructions.add(AsmUtil.insnVarLoad(WrapperObject.class, 0));
                    for(int i=0,j=1;i< pts.length;i++)
                    {
                        mn.instructions.add(AsmUtil.insnVarLoad(pts[i], j));
                        j+=AsmUtil.getCategory(pts[i]);
                    }
                    mn.visitMethodInsn(Opcodes.INVOKEINTERFACE, AsmUtil.getType(getWrapperClass()), m.getName(), AsmUtil.getDesc(m), true);
                    mn.instructions.add(AsmUtil.insnReturn(target.getReturnType()));
                    mn.visitEnd();
                    cn.methods.add(mn);
                }
            }
            mn = new MethodNode(Opcodes.ACC_PUBLIC, "staticGetWrappedClass", AsmUtil.getDesc(Class.class, new Class[0]), null, new String[0]);
            mn.instructions.add(AsmUtil.insnConst(getWrappedClass()));
            mn.instructions.add(AsmUtil.insnReturn(Class.class));
            cn.methods.add(mn);
            for (Map.Entry<Method, Member> i : wrappedMembers.entrySet())
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
                        for (int j = 0,k=1; j < pts.length; j++)
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(pts[j], k));
                            if (WrapperObject.class.isAssignableFrom(pts[j]))
                            {
                                mn.instructions.add(AsmUtil.insnGetWrapped());
                                mn.instructions.add(AsmUtil.insnCast(ptsTar[j], Object.class));
                            }
                            else
                            {
                                mn.instructions.add(AsmUtil.insnCast(ptsTar[j], pts[j]));
                            }
                            k+=AsmUtil.getCategory(pts[j]);
                        }
                        mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, AsmUtil.getType(i.getValue().getDeclaringClass()), "<init>", AsmUtil.getDesc((Constructor<?>) i.getValue())));
                    }
                    else
                    {
                        for (int j = 0,k=1; j < pts.length; j++)
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(pts[j], k));
                            if (WrapperObject.class.isAssignableFrom(pts[j]))
                            {
                                mn.instructions.add(AsmUtil.insnGetWrapped());
                                ptsTar[j] = Object.class;
                            }
                            else
                            {
                                ptsTar[j] = pts[j];
                            }
                            k+=AsmUtil.getCategory(pts[j]);
                        }
                        mn.visitInvokeDynamicInsn("new", AsmUtil.getDesc(Object.class, ptsTar), new Handle(Opcodes.H_INVOKESTATIC, AsmUtil.getType(ClassUtil.class), "getConstructorCallSite", AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Class.class, MethodType.class), false), Type.getType(i.getValue().getDeclaringClass()), Type.getMethodType(AsmUtil.getDesc(i.getValue())));
                    }
                    mn.instructions.add(AsmUtil.insnCreateWrapper(this.getWrapperClass()));
                    mn.instructions.add(AsmUtil.insnReturn(this.getWrapperClass()));
                }
                else if (i.getValue() instanceof Method)
                {
                    Class<?>[] ptsTar = ((Method) i.getValue()).getParameterTypes();
                    Class<?> rt = ((Method) i.getValue()).getReturnType();
                    if (isPublic)
                    {
                        if (!Modifier.isStatic(i.getValue().getModifiers()))
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(this.getWrapperClass(), 0));
                            mn.instructions.add(AsmUtil.insnGetWrapped());
                            mn.instructions.add(AsmUtil.insnCast(i.getValue().getDeclaringClass(), Object.class));
                        }
                        for (int j = 0,k=1; j < pts.length; j++)
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(pts[j], 1 + j));
                            if (WrapperObject.class.isAssignableFrom(pts[j]))
                            {
                                mn.instructions.add(AsmUtil.insnGetWrapped());
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
                            mn.instructions.add(AsmUtil.insnVarLoad(this.getWrapperClass(), 0));
                            mn.instructions.add(AsmUtil.insnGetWrapped());
                        }
                        for (int j = 0; j < pts.length; j++)
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(pts[j], 1 + j));
                            if (WrapperObject.class.isAssignableFrom(pts[j]))
                            {
                                mn.instructions.add(AsmUtil.insnGetWrapped());
                                ptsTar[j] = Object.class;
                            }
                            else
                            {
                                ptsTar[j] = pts[j];
                            }
                        }
                        if (!Modifier.isStatic(i.getValue().getModifiers()))
                            ptsTar = CollectionUtil.addAll(CollectionUtil.newArrayList(Object.class), ptsTar).toArray(new Class[0]);
                        mn.visitInvokeDynamicInsn(i.getValue().getName(), AsmUtil.getDesc(rt, ptsTar), new Handle(Opcodes.H_INVOKESTATIC, AsmUtil.getType(ClassUtil.class), "getMethodCallSite", AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Class.class, MethodType.class, int.class), false), Type.getType(i.getValue().getDeclaringClass()), Type.getMethodType(AsmUtil.getDesc(i.getValue())), Modifier.isStatic(i.getValue().getModifiers()) ? 1 : 0);
                    }
                    if (WrapperObject.class.isAssignableFrom(i.getKey().getReturnType()))
                    {
                        mn.instructions.add(AsmUtil.insnCreateWrapper(RuntimeUtil.<Class<WrapperObject>>cast(i.getKey().getReturnType())));
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
                                    mn.instructions.add(AsmUtil.insnVarLoad(getWrapperClass(), 0));
                                    mn.instructions.add(AsmUtil.insnGetWrapped());
                                    mn.instructions.add(AsmUtil.insnCast(i.getValue().getDeclaringClass(), Object.class));
                                    mn.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, AsmUtil.getType(i.getValue().getDeclaringClass()), i.getValue().getName(), AsmUtil.getDesc(type)));
                                }
                            }
                            else
                            {
                                if (Modifier.isStatic(i.getValue().getModifiers()))
                                    mn.visitInvokeDynamicInsn(i.getValue().getName(), AsmUtil.getDesc(type,new Class[0]), new Handle(Opcodes.H_INVOKESTATIC, AsmUtil.getType(ClassUtil.class), "getFieldGetterCallSite", AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Class.class, MethodType.class), false), Type.getType(i.getValue().getDeclaringClass()), Type.getMethodType(Type.getType(((Field) i.getValue()).getType())));
                                else
                                {
                                    mn.instructions.add(AsmUtil.insnVarLoad(getWrapperClass(), 0));
                                    mn.instructions.add(AsmUtil.insnGetWrapped());
                                    mn.visitInvokeDynamicInsn(i.getValue().getName(), AsmUtil.getDesc(type,Object.class), new Handle(Opcodes.H_INVOKESTATIC, AsmUtil.getType(ClassUtil.class), "getFieldGetterCallSite", AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Class.class, MethodType.class), false), Type.getType(i.getValue().getDeclaringClass()), Type.getMethodType(Type.getType(((Field) i.getValue()).getType()), Type.getType(i.getValue().getDeclaringClass())));
                                }
                            }
                            if (WrapperObject.class.isAssignableFrom(i.getKey().getReturnType()))
                            {
                                mn.instructions.add(AsmUtil.insnCreateWrapper(RuntimeUtil.<Class<WrapperObject>>cast(i.getKey().getReturnType())));
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
                            if (WrapperObject.class.isAssignableFrom(inputType))
                            {
                                mn.instructions.add(AsmUtil.insnGetWrapped());
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
                                    mn.instructions.add(AsmUtil.insnVarLoad(getWrapperClass(), 0));
                                    mn.instructions.add(AsmUtil.insnGetWrapped());
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
                                    mn.instructions.add(AsmUtil.insnVarLoad(getWrapperClass(), 0));
                                    mn.instructions.add(AsmUtil.insnGetWrapped());
                                    mn.instructions.add(AsmUtil.insnSwap(getWrapperClass(), inputType));
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
            this.WrapperClassAnnotation.annotationType().getDeclaredAnnotation(WrappedClassFinderClass.class).value().newInstance().extra(this.WrapperClassAnnotation, cn);
            cn.visitEnd();
            ClassWriter cw = new ClassWriter(wrapperClass.getClassLoader());
            cn.accept(cw);
            Class<?> c = ClassUtil.defineClass(new SimpleClassLoader(this.wrapperClass.getClassLoader()), cn.name, cw.toByteArray());
            try
            {
                constructor = ClassUtil.unreflect(c.getDeclaredConstructor(Object.class)).asType(MethodType.methodType(WrapperObject.class, Object.class));
            }
            catch (VerifyError e)
            {
                try (FileOutputStream fos = new FileOutputStream("test.class"))
                {
                    fos.write(cw.toByteArray());
                }
                catch (Throwable e1)
                {
                    throw RuntimeUtil.sneakilyThrow(e1);
                }
                throw e;
            }

            cn = new ClassNode();
            new ClassReader(ClassUtil.getByteCode(wrapperClass)).accept(cn, 0);
            for (Method m : wrapperClass.getDeclaredMethods())
            {
                if (!m.isAnnotationPresent(WrapperCreator.class))
                {
                    continue;
                }
                mn = AsmUtil.getMethodNode(cn, m.getName(), AsmUtil.getDesc(m));
                mn.instructions = new InsnList();
                mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
                mn.instructions.add(AsmUtil.insnCreateWrapper(wrapperClass));
                mn.instructions.add(AsmUtil.insnCast(m.getReturnType(), wrapperClass));
                mn.instructions.add(AsmUtil.insnReturn(m.getReturnType()));
                mn.visitEnd();
            }
            cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            cn.accept(cw);
            ClassUtil.defineClass(wrapperClass.getClassLoader(), cn.name, cw.toByteArray());
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}