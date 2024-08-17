package mz.mzlib.util.async;

import mz.mzlib.asm.*;
import mz.mzlib.asm.tree.*;
import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.MapEntry;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.asm.CastChecker;

import java.lang.invoke.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class Coroutine
{
    public CompletableFuture<?> future = new CompletableFuture<>();
    public int step = 0;

    public static Coroutine init(AsyncFunction<?> function)
    {
        ClassNode cn = new ClassNode();
        new ClassReader(ClassUtil.getByteCode(function.getClass())).accept(cn, 0);

        ClassNode coroutine = new ClassNode();
        coroutine.visit(cn.version, Opcodes.ACC_PUBLIC, AsmUtil.getType(function.getClass()) + "$0MzCoroutine", null, AsmUtil.getType(Coroutine.class), new String[0]);
        MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC, "<init>", AsmUtil.getDesc(void.class, new Class[0]), null, new String[0]);
        mn.instructions.add(AsmUtil.insnVarLoad(Coroutine.class, 0));
        mn.visitMethodInsn(Opcodes.INVOKESPECIAL, coroutine.superName, "<init>", AsmUtil.getDesc(void.class, new Class[0]), false);
        mn.instructions.add(AsmUtil.insnReturn(void.class));
        mn.visitEnd();
        coroutine.methods.add(mn);

        for (MethodNode i : cn.methods)
        {
            if (!i.name.equals("<init>"))
            {
                continue;
            }
            InsnList il = new InsnList();
            il.add(AsmUtil.insnVarLoad(AsyncFunction.class, 0));
            il.add(new TypeInsnNode(Opcodes.NEW, coroutine.name));
            il.add(new InsnNode(Opcodes.DUP));
            il.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, coroutine.name, "<init>", AsmUtil.getDesc(void.class, new Class[0]), false));
            il.add(new FieldInsnNode(Opcodes.PUTFIELD, AsmUtil.getType(AsyncFunction.class), "coroutine", AsmUtil.getDesc(Coroutine.class)));
            i.instructions.add(AsmUtil.insnVarLoad(Coroutine.class, 0));
            for (AbstractInsnNode insn : i.instructions)
            {
                if (insn instanceof MethodInsnNode && insn.getOpcode() == Opcodes.INVOKESPECIAL && ((MethodInsnNode) insn).name.equals("<init>") && ((MethodInsnNode) insn).owner.equals(AsmUtil.getType(AsyncFunction.class)))
                {
                    i.instructions.insert(insn, il);
                    break;
                }
            }
        }
        ClassNode temp = new ClassNode();
        new ClassReader(ClassUtil.getByteCode(function.getClass())).accept(temp, 0);
        MethodNode template = temp.methods.stream().filter(m -> m.name.equals("template") && (m.access & Opcodes.ACC_SYNTHETIC) == 0).findFirst().orElseThrow(AssertionError::new);
        mn = AsmUtil.getMethodNode(cn, "run", AsmUtil.getDesc(void.class, new Class[0]));
        mn.instructions.clear();
        Map<MapEntry<Integer, String>, Integer> vars = new HashMap<>();
        mn.tryCatchBlocks = template.tryCatchBlocks;
        Label try1 = new Label(), try2 = new Label();
        mn.visitTryCatchBlock(try1, try2, try2, AsmUtil.getType(Throwable.class));
        mn.instructions.add((LabelNode) try1.info);
        TableSwitchInsnNode sn = new TableSwitchInsnNode(0, -1, new LabelNode());
        mn.instructions.add(AsmUtil.insnVarLoad(AsyncFunction.class, 0));
        mn.visitFieldInsn(Opcodes.GETFIELD, AsmUtil.getType(AsyncFunction.class), "coroutine", AsmUtil.getDesc(Coroutine.class));
        mn.visitFieldInsn(Opcodes.GETFIELD, AsmUtil.getType(Coroutine.class), "step", AsmUtil.getDesc(int.class));
        mn.instructions.add(sn); // jump step
        sn.max++;
        mn.instructions.add(sn.dflt);
        sn.labels.add(sn.dflt);
        for (int i = 0; i < template.instructions.size(); i++)
        {
            AbstractInsnNode insn = template.instructions.get(i);
            if (insn instanceof IincInsnNode && insn.getOpcode() == Opcodes.IINC || insn instanceof VarInsnNode && (AsmUtil.opcodesVarLoad.contains(insn.getOpcode()) || AsmUtil.opcodesVarStore.contains(insn.getOpcode())))
            {
                String type;
                switch (insn.getOpcode())
                {
                    case Opcodes.IINC:
                    case Opcodes.ILOAD:
                    case Opcodes.ISTORE:
                        type = AsmUtil.getDesc(int.class);
                        break;
                    case Opcodes.LLOAD:
                    case Opcodes.LSTORE:
                        type = AsmUtil.getDesc(long.class);
                        break;
                    case Opcodes.FLOAD:
                    case Opcodes.FSTORE:
                        type = AsmUtil.getDesc(float.class);
                        break;
                    case Opcodes.DLOAD:
                    case Opcodes.DSTORE:
                        type = AsmUtil.getDesc(double.class);
                        break;
                    case Opcodes.ALOAD:
                    case Opcodes.ASTORE:
                        type = AsmUtil.getDesc(Object.class);
                        break;
                    default:
                        throw new UnsupportedOperationException();
                }
                int var = insn instanceof IincInsnNode ? ((IincInsnNode) insn).var : ((VarInsnNode) insn).var;
                if (var != 0)
                {
                    mn.instructions.add(AsmUtil.insnVarLoad(AsyncFunction.class, 0));
                    mn.visitFieldInsn(Opcodes.GETFIELD, AsmUtil.getType(AsyncFunction.class), "coroutine", AsmUtil.getDesc(Coroutine.class));
                    mn.visitTypeInsn(Opcodes.CHECKCAST, coroutine.name); // this
                    int index = vars.computeIfAbsent(new MapEntry<>(var, type), k1 ->
                    {
                        coroutine.visitField(Opcodes.ACC_PUBLIC, "$var" + vars.size(), k1.getValue(), null, null);
                        return vars.size();
                    });
                    if (insn instanceof IincInsnNode)
                    {
                        mn.visitFieldInsn(Opcodes.GETFIELD, coroutine.name, "$var" + index, type);
                        mn.instructions.add(AsmUtil.insnConst(((IincInsnNode) insn).incr));
                        mn.visitInsn(Opcodes.IADD);
                        mn.instructions.add(AsmUtil.insnVarLoad(AsyncFunction.class, 0));
                        mn.visitFieldInsn(Opcodes.GETFIELD, AsmUtil.getType(AsyncFunction.class), "coroutine", AsmUtil.getDesc(Coroutine.class));
                        mn.visitTypeInsn(Opcodes.CHECKCAST, coroutine.name); // this
                        mn.instructions.add(AsmUtil.insnSwap(Coroutine.class, AsmUtil.getClass(type, function.getClass().getClassLoader())));
                        mn.visitFieldInsn(Opcodes.PUTFIELD, coroutine.name, "$var" + index, type);
                    }
                    else if (AsmUtil.opcodesVarLoad.contains(insn.getOpcode()))
                    {
                        mn.visitFieldInsn(Opcodes.GETFIELD, coroutine.name, "$var" + index, type);
                    }
                    else
                    {
                        mn.instructions.add(AsmUtil.insnSwap(Coroutine.class, AsmUtil.getClass(type, function.getClass().getClassLoader())));
                        mn.visitFieldInsn(Opcodes.PUTFIELD, coroutine.name, "$var" + index, type);
                    }
                    continue;
                }
            }
            else if (insn instanceof MethodInsnNode)
            {
                MethodInsnNode in = (MethodInsnNode) insn;
                if ((Objects.equals(in.name, "await") || Objects.equals(in.name, "await0")) && (Objects.equals(in.owner, AsmUtil.getType(AsyncFunction.class)) || Objects.equals(in.owner, AsmUtil.getType(function.getClass()))))
                {
                    sn.max++;
                    mn.instructions.add(AsmUtil.insnVarLoad(AsyncFunction.class, 0)); // this
                    mn.visitFieldInsn(Opcodes.GETFIELD, AsmUtil.getType(AsyncFunction.class), "coroutine", AsmUtil.getDesc(Coroutine.class));
                    mn.instructions.add(AsmUtil.insnConst(sn.max));
                    mn.visitFieldInsn(Opcodes.PUTFIELD, AsmUtil.getType(Coroutine.class), "step", AsmUtil.getDesc(int.class)); // this.step=++maxStep;
                    if (Objects.equals(in.desc, AsmUtil.getDesc(void.class, CompletableFuture.class))) // await(completableFuture);
                    {
                        mn.instructions.add(AsmUtil.insnVarLoad(AsyncFunction.class, 0));
                        mn.visitInvokeDynamicInsn("accept", AsmUtil.getDesc(BiConsumer.class, AsyncFunction.class), new Handle(Opcodes.H_INVOKESTATIC, AsmUtil.getType(LambdaMetafactory.class), "metafactory", AsmUtil.getDesc(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, MethodType.class, MethodHandle.class, MethodType.class), false), Type.getMethodType(Type.VOID_TYPE, Type.getType(Object.class), Type.getType(Object.class)), new Handle(Opcodes.H_INVOKEVIRTUAL, AsmUtil.getType(AsyncFunction.class), "run", AsmUtil.getDesc(void.class, Object.class, Throwable.class), false), Type.getMethodType(Type.VOID_TYPE, Type.getType(Object.class), Type.getType(Throwable.class)));
                        mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL, AsmUtil.getType(CompletableFuture.class), "whenComplete", AsmUtil.getDesc(CompletableFuture.class, BiConsumer.class), false);
                        mn.instructions.add(AsmUtil.insnPop(CompletableFuture.class)); // completableFuture.whenComplete(this.runner);
                    }
                    else // await(basicAwait);
                    {
                        mn.instructions.add(AsmUtil.insnVarLoad(AsyncFunction.class, 0)); // this
                        mn.visitFieldInsn(Opcodes.GETFIELD, AsmUtil.getType(AsyncFunction.class), "runner", AsmUtil.getDesc(AsyncFunctionRunner.class));
                        mn.instructions.add(AsmUtil.insnSwap(AsyncFunctionRunner.class, BasicAwait.class));
                        mn.instructions.add(AsmUtil.insnVarLoad(AsyncFunction.class, 0));
                        mn.instructions.add(AsmUtil.insnSwap(AsyncFunction.class, BasicAwait.class));
                        mn.visitMethodInsn(Opcodes.INVOKEINTERFACE, AsmUtil.getType(AsyncFunctionRunner.class), "schedule", AsmUtil.getDesc(void.class, AsyncFunction.class, BasicAwait.class), true); // this.function.runner.schedule(this,basicAwait);

                    }
                    mn.instructions.add(AsmUtil.insnReturn(void.class)); // return;
                    LabelNode ln = new LabelNode();
                    mn.instructions.add(ln);
                    sn.labels.add(ln); // next step
                    continue;
                }
            }
            else if (insn instanceof InsnNode && insn.getOpcode() == Opcodes.ARETURN) // return result;
            {
                mn.instructions.add(AsmUtil.insnVarLoad(AsyncFunction.class, 0)); // this
                mn.visitFieldInsn(Opcodes.GETFIELD, AsmUtil.getType(AsyncFunction.class), "coroutine", AsmUtil.getDesc(Coroutine.class));
                mn.visitFieldInsn(Opcodes.GETFIELD, AsmUtil.getType(Coroutine.class), "future", AsmUtil.getDesc(CompletableFuture.class));
                mn.instructions.add(AsmUtil.insnSwap(CompletableFuture.class, Object.class));
                mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL, AsmUtil.getType(CompletableFuture.class), "complete", AsmUtil.getDesc(boolean.class, Object.class), false);
                mn.instructions.add(AsmUtil.insnPop(boolean.class));
                mn.instructions.add(AsmUtil.insnReturn(void.class));
                continue;
            }
            mn.instructions.add(insn);
        }
        mn.instructions.add((LabelNode) try2.info);
        mn.instructions.add(AsmUtil.insnVarLoad(AsyncFunction.class, 0));
        mn.visitFieldInsn(Opcodes.GETFIELD, AsmUtil.getType(AsyncFunction.class), "coroutine", AsmUtil.getDesc(Coroutine.class));
        mn.visitFieldInsn(Opcodes.GETFIELD, AsmUtil.getType(Coroutine.class), "future", AsmUtil.getDesc(CompletableFuture.class));
        mn.instructions.add(AsmUtil.insnSwap(CompletableFuture.class, Throwable.class));
        mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL, AsmUtil.getType(CompletableFuture.class), "completeExceptionally", AsmUtil.getDesc(boolean.class, Throwable.class), false);
        mn.instructions.add(AsmUtil.insnPop(boolean.class));
        mn.instructions.add(AsmUtil.insnReturn(void.class));
        CastChecker checker = new CastChecker(mn);
        checker.analyze();
        checker.autoCast();

        try
        {
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            coroutine.accept(cw);
            Class<?> c = ClassUtil.defineClass(function.getClass().getClassLoader(), coroutine.name, cw.toByteArray());

            cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            cn.accept(cw);
            ClassUtil.defineClass(function.getClass().getClassLoader(), cn.name, cw.toByteArray());

            return (Coroutine) c.newInstance();
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
