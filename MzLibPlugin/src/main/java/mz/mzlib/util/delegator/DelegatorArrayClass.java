package mz.mzlib.util.delegator;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.Type;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.asm.tree.MethodNode;
import mz.mzlib.util.asm.AsmUtil;

import java.lang.annotation.*;
import java.lang.reflect.Array;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DelegatorClassFinderClass(DelegatorArrayClass.Finder.class)
public @interface DelegatorArrayClass
{
    Class<? extends Delegator> value();

    class Finder extends DelegatorClassFinder
    {
        public Class<?> find(ClassLoader classLoader, Annotation annotation)
        {
            return Array.newInstance(Delegator.getDelegateClass(((DelegatorArrayClass) annotation).value()), 0).getClass();
        }

        @Override
        public void extra(Annotation annotation, ClassNode cn)
        {
            Class<? extends Delegator> type = ((DelegatorArrayClass) annotation).value();
            MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC, "staticNewInstance", AsmUtil.getDesc(DelegatorArray.class, int.class), null, new String[0]);
            mn.instructions.add(AsmUtil.insnVarLoad(int.class, 1));
            mn.instructions.add(AsmUtil.insnArray(Delegator.getDelegateClass(type)));
            mn.instructions.add(AsmUtil.insnCreateDelegator(Type.getType(cn.name)));
            mn.instructions.add(AsmUtil.insnReturn(DelegatorArray.class));
            cn.methods.add(mn);
            mn = new MethodNode(Opcodes.ACC_PUBLIC, "get", AsmUtil.getDesc(Object.class, int.class), null, new String[0]);
            mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
            mn.instructions.add(AsmUtil.insnGetDelegate());
            mn.instructions.add(AsmUtil.insnCast(Object[].class, Object.class));
            mn.instructions.add(AsmUtil.insnArrayLoad(Object.class, AsmUtil.toList(AsmUtil.insnVarLoad(int.class, 1))));
            mn.instructions.add(AsmUtil.insnCreateDelegator(type));
            mn.instructions.add(AsmUtil.insnReturn(type));
            mn.visitEnd();
            cn.methods.add(mn);
        }
    }
}
