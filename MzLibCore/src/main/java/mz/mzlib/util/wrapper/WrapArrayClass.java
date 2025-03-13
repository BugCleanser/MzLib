package mz.mzlib.util.wrapper;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.Type;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.asm.tree.MethodNode;
import mz.mzlib.util.asm.AsmUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
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
            MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC, "staticNewInstance", AsmUtil.getDesc(WrapperArray.class, int.class), null, new String[0]);
            mn.instructions.add(AsmUtil.insnVarLoad(int.class, 1));
            mn.instructions.add(AsmUtil.insnArray(WrapperObject.getWrappedClass(type)));
            mn.instructions.add(AsmUtil.insnCreateWrapper(Type.getType("L"+cn.name+";")));
            mn.instructions.add(AsmUtil.insnReturn(WrapperArray.class));
            cn.methods.add(mn);
            mn = new MethodNode(Opcodes.ACC_PUBLIC, "get", AsmUtil.getDesc(Object.class, int.class), null, new String[0]);
            mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
            mn.instructions.add(AsmUtil.insnGetWrapped());
            mn.instructions.add(AsmUtil.insnCast(Object[].class, Object.class));
            mn.instructions.add(AsmUtil.insnArrayLoad(Object.class, AsmUtil.toList(AsmUtil.insnVarLoad(int.class, 1))));
            mn.instructions.add(AsmUtil.insnCreateWrapper(type));
            mn.instructions.add(AsmUtil.insnReturn(type));
            mn.visitEnd();
            cn.methods.add(mn);
        }
    }
}
