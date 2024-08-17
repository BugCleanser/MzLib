package mz.mzlib.util.compound;

import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.asm.tree.MethodNode;
import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.asm.AsmUtil;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class CompoundClassInfo
{
    public Class<? extends Compound> clazz;

    public CompoundClassInfo(Class<? extends Compound> clazz)
    {
        this.clazz = clazz;
    }

    public MethodHandle constructor;

    public void build()
    {
        Map<String, Integer> props = new HashMap<>();
        ClassNode cn = new ClassNode();
        cn.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, cn.name, null, AsmUtil.getType(Object.class), new String[]{AsmUtil.getType(clazz)});
        for (Method m : this.clazz.getMethods())
        {
            PropAccessor p = m.getDeclaredAnnotation(PropAccessor.class);
            if (p == null)
            {
                continue;
            }
            Class<?> type;
            if (m.getParameterCount() == 1)
            {
                type = m.getParameterTypes()[0];
            }
            else
            {
                type = m.getReturnType();
            }
            int index = props.computeIfAbsent(p.value(), k ->
            {
                int i = props.size();
                cn.visitField(Opcodes.ACC_PUBLIC, "0mzProp" + i, AsmUtil.getDesc(type), null, null).visitEnd();
                return i;
            });
            MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC, m.getName(), AsmUtil.getDesc(m), null, new String[0]);
            if (m.getParameterCount() == 1)
            {
                mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
                mn.instructions.add(AsmUtil.insnVarLoad(type, 1));
                mn.visitFieldInsn(Opcodes.PUTFIELD, cn.name, "0mzProp" + index, AsmUtil.getDesc(type));
                mn.instructions.add(AsmUtil.insnReturn(void.class));
            }
            else
            {
                mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
                mn.visitFieldInsn(Opcodes.GETFIELD, cn.name, "0mzProp" + index, AsmUtil.getDesc(type));
                mn.instructions.add(AsmUtil.insnReturn(type));
            }
            mn.visitEnd();
            cn.methods.add(mn);
        }
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        try
        {
            this.constructor = ClassUtil.findConstructor(ClassUtil.defineClass(clazz.getClassLoader(), cn.name, cw.toByteArray()));
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    public static Map<Class<? extends Compound>, CompoundClassInfo> cache = Collections.synchronizedMap(new WeakHashMap<>());

    public static CompoundClassInfo get(Class<? extends Compound> clazz)
    {
        return cache.computeIfAbsent(clazz, k ->
        {
            CompoundClassInfo info = new CompoundClassInfo(k);
            info.build();
            return info;
        });
    }
}
