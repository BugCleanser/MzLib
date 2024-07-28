package mz.mzlib.util.asm;

import mz.mzlib.asm.tree.AbstractInsnNode;

import java.util.Set;
import java.util.Stack;

public abstract class CastCheckerAnalyzer<T extends AbstractInsnNode>
{
	public abstract Set<Integer> analyze(CastChecker analyzer,int index,T insn,Stack<CastChecker.OperandVisitor> context);
}
