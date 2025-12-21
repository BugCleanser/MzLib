package mz.mzlib.util.asm;

import mz.mzlib.asm.Label;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.Type;
import mz.mzlib.asm.tree.*;
import mz.mzlib.util.CollectionUtil;
import mz.mzlib.util.Pair;
import mz.mzlib.util.RuntimeUtil;

import java.lang.reflect.Array;
import java.util.*;

@Deprecated
public class CastChecker
{
    public static class OperandVisitor implements Cloneable
    {
        public AbstractInsnNode top, second;

        public OperandVisitor()
        {
        }

        public OperandVisitor(AbstractInsnNode top, AbstractInsnNode second)
        {
            this.top = top;
            this.second = second;
        }

        @Override
        public OperandVisitor clone()
        {
            try
            {
                OperandVisitor result = (OperandVisitor) super.clone();
                result.top = this.top;
                result.second = this.second;
                return result;
            }
            catch(CloneNotSupportedException e)
            {
                throw new InternalError(e);
            }
        }
    }

    public static Map<Class<? extends AbstractInsnNode>, CastCheckerAnalyzer<?>> analyzers = new HashMap<>();

    static
    {
        analyzers.put(LabelNode.class, CastCheckerAnalyzerNonInsnNode.instance);
        analyzers.put(FrameNode.class, CastCheckerAnalyzerNonInsnNode.instance);
        analyzers.put(LineNumberNode.class, CastCheckerAnalyzerNonInsnNode.instance);
        analyzers.put(InsnNode.class, CastCheckerAnalyzerInsnNode.instance);
        analyzers.put(IntInsnNode.class, CastCheckerAnalyzerIntInsnNode.instance);
        analyzers.put(LdcInsnNode.class, CastCheckerAnalyzerLdcInsnNode.instance);
        analyzers.put(VarInsnNode.class, CastCheckerAnalyzerVarInsnNode.instance);
        analyzers.put(IincInsnNode.class, CastCheckerAnalyzerIincInsnNode.instance);
        analyzers.put(JumpInsnNode.class, CastCheckerAnalyzerJumpInsnNode.instance);
        analyzers.put(TableSwitchInsnNode.class, CastCheckerAnalyzerTableSwitchInsnNode.instance);
        analyzers.put(LookupSwitchInsnNode.class, CastCheckerAnalyzerLookupSwitchInsnNode.instance);
        analyzers.put(FieldInsnNode.class, CastCheckerAnalyzerFieldInsnNode.instance);
        analyzers.put(MethodInsnNode.class, CastCheckerAnalyzerMethodInsnNode.instance);
        analyzers.put(InvokeDynamicInsnNode.class, CastCheckerAnalyzerInvokeDynamicInsnNode.instance);
        analyzers.put(TypeInsnNode.class, CastCheckerAnalyzerTypeInsnNode.instance);
        analyzers.put(MultiANewArrayInsnNode.class, CastCheckerAnalyzerMultiANewArrayInsnNode.instance);
    }

    public InsnList insns;
    public List<Stack<OperandVisitor>> contexts;
    public Map<Label, Integer> labels;
    public Map<Pair<AbstractInsnNode, Integer>, Type> autoCaster;
    public List<TryCatchBlockNode> tryCatchBlocks;

    public CastChecker(InsnList insns, List<TryCatchBlockNode> tryCatchBlocks)
    {
        this.insns = insns;
        this.contexts = new ArrayList<>(Collections.nCopies(insns.size(), null));
        this.labels = new HashMap<>();
        this.autoCaster = new HashMap<>();
        this.tryCatchBlocks = tryCatchBlocks;
    }

    public CastChecker(MethodNode mn)
    {
        this(mn.instructions, mn.tryCatchBlocks);
    }

    public Map<OperandVisitor, Type> casters = new HashMap<>();

    public void cast(OperandVisitor visitor, Type type)
    {
        if(type.getSort() != Type.OBJECT && type.getSort() != Type.ARRAY)
        {
            return;
        }
        casters.put(visitor, type);
    }

    public void analyze()
    {
        for(int i = 0; i < insns.size(); i++)
        {
            AbstractInsnNode insn = insns.get(i);
            if(insn instanceof LabelNode)
            {
                labels.put(((LabelNode) insn).getLabel(), i);
            }
        }
        Queue<Integer> queue = new ArrayDeque<>();
        contexts.set(0, new Stack<>());
        queue.add(0);
        for(TryCatchBlockNode i : tryCatchBlocks)
        {
            int index = labels.get(i.handler.getLabel());
            contexts.set(index, CollectionUtil.addAll(new Stack<>(), new OperandVisitor(i.handler, null)));
            queue.add(index);
        }
        while(!queue.isEmpty())
        {
            int index = queue.poll();
            CastCheckerAnalyzer<?> analyzer = analyzers.get(insns.get(index).getClass());
            @SuppressWarnings("unchecked") Stack<OperandVisitor> result = (Stack<OperandVisitor>) contexts.get(index)
                .clone();
            Set<Integer> next = analyzer.analyze(this, index, RuntimeUtil.cast(insns.get(index)), result);
            if(!result.isEmpty())
            {
                OperandVisitor top = result.pop();
                if(result.size() >= 2)
                {
                    OperandVisitor second = result.pop();
                    result.push(new OperandVisitor(second.top, insns.get(index)));
                }
                result.push(new OperandVisitor(insns.get(index), top.second));
            }
            for(int i : next)
            {
                if(contexts.set(i, result) == null)
                {
                    queue.add(i);
                }
            }
        }
    }

    public void autoCast()
    {
        for(Map.Entry<OperandVisitor, Type> i : casters.entrySet())
        {
            if(i.getKey().top != null)
            {
                insns.insert(i.getKey().top, new TypeInsnNode(Opcodes.CHECKCAST, i.getValue().getInternalName()));
            }
            else if(i.getKey().second != null)
            {
                InsnList il = new InsnList();
                il.add(new InsnNode(Opcodes.SWAP));
                il.add(new TypeInsnNode(Opcodes.CHECKCAST, i.getValue().getInternalName()));
                il.add(new InsnNode(Opcodes.SWAP));
                insns.insert(i.getKey().second, new TypeInsnNode(Opcodes.CHECKCAST, i.getValue().getInternalName()));
            }
            else
            {
                throw new UnsupportedOperationException();
            }
        }
        for(AbstractInsnNode insn : insns.toArray())
        {
            if(insn instanceof InsnNode)
            {
                switch(insn.getOpcode())
                {
                    case Opcodes.ARRAYLENGTH:
                        insns.insert(insn, new MethodInsnNode(
                                Opcodes.INVOKESTATIC, AsmUtil.getType(Array.class), "getLength",
                                AsmUtil.getDesc(int.class, Object.class), false
                            )
                        );
                        insns.remove(insn);
                    default:
                        break;
                }
            }
        }
    }
}
