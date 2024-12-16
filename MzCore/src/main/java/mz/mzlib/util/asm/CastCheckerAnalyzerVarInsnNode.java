package mz.mzlib.util.asm;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.VarInsnNode;

import java.util.Collections;
import java.util.Set;
import java.util.Stack;

public class CastCheckerAnalyzerVarInsnNode extends CastCheckerAnalyzer<VarInsnNode>
{
    public static CastCheckerAnalyzerVarInsnNode instance = new CastCheckerAnalyzerVarInsnNode();

    public Set<Integer> analyze(CastChecker analyzer, int index, VarInsnNode insn, Stack<CastChecker.OperandVisitor> context)
    {
        switch (insn.getOpcode())
        {
            case Opcodes.ILOAD:
            case Opcodes.LLOAD:
            case Opcodes.ALOAD:
                context.push(new CastChecker.OperandVisitor());
                break;
            case Opcodes.FLOAD:
            case Opcodes.DLOAD:
                context.push(new CastChecker.OperandVisitor());
                context.push(new CastChecker.OperandVisitor());
                break;
            case Opcodes.ISTORE:
            case Opcodes.FSTORE:
            case Opcodes.ASTORE:
                context.pop();
                break;
            case Opcodes.LSTORE:
            case Opcodes.DSTORE:
                context.pop();
                context.pop();
                break;
            case Opcodes.RET:
            default:
                throw new UnsupportedOperationException();
        }
        return Collections.singleton(index + 1);
    }
}
