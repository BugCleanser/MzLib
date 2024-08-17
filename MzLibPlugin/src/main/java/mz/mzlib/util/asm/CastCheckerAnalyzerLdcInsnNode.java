package mz.mzlib.util.asm;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.LdcInsnNode;

import java.util.Collections;
import java.util.Set;
import java.util.Stack;

public class CastCheckerAnalyzerLdcInsnNode extends CastCheckerAnalyzer<LdcInsnNode>
{
    public static CastCheckerAnalyzerLdcInsnNode instance = new CastCheckerAnalyzerLdcInsnNode();

    public Set<Integer> analyze(CastChecker analyzer, int index, LdcInsnNode insn, Stack<CastChecker.OperandVisitor> context)
    {
        if (insn.getOpcode() == Opcodes.LDC)
        {
            context.push(new CastChecker.OperandVisitor());
            if (insn.cst instanceof Long || insn.cst instanceof Double)
            {
                context.push(new CastChecker.OperandVisitor());
            }
        }
        else
        {
            throw new UnsupportedOperationException();
        }
        return Collections.singleton(index + 1);
    }
}
