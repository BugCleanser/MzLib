package mz.mzlib.util.asm;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.Type;
import mz.mzlib.asm.tree.MethodInsnNode;

import java.util.*;

public class CastCheckerAnalyzerMethodInsnNode extends CastCheckerAnalyzer<MethodInsnNode>
{
	public static CastCheckerAnalyzerMethodInsnNode instance=new CastCheckerAnalyzerMethodInsnNode();
	
	@Override
	public Set<Integer> analyze(CastChecker caster,int index,MethodInsnNode insn,Stack<CastChecker.OperandVisitor> context)
	{
		Type methodType=Type.getMethodType(insn.desc);
		Type[] argTypes=methodType.getArgumentTypes();
		List<CastChecker.OperandVisitor> args=new LinkedList<>();
		for(Type t:argTypes)
		{
			args.add(0,context.pop());
			if(AsmUtil.getCategory(t)==2)
				args.add(0,context.pop());
		}
		if(insn.getOpcode()==Opcodes.INVOKESPECIAL)
			context.pop();
		else if(insn.getOpcode()!=Opcodes.INVOKESTATIC)
			caster.cast(context.pop(),Type.getObjectType(insn.owner));
		for(int i=0,j=0;i<argTypes.length;i++,j++)
		{
			caster.cast(args.get(j),argTypes[i]);
			if(AsmUtil.getCategory(argTypes[i])==2)
				j++;
		}
		for(int n=AsmUtil.getCategory(methodType.getReturnType()),i=0;i<n;i++)
			context.push(new CastChecker.OperandVisitor());
		return Collections.singleton(index+1);
	}
}
