package mz.mzlib.util.wrapper;

import mz.mzlib.asm.ClassReader;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.Type;
import mz.mzlib.asm.tree.AbstractInsnNode;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.asm.tree.MethodInsnNode;
import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.ThrowableFunction;
import mz.mzlib.util.asm.AsmUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WrappedMemberFinderClass(WrapMethodFromBridge.Handler.class)
public @interface WrapMethodFromBridge
{
    String name();
    Class<?>[] params();

    class Handler implements WrappedMemberFinder<WrapMethodFromBridge>
    {
        @Override
        public Member find(
            Class<? extends WrapperObject> wrapperClass,
            Class<?> wrappedClass,
            Method wrapperMethod,
            WrapMethodFromBridge annotation,
            Class<?> returnType,
            Class<?>[] argTypes) throws NoSuchMethodException
        {
            Method bridge = (Method) WrapperClassInfo.get(wrapperClass)
                .analyseWrappedMember(wrapperClass.getMethod(annotation.name(), annotation.params())).getFirst();
            ClassNode cn = new ClassNode();
            new ClassReader(ClassUtil.getByteCode(wrappedClass)).accept(cn, 0);
            for(AbstractInsnNode insn : AsmUtil.getMethodNode(
                cn, bridge.getName(), AsmUtil.getDesc(bridge)).instructions)
            {
                if(insn instanceof MethodInsnNode && insn.getOpcode() == Opcodes.INVOKEVIRTUAL)
                {
                    MethodInsnNode i = (MethodInsnNode) insn;
                    return wrappedClass.getDeclaredMethod(
                        i.name, Arrays.stream(Type.getArgumentTypes(i.desc))
                            .map(ThrowableFunction.of(
                                t -> AsmUtil.getClass(t.getDescriptor(), wrappedClass.getClassLoader())))
                            .toArray(Class[]::new)
                    );
                }
            }
            throw new NoSuchMethodException("No such method: " + annotation);
        }
    }
}
