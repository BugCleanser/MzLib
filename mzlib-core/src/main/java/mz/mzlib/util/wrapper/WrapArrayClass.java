package mz.mzlib.util.wrapper;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.asm.tree.MethodNode;
import mz.mzlib.util.asm.AsmUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(WrapArrayClass.Handler.class)
public @interface WrapArrayClass
{
    Class<? extends WrapperObject> value();

    class Handler implements WrappedClassFinder<WrapArrayClass>
    {
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, WrapArrayClass annotation)
        {
            return Array.newInstance(WrapperObject.getWrappedClass(annotation.value()), 0).getClass();
        }

        @Override
        public void extra(WrapArrayClass annotation, ClassNode cn)
        {
            Class<? extends WrapperObject> type = annotation.value();
            MethodNode mn = new MethodNode(
                Opcodes.ACC_PUBLIC, "static$getElementFactory",
                AsmUtil.getDesc(MethodType.methodType(WrapperFactory.class)), null,
                new String[0]
            );
            mn.instructions.add(AsmUtil.insnWrapperFactory(type));
            mn.instructions.add(AsmUtil.insnReturn(WrapperFactory.class));
            mn.visitEnd();
            cn.methods.add(mn);
        }
    }
}
