package mz.mzlib.util.asm;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.Type;
import mz.mzlib.asm.tree.FieldInsnNode;

import java.util.Collections;
import java.util.Set;
import java.util.Stack;

public class CastCheckerAnalyzerFieldInsnNode extends CastCheckerAnalyzer<FieldInsnNode>
{
	public static CastCheckerAnalyzerFieldInsnNode instance=new CastCheckerAnalyzerFieldInsnNode();
	
	@Override
	public Set<Integer> analyze(CastChecker caster,int index,FieldInsnNode insn,Stack<CastChecker.OperandVisitor> context)
	{
		switch(insn.getOpcode())
		{
			case Opcodes.GETSTATIC:
				context.push(new CastChecker.OperandVisitor());
				if(AsmUtil.getCategory(Type.getType(insn.desc))==2)
					context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.PUTSTATIC:
				caster.cast(context.pop(),Type.getType(insn.desc));
				break;
			case Opcodes.GETFIELD:
				caster.cast(context.pop(),Type.getObjectType(insn.owner));
				context.push(new CastChecker.OperandVisitor());
				if(AsmUtil.getCategory(Type.getType(insn.desc))==2)
					context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.PUTFIELD:
				caster.cast(context.pop(),Type.getType(insn.desc));
				if(AsmUtil.getCategory(Type.getType(insn.desc))==2)
					context.pop();
				caster.cast(context.pop(),Type.getObjectType(insn.owner));
				break;
			default:
				throw new UnsupportedOperationException();
		}
		return Collections.singleton(index+1);
	}
}
