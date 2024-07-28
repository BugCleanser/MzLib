package mz.mzlib.util.asm;

import mz.mzlib.asm.tree.AbstractInsnNode;

import java.util.Collections;
import java.util.Set;
import java.util.Stack;

public class CastCheckerAnalyzerNonInsnNode extends CastCheckerAnalyzer<AbstractInsnNode>
{
	public static CastCheckerAnalyzerNonInsnNode instance=new CastCheckerAnalyzerNonInsnNode();
	
	public Set<Integer> analyze(CastChecker analyzer,int index,AbstractInsnNode insn,Stack<CastChecker.OperandVisitor> context)
	{
		return Collections.singleton(index+1);
	}
}
