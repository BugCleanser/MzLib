package mz.mzlib.util.compound;

import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.asm.tree.MethodNode;
import mz.mzlib.util.*;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.wrapper.WrappedClassFinder;
import mz.mzlib.util.wrapper.WrappedClassFinderClass;
import mz.mzlib.util.wrapper.WrapperClassInfo;
import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(Compound.Handler.class)
public @interface Compound
{
    class Handler implements WrappedClassFinder<Compound>
    {
        @Override
        @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, Compound annotation)
            throws ClassNotFoundException
        {
            synchronized(wrapperClass)
            {
                String className = wrapperClass.getName() + "$0mzCompoundFinal";
                try
                {
                    return Class.forName(className, true, wrapperClass.getClassLoader());
                }
                catch(ClassNotFoundException ignored)
                {
                }
                className = AsmUtil.getType(className);
                ClassNode cn = new ClassNode();
                Set<Class<?>> superclasses = getSuperclasses(wrapperClass);
                Set<Class<?>> interfaces = superclasses.stream().filter(Class::isInterface).collect(Collectors.toSet());
                superclasses = superclasses.stream().filter(i -> !i.isInterface()).collect(Collectors.toSet());
                Class<?> superclass = superclasses.stream().findFirst().orElse(Object.class);
                boolean flag = true;
                for(Class<?> i : superclasses)
                {
                    flag = true;
                    for(Class<?> j : superclasses)
                    {
                        if(!j.isAssignableFrom(i))
                        {
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                    {
                        superclass = i;
                        break;
                    }
                }
                if(!flag)
                    throw new IllegalStateException("You can only extend a class.");
                cn.visit(
                    Opcodes.V1_8, Opcodes.ACC_PUBLIC, className, null, AsmUtil.getType(superclass),
                    interfaces.stream().map(AsmUtil::getType).toArray(String[]::new)
                );
                PriorityQueue<Pair<DelegateField, Class<?>>> delegates = new PriorityQueue<>(
                    Pair.comparingByFirst(Comparator.comparing(DelegateField::priority)));
                for(Method method : wrapperClass.getMethods())
                {
                    if(Modifier.isStatic(method.getModifiers()) || !ElementSwitcher.isEnabled(method))
                        continue;
                    for(PropAccessor anno : Option.fromNullable(method.getDeclaredAnnotation(PropAccessor.class)))
                    {
                        if(AsmUtil.getFieldNode(cn, anno.value()) != null)
                            continue;
                        Class<?> type;
                        switch(method.getParameterCount())
                        {
                            case 0:
                                type = method.getReturnType();
                                break;
                            case 1:
                                type = method.getParameterTypes()[0];
                                break;
                            default:
                                throw new IllegalArgumentException("Too many args of " + method + ".");
                        }
                        if(WrapperObject.class.isAssignableFrom(type))
                            type = WrapperObject.getWrappedClass(RuntimeUtil.cast(type));
                        cn.visitField(Opcodes.ACC_PUBLIC, anno.value(), AsmUtil.getDesc(type), null, null)
                            .visitEnd();
                    }
                    for(CompoundSuper anno : Option.fromNullable(method.getDeclaredAnnotation(CompoundSuper.class)))
                    {
                        Method wrapper;
                        try
                        {
                            wrapper = anno.parent().getMethod(anno.method(), method.getParameterTypes());
                        }
                        catch(NoSuchMethodException e)
                        {
                            throw new IllegalStateException("Wrapper method not found: " + method);
                        }
                        if(!ElementSwitcher.isEnabled(wrapper))
                            continue;
                        Method tar = Objects.requireNonNull(
                            (Method) WrapperClassInfo.get(anno.parent()).getWrappedMembers().get(wrapper),
                            "Wrapped method of " + wrapper
                        );
                        MethodNode mn = new MethodNode(
                            Opcodes.ACC_PUBLIC, CompoundSuper.Handler.getInnerMethodName(anno),
                            AsmUtil.getDesc(tar), null, null
                        );
                        Class<?>[] tarParams = tar.getParameterTypes();
                        mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
                        for(int i = 0, j = 1; i < tarParams.length; i++)
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(tarParams[i], j));
                            j += AsmUtil.getCategory(tarParams[i]);
                        }
                        mn.visitMethodInsn(
                            Opcodes.INVOKESPECIAL, AsmUtil.getType(tar.getDeclaringClass()), tar.getName(),
                            AsmUtil.getDesc(tar), Modifier.isInterface(tar.getDeclaringClass().getModifiers())
                        );
                        mn.instructions.add(AsmUtil.insnReturn(tar.getReturnType()));
                        mn.visitEnd();
                        cn.methods.add(mn);
                    }
                    for(CompoundOverride compoundOverride : Option.fromNullable(
                        method.getDeclaredAnnotation(CompoundOverride.class)))
                    {
                        Method wrapper;
                        try
                        {
                            wrapper = compoundOverride.parent()
                                .getMethod(compoundOverride.method(), method.getParameterTypes());
                        }
                        catch(NoSuchMethodException e)
                        {
                            throw new IllegalStateException("Wrapper method not found: " + method);
                        }
                        if(!ElementSwitcher.isEnabled(wrapper))
                            continue;
                        wrapper = Objects.requireNonNull(
                            (Method) WrapperClassInfo.get(RuntimeUtil.cast(wrapper.getDeclaringClass()))
                                .getWrappedMembers().get(wrapper), "Wrapped method of " + wrapper
                        );
                        MethodNode mn = new MethodNode(
                            Opcodes.ACC_PUBLIC, wrapper.getName(), AsmUtil.getDesc(wrapper), null, null);
                        mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
                        mn.instructions.add(AsmUtil.insnCreateWrapper(wrapperClass));
                        Class<?>[] tarParams = wrapper.getParameterTypes(), srcParams = method.getParameterTypes();
                        for(int i = 0, j = 1; i < tarParams.length; i++)
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(tarParams[i], j));
                            if(WrapperObject.class.isAssignableFrom(srcParams[i]))
                            {
                                mn.instructions.add(AsmUtil.insnCast(Object.class, tarParams[i]));
                                mn.instructions.add(AsmUtil.insnCreateWrapper(
                                    RuntimeUtil.<Class<? extends WrapperObject>>cast(srcParams[i])));
                            }
                            else
                                mn.instructions.add(AsmUtil.insnCast(srcParams[i], tarParams[i]));
                            j += AsmUtil.getCategory(tarParams[i]);
                        }
                        mn.visitMethodInsn(
                            Opcodes.INVOKEINTERFACE, AsmUtil.getType(wrapperClass), method.getName(),
                            AsmUtil.getDesc(method), true
                        );
                        if(WrapperObject.class.isAssignableFrom(method.getReturnType()))
                        {
                            mn.instructions.add(AsmUtil.insnGetWrapped());
                            mn.instructions.add(AsmUtil.insnCast(wrapper.getReturnType(), Object.class));
                        }
                        else
                            mn.instructions.add(AsmUtil.insnCast(wrapper.getReturnType(), method.getReturnType()));
                        mn.instructions.add(AsmUtil.insnReturn(wrapper.getReturnType()));
                        mn.visitEnd();
                        cn.methods.add(mn);
                    }
                    for(DelegateField anno : Option.fromNullable(method.getDeclaredAnnotation(DelegateField.class)))
                    {
                        Class<?>[] pts = method.getParameterTypes();
                        if(pts.length != 1)
                            throw new IllegalArgumentException("Method @DelegateField must be a setter: " + method);
                        if(!WrapperObject.class.isAssignableFrom(pts[0]))
                            throw new IllegalArgumentException(
                                "Type of delegate field must be child of WrapperObject: " + method);
                        Class<?> raw = WrapperObject.getWrappedClass(RuntimeUtil.cast(pts[0]));
                        cn.visitField(Opcodes.ACC_PUBLIC, anno.value(), AsmUtil.getDesc(raw), null, null).visitEnd();
                        delegates.offer(Pair.of(anno, raw));
                    }
                }
                for(Constructor<?> constructor : superclass.getDeclaredConstructors())
                {
                    if(Modifier.isPrivate(constructor.getModifiers()))
                        continue;
                    MethodNode mn = new MethodNode(
                        Opcodes.ACC_PUBLIC, "<init>", AsmUtil.getDesc(constructor), null, null);
                    mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
                    int i = 1;
                    for(Class<?> param : constructor.getParameterTypes())
                    {
                        mn.instructions.add(AsmUtil.insnVarLoad(param, i));
                        i += AsmUtil.getCategory(param);
                    }
                    mn.visitMethodInsn(
                        Opcodes.INVOKESPECIAL, AsmUtil.getType(superclass), "<init>", AsmUtil.getDesc(constructor),
                        false
                    );
                    mn.instructions.add(AsmUtil.insnReturn(void.class));
                    mn.visitEnd();
                    cn.methods.add(mn);
                }
                for(Pair<DelegateField, Class<?>> delegate : delegates)
                {
                    for(Method method : delegate.getSecond().getMethods())
                    {
                        String desc = AsmUtil.getDesc(method);
                        if(Modifier.isStatic(method.getModifiers()) || Modifier.isPrivate(method.getModifiers()) ||
                            Modifier.isFinal(method.getModifiers()) || cn.methods.stream()
                            .anyMatch(it -> Objects.equals(desc, it.desc) && Objects.equals(method.getName(), it.name)))
                            continue;
                        MethodNode mn = new MethodNode(
                            Opcodes.ACC_PUBLIC, method.getName(), AsmUtil.getDesc(method), null, new String[0]);
                        mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
                        mn.visitFieldInsn(
                            Opcodes.GETFIELD, className, delegate.getFirst().value(),
                            AsmUtil.getDesc(delegate.getSecond())
                        );
                        int i = 1;
                        for(Class<?> param : method.getParameterTypes())
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(param, i));
                            i += AsmUtil.getCategory(param);
                        }
                        boolean isInterface = Modifier.isInterface(delegate.getSecond().getModifiers());
                        mn.visitMethodInsn(
                            isInterface ? Opcodes.INVOKEINTERFACE : Opcodes.INVOKEVIRTUAL,
                            AsmUtil.getType(delegate.getSecond()), method.getName(), AsmUtil.getDesc(method),
                            isInterface
                        );
                        mn.instructions.add(AsmUtil.insnReturn(method.getReturnType()));
                        mn.visitEnd();
                        cn.methods.add(mn);
                    }
                }
                //noinspection deprecation legacy
                if(Delegator.class.isAssignableFrom(wrapperClass))
                {
                    String delegateField = "mzlib$Delegate";
                    cn.visitField(Opcodes.ACC_PUBLIC, delegateField, AsmUtil.getDesc(Object.class), null, null)
                        .visitEnd();
                    MethodNode mn = new MethodNode(
                        Opcodes.ACC_PUBLIC, "getDelegate", AsmUtil.getDesc(Object.class, new Class[0]), null,
                        new String[0]
                    );
                    mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
                    mn.visitFieldInsn(Opcodes.GETFIELD, className, delegateField, AsmUtil.getDesc(Object.class));
                    mn.instructions.add(AsmUtil.insnReturn(Object.class));
                    mn.visitEnd();
                    cn.methods.add(mn);
                    mn = new MethodNode(
                        Opcodes.ACC_PUBLIC, "setDelegate", AsmUtil.getDesc(void.class, Object.class), null,
                        new String[0]
                    );
                    mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
                    mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 1));
                    mn.visitFieldInsn(Opcodes.PUTFIELD, className, delegateField, AsmUtil.getDesc(Object.class));
                    mn.instructions.add(AsmUtil.insnReturn(void.class));
                    mn.visitEnd();
                    cn.methods.add(mn);
                    for(Method method : CollectionUtil.addAll(new HashSet<>(interfaces), superclass).stream()
                        .map(Class::getMethods)
                        .flatMap(Arrays::stream)
                        .collect(Collectors.toSet()))
                    {
                        String desc = AsmUtil.getDesc(method);
                        if(Modifier.isStatic(method.getModifiers()) || Modifier.isPrivate(method.getModifiers()) ||
                            Modifier.isFinal(method.getModifiers()) || cn.methods.stream()
                            .anyMatch(it -> Objects.equals(desc, it.desc) && Objects.equals(method.getName(), it.name)))
                            continue;
                        mn = new MethodNode(
                            Opcodes.ACC_PUBLIC, method.getName(), AsmUtil.getDesc(method), null, new String[0]);
                        mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
                        mn.visitFieldInsn(Opcodes.GETFIELD, className, delegateField, AsmUtil.getDesc(Object.class));
                        mn.instructions.add(AsmUtil.insnCast(method.getDeclaringClass(), Object.class));
                        int i = 1;
                        for(Class<?> param : method.getParameterTypes())
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(param, i));
                            i += AsmUtil.getCategory(param);
                        }
                        mn.visitMethodInsn(
                            Modifier.isInterface(method.getDeclaringClass().getModifiers()) ? Opcodes.INVOKEINTERFACE :
                                Opcodes.INVOKEVIRTUAL, AsmUtil.getType(method.getDeclaringClass()), method.getName(),
                            AsmUtil.getDesc(method), Modifier.isInterface(method.getDeclaringClass().getModifiers())
                        );
                        mn.instructions.add(AsmUtil.insnReturn(method.getReturnType()));
                        mn.visitEnd();
                        cn.methods.add(mn);
                    }
                }
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
                cn.accept(cw);
                return ClassUtil.defineClass(wrapperClass.getClassLoader(), className, cw.toByteArray());
            }
        }

        public Set<Class<?>> getSuperclasses(Class<? extends WrapperObject> compoundClass)
        {
            if(!compoundClass.isAnnotationPresent(Compound.class))
                return Collections.singleton(WrapperObject.getWrappedClass(compoundClass));
            Set<Class<?>> result = new HashSet<>();
            for(Class<?> i : compoundClass.getInterfaces())
            {
                if(WrapperObject.class.isAssignableFrom(i))
                    result.addAll(this.getSuperclasses(RuntimeUtil.cast(i)));
            }
            return result;
        }
    }
}
