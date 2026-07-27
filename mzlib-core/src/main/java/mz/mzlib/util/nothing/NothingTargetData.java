package mz.mzlib.util.nothing;

import mz.mzlib.asm.*;
import mz.mzlib.asm.tree.*;
import mz.mzlib.util.*;
import mz.mzlib.util.adapter.Adapter;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.wrapper.WrapperClassData;
import mz.mzlib.util.wrapper.WrapperObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

@ApiStatus.Internal
public class NothingTargetData
{
    public final Class<?> target;
    Map<Object, Collection<Element>> nothings;
    byte[] rawByteCode;
    final String metafactoryName;
    
    public NothingTargetData(Class<?> target)
    {
        this.target = target;
        this.nothings = new HashMap<>();
        this.rawByteCode = ClassUtil.getByteCode(target);
        this.metafactoryName = AsmUtil.getType(this.target) + "$mzlib@NothingMetafactory";
    }
    
    public synchronized void add(Class<? extends Nothing> nothing)
    {
        if(this.nothings.containsKey(nothing))
            throw new IllegalStateException("Duplicately adding Nothing class: " + nothing);
        try
        {
            this.nothings.put(nothing, Element.ofNothingClass(nothing));
        }
        catch(NoSuchMethodException e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
        this.apply();
    }
    
    public synchronized void remove(Class<? extends Nothing> nothing)
    {
        if(this.nothings.remove(nothing) == null)
            throw new IllegalStateException("Removing the unadded Nothing class: " + nothing);
        this.apply();
    }
    
    public boolean isEmpty()
    {
        return this.nothings.isEmpty();
    }
    
    @Nullable List<CallSite> callSites;
    int version;
    
    @SuppressWarnings("unused")
    private static CallSite metafactory$empty(MethodHandles.Lookup lookup, String name, MethodType type, int version, int index)
    {
        Class<?> ret = type.returnType();
        Object value;
        if(ret == void.class)
            return new ConstantCallSite(MethodHandles.dropArguments(MethodHandles.constant(Void.class, null).asType(MethodType.methodType(void.class)), 0, type.parameterArray()));
        if(ret == boolean.class)
            value = false;
        else if(ret.isPrimitive())
            throw new IllegalArgumentException(type.toString());
        else
            value = null;
        return new ConstantCallSite(MethodHandles.dropArguments(MethodHandles.constant(ret, value), 0, type.parameterArray()));
    }
    @SuppressWarnings("unused")
    synchronized CallSite metafactory(MethodHandles.Lookup lookup, String name, MethodType type, int version, int index)
    {
        if(this.callSites == null || version != this.version)
            return metafactory$empty(lookup, name, type, version, index);
        return this.callSites.get(index);
    }
    
    static MethodHandle mh$metafactory;
    static
    {
        try
        {
            mh$metafactory = ClassUtil.findMethod(NothingTargetData.class, false, "metafactory", CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, int.class, int.class);
        }
        catch(NoSuchMethodException e)
        {
            throw new AssertionError(e);
        }
    }
    
    AbstractInsnNode insnInvokeDynamic(int index, String desc, String name)
    {
        return new InvokeDynamicInsnNode(name, desc, new Handle(Opcodes.H_INVOKESTATIC, this.metafactoryName, "metafactory", AsmUtil.getDesc(mh$metafactory.type().dropParameterTypes(0, 1)), false), this.version, index);
    }
    AbstractInsnNode insnInvokeDynamic(int index, String desc)
    {
        return this.insnInvokeDynamic(index, desc, "no_name");
    }
    
    AbstractInsnNode insnInvokeDynamic(MethodHandle handle, String name)
    {
        AbstractInsnNode result = insnInvokeDynamic(Objects.requireNonNull(this.callSites).size(), AsmUtil.getDesc(handle.type()), name);
        this.callSites.add(new ConstantCallSite(handle));
        return result;
    }
    
    static class Element implements Comparable<Element>
    {
        MethodKey target;
        NothingInjectType type;
        float priority;
        MethodHandle[] locator;
        AnnotatedElement[] parameters;
        AnnotatedType[] parameterTypes;
        AnnotatedType returnType;
        @Nullable MethodHandle thisWrapper;
        MethodHandle handler;
        
        @TestOnly
        @SuppressWarnings("DataFlowIssue")
        Element()
        {
            target = null;
            type = null;
            locator = null;
            parameters = null;
            parameterTypes = null;
            returnType = null;
            handler = null;
        }
        
        Element(Method method, NothingInject annot) throws NoSuchMethodException
        {
            Class<? extends WrapperObject> owner = RuntimeUtil.castClass(method.getDeclaringClass());
            this.target = getTarget(owner, annot);
            this.type = annot.type();
            this.priority = annot.priority();
            this.locator = getLocator(annot).stream().map(name ->
            {
                try
                {
                    Method m = owner.getMethod(name, NothingInjectLocating.class);
                    if(Modifier.isStatic(m.getModifiers()))
                        return ClassUtil.unreflect(m);
                    else
                        return ClassUtil.unreflect(m).bindTo(WrapperObject.create(owner, null));
                }
                catch(NoSuchMethodException e)
                {
                    try
                    {
                        Method m = NothingInjectLocating.class.getMethod(name);
                        if(Modifier.isStatic(m.getModifiers()))
                            throw new NoSuchMethodException(name);
                        return ClassUtil.unreflect(m);
                    }
                    catch(NoSuchMethodException e1)
                    {
                        throw RuntimeUtil.sneakilyThrow(new NoSuchMethodException("Locator not found: " + name).initCause(e));
                    }
                }
            }).toArray(MethodHandle[]::new);
            RuntimeUtil.<NoSuchMethodException>declareThrowing();
            this.parameters = Arrays.stream(method.getParameters()).toArray(AnnotatedElement[]::new);
            this.parameterTypes = method.getAnnotatedParameterTypes();
            this.returnType = method.getAnnotatedReturnType();
            if(Modifier.isStatic(method.getModifiers()))
                this.thisWrapper = null;
            else
                this.thisWrapper = WrapperClassData.get(owner).getConstructor();
            this.handler = ClassUtil.unreflect(method).asFixedArity();
        }
        
        private static MethodKey getTarget(Class<? extends WrapperObject> owner, NothingInject annot) throws NoSuchMethodException
        {
            String targetName = annot.name();
            if(targetName.isEmpty())
            {
                //noinspection deprecation
                targetName = annot.wrapperMethodName();
                if(targetName.isEmpty())
                    throw new IllegalArgumentException("name is unset");
                if(targetName.equals("<init>"))
                {
                    new UnsupportedOperationException("\"<init>\" will be no longer supported.").printStackTrace(System.err);
                    try
                    {
                        //noinspection deprecation, unchecked
                        return MethodKey.of(WrapperClassData.get(owner).getWrappedClass().getDeclaredConstructor(Arrays.stream(annot.wrapperMethodParams()).map(type ->
                                WrapperObject.class.isAssignableFrom(type) ? WrapperClassData.get((Class<? extends WrapperObject>) type).getWrappedClass() : type).toArray(Class[]::new)));
                    }
                    catch(NoSuchMethodException e)
                    {
                        throw (NoSuchMethodException) new NoSuchMethodException("wrapper not found").initCause(e);
                    }
                }
            }
            Class<?>[] targetParams = annot.params();
            if(targetParams.length == 1 && targetParams[0] == void.class)
            {
                //noinspection deprecation
                targetParams = annot.wrapperMethodParams();
            }
            Method wrapper;
            if(targetParams.length == 1 && targetParams[0] == void.class) // unset
            {
                String finalTargetName = targetName;
                List<Method> found = Arrays.stream(owner.getMethods()).filter(m -> m.getName().equals(finalTargetName)).collect(Collectors.toList());
                if(found.isEmpty())
                    throw new NoSuchMethodException("wrapper not found");
                else if(found.size() > 1)
                    throw new NoSuchMethodException("Too many matching methods: " + found);
                wrapper = found.get(0);
            }
            else
            {
                try
                {
                    wrapper = owner.getMethod(targetName, targetParams);
                }
                catch(NoSuchMethodException e)
                {
                    throw (NoSuchMethodException) new NoSuchMethodException("wrapper not found").initCause(e);
                }
            }
            WrapperClassData.MemberData target;
            if(!WrapperObject.class.isAssignableFrom(wrapper.getDeclaringClass()))
                target = null;
            else
            {
                //noinspection unchecked
                target = WrapperClassData.get((Class<? extends WrapperObject>) wrapper.getDeclaringClass()).getMember(wrapper);
                if(target != null && !target.isInherited() && target.getTarget().getDeclaringClass() != WrapperClassData.get(owner).getWrappedClass())
                    throw new NoSuchMethodException("Target is not owned by this");
            }
            if(target == null)
                throw new NoSuchMethodException("Method is not wrapper: " + wrapper);
            if(!(target.getTarget() instanceof Executable))
                throw new NoSuchMethodException("Method is not executable wrapper (is field)");
            return MethodKey.of((Executable) target.getTarget());
        }
        
        private static List<String> getLocator(NothingInject annot)
        {
            List<String> locator = Arrays.asList(annot.locator());
            if(locator.isEmpty()) // legacy
            {
                locator = new ArrayList<>();
                //noinspection deprecation
                locator.add(annot.locateMethod());
                if(locator.get(0).equals("<unset>"))
                    throw new IllegalArgumentException("locator is unset");
                //noinspection deprecation
                locator.add(annot.locateMethodEnd());
                if(locator.get(1).equals("<unset>"))
                    locator.remove(1);
                //noinspection DanglingJavadoc
                {
                    /**
                     * @see NothingInjectLocating#stay()
                     */
                    locator = locator.stream().map(it -> it.isEmpty() ? "stay" : it).collect(Collectors.toList());
                }
            }
            return locator;
        }
        
        static Collection<Element> ofNothingClass(Class<? extends Nothing> nothing) throws NoSuchMethodException
        {
            Collection<Element> result = new ArrayList<>();
            for(Method method: nothing.getDeclaredMethods())
            {
                if(!ElementSwitcher.isEnabled(method))
                    continue;
                for(NothingInject annot: method.getDeclaredAnnotationsByType(NothingInject.class))
                {
                    result.add(new Element(method, annot));
                }
            }
            return result;
        }
        
        @Override
        public int compareTo(Element o)
        {
            return Float.compare(o.priority, this.priority);
        }
    }
    
    static ClassNode self;
    static
    {
        self = new ClassNode();
        new ClassReader(ClassUtil.getByteCode(NothingTargetData.class)).accept(self, ClassReader.SKIP_FRAMES);
    }
    public synchronized void apply()
    {
        if(this.callSites == null)
        {
            if(this.isEmpty())
                return;
            MethodHandle methodHandle = mh$metafactory.bindTo(this);
            ClassNode cn = new ClassNode();
            cn.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, this.metafactoryName, null, AsmUtil.getType(Object.class), new String[0]);
            cn.visitField(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "version", AsmUtil.getDesc(int.class), null, 0).visitEnd();
            cn.visitField(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "handle", AsmUtil.getDesc(MethodHandle.class), null, null).visitEnd();
            
            MethodVisitor method = cn.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "metafactory", AsmUtil.getDesc(methodHandle.type()), null, new String[0]);
            method.visitFieldInsn(Opcodes.GETSTATIC, this.metafactoryName, "handle", AsmUtil.getDesc(MethodHandle.class));
            AsmUtil.insnDup(MethodHandle.class).accept(method); // dup
            Label label = new Label();
            method.visitJumpInsn(Opcodes.IFNONNULL, label);
            Objects.requireNonNull(AsmUtil.getMethodNode(self, "metafactory$empty",
                    AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, int.class, int.class))
            ).instructions.accept(method);
            method.visitLabel(label);
            Class<?>[] params = methodHandle.type().parameterArray();
            for(int i = 0; i < params.length; i++)
            {
                AsmUtil.insnVarLoad(params[i], i).accept(method);
            }
            method.visitMethodInsn(Opcodes.INVOKEVIRTUAL, AsmUtil.getType(MethodHandle.class), "invokeExact", AsmUtil.getDesc(methodHandle.type()), false);
            AsmUtil.insnReturn(methodHandle.type().returnType()).accept(method);
            method.visitEnd();
            
