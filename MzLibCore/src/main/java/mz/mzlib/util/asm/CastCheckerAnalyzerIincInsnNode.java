package mz.mzlib.util.asm;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.IincInsnNode;

import java.util.Collections;
import java.util.Set;
import java.util.Stack;

public class CastCheckerAnalyzerIincInsnNode extends CastCheckerAnalyzer<IincInsnNode>
{
    public static CastCheckerAnalyzerIincInsnNode instance = new CastCheckerAnalyzerIincInsnNode();

    public Set<Integer> analyze(CastChecker analyzer, int index, IincInsnNode insn, Stack<CastChecker.OperandVisitor> context)
    {
        switch (insn.getOpcode())
        {
            case Opcodes.IINC:
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return Collections.singleton(index + 1);
    }
}