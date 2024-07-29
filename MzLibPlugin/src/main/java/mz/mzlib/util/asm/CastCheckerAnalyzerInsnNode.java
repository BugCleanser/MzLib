package mz.mzlib.util.asm;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.Type;
import mz.mzlib.asm.tree.InsnNode;

import java.util.Collections;
import java.util.Set;
import java.util.Stack;

public class CastCheckerAnalyzerInsnNode extends CastCheckerAnalyzer<InsnNode>
{
	public static CastCheckerAnalyzerInsnNode instance=new CastCheckerAnalyzerInsnNode();
	
	@Override
	public Set<Integer> analyze(CastChecker caster,int index,InsnNode insn,Stack<CastChecker.OperandVisitor> context)
	{
		CastChecker.OperandVisitor top,top2,top3,top4;
		switch(insn.getOpcode())
		{
			case Opcodes.NOP:
				break;
			case Opcodes.ACONST_NULL:
			case Opcodes.ICONST_M1:
			case Opcodes.ICONST_0:
			case Opcodes.ICONST_1:
			case Opcodes.ICONST_2:
			case Opcodes.ICONST_3:
			case Opcodes.ICONST_4:
			case Opcodes.ICONST_5:
			case Opcodes.FCONST_0:
			case Opcodes.FCONST_1:
			case Opcodes.FCONST_2:
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.LCONST_0:
			case Opcodes.LCONST_1:
			case Opcodes.DCONST_0:
			case Opcodes.DCONST_1:
				context.push(new CastChecker.OperandVisitor());
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.IALOAD:
			case Opcodes.FALOAD:
			case Opcodes.AALOAD:
			case Opcodes.BALOAD:
			case Opcodes.CALOAD:
			case Opcodes.SALOAD:
			case Opcodes.LALOAD:
			case Opcodes.DALOAD:
				context.pop(); // index
				switch(insn.getOpcode())
				{
					case Opcodes.IASTORE:
						caster.cast(context.pop(),Type.getType(int[].class));
						break;
					case Opcodes.FASTORE:
						caster.cast(context.pop(),Type.getType(float[].class));
						break;
					case Opcodes.BASTORE:
						caster.cast(context.pop(),Type.getType(byte[].class));
						break;
					case Opcodes.CASTORE:
						caster.cast(context.pop(),Type.getType(char[].class));
						break;
					case Opcodes.SASTORE:
						caster.cast(context.pop(),Type.getType(short[].class));
						break;
					case Opcodes.LASTORE:
						caster.cast(context.pop(),Type.getType(long[].class));
						context.push(new CastChecker.OperandVisitor());
						break;
					case Opcodes.DASTORE:
						caster.cast(context.pop(),Type.getType(double[].class));
						context.push(new CastChecker.OperandVisitor());
						break;
					default:
						caster.cast(context.pop(),Type.getType(Object[].class));
						break;
				}
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.LASTORE:
			case Opcodes.DASTORE:
				context.pop();
			case Opcodes.IASTORE:
			case Opcodes.FASTORE:
			case Opcodes.AASTORE:
			case Opcodes.BASTORE:
			case Opcodes.CASTORE:
			case Opcodes.SASTORE:
				context.pop();
				context.pop();
				switch(insn.getOpcode())
				{
					case Opcodes.IASTORE:
						caster.cast(context.pop(),Type.getType(int[].class));
						break;
					case Opcodes.FASTORE:
						caster.cast(context.pop(),Type.getType(float[].class));
						break;
					case Opcodes.BASTORE:
						caster.cast(context.pop(),Type.getType(byte[].class));
						break;
					case Opcodes.CASTORE:
						caster.cast(context.pop(),Type.getType(char[].class));
						break;
					case Opcodes.SASTORE:
						caster.cast(context.pop(),Type.getType(short[].class));
						break;
					case Opcodes.LASTORE:
						caster.cast(context.pop(),Type.getType(long[].class));
						break;
					case Opcodes.DASTORE:
						caster.cast(context.pop(),Type.getType(double[].class));
						break;
					default:
						caster.cast(context.pop(),Type.getType(Object[].class));
						break;
				}
				break;
			case Opcodes.POP:
				context.pop();
				break;
			case Opcodes.POP2:
				context.pop();
				context.pop();
				break;
			case Opcodes.DUP:
				context.push(new CastChecker.OperandVisitor()); // new duped value
				break;
			case Opcodes.DUP_X1:
				top=context.pop();
				top2=context.pop();
				context.push(top);
				context.push(top2);
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.DUP_X2:
				top=context.pop();
				top2=context.pop();
				top3=context.pop();
				context.push(top);
				context.push(top3);
				context.push(top2);
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.DUP2:
				context.push(new CastChecker.OperandVisitor()); // new duped two value
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.DUP2_X1:
				top=context.pop();
				top2=context.pop();
				top3=context.pop();
				context.push(top2);
				context.push(top);
				context.push(top3);
				context.push(new CastChecker.OperandVisitor());
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.DUP2_X2:
				top=context.pop();
				top2=context.pop();
				top3=context.pop();
				top4=context.pop();
				context.push(top2);
				context.push(top);
				context.push(top4);
				context.push(top3);
				context.push(new CastChecker.OperandVisitor());
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.SWAP:
				top=context.pop();
				top2=context.pop();
				context.push(top);
				context.push(top2);
				break;
			case Opcodes.IADD:
			case Opcodes.FADD:
			case Opcodes.ISUB:
			case Opcodes.FSUB:
			case Opcodes.IMUL:
			case Opcodes.FMUL:
			case Opcodes.IDIV:
			case Opcodes.FDIV:
			case Opcodes.IREM:
			case Opcodes.FREM:
				context.pop();
				context.pop();
				context.push(new CastChecker.OperandVisitor()); // Calculate two numbers to get one number
				break;
			case Opcodes.LADD:
			case Opcodes.DADD:
			case Opcodes.LSUB:
			case Opcodes.DSUB:
			case Opcodes.LMUL:
			case Opcodes.DMUL:
			case Opcodes.LDIV:
			case Opcodes.DDIV:
			case Opcodes.LREM:
			case Opcodes.DREM:
				context.pop();
				context.pop();
				context.pop();
				context.pop();
				context.push(new CastChecker.OperandVisitor());
				context.push(new CastChecker.OperandVisitor()); // Calculate two numbers to get one number
				break;
			case Opcodes.INEG:
			case Opcodes.FNEG:
				context.pop();
				context.push(new CastChecker.OperandVisitor()); // Calculate one number to get one number
				break;
			case Opcodes.LNEG:
			case Opcodes.DNEG:
				context.pop();
				context.pop();
				context.push(new CastChecker.OperandVisitor());
				context.push(new CastChecker.OperandVisitor()); // Calculate one number to get one number
				break;
			case Opcodes.ISHL:
			case Opcodes.ISHR:
			case Opcodes.IAND:
			case Opcodes.IOR:
			case Opcodes.IXOR:
				context.pop();
				context.pop();
				context.push(new CastChecker.OperandVisitor()); // Calculate two numbers to get one number
				break;
			case Opcodes.LSHL:
			case Opcodes.LSHR:
			case Opcodes.LUSHR:
				context.pop();
				context.pop();
				context.pop();
				context.push(new CastChecker.OperandVisitor());
				context.push(new CastChecker.OperandVisitor()); // Calculate two numbers to get one number
				break;
			case Opcodes.LAND:
			case Opcodes.LOR:
			case Opcodes.LXOR:
				context.pop();
				context.pop();
				context.pop();
				context.pop();
				context.push(new CastChecker.OperandVisitor());
				context.push(new CastChecker.OperandVisitor()); // Calculate two long to get one long
				break;
			case Opcodes.L2I:
			case Opcodes.D2I:
				context.pop();
			case Opcodes.F2I:
				context.pop();
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.D2L:
				context.pop();
			case Opcodes.I2L:
			case Opcodes.F2L:
				context.pop();
				context.push(new CastChecker.OperandVisitor());
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.L2F:
			case Opcodes.D2F:
				context.pop();
			case Opcodes.I2F:
				context.pop();
				context.push(new CastChecker.OperandVisitor());
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.L2D:
				context.pop();
			case Opcodes.I2D:
			case Opcodes.F2D:
				context.pop();
				context.push(new CastChecker.OperandVisitor());
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.I2B:
			case Opcodes.I2C:
			case Opcodes.I2S:
				context.pop();
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.FCMPL:
			case Opcodes.FCMPG:
				context.pop(); // pop the two numbers
				context.pop();
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.LCMP:
			case Opcodes.DCMPL:
			case Opcodes.DCMPG:
				context.pop();
				context.pop();
				context.pop();
				context.pop();
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.ATHROW:
				caster.cast(context.pop(),Type.getType(Throwable.class));
			case Opcodes.IRETURN:
			case Opcodes.LRETURN:
			case Opcodes.FRETURN:
			case Opcodes.DRETURN:
			case Opcodes.ARETURN:
			case Opcodes.RETURN:
				return Collections.emptySet();
			case Opcodes.ARRAYLENGTH:
				context.pop();
				context.push(new CastChecker.OperandVisitor());
				break;
			case Opcodes.MONITORENTER:
			case Opcodes.MONITOREXIT:
				context.pop(); // pop the object
				break;
			default:
				throw new UnsupportedOperationException();
		}
		return Collections.singleton(index+1);
	}
}