            cn.visitEnd();
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            cn.accept(cw);
            Class<?> classMetafactory = ClassUtil.defineClass(this.target.getClassLoader(), this.metafactoryName, cw.toByteArray());
            try
            {
                classMetafactory.getDeclaredField("handle").set(null, methodHandle);
            }
            catch(IllegalAccessException | NoSuchFieldException e)
            {
                throw new AssertionError(e);
            }
        }
        Class<?> classMetafactory;
        try
        {
            classMetafactory = Class.forName(this.metafactoryName.replace('/', '.'), false, this.target.getClassLoader());
        }
        catch(ClassNotFoundException e)
        {
            throw new AssertionError(e);
        }
        if(this.isEmpty()) // remove ref
        {
            ClassUtil.defineClass(target.getClassLoader(), AsmUtil.getType(target), this.rawByteCode);
            this.callSites = null;
            try
            {
                classMetafactory.getDeclaredField("handle").set(null, null);
            }
            catch(IllegalAccessException | NoSuchFieldException e)
            {
                throw new AssertionError(e);
            }
            return;
        }
        this.callSites = new ArrayList<>();
        try
        {
            Field fieldVersion = classMetafactory.getField("version");
            this.version = (int) fieldVersion.get(null) + 1;
            fieldVersion.set(null, this.version);
        }
        catch(IllegalAccessException | NoSuchFieldException e)
        {
            throw new AssertionError(e);
        }
        
        int indy$Box$of;
        int indy$Box$get;
        int indy$WrapperObject$getWrapped; // legacy
        MethodHandle mh$Adapter$Processor$adapt;
        MethodHandle mh$Adapter$Processor$revert;
        try
        {
            indy$Box$of = this.callSites.size();
            this.callSites.add(new ConstantCallSite(ClassUtil.findMethod(Box.class, true, "of", Box.Mut.class, Object.class).asType(MethodType.methodType(Object.class, Object.class))));
            indy$Box$get = this.callSites.size();
            this.callSites.add(new ConstantCallSite(ClassUtil.findMethod(Box.class, false, "get", Object.class).asType(MethodType.methodType(Object.class, Object.class))));
            indy$WrapperObject$getWrapped = this.callSites.size();
            this.callSites.add(new ConstantCallSite(ClassUtil.findMethod(WrapperObject.class, false, "getWrapped", Object.class).asType(MethodType.methodType(Object.class, Object.class))));
            
            mh$Adapter$Processor$adapt = ClassUtil.findMethod(Adapter.Processor.class, false, "adapt", Object.class, Object.class);
            mh$Adapter$Processor$revert = ClassUtil.findMethod(Adapter.Processor.class, false, "revert", Object.class, Object.class);
        }
        catch(Throwable e)
        {
            throw new AssertionError(e);
        }
        ClassNode cn = new ClassNode();
        new ClassReader(this.rawByteCode).accept(cn, 0);
        cn.version = Math.max(cn.version, Opcodes.V1_8);
        Map<MethodNode, List<AbstractInsnNode>> raws = new HashMap<>();
        for(MethodNode m: cn.methods)
        {
            raws.put(m, new ListIndexed<>(Arrays.asList(m.instructions.toArray())));
        }
        Map<MethodNode, PriorityQueue<Element>> elements = new HashMap<>();
        for(Element i: CollectionUtil.asIterable(this.nothings.values().stream().flatMap(Collection::stream).iterator()))
        {
            MethodNode mn = AsmUtil.getMethodNode(cn, i.target);
            if(mn == null)
                throw new IllegalStateException(new NoSuchMethodException(i.target.toString()));
            elements.computeIfAbsent(mn, k -> new PriorityQueue<>()).add(i);
        }
        for(Map.Entry<MethodNode, PriorityQueue<Element>> entry: elements.entrySet())
        {
            MethodNode mn = entry.getKey();
            Map<String, Integer> customVars = new HashMap<>();
            while(!entry.getValue().isEmpty())
            {
                Element element = entry.getValue().poll();
                if(element.locator.length == 0)
                    throw new IllegalStateException("No locator");
                mn.maxStack = -1;
                NothingInjectLocating locating = new NothingInjectLocating(cn.name, mn, raws.get(mn));
                try
                {
                    element.locator[0].invoke(locating);
                }
                catch(Throwable e)
                {
                    throw new RuntimeException("Invoking locator", e);
                }
                if(element.type == NothingInjectType.RAW)
                {
                    if(element.thisWrapper != null)
                        throw new IllegalStateException("Raw Nothing must be static: " + element);
                    Executable target;
                    try
                    {
                        target = element.target.getDeclaredMember(this.target);
                    }
                    catch(NoSuchMethodException e)
                    {
                        throw new IllegalStateException(e);
                    }
                    try
                    {
                        element.handler.invoke(target, mn, locating);
                    }
                    catch(Throwable e)
                    {
                        throw new RuntimeException("Invoking raw handler", e);
                    }
                    continue;
                }
                // handle args
                InsnList insns = new InsnList();
                InsnList loadingVars = new InsnList(), afterCall = new InsnList(), afterPop = new InsnList();
                if(element.thisWrapper != null)
                {
                    if(Modifier.isStatic(mn.access))
                        throw new IllegalStateException("Target is static: " + element);
                    loadingVars.add(AsmUtil.insnVarLoad(Object.class, 0));
                    loadingVars.add(this.insnInvokeDynamic(element.thisWrapper.asType(MethodType.methodType(Object.class, Object.class)), "wrap"));
                }
                for(int i = 0; i < element.parameters.length; i++)
                {
                    AnnotatedType type = element.parameterTypes[i];
                    Class<?> clazz = TypeUtil.toClass(type.getType());
                    Integer localVarIndex = null;
                    LocalVar localVar = element.parameters[i].getAnnotation(LocalVar.class);
                    if(localVar != null)
                        localVarIndex = localVar.value();
                    else
                    {
                        LocalVarTagged localVarTagged = element.parameters[i].getDeclaredAnnotation(LocalVarTagged.class);
                        if(localVarTagged != null)
                            localVarIndex = locating.taggedLocalVars.get(localVarTagged.value());
                    }
                    if(localVarIndex != null)
                    {
                        if(Box.class.isAssignableFrom(clazz))
                        {
                            int store = mn.maxLocals++;
                            if(!(type instanceof AnnotatedParameterizedType))
                                throw new IllegalStateException("The Box of parameter type has no type argument" + element);
                            Adapter.Processor<?, ?> adapter = Adapter.Processor.of(((AnnotatedParameterizedType) type).getAnnotatedActualTypeArguments()[0]);
                            loadingVars.add(AsmUtil.insnVarLoad(adapter.getAdapteeClass(), localVarIndex));
                            loadingVars.add(AsmUtil.insnCast(Object.class, adapter.getAdapteeClass()));
                            if(!adapter.isIdentity())
                                loadingVars.add(this.insnInvokeDynamic(mh$Adapter$Processor$adapt.bindTo(adapter), "adapt"));
                            loadingVars.add(this.insnInvokeDynamic(indy$Box$of, AsmUtil.getDesc(Object.class, Object.class), "wrap"));
                            loadingVars.add(AsmUtil.insnDup(Object.class));
                            loadingVars.add(AsmUtil.insnVarStore(Object.class, store));
                            
                            afterCall.add(AsmUtil.insnVarLoad(Object.class, store));
                            afterCall.add(this.insnInvokeDynamic(indy$Box$get, AsmUtil.getDesc(Object.class, Object.class), "unwrap"));
                            if(!adapter.isIdentity())
                                afterCall.add(this.insnInvokeDynamic(mh$Adapter$Processor$revert.bindTo(adapter), "revert"));
                            afterCall.add(AsmUtil.insnCast(adapter.getAdapteeClass(), Object.class));
                            afterCall.add(AsmUtil.insnVarStore(adapter.getAdapteeClass(), localVarIndex));
                        }
                        else if(WrapperObject.class.isAssignableFrom(clazz)) // legacy
                        {
                            int store = mn.maxLocals++;
                            @SuppressWarnings("unchecked")
                            WrapperClassData data = WrapperClassData.get((Class<? extends WrapperObject>) clazz);
                            loadingVars.add(AsmUtil.insnVarLoad(data.getWrappedClass(), localVarIndex));
                            loadingVars.add(this.insnInvokeDynamic(data.getConstructor().asType(MethodType.methodType(Object.class, data.getWrappedClass())), "wrap"));
                            loadingVars.add(AsmUtil.insnDup(Object.class));
                            loadingVars.add(AsmUtil.insnVarStore(Object.class, store));
                            
                            afterCall.add(AsmUtil.insnVarLoad(Object.class, store));
                            afterCall.add(this.insnInvokeDynamic(indy$WrapperObject$getWrapped, AsmUtil.getDesc(Object.class, Object.class), "unwrap"));
                            afterCall.add(AsmUtil.insnCast(data.getWrappedClass(), Object.class));
                            afterCall.add(AsmUtil.insnVarStore(data.getWrappedClass(), localVarIndex));
                        }
                        else // legacy raw
                        {
                            loadingVars.add(AsmUtil.insnVarLoad(clazz, localVarIndex));
                            loadingVars.add(AsmUtil.insnCast(Object.class, clazz));
                        }
                        continue;
                    }
                    CustomVar customVar = element.parameters[i].getAnnotation(CustomVar.class);
                    if(customVar != null)
                    {
                        Integer index = customVars.get(customVar.value());
                        if(index == null)
                        {
                            index = mn.maxLocals++;
                            customVars.put(customVar.value(), index);
                            InsnList init = new InsnList();
                            init.add(AsmUtil.insnConst(null));
                            init.add(this.insnInvokeDynamic(indy$Box$of, AsmUtil.getDesc(Object.class, Object.class), "wrap"));
                            init.add(AsmUtil.insnVarStore(Object.class, index));
                            mn.instructions.insert(init);
                        }
                        if(Box.class.isAssignableFrom(clazz))
                            loadingVars.add(AsmUtil.insnVarLoad(Object.class, index));
                        else if(WrapperObject.class.isAssignableFrom(clazz)) // legacy
                        {
                            @SuppressWarnings("unchecked")
                            WrapperClassData data = WrapperClassData.get((Class<? extends WrapperObject>) clazz);
                            loadingVars.add(AsmUtil.insnVarLoad(Object.class, index));
                            loadingVars.add(this.insnInvokeDynamic(indy$Box$get, AsmUtil.getDesc(Object.class, Object.class), "unwrap"));
                            loadingVars.add(this.insnInvokeDynamic(data.getConstructor().asType(MethodType.methodType(Object.class, Object.class)), "wrap"));
                            loadingVars.add(AsmUtil.insnDup(Object.class));
                            loadingVars.add(AsmUtil.insnVarStore(Object.class, index));
                            
                            afterCall.add(AsmUtil.insnVarLoad(Object.class, index));
                            afterCall.add(this.insnInvokeDynamic(indy$WrapperObject$getWrapped, AsmUtil.getDesc(Object.class, Object.class), "unwrap"));
                            afterCall.add(this.insnInvokeDynamic(indy$Box$of, AsmUtil.getDesc(Object.class, Object.class), "wrap"));
                            afterCall.add(AsmUtil.insnVarStore(Object.class, index));
                        }
                        else // legacy raw
                        {
                            loadingVars.add(AsmUtil.insnVarLoad(Object.class, index));
                            loadingVars.add(this.insnInvokeDynamic(indy$Box$get, AsmUtil.getDesc(Object.class, Object.class), "unwrap"));
                        }
                        continue;
                    }
                    StackTop stackTop = element.parameters[i].getAnnotation(StackTop.class);
                    if(stackTop != null)
                    {
                        InsnList back = new InsnList();
                        if(Box.class.isAssignableFrom(clazz))
                        {
                            int store = mn.maxLocals++;
                            if(!(type instanceof AnnotatedParameterizedType))
                                throw new IllegalStateException("The Box of parameter type has no type argument" + element);
                            Adapter.Processor<?, ?> adapter = Adapter.Processor.of(((AnnotatedParameterizedType) type).getAnnotatedActualTypeArguments()[0]);
                            insns.add(AsmUtil.insnCast(Object.class, adapter.getAdapteeClass()));
                            if(!adapter.isIdentity())
                                insns.add(this.insnInvokeDynamic(mh$Adapter$Processor$adapt.bindTo(adapter), "adapt"));
                            insns.add(this.insnInvokeDynamic(indy$Box$of, AsmUtil.getDesc(Object.class, Object.class), "wrap"));
                            //noinspection DuplicatedCode
                            insns.add(AsmUtil.insnVarStore(Object.class, store));
                            
                            loadingVars.add(AsmUtil.insnVarLoad(Object.class, store));
                            
                            back.add(AsmUtil.insnVarLoad(Object.class, store));
                            back.add(this.insnInvokeDynamic(indy$Box$get, AsmUtil.getDesc(Object.class, Object.class), "unwrap"));
                            if(!adapter.isIdentity())
                                back.add(this.insnInvokeDynamic(mh$Adapter$Processor$revert.bindTo(adapter), "revert"));
                            back.add(AsmUtil.insnCast(adapter.getAdapteeClass(), Object.class));
                        }
                        else if(WrapperObject.class.isAssignableFrom(clazz)) // legacy
                        {
                            int store = mn.maxLocals++;
                            @SuppressWarnings("unchecked")
                            WrapperClassData data = WrapperClassData.get((Class<? extends WrapperObject>) clazz);
                            insns.add(this.insnInvokeDynamic(data.getConstructor().asType(MethodType.methodType(Object.class, data.getWrappedClass())), "wrap"));
                            //noinspection DuplicatedCode
                            insns.add(AsmUtil.insnVarStore(Object.class, store));
                            
                            loadingVars.add(AsmUtil.insnVarLoad(Object.class, store));
                            
                            back.add(AsmUtil.insnVarLoad(Object.class, store));
                            back.add(this.insnInvokeDynamic(indy$WrapperObject$getWrapped, AsmUtil.getDesc(Object.class, Object.class), "unwrap"));
                            back.add(AsmUtil.insnCast(data.getWrappedClass(), Object.class));
                        }
                        else // legacy raw
                        {
                            int store = mn.maxLocals;
                            mn.maxLocals += AsmUtil.getSize(clazz);
                            insns.add(AsmUtil.insnVarStore(clazz, store));
                            
                            loadingVars.add(AsmUtil.insnVarLoad(clazz, store));
                            loadingVars.add(AsmUtil.insnCast(Object.class, clazz));
                            
                            back.add(AsmUtil.insnVarLoad(clazz, store));
                        }
                        afterPop.insert(back);
                        continue;
                    }
                    throw new IllegalStateException("Unknown parameter: " + element.parameters[i]);
                }
                insns.add(loadingVars);
                Class<?> rt = TypeUtil.toClass(element.returnType.getType());
                if(element.type != NothingInjectType.BRTRUE)
                {
                    insns.add(this.insnInvokeDynamic(element.handler.asType(element.handler.type().generic().changeReturnType(rt == void.class ? void.class : Object.class)), "handle"));
                    insns.add(afterCall);
                    if(Box.class.isAssignableFrom(rt))
                    {
                        if(!(element.returnType instanceof AnnotatedParameterizedType))
                            throw new IllegalStateException("The Box of parameter type has no type argument" + element);
                        Adapter.Processor<?, ?> adapter = Adapter.Processor.of(((AnnotatedParameterizedType) element.returnType).getAnnotatedActualTypeArguments()[0]);
                        insns.add(AsmUtil.insnDup(Object.class));
                        LabelNode label = new LabelNode();
                        insns.add(new JumpInsnNode(Opcodes.IFNULL, label));
                        insns.add(this.insnInvokeDynamic(indy$Box$get, AsmUtil.getDesc(Object.class, Object.class), "unwrap"));
                        if(!adapter.isIdentity())
                            insns.add(this.insnInvokeDynamic(mh$Adapter$Processor$revert.bindTo(adapter), "revert"));
                        insns.add(AsmUtil.insnCast(adapter.getAdapteeClass(), Object.class));
                        insns.add(AsmUtil.insnReturn(adapter.getAdapteeClass()));
                        insns.add(label);
                        insns.add(AsmUtil.insnPop(Object.class));
                    }
                    else if(WrapperObject.class.isAssignableFrom(rt)) // legacy
                    {
                        @SuppressWarnings("unchecked")
                        WrapperClassData data = WrapperClassData.get((Class<? extends WrapperObject>) rt);
                        insns.add(AsmUtil.insnDup(Object.class));
                        LabelNode label = new LabelNode();
                        insns.add(new JumpInsnNode(Opcodes.IFNULL, label));
                        insns.add(this.insnInvokeDynamic(indy$WrapperObject$getWrapped, AsmUtil.getDesc(Object.class, Object.class), "unwrap"));
                        insns.add(AsmUtil.insnCast(data.getWrappedClass(), Object.class));
                        insns.add(AsmUtil.insnReturn(data.getWrappedClass()));
                        insns.add(label);
                        insns.add(AsmUtil.insnPop(Object.class));
                    }
                    else if(rt != void.class)
                        throw new IllegalStateException(element.toString());
                    insns.add(afterPop);
                }
                NothingInjectLocating locating1;
                switch(element.type)
                {
                    case INSERT_BEFORE:
                        if(element.locator.length != 1)
                            throw new IllegalStateException("Too many locators");
                        for(int location: locating.locations)
                        {
                            mn.instructions.insertBefore(locating.getInsnNode(location), AsmUtil.clone(insns));
                        }
                        break;
                    case BRTRUE:
                        // TODO: check stack
                        if(element.locator.length != 2)
                            throw new IllegalStateException("number of locators");
                        mn.maxStack = -1;
                        locating1 = new NothingInjectLocating(cn.name, mn, raws.get(mn));
                        try
                        {
                            element.locator[1].invoke(locating1);
                        }
                        catch(Throwable e)
                        {
                            throw new RuntimeException("Invoking locator", e);
                        }
                        if(locating1.getLocations().size() != 1)
                            throw new IllegalStateException("number of locations: " + locating1.getLocations());
                        if(rt != boolean.class && rt != void.class)
                            throw new IllegalStateException("return type of BrTrue must be boolean or void");
                        insns.add(this.insnInvokeDynamic(element.handler.asType(element.handler.type().generic().changeReturnType(rt)), "handle"));
                        insns.add(afterCall);
                        LabelNode l1 = new LabelNode(), l2 = new LabelNode();
                        InsnList insns1 = new InsnList();
                        insns1.add(new JumpInsnNode(Opcodes.GOTO, l2));
                        insns1.add(l1);
                        insns1.add(AsmUtil.clone(afterPop));
                        insns1.add(l2);
                        mn.instructions.insertBefore(locating1.getInsnNode(locating1.getLocations().iterator().next()), insns1);
                        for(int location: locating.locations)
                        {
                            InsnList cl = AsmUtil.clone(insns);
                            if(rt == boolean.class)
                            {
                                cl.add(new JumpInsnNode(Opcodes.IFNE, l1));
                                cl.add(AsmUtil.clone(afterPop));
                            }
                            else
                                cl.add(new JumpInsnNode(Opcodes.GOTO, l1));
                            mn.instructions.insertBefore(locating.getInsnNode(location), cl);
                        }
                        break;
                    case CATCH:
                        // TODO: check stack
                        if(element.locator.length != 2)
                            throw new IllegalStateException("number of locators");
                        if(locating.getLocations().size() != 1)
                            throw new IllegalStateException("number of locations: " + locating.getLocations());
                        mn.maxStack = -1;
                        locating1 = new NothingInjectLocating(cn.name, mn, locating.getInstructions());
                        try
                        {
                            element.locator[1].invoke(locating1);
                        }
                        catch(Throwable e)
                        {
                            throw new RuntimeException("Invoking locator", e);
                        }
                        if(locating1.getLocations().size() != 1)
                            throw new IllegalStateException("number of locations: " + locating1.getLocations());
                        if(locating.getLocations().iterator().next() >= locating1.getLocations().iterator().next())
                            throw new IllegalStateException("Location [1] must be after [0]");
                        LabelNode from = new LabelNode(), to = new LabelNode(), end = new LabelNode();
                        mn.instructions.insertBefore(locating.getInsnNode(locating.getLocations().iterator().next()), from);
                        InsnList insnsCatch = new InsnList();
                        insnsCatch.add(new JumpInsnNode(Opcodes.GOTO, end));
                        insnsCatch.add(to);
                        insnsCatch.add(insns);
                        insnsCatch.add(AsmUtil.insnPop(Throwable.class));
                        insnsCatch.add(end);
                        mn.instructions.insertBefore(locating1.getInsnNode(locating1.getLocations().iterator().next()), insnsCatch);
                        if(mn.tryCatchBlocks == null)
                            mn.tryCatchBlocks = new ArrayList<>(1);
                        mn.tryCatchBlocks.add(0, new TryCatchBlockNode(from, to, to, AsmUtil.getType(Throwable.class)));
                        break;
                }
            }
        }
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        try
        {
            cn.accept(cw);
        }
        catch(Throwable e)
        {
            e.printStackTrace(System.err);
            cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cn.accept(cw);
        }
        ClassUtil.defineClass(target.getClassLoader(), cn.name, cw.toByteArray());
    }
}
