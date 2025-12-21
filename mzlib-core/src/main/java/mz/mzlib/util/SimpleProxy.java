package mz.mzlib.util;

import mz.mzlib.asm.ClassReader;
import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.asm.tree.MethodNode;
import mz.mzlib.util.asm.AsmUtil;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public interface SimpleProxy
{
    Map<Class<?>, MethodHandle> cache = Collections.synchronizedMap(new WeakHashMap<>());

    default <T> T create(T target, Class<?> handler)
    {
        try
        {
            return RuntimeUtil.cast((Object) cache.computeIfAbsent(
                handler, k ->
                {
                    ClassNode cnHandler = new ClassNode();
                    new ClassReader(ClassUtil.getByteCode(handler)).accept(cnHandler, 0);
                    ClassNode cn = new ClassNode();
                    cn.visit(
                        Opcodes.V1_8, Opcodes.ACC_PUBLIC, AsmUtil.getType(handler) + "$0MzProxy", null,
                        AsmUtil.getType(target.getClass()), new String[0]
                    );
                    cn.visitField(Opcodes.ACC_PUBLIC, "0mzProxyTarget", AsmUtil.getDesc(target.getClass()), null, null)
                        .visitEnd();
                    cn.methods = cnHandler.methods;
                    for(Method m : target.getClass().getMethods())
                    {
                        if(Modifier.isStatic(m.getModifiers()))
                        {
                            continue;
                        }
                        if(cn.methods.stream()
                            .anyMatch(mn -> mn.name.equals(m.getName()) && mn.desc.equals(AsmUtil.getDesc(m))))
                        {
                            continue;
                        }
                        MethodNode mn = new MethodNode(
                            Opcodes.ACC_PUBLIC, m.getName(), AsmUtil.getDesc(m), null, new String[0]);
                        mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
                        Class<?>[] pts = m.getParameterTypes();
                        for(int i = 0, loc = 1; i < pts.length; i++)
                        {
                            mn.instructions.add(AsmUtil.insnVarLoad(pts[i], loc));
                            loc += AsmUtil.getCategory(pts[i]);
                        }
                        mn.visitMethodInsn(
                            Opcodes.INVOKEVIRTUAL, AsmUtil.getType(target.getClass()), m.getName(), AsmUtil.getDesc(m),
                            false
                        );
                        mn.instructions.add(AsmUtil.insnReturn(m.getReturnType()));
                        cn.methods.add(mn);
                    }
                    MethodNode mn = new MethodNode(
                        Opcodes.ACC_PUBLIC, "<init>", AsmUtil.getDesc(void.class, Object.class), null, new String[0]);
                    mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 0));
                    mn.instructions.add(AsmUtil.insnVarLoad(Object.class, 1));
                    mn.instructions.add(AsmUtil.insnCast(target.getClass(), Object.class));
                    mn.visitFieldInsn(Opcodes.PUTFIELD, cn.name, "0mzProxyTarget", AsmUtil.getDesc(target.getClass()));
                    mn.instructions.add(AsmUtil.insnReturn(void.class));
                    mn.visitEnd();
                    cn.methods.add(mn);
                    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                    cn.accept(cn);
                    try
                    {
                        return ClassUtil.findConstructor(
                            ClassUtil.defineClass(
                                new SimpleClassLoader(handler.getClassLoader()), cn.name,
                                cw.toByteArray()
                            ), Object.class
                        );
                    }
                    catch(Throwable e)
                    {
                        throw RuntimeUtil.sneakilyThrow(e);
                    }
                }
            ).invokeExact((Object) target));
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
