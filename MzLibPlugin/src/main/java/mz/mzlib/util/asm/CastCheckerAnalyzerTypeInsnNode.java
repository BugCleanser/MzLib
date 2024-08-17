package mz.mzlib.util.asm;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.TypeInsnNode;

import java.util.Collections;
import java.util.Set;
import java.util.Stack;

public class CastCheckerAnalyzerTypeInsnNode extends CastCheckerAnalyzer<TypeInsnNode>
{
    public static CastCheckerAnalyzerTypeInsnNode instance = new CastCheckerAnalyzerTypeInsnNode();

    @Override
    public Set<Integer> analyze(CastChecker caster, int index, TypeInsnNode insn, Stack<CastChecker.OperandVisitor> context)
    {
        switch (insn.getOpcode())
        {
            case Opcodes.NEW:
                context.push(new CastChecker.OperandVisitor());
                break;
            case Opcodes.ANEWARRAY:
                context.pop(); // count
                context.push(new CastChecker.OperandVisitor());
                break;
            case Opcodes.CHECKCAST:
                context.pop();
                context.push(new CastChecker.OperandVisitor());
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return Collections.singleton(index + 1);
    }
}
