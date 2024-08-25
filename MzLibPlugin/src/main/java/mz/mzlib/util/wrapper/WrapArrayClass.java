package mz.mzlib.util.wrapper;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.Type;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.asm.tree.MethodNode;
import mz.mzlib.util.asm.AsmUtil;

import java.lang.annotation.*;
import java.lang.reflect.Array;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WrappedClassFinderClass(WrapArrayClass.Finder.class)
public @interface WrapArrayClass
{
    Class<? extends WrapperObject> value();

    class Finder extends WrappedClassFinder
    {
        @SuppressWarnings("deprecation")
        public Class<?> find(Class<? extends WrapperObject> wrapperClass, Annotation annotation)
        {
            return Array.newInstance(WrapperObject.getWrappedClass(((WrapArrayClass) annotation).value()), 0).getClass();
        }

        @SuppressWarnings("deprecation")
        @Override
        public void extra(Annotation annotation, ClassNode cn)
        {
            Class<? extends WrapperObject> type = ((WrapArrayClass) annotation).value();
            MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC, "staticNewInstance", AsmUtil.getDesc(WrapperArray.class, int.class), null, new String[0]);
            mn.instructions.add(AsmUtil.insnVarLoad(int.class, 1));
            mn.instructions.add(AsmUtil.insnArray(WrapperObject.getWrappedClass(type)));
            mn.instructions.add(AsmUtil.insnCreateWrapper(Type.getType(cn.name)));
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
