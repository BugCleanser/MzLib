package mz.mzlib.util.asm;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.LabelNode;
import mz.mzlib.asm.tree.LookupSwitchInsnNode;

import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CastCheckerAnalyzerLookupSwitchInsnNode extends CastCheckerAnalyzer<LookupSwitchInsnNode>
{
    public static CastCheckerAnalyzerLookupSwitchInsnNode instance = new CastCheckerAnalyzerLookupSwitchInsnNode();

    public Set<Integer> analyze(CastChecker analyzer, int index, LookupSwitchInsnNode insn, Stack<CastChecker.OperandVisitor> context)
    {
        switch (insn.getOpcode())
        {
            case Opcodes.LOOKUPSWITCH:
                context.pop();
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return Stream.concat(insn.labels.stream(), Stream.of(insn.dflt)).map(LabelNode::getLabel).map(analyzer.labels::get).collect(Collectors.toSet());
    }
}