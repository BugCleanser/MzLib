package mz.mzlib.util;

import moe.karla.usf.root.RootAccess;
import moe.karla.usf.unsafe.Unsafe;
import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.asm.tree.MethodNode;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.wrapper.WrapperClassData;
import mz.mzlib.util.wrapper.WrapperObject;
import net.bytebuddy.agent.ByteBuddyAgent;
import org.jetbrains.annotations.Nullable;

import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.*;
import java.lang.reflect.*;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ClassUtil
{
    private ClassUtil()
    {
    }

    public static ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
    public static ClassLoader extClassLoader = sysClassLoader.getParent();
    
    public static Class<?> arrayClass(Class<?> type)
    {
        return Array.newInstance(type, 0).getClass();
    }
    
    public static List<Class<?>> PRIMITIVES = Arrays.asList(
            void.class,
            byte.class,
            short.class,
            int.class,
            long.class,
            float.class,
            double.class,
            char.class,
            boolean.class
    );
    
    public static Class<?> erase(Class<?> type)
    {
        if(type.isPrimitive())
            return type;
        else
            return Object.class;
    }
    
    @Deprecated
    public static String getName(Class<?> clazz)
    {
        return clazz.getName();
    }
    public static Class<?> classForName(String name, ClassLoader cl) throws ClassNotFoundException
    {
        switch(name)
        {
            case "void":
                return void.class;
            case "byte":
                return byte.class;
            case "short":
                return short.class;
            case "int":
                return int.class;
            case "long":
                return long.class;
            case "float":
                return float.class;
            case "double":
                return double.class;
            case "boolean":
                return boolean.class;
            case "char":
                return char.class;
            default:
                return Class.forName(name, false, cl);
        }
    }
    
    public static MethodType methodType(Method method)
    {
        return MethodType.methodType(method.getReturnType(), method.getParameterTypes());
    }
    public static MethodType methodType(Constructor<?> constructor)
    {
        return MethodType.methodType(void.class, constructor.getParameterTypes());
    }
    public static MethodType methodType(Member member)
    {
        if(member instanceof Method)
            return methodType((Method) member);
        else if(member instanceof Constructor)
            return methodType((Constructor<?>) member);
        else
            throw new IllegalArgumentException("Unsupported member type: " + member);
    }

    public static Field getField(Class<?> clazz, String name) throws Throwable
    {
        try
        {
            return clazz.getDeclaredField(name);
        }
        catch(Throwable e)
        {
            try
            {
                if(clazz != Object.class)
                    return getField(getSuperclass(clazz), name);
            }
            catch(Throwable ignored)
            {
            }
            for(Class<?> i : clazz.getInterfaces())
            {
                try
                {
                    return getField(i, name);
                }
                catch(Throwable ignored)
                {
                }
            }
            throw e;
        }
    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws Throwable
    {
        try
        {
            return clazz.getDeclaredMethod(name, parameterTypes);
        }
        catch(Throwable e)
        {
            try
            {
                if(clazz != Object.class)
                    return getMethod(getSuperclass(clazz), name, parameterTypes);
            }
            catch(Throwable ignored)
            {
            }
            for(Class<?> i : clazz.getInterfaces())
            {
                try
                {
                    return getMethod(i, name, parameterTypes);
                }
                catch(Throwable ignored)
                {
                }
            }
            throw e;
        }
    }

    public static Class<?> baseType(Class<?> type)
    {
        if(type.isPrimitive())
            return type;
        else
            return Object.class;
    }

    public static Class<?> getReturnType(Member m)
    {
        if(m instanceof Method)
        {
            return ((Method) m).getReturnType();
        }
        if(!(m instanceof Constructor))
            throw new IllegalArgumentException(Objects.toString(m));
        return void.class;
    }

    public static List<? extends Member> getDeclaredMembers(Class<?> clazz)
    {
        List<Member> result = new ArrayList<>(Arrays.asList(clazz.getDeclaredConstructors()));
        result.addAll(Arrays.asList(clazz.getDeclaredFields()));
        result.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        return result;
    }

    public static Method getDeclaredMethod(Class<?> clazz, Method method)
    {
        try
        {
            return clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    
    public static MethodHandle findConstructor(Class<?> declaringClass, MethodType type)
            throws NoSuchMethodException
    {
        try
        {
            return RootAccess.getTrustedLookupIn(declaringClass)
                    .findConstructor(declaringClass, type);
        }
        catch(IllegalAccessException e)
        {
            throw new AssertionError(e);
        }
    }
    public static MethodHandle findConstructor(Class<?> declaringClass, Class<?>... parameterTypes)
            throws NoSuchMethodException
    {
        return findConstructor(declaringClass, MethodType.methodType(void.class, parameterTypes));
    }
    
    public static MethodHandle findMethod(
            Class<?> declaringClass,
            boolean isStatic,
            String name,
            MethodType type) throws NoSuchMethodException
    {
        try
        {
            if(isStatic)
                return RootAccess.getTrustedLookupIn(declaringClass)
                        .findStatic(declaringClass, name, type);
            else
                return RootAccess.getTrustedLookupIn(declaringClass)
                        .findVirtual(declaringClass, name, type);
        }
        catch(IllegalAccessException e)
        {
            throw new AssertionError(e);
        }
    }
    public static MethodHandle findMethod(
            Class<?> declaringClass,
            boolean isStatic,
            String name,
            Class<?> returnType,
            Class<?>... parameterTypes) throws NoSuchMethodException
    {
        return findMethod(declaringClass, isStatic, name, MethodType.methodType(returnType, parameterTypes));
    }
    
    public static MethodHandle findMethodSpecial(
            Class<?> declaringClass,
            String name,
            MethodType type) throws NoSuchMethodException
    {
        try
        {
            return RootAccess.getTrustedLookupIn(declaringClass)
                    .findSpecial(declaringClass, name, type, declaringClass);
        }
        catch(IllegalAccessException e)
        {
            throw new AssertionError(e);
        }
    }
    public static MethodHandle findMethodSpecial(
            Class<?> declaringClass,
            String name,
            Class<?> returnType,
            Class<?>... parameterTypes) throws NoSuchMethodException
    {
        return findMethodSpecial(declaringClass, name, MethodType.methodType(returnType, parameterTypes));
    }

    public static MethodHandle findFieldGetter(Class<?> declaringClass, boolean isStatic, String name, Class<?> type)
        throws NoSuchFieldException
    {
        try
        {
            if(isStatic)
                return RootAccess.getTrustedLookupIn(declaringClass).findStaticGetter(declaringClass, name, type);
            else
                return RootAccess.getTrustedLookupIn(declaringClass).findGetter(declaringClass, name, type);
        }
        catch(IllegalAccessException e)
        {
            throw new AssertionError(e);
        }
    }
    public static MethodHandle findFieldGetter(Class<?> declaringClass, boolean isStatic, String name)
        throws NoSuchFieldException
    {
        return findFieldGetter(declaringClass, isStatic, name, declaringClass.getDeclaredField(name).getType());
    }

    public static MethodHandle findFieldSetter(Class<?> declaringClass, boolean isStatic, String name, Class<?> type)
        throws NoSuchFieldException
    {
        try
        {
            if(isStatic)
            {
                return RootAccess.getTrustedLookupIn(declaringClass).findStaticSetter(declaringClass, name, type);
            }
            else
            {
                return RootAccess.getTrustedLookupIn(declaringClass).findSetter(declaringClass, name, type);
            }
        }
        catch(IllegalAccessException e)
        {
            throw new AssertionError(e);
        }
    }
    public static MethodHandle findFieldSetter(Class<?> declaringClass, boolean isStatic, String name)
        throws NoSuchFieldException
    {
        return findFieldSetter(declaringClass, isStatic, name, declaringClass.getDeclaredField(name).getType());
    }

    public static MethodHandle unreflect(Constructor<?> constructor)
    {
        try
        {
            return findConstructor(constructor.getDeclaringClass(), methodType(constructor));
        }
        catch(NoSuchMethodException e)
        {
            throw new AssertionError(e);
        }
    }

    public static MethodHandle unreflectGetter(Field field)
    {
        try
        {
            return findFieldGetter(field.getDeclaringClass(), Modifier.isStatic(field.getModifiers()), field.getName(), field.getType());
        }
        catch(NoSuchFieldException e)
        {
            throw new AssertionError(e);
        }
    }

    public static MethodHandle unreflectSetter(Field field)
    {
        try
        {
            return findFieldSetter(field.getDeclaringClass(), Modifier.isStatic(field.getModifiers()), field.getName(), field.getType());
        }
        catch(NoSuchFieldException e)
        {
            throw new AssertionError(e);
        }
    }

    public static MethodHandle unreflect(Method method)
    {
        try
        {
            return findMethod(method.getDeclaringClass(), Modifier.isStatic(method.getModifiers()), method.getName(), methodType(method));
        }
        catch(NoSuchMethodException e)
        {
            throw new AssertionError(e);
        }
    }

    public static MethodHandle unreflectSpecial(Method method)
    {
        try
        {
            return findMethodSpecial(method.getDeclaringClass(), method.getName(), methodType(method));
        }
        catch(NoSuchMethodException e)
        {
            throw new AssertionError(e);
        }
    }

    public static Class<?> getSuperclass(Class<?> clazz)
    {
        if(clazz.isInterface())
            return Object.class;
        return clazz.getSuperclass();
    }

    public static <E extends Throwable> void forEachSuper(Class<?> clazz, ThrowableConsumer<Class<?>, E> proc) throws E
    {
        proc.acceptOrThrow(clazz);
        if(clazz != Object.class)
            forEachSuper(getSuperclass(clazz), proc);
        for(Class<?> i : clazz.getInterfaces())
        {
            forEachSuper(i, proc);
        }
    }

    public static <E extends Throwable> void forEachSuperUnique(Class<?> clazz, ThrowableConsumer<Class<?>, E> proc)
        throws E
    {
        Set<Class<?>> history = new HashSet<>();
        forEachSuper(
            clazz, c ->
            {
                if(history.add(c))
                    proc.acceptOrThrow(c);
            }
        );
    }

    /**
     * Iterate through all super classes in topological order
     * From super to children
     */
    public static <E extends Throwable> void forEachSuperTopology(Class<?> clazz, ThrowableConsumer<Class<?>, E> proc)
        throws E
    {
        Map<Class<?>, Integer> degreeIn = new HashMap<>();
        Map<Class<?>, Set<Class<?>>> edgeOut = new HashMap<>();
        forEachSuperUnique(
            clazz, c ->
            {
                if(c != Object.class)
                {
                    edgeOut.computeIfAbsent(getSuperclass(c), ThrowableSupplier.of(HashSet<Class<?>>::new).ignore())
                        .add(c);
                    degreeIn.compute(c, (k, v) -> Option.fromNullable(v).unwrapOr(0) + 1);
                }
                for(Class<?> i : c.getInterfaces())
                {
                    edgeOut.computeIfAbsent(i, ThrowableSupplier.of(HashSet<Class<?>>::new).ignore()).add(c);
                }
                degreeIn.compute(c, (k, v) -> Option.fromNullable(v).unwrapOr(0) + c.getInterfaces().length);
            }
        );
        Queue<Class<?>> q = new ArrayDeque<>();
        q.add(Object.class);
        while(!q.isEmpty())
        {
            Class<?> now = q.poll();
            proc.acceptOrThrow(now);
            for(Set<Class<?>> es : Option.fromNullable(edgeOut.get(now)))
            {
                for(Class<?> c : es)
                {
                    if(degreeIn.compute(c, (k, v) -> Objects.requireNonNull(v) - 1) == 0)
                        q.add(c);
                }
            }
        }
        for(Integer value : degreeIn.values())
        {
            assert value == 0;
        }
    }

    static @Nullable Instrumentation instrumentation;

    public static Instrumentation getInstrumentation()
    {
        if(instrumentation == null)
        {
            try
            {
                ByteBuddyAgent.install();
            }
            catch(Throwable e)
            {
                System.err.println("Unable to inject JavaAgent");
                System.err.println(
                    "Please remove the startup parameters -XX:+DisableAttachMechanism and -Djdk.attach.allowAttachSelf=false");
                System.err.println(
                    "You can also try installing ByteBuddyAgent manually (this is not a plugin, check the installation method on the MzLib official website)");
                System.err.println("无法注入 JavaAgent");
                System.err.println(
                    "请删除启动参数-XX:+DisableAttachMechanism 和 -D" + "jdk.attach.allowAttachSelf=false");
                System.err.println("也可以尝试手动安装 ByteBuddyAgent（这不是一个插件，在 MzLib 官网查看安装方法）");
                throw e;
            }
            instrumentation = ByteBuddyAgent.getInstrumentation();
        }
        return instrumentation;
    }

    public synchronized static byte[] getByteCode(Class<?> clazz)
    {
        try
        {
            Box.Mut<byte @Nullable[]> result = Box.Mut.of(null);
            while(result.get() == null)
            {
                ClassFileTransformer tr = new ClassFileTransformer()
                {
                    @Override
                    public byte @Nullable[] transform(
                        ClassLoader cl,
                        String name,
                        Class<?> c,
                        ProtectionDomain d,
                        byte[] byteCode)
                    {
                        if(c == clazz)
                            result.set(byteCode);
                        getInstrumentation().removeTransformer(this);
                        return null;
                    }
                };
                getInstrumentation().addTransformer(tr, true);
                getInstrumentation().retransformClasses(clazz);
            }
            return Objects.requireNonNull(result.get());
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    public static Class<?> defineClass(ClassLoader classLoader, String name, byte[] byteCode)
    {
        synchronized(ClassUtil.class)
        {
            try
            {
                try
                {
                    try
                    {
                        Class<?> clazz = Class.forName(name.replace('/', '.'), false, classLoader);
                        getInstrumentation().redefineClasses(new ClassDefinition(clazz, byteCode));
                        return clazz;
                    }
                    catch(Throwable e)
                    {
                        try
                        {
                            return Unsafe.getUnsafe().defineClass(name, byteCode, 0, byteCode.length, classLoader, null);
                        }
                        catch(Throwable e1)
                        {
                            e1.addSuppressed(e);
                            throw e1;
                        }
                    }
                }
                catch(VerifyError e)
                {
                    try(FileOutputStream fos = new FileOutputStream("test.class"))
                    {
                        fos.write(byteCode);
                    }
                    catch(Throwable e1)
                    {
                        throw RuntimeUtil.sneakilyThrow(e1);
                    }
                    throw RuntimeUtil.sneakilyThrow(e);
                }
                catch(Throwable e)
                {
                    throw RuntimeUtil.sneakilyThrow(e);
                }
            }
            catch(Throwable e)
            {
                try(FileOutputStream fos = new FileOutputStream("test.class"))
                {
                    fos.write(byteCode);
                }
                catch(Throwable ignored)
                {
                }
                throw e;
            }
        }
    }

    public static void makeReference(ClassLoader classLoader, Object target)
    {
        try
        {
            String attachedName = "0MzAttachedObjects";
            Class<?> attached;
            try
            {
                attached = Class.forName(attachedName, false, classLoader);
            }
            catch(ClassNotFoundException e)
            {
                ClassNode cn = new ClassNode();
                cn.visit(
                    Opcodes.V1_8, Opcodes.ACC_PUBLIC, attachedName, null, AsmUtil.getType(Object.class),
                    new String[]{}
                );
                cn.visitField(
                        Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "instance", AsmUtil.getDesc(Set.class), null, null)
                    .visitEnd();
                cn.visitEnd();
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
                cn.accept(cw);
                attached = defineClass(classLoader, attachedName, cw.toByteArray());
                attached.getDeclaredField("instance").set(null, ConcurrentHashMap.newKeySet());
            }
            RuntimeUtil.<Set<Object>>cast(attached.getDeclaredField("instance").get(null)).add(new RefStrong<>(target));
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    public static <T> Class<T> getPrimitive(Class<T> src)
    {
        if(src == Character.class)
            return RuntimeUtil.castClass(char.class);
        else if(src == Boolean.class)
            return RuntimeUtil.castClass(boolean.class);
        else if(src == Byte.class)
            return RuntimeUtil.castClass(byte.class);
        else if(src == Short.class)
            return RuntimeUtil.castClass(short.class);
        else if(src == Integer.class)
            return RuntimeUtil.castClass(int.class);
        else if(src == Long.class)
            return RuntimeUtil.castClass(long.class);
        else if(src == Float.class)
            return RuntimeUtil.castClass(float.class);
        else if(src == Double.class)
            return RuntimeUtil.castClass(double.class);
        else if(src == Void.class)
            return RuntimeUtil.castClass(void.class);
        else
            return src;
    }

    public static <T> Class<T> getWrapper(Class<T> src)
    {
        if(src == char.class)
            return RuntimeUtil.castClass(Character.class);
        else if(src == boolean.class)
            return RuntimeUtil.castClass(Boolean.class);
        else if(src == byte.class)
            return RuntimeUtil.castClass(Byte.class);
        else if(src == short.class)
            return RuntimeUtil.castClass(Short.class);
        else if(src == int.class)
            return RuntimeUtil.castClass(Integer.class);
        else if(src == long.class)
            return RuntimeUtil.castClass(Long.class);
        else if(src == float.class)
            return RuntimeUtil.castClass(Float.class);
        else if(src == double.class)
            return RuntimeUtil.castClass(Double.class);
        else if(src == void.class)
            return RuntimeUtil.castClass(Void.class);
        else
            return src;
    }
    
    public static <A extends Annotation> A findAnnotation(Class<?> type, Class<A> annotationType)
    {
        @Nullable A result = type.getDeclaredAnnotation(annotationType);
        if(result != null)
            return result;
        if(type != Object.class)
        {
            result = findAnnotation(type.getSuperclass(), annotationType);
            if(result != null)
                return result;
        }
        for(Class<?> i: type.getInterfaces())
        {
            result = findAnnotation(i, annotationType);
            if(result != null)
                return result;
        }
        return null;
    }

    public static MethodHandle defineMethod(ClassLoader cl, MethodNode mn)
    {
        ClassNode cn = new ClassNode();
        cn.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "$Method", null, AsmUtil.getType(Object.class), new String[0]);
        cn.methods.add(mn);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cn.accept(cw);
        return unreflect(defineClass(new SimpleClassLoader(cl), cn.name, cw.toByteArray()).getDeclaredMethods()[0]);
    }

    public static CallSite getConstructorCallSite(
        MethodHandles.Lookup caller,
        String invokedName,
        MethodType invokedType,
        String ownerName,
        MethodType methodType) throws NoSuchMethodException, ClassNotFoundException
    {
        return new ConstantCallSite(
            findConstructor(
                Class.forName(ownerName, false, caller.lookupClass().getClassLoader()),
                methodType.parameterArray()
            ).asType(invokedType));
    }

    public static CallSite getMethodCallSite(
        MethodHandles.Lookup caller,
        String invokedName,
        MethodType invokedType,
        String ownerName,
        MethodType methodType,
        int isStatic) throws NoSuchMethodException, ClassNotFoundException
    {
        return new ConstantCallSite(
            findMethod(
                Class.forName(ownerName, false, caller.lookupClass().getClassLoader()), isStatic != 0, invokedName,
                methodType.returnType(), methodType.parameterArray()
            ).asType(invokedType));
    }

    public static CallSite getMethodSpecialCallSite(
        MethodHandles.Lookup caller,
        String invokedName,
        MethodType invokedType,
        String ownerName,
        MethodType methodType) throws NoSuchMethodException, ClassNotFoundException
    {
        return new ConstantCallSite(
            findMethodSpecial(
                Class.forName(ownerName, false, caller.lookupClass().getClassLoader()), invokedName,
                methodType.returnType(), methodType.parameterArray()
            ).asType(invokedType));
    }

    public static CallSite getFieldGetterCallSite(
        MethodHandles.Lookup caller,
        String invokedName,
        MethodType invokedType,
        String ownerName,
        MethodType methodType) throws NoSuchFieldException, ClassNotFoundException
    {
        return new ConstantCallSite(
            findFieldGetter(
                Class.forName(ownerName, false, caller.lookupClass().getClassLoader()),
                invokedType.parameterCount() == 0, invokedName, methodType.returnType()
            ).asType(invokedType));
    }

    public static CallSite getFieldSetterCallSite(
        MethodHandles.Lookup caller,
        String invokedName,
        MethodType invokedType,
        String ownerName,
        MethodType methodType) throws NoSuchFieldException, ClassNotFoundException
    {
        return new ConstantCallSite(
            findFieldSetter(
                Class.forName(ownerName, false, caller.lookupClass().getClassLoader()),
                invokedType.parameterCount() == 1, invokedName,
                methodType.parameterType(methodType.parameterCount() - 1)
            ).asType(invokedType));
    }

    @Deprecated
    public static Class<?> toWrappedClass(Class<?> wrapperClass)
    {
        if(WrapperObject.class.isAssignableFrom(wrapperClass))
            return WrapperClassData.get(RuntimeUtil.cast(wrapperClass)).getWrappedClass();
        return wrapperClass;
    }
    @Deprecated
    public static MethodType getWrappedType(MethodType wrapperType)
    {
        return MethodType.methodType(
            toWrappedClass(wrapperType.returnType()),
            Arrays.stream(wrapperType.parameterArray()).map(ClassUtil::toWrappedClass).collect(Collectors.toList())
        );
    }

    public static CallSite getConstructorCallSiteWithWrapperType(
        MethodHandles.Lookup caller,
        String invokedName,
        MethodType invokedType,
        String ownerName,
        MethodType methodType) throws NoSuchMethodException, ClassNotFoundException
    {
        return getConstructorCallSite(caller, invokedName, invokedType, ownerName, getWrappedType(methodType));
    }

    public static CallSite getMethodCallSiteWithWrapperType(
        MethodHandles.Lookup caller,
        String invokedName,
        MethodType invokedType,
        String ownerName,
        MethodType methodType,
        int isStatic) throws NoSuchMethodException, ClassNotFoundException
    {
        return getMethodCallSite(caller, invokedName, invokedType, ownerName, getWrappedType(methodType), isStatic);
    }

    public static CallSite getFieldGetterCallSite(
        MethodHandles.Lookup caller,
        String invokedName,
        MethodType invokedType,
        String ownerName) throws NoSuchFieldException, ClassNotFoundException
    {
        return new ConstantCallSite(
            findFieldGetter(
                Class.forName(ownerName, false, caller.lookupClass().getClassLoader()),
                invokedType.parameterCount() == 0, invokedName
            ).asType(invokedType));
    }

    public static CallSite getFieldSetterCallSite(
        MethodHandles.Lookup caller,
        String invokedName,
        MethodType invokedType,
        String ownerName) throws NoSuchFieldException, ClassNotFoundException
    {
        return new ConstantCallSite(
            findFieldSetter(
                Class.forName(ownerName, false, caller.lookupClass().getClassLoader()),
                invokedType.parameterCount() == 1, invokedName
            ).asType(invokedType));
    }
}
