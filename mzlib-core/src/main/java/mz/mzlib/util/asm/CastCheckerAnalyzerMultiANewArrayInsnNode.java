package mz.mzlib.util.asm;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.MultiANewArrayInsnNode;

import java.util.Collections;
import java.util.Set;
import java.util.Stack;

public class CastCheckerAnalyzerMultiANewArrayInsnNode extends CastCheckerAnalyzer<MultiANewArrayInsnNode>
{
    public static CastCheckerAnalyzerMultiANewArrayInsnNode instance = new CastCheckerAnalyzerMultiANewArrayInsnNode();

    @Override
    public Set<Integer> analyze(
        CastChecker caster,
        int index,
        MultiANewArrayInsnNode insn,
        Stack<CastChecker.OperandVisitor> context)
    {
        switch(insn.getOpcode())
        {
            case Opcodes.MULTIANEWARRAY:
                for(int i = 0; i < insn.dims; i++)
                {
                    context.pop();
                }
                context.push(new CastChecker.OperandVisitor());
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return Collections.singleton(index + 1);
    }
}
