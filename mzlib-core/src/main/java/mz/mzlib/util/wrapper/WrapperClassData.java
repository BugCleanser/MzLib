package mz.mzlib.util.wrapper;

import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Handle;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.Type;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.asm.tree.MethodInsnNode;
import mz.mzlib.asm.tree.MethodNode;
import mz.mzlib.util.*;
import mz.mzlib.util.adapter.Adapter;
import mz.mzlib.util.asm.AsmUtil;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

@ApiStatus.Internal
public class WrapperClassData
{
    @ApiStatus.Internal
    public static class MemberData
    {
        Member target;
        boolean inherited;
        Adapter.Processor<?, ?>[] arguments;
        Adapter.Processor<?, ?> returnValue;
        MethodHandle methodHandle;
        boolean isStatic;
        
        public MemberData(Member target, boolean inherited, Adapter.Processor<?, ?>[] arguments, Adapter.Processor<?, ?> returnValue)
        {
            this.target = target;
            this.inherited = inherited;
            this.arguments = arguments;
            this.returnValue = returnValue;
            this.isStatic = Modifier.isStatic(target.getModifiers());
            if(target instanceof Constructor)
            {
                this.isStatic = true;
                this.methodHandle = ClassUtil.unreflect((Constructor<?>) target);
            }
            else if(target instanceof Field)
            {
                switch(arguments.length)
                {
                    case 0:
                        this.methodHandle = ClassUtil.unreflectGetter((Field) target);
                        break;
                    case 1:
                        this.methodHandle = ClassUtil.unreflectSetter((Field) target);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
            else if(target instanceof Method)
                this.methodHandle = ClassUtil.unreflect((Method) target);
            else
                throw new IllegalStateException(target.toString());
        }
        
        public boolean isInherited()
        {
            return this.inherited;
        }
        public boolean isStatic()
        {
            return this.isStatic;
        }
        
        public Member getTarget()
        {
            return this.target;
        }
    }
    volatile @Nullable Map<Method, MemberData> members;
    public Map<Method, MemberData> getMembers()
    {
        Map<Method, MemberData> result = this.members;
        if(result == null)
        {
            synchronized(this)
            {
                result = this.members;
                if(result == null)
                {
                    this.analyseMembers();
                    result = Objects.requireNonNull(this.members);
                }
            }
        }
        return result;
    }
    public @Nullable MemberData getMember(Method method)
    {
        return this.getMembers().get(method);
    }
    
    public Class<? extends WrapperObject> wrapperClass;
    @Nullable Annotation wrapperClassAnnotation;
    @Nullable Class<?> wrappedClass;

    public WrapperClassData(Class<? extends WrapperObject> wrapperClass)
    {
        this.wrapperClass = wrapperClass;
    }

    public Class<? extends WrapperObject> getWrapperClass()
    {
        return this.wrapperClass;
    }

    public Class<?> getWrappedClass()
    {
        if(this.wrappedClass == null)
            throw new IllegalStateException("Wrapped class not found: " + this.wrapperClass);
        return this.wrappedClass;
    }
    
    static MethodHandle mh$AbsWrapper$wrapped$get;
    static MethodHandle mh$Adapter$Processor$adapt;
    static MethodHandle mh$Adapter$Processor$revert;
    static
    {
        try
        {
            mh$AbsWrapper$wrapped$get = ClassUtil.findFieldGetter(AbsWrapper.class, false, "wrapped", Object.class);
            mh$Adapter$Processor$adapt = ClassUtil.findMethod(Adapter.Processor.class, false, "adapt", Object.class, Object.class);
            mh$Adapter$Processor$revert = ClassUtil.findMethod(Adapter.Processor.class, false, "revert", Object.class, Object.class);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    
    public static CallSite metafactory(
            MethodHandles.Lookup lookup,
            String name,
            MethodType type
    ) throws NoSuchMethodException
    {
        Class<?> wrapperImpl = lookup.lookupClass();
        Class<?>[] interfaces = wrapperImpl.getInterfaces();
        if(interfaces.length != 1)
            throw new IllegalArgumentException(Arrays.toString(interfaces));
        if(!WrapperObject.class.isAssignableFrom(interfaces[0]))
            throw new IllegalArgumentException(wrapperImpl.toString());
        //noinspection unchecked
        Class<? extends WrapperObject> wrapper = (Class<? extends WrapperObject>) interfaces[0];
        WrapperClassData data = get(wrapper);
        if(name.equals("static$getWrappedClass") && type.equals(MethodType.methodType(Class.class)))
            return new ConstantCallSite(MethodHandles.constant(Class.class, data.getWrappedClass()));
        MemberData member = data.getMember(wrapper.getMethod(name, Arrays.copyOfRange(type.parameterArray(), 1, type.parameterCount())));
        if(member == null)
            throw new IllegalArgumentException(name + type);
        MethodHandle result = member.methodHandle.asFixedArity();
        List<MethodHandle> filters = new ArrayList<>();
        for(int i = 0; i < member.arguments.length; i++)
        {
            Adapter.Processor<?, ?> adapter = member.arguments[i];
            filters.add(mh$Adapter$Processor$revert.bindTo(adapter.activate()).asType(MethodType.methodType(result.type().parameterType(member.isStatic ? i : 1 + i), type.parameterType(1 + i))));
        }
        if(!member.isStatic)
            filters.add(0, mh$AbsWrapper$wrapped$get.asType(MethodType.methodType(result.type().parameterType(0), type.parameterType(0))));
        result = MethodHandles.filterArguments(result, 0, filters.toArray(new MethodHandle[0]));
        if(member.isStatic) // TODO: optimize
            result = MethodHandles.dropArguments(result, 0, type.parameterType(0));
        if(type.returnType() != void.class)
            result = MethodHandles.filterReturnValue(result, mh$Adapter$Processor$adapt.bindTo(member.returnValue.activate()).asType(MethodType.methodType(type.returnType(), result.type().returnType())));
        else
            result = result.asType(result.type().changeReturnType(void.class));
        result = result.asType(type);
        return new ConstantCallSite(result);
    }
    
    void analyseWrappedClass()
    {
        Throwable lastException = null;
        for(Annotation i : this.wrapperClass.getDeclaredAnnotations())
        {
            WrappedClassFinderClass finder = i.annotationType().getDeclaredAnnotation(WrappedClassFinderClass.class);
            if(finder != null)
            {
                try
                {
                    this.wrappedClass = finder.value().newInstance().find(this.wrapperClass, RuntimeUtil.cast(i));
                }
                catch(Throwable e)
                {
                    lastException = e;
                }
                if(this.wrappedClass != null)
                {
                    this.wrapperClassAnnotation = i;
                    return;
                }
            }
        }
        throw new IllegalStateException("Wrapped class not found: " + this.wrapperClass, lastException);
    }

    static ClassCache<Class<? extends WrapperObject>, WrapperClassData> cache = new ClassCache<>(clazz ->
    {
        WrapperClassData result = new WrapperClassData(clazz);
        if(ElementSwitcher.isEnabled(clazz))
            result.analyseWrappedClass();
        ClassUtil.makeReference(clazz.getClassLoader(), result);
        return result;
    });

    public static WrapperClassData get(Class<? extends WrapperObject> clazz)
    {
        return cache.get(clazz);
    }

    public void analyseMembers()
    {
        Map<Method, MemberData> members = new HashMap<>();
        for(Method i : this.getWrapperClass().getMethods())
        {
            try
            {
                @Nullable MemberData result = analyseMember(i);
                if(result == null)
                    continue;
                members.put(i, result);
            }
            catch(Throwable e)
            {
                throw new IllegalStateException(
                        "Failed to analyze wrapped member: " + i, e);
            }
        }
        this.members = members;
    }
    @Nullable MemberData analyseMember(Method method)
    {
        if(!Modifier.isAbstract(method.getModifiers()) || !ElementSwitcher.isEnabled(method))
            return null;
        if(method.isBridge() || method.isSynthetic())
            return null;
        Adapter.Processor<?, ?> returnValue = Adapter.Processor.of(method.getAnnotatedReturnType());
        List<Adapter.Processor<?, ?>> args = Arrays.stream(method.getAnnotatedParameterTypes()).map(Adapter.Processor::of).collect(Collectors.toList());
        Exception lastException1 = null;
        for(Annotation j : method.getDeclaredAnnotations())
        {
            WrappedMemberFinderClass finder = j.annotationType()
                .getDeclaredAnnotation(WrappedMemberFinderClass.class);
            if(finder != null && (method.getDeclaringClass() == this.getWrapperClass() || !finder.inheritable()))
            {
                try
                {
                    @Nullable Member m = finder.value().newInstance().find(
                        this.getWrapperClass(), this.getWrappedClass(), method, RuntimeUtil.cast(j), returnValue.getAdapteeClass(),
                        args.stream().map(Adapter.Processor::getAdapteeClass).toArray(Class[]::new)
                    );
                    return new MemberData(m, finder.inheritable() && !(m instanceof Constructor), args.toArray(new Adapter.Processor[0]), returnValue);
                }
                catch(NoSuchMethodException | NoSuchFieldException | InstantiationException | IllegalAccessException e)
                {
                    lastException1 = e;
                }
            }
        }
        if(lastException1 != null)
        {
            //noinspection UnnecessaryInitCause
            throw RuntimeUtil.sneakilyThrow(
                new NoSuchElementException("Of wrapper: " + method).initCause(lastException1));
        }

        if(Modifier.isAbstract(method.getModifiers()) && ElementSwitcher.isEnabled(method) &&
            method.getDeclaringClass() != this.getWrapperClass() &&
            WrapperObject.class.isAssignableFrom(method.getDeclaringClass()))
        {
            @Nullable MemberData m = WrapperClassData.get(RuntimeUtil.cast(method.getDeclaringClass()))
                .getMember(method);
            if(m != null && m.inherited)
                return m;
        }
        return null;
    }

    volatile @Nullable MethodHandle constructorCache = null;

    public MethodHandle getConstructor()
    {
        @Nullable MethodHandle result = this.constructorCache;
        if(result == null)
        {
            synchronized(this)
            {
                result = this.constructorCache;
                if(result == null)
                {
                    this.genAClassAndPhuckTheJvm();
                    result = Objects.requireNonNull(this.constructorCache);
                }
            }
        }
        return result;
    }

    public boolean hasAccessTo(Class<?> klass)
    {
        if(!Modifier.isPublic(klass.getModifiers()))
            return false;
        if(RuntimeUtil.jvmVersion >= 9 && this.getWrapperClass() != WrapperClass.class &&
            this.getWrapperClass() != WrapperModuleJ9.class)
        {
            //noinspection RedundantIfStatement
            if(WrapperClass.FACTORY.create(Object.class).getModuleJ9().getWrapped() !=
                WrapperClass.FACTORY.create(klass).getModuleJ9().getWrapped() &&
                !WrapperClass.FACTORY.create(klass).getModuleJ9().isExported(
                    klass.getPackage() == null ? "" : klass.getPackage().getName(),
                    WrapperClass.FACTORY.create(this.getWrapperClass()).getModuleJ9()
                ))
                return false;
        }
        return true;
    }

    void genAClassAndPhuckTheJvm()
    {
        try
        {
            ClassNode cn = new ClassNode();
            cn.visit(
                Opcodes.V1_8, Opcodes.ACC_PUBLIC, AsmUtil.getType(this.getWrapperClass()) + "$mzlib@Impl", null,
                AsmUtil.getType(AbsWrapper.class), new String[]{ AsmUtil.getType(getWrapperClass()) }
            );
            MethodNode mn = new MethodNode(
                Opcodes.ACC_PUBLIC, "<init>", AsmUtil.getDesc(void.class, Object.class), null, new String[0]);
            mn.instructions.add(AsmUtil.insnVarLoad(getWrapperClass(), 0));
            mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 1));
            mn.instructions.add(
                new MethodInsnNode(Opcodes.INVOKESPECIAL, AsmUtil.getType(AbsWrapper.class), mn.name, mn.desc, false));
            mn.instructions.add(AsmUtil.insnReturn(void.class));
            mn.visitEnd();
            cn.methods.add(mn);
            for(Method m : this.getWrapperClass().getMethods())
            {
                if(!ElementSwitcher.isEnabled(m))
                    continue;
                if(m.isSynthetic() || m.isBridge())
                    continue;
                if(m.getName().equals("getWrapped") && m.getParameterCount() == 0 &&
                    !Modifier.isStatic(m.getModifiers()))
                {
                    mn = new MethodNode(Opcodes.ACC_PUBLIC, m.getName(), AsmUtil.getDesc(m), null, new String[0]);
                    mn.instructions.add(AsmUtil.insnVarLoad(WrapperObject.class, 0));
                    mn.visitMethodInsn(
                        Opcodes.INVOKESPECIAL, AsmUtil.getType(AbsWrapper.class), m.getName(),
                        AsmUtil.getDesc(Object.class, new Class[0]), false
                    );
                    mn.instructions.add(AsmUtil.insnCast(m.getReturnType(), Object.class));
                    mn.instructions.add(AsmUtil.insnReturn(m.getReturnType()));
                    mn.visitEnd();
                    cn.methods.add(mn);
                }
                String implName = null;
                Impl impl = m.getDeclaredAnnotation(Impl.class);
                if(impl != null)
                    implName = impl.value();
                @SuppressWarnings("deprecation")
                SpecificImpl specificImpl = m.getDeclaredAnnotation(SpecificImpl.class);
                if(specificImpl != null)
                    implName = specificImpl.value();
                if(implName != null)
                {
                    Class<?>[] pts = m.getParameterTypes();
                    Method target = this.getWrapperClass().getMethod(implName, pts);
                    if(target.getDeclaringClass().isAssignableFrom(m.getDeclaringClass()))
                    {
                        if(AsmUtil.getMethodNode(cn, target.getName(), AsmUtil.getDesc(target)) != null)
                            throw new IllegalStateException("Multiple implementations for method: " + target); // TODO: allow override
                        mn = new MethodNode(
                            Opcodes.ACC_PUBLIC, target.getName(), AsmUtil.getDesc(target), null, new String[0]);
                        mn.instructions.add(AsmUtil.insnVarLoad(WrapperObject.class, 0));
                        for(int i = 0, j = 1; i < pts.length; i++)
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(pts[i], j));
                            j += AsmUtil.getSize(pts[i]);
                        }
                        mn.visitMethodInsn(
                            Opcodes.INVOKEINTERFACE, AsmUtil.getType(getWrapperClass()), m.getName(),
                            AsmUtil.getDesc(m),
                            true
                        );
                        mn.instructions.add(AsmUtil.insnReturn(target.getReturnType()));
                        mn.visitEnd();
                        cn.methods.add(mn);
                    }
                }
            }
            mn = new MethodNode(Opcodes.ACC_PUBLIC, "static$getWrappedClass", AsmUtil.getDesc(Class.class, new Class[0]), null, new String[0]);
            mn.visitInvokeDynamicInsn(mn.name, mn.desc, new Handle(Opcodes.H_INVOKESTATIC, AsmUtil.getType(WrapperClassData.class), "metafactory",
                    AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class), false));
            mn.instructions.add(AsmUtil.insnReturn(Class.class));
            cn.methods.add(mn);
            //noinspection RedundantArrayCreation
            mn = new MethodNode(
                Opcodes.ACC_PUBLIC, "static$create", AsmUtil.getDesc(WrapperObject.class, new Class[]{ Object.class }),
                null, new String[0]
            );
            mn.visitTypeInsn(Opcodes.NEW, cn.name);
            mn.instructions.add(AsmUtil.insnDup(WrapperObject.class));
            mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 1));
            mn.visitMethodInsn(
                Opcodes.INVOKESPECIAL, cn.name, "<init>", AsmUtil.getDesc(void.class, Object.class), false);
            mn.instructions.add(AsmUtil.insnReturn(WrapperObject.class));
            cn.methods.add(mn);
            for(Map.Entry<Method, MemberData> i : this.getMembers().entrySet())
            {
                Method m = i.getKey();
                Class<?>[] pts = m.getParameterTypes();
                mn = new MethodNode(Opcodes.ACC_PUBLIC, m.getName(), AsmUtil.getDesc(m), null, new String[0]);
                mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0)); // this
                for(int j = 0, k = 1; j < pts.length; j++)
                {
                    mn.instructions.add(AsmUtil.insnVarLoad(pts[j], k));
                    k += AsmUtil.getSize(pts[j]);
                }
                mn.visitInvokeDynamicInsn(m.getName(), AsmUtil.getDesc(ClassUtil.methodType(m).insertParameterTypes(0, this.getWrapperClass())),
                        new Handle(Opcodes.H_INVOKESTATIC, AsmUtil.getType(WrapperClassData.class), "metafactory",
                                AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class), false));
                mn.instructions.add(AsmUtil.insnReturn(m.getReturnType()));
                mn.visitEnd();
                cn.methods.add(mn);
            }
            Set<Pair<String, MethodType>> callOnceMethods = new HashSet<>();
            ClassUtil.forEachSuperUnique(
                this.getWrapperClass(), c ->
                {
                    for(Method m : c.getDeclaredMethods())
                    {
                        if(!m.isAnnotationPresent(CallOnce.class))
                            continue;
                        if(Modifier.isStatic(m.getModifiers()) || m.getReturnType() != void.class)
                            throw new IllegalStateException(
                                "@CallOnce method must be non-static and return void: " + m);
                        callOnceMethods.add(
                            new Pair<>(m.getName(), MethodType.methodType(m.getReturnType(), m.getParameterTypes())));
                    }
                }
            );
            for(Pair<String, MethodType> i : callOnceMethods)
            {
                mn = new MethodNode(
                    Opcodes.ACC_PUBLIC, i.getFirst(), AsmUtil.getDesc(i.getSecond()), null, new String[0]);
                final MethodNode finalMn = mn;
                ClassUtil.forEachSuperTopology(
                    this.getWrapperClass(), c ->
                    {
                        try
                        {
                            if(Modifier.isAbstract(
                                c.getDeclaredMethod(i.getFirst(), i.getSecond().parameterArray()).getModifiers()))
                                return;
                        }
                        catch(NoSuchMethodException ignored)
                        {
                            return;
                        }
                        finalMn.instructions.add(AsmUtil.insnVarLoad(c, 0));
                        for(int j = 0, k = 1; j < i.getSecond().parameterCount(); j++)
                        {
                            finalMn.instructions.add(AsmUtil.insnVarLoad(i.getSecond().parameterType(j), k));
                            k += AsmUtil.getSize(i.getSecond().parameterType(j));
                        }
                        finalMn.visitInvokeDynamicInsn(
                            i.getFirst(), AsmUtil.getDesc(i.getSecond().insertParameterTypes(0, c)), new Handle(
                                Opcodes.H_INVOKESTATIC, AsmUtil.getType(ClassUtil.class), "getMethodSpecialCallSite",
                                AsmUtil.getDesc(
                                    CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class,
                                    String.class, MethodType.class
                                ), false
                            ), c.getName(), Type.getMethodType(AsmUtil.getDesc(i.getSecond()))
                        );
                    }
                );
                mn.instructions.add(AsmUtil.insnReturn(void.class));
                cn.methods.add(mn);
            }
            this.wrapperClassAnnotation.annotationType().getDeclaredAnnotation(WrappedClassFinderClass.class).value()
                .newInstance().extra(RuntimeUtil.cast(this.wrapperClassAnnotation), cn);
            cn.visitEnd();
            ClassWriter cw = new ClassWriter(wrapperClass.getClassLoader());
            cn.accept(cw);
            Class<?> c = ClassUtil.defineClass(this.wrapperClass.getClassLoader(), cn.name, cw.toByteArray());
            try
            {
                constructorCache = ClassUtil.unreflect(c.getDeclaredConstructor(Object.class))
                    .asType(MethodType.methodType(WrapperObject.class, Object.class));
            }
            catch(VerifyError e)
            {
                try(FileOutputStream fos = new FileOutputStream("test" + UUID.randomUUID() + ".class"))
                {
                    fos.write(cw.toByteArray());
                }
                catch(Throwable e1)
                {
                    throw RuntimeUtil.sneakilyThrow(e1);
                }
                throw e;
            }
        }
        catch(Throwable e)
        {
            throw new RuntimeException("Of wrapper " + this.getWrapperClass().getName(), e);
        }
    }
}
