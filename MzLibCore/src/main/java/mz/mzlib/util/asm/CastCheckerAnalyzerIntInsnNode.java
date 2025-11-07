package mz.mzlib.util.asm;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.IntInsnNode;

import java.util.Collections;
import java.util.Set;
import java.util.Stack;

public class CastCheckerAnalyzerIntInsnNode extends CastCheckerAnalyzer<IntInsnNode>
{
    public static CastCheckerAnalyzerIntInsnNode instance = new CastCheckerAnalyzerIntInsnNode();

    public Set<Integer> analyze(
        CastChecker analyzer,
        int index,
        IntInsnNode insn,
        Stack<CastChecker.OperandVisitor> context)
    {
        switch(insn.getOpcode())
        {
            case Opcodes.BIPUSH:
            case Opcodes.SIPUSH:
                context.push(new CastChecker.OperandVisitor());
                break;
            case Opcodes.NEWARRAY:
                context.pop();
                context.push(new CastChecker.OperandVisitor());
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return Collections.singleton(index + 1);
    }
}
