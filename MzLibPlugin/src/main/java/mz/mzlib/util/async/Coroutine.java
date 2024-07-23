package mz.mzlib.util.async;

import mz.mzlib.asm.ClassReader;
import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.*;
import mz.mzlib.util.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class Coroutine
{
	public AsyncFunction<?> function;
	public CompletableFuture<?> future;
	public Coroutine(AsyncFunction<?> function)
	{
		this.function=function;
		this.future=new CompletableFuture<>();
	}
	
	public BiConsumer<Object,Throwable> runner=(result,e)->
	{
		if(e!=null)
			throw RuntimeUtil.sneakilyThrow(e);
		Coroutine.this.run();
	};
	public int step=0;
	public abstract void run();
	
	public static Map<Class<?>,WeakRef<MethodHandle>> cache=Collections.synchronizedMap(new WeakHashMap<>());
	public static Coroutine create(AsyncFunction<?> function)
	{
		try
		{
			return (Coroutine)cache.computeIfAbsent(function.getClass(),k->
			{
				ClassNode template=new ClassNode();
				new ClassReader(ClassUtil.getByteCode(function.getClass())).accept(template,0);
				ClassNode cn=new ClassNode();
				cn.visit(template.version,Opcodes.ACC_PUBLIC,"0MzCoroutine",null,AsmUtil.getType(Coroutine.class),new String[0]);
				
				MethodNode mn=new MethodNode(Opcodes.ACC_PUBLIC,"<init>",AsmUtil.getDesc(void.class,AsyncFunction.class),null,new String[0]); // void <init>(AsyncFunction function)
				mn.instructions.add(AsmUtil.insnVarLoad(Coroutine.class,0));
				mn.instructions.add(AsmUtil.insnVarLoad(AsyncFunction.class,1));
				mn.visitMethodInsn(Opcodes.INVOKESPECIAL,AsmUtil.getType(Coroutine.class),"<init>",AsmUtil.getDesc(void.class,AsyncFunction.class),false); // super(function);
				mn.instructions.add(AsmUtil.insnReturn(void.class)); // return;
				mn.visitEnd();
				cn.methods.add(mn);
				
				mn=new MethodNode(Opcodes.ACC_PUBLIC,"run",AsmUtil.getDesc(void.class,new Class[0]),null,new String[0]);
				Map<MapEntry<Integer,String>,Integer> vars=new HashMap<>();
				Map<Integer,String> varTypes=new HashMap<>();
				MethodNode templateMethod=template.methods.stream().filter(m->m.name.equals("template")&&(m.access&Opcodes.ACC_SYNTHETIC)==0).findFirst().orElseThrow(AssertionError::new);
				TableSwitchInsnNode sn=new TableSwitchInsnNode(0,-1,new LabelNode());
				mn.instructions.add(AsmUtil.insnVarLoad(Coroutine.class,0));
				mn.visitFieldInsn(Opcodes.GETFIELD,AsmUtil.getType(Coroutine.class),"step",AsmUtil.getDesc(int.class));
				mn.instructions.add(sn); // jump step
				sn.max++;
				mn.instructions.add(sn.dflt);
				sn.labels.add(sn.dflt);
				for(int i=0;i<templateMethod.instructions.size();i++)
				{
					AbstractInsnNode insn=templateMethod.instructions.get(i);
					for(LocalVariableNode vn:templateMethod.localVariables)
					{
						if(i+1<templateMethod.instructions.size()&&vn.start==templateMethod.instructions.get(i+1))
							varTypes.put(vn.index,vn.desc);
						if(vn.end==insn)
							varTypes.remove(vn.index);
					}
					if(insn instanceof VarInsnNode&&insn.getOpcode()==Opcodes.ALOAD&&((VarInsnNode)insn).var==0) // this
					{
						mn.instructions.add(insn);
						mn.visitFieldInsn(Opcodes.GETFIELD,AsmUtil.getType(Coroutine.class),"function",AsmUtil.getDesc(AsyncFunction.class));
						mn.instructions.add(AsmUtil.insnCast(function.getClass(),AsyncFunction.class)); // (This)this.function
						continue;
					}
					else if(insn instanceof IincInsnNode&&insn.getOpcode()==Opcodes.IINC||insn instanceof VarInsnNode&&(AsmUtil.opcodesVarLoad.contains(insn.getOpcode())||AsmUtil.opcodesVarStore.contains(insn.getOpcode()))) // $x
					{
						int var=insn instanceof IincInsnNode?((IincInsnNode)insn).var:((VarInsnNode)insn).var;
						if(!varTypes.containsKey(var))
							throw new UnsupportedOperationException("Don't use enhanced for-loop in async function.");
						mn.instructions.add(AsmUtil.insnVarLoad(Coroutine.class,0)); // this
						int index=vars.computeIfAbsent(new MapEntry<>(var,varTypes.get(var)),k1->
						{
							cn.visitField(Opcodes.ACC_PUBLIC,"$var"+vars.size(),k1.getValue(),null,null);
							return vars.size();
						});
						if(insn instanceof IincInsnNode)
						{
							mn.visitFieldInsn(Opcodes.GETFIELD,cn.name,"$var"+index,varTypes.get(var));
							mn.instructions.add(AsmUtil.insnConst(((IincInsnNode)insn).incr));
							mn.visitInsn(Opcodes.IADD);
							mn.instructions.add(AsmUtil.insnVarLoad(Coroutine.class,0));
							mn.instructions.add(AsmUtil.insnSwap(Coroutine.class,AsmUtil.getClass(varTypes.get(var),function.getClass().getClassLoader())));
							mn.visitFieldInsn(Opcodes.PUTFIELD,cn.name,"$var"+index,varTypes.get(var));
						}
						else if(AsmUtil.opcodesVarLoad.contains(insn.getOpcode()))
							mn.visitFieldInsn(Opcodes.GETFIELD,cn.name,"$var"+index,varTypes.get(var));
						else
						{
							mn.instructions.add(AsmUtil.insnSwap(Coroutine.class,AsmUtil.getClass(varTypes.get(var),function.getClass().getClassLoader())));
							mn.visitFieldInsn(Opcodes.PUTFIELD,cn.name,"$var"+index,varTypes.get(var));
						}
						continue;
					}
					else if(insn instanceof MethodInsnNode)
					{
						MethodInsnNode in=(MethodInsnNode)insn;
						if(Objects.equals(in.name,"await")&&(Objects.equals(in.owner,AsmUtil.getType(AsyncFunction.class))||Objects.equals(in.owner,AsmUtil.getType(function.getClass()))))
						{
							sn.max++;
							mn.instructions.add(AsmUtil.insnVarLoad(Coroutine.class,0));
							mn.instructions.add(AsmUtil.insnConst(sn.max));
							mn.visitFieldInsn(Opcodes.PUTFIELD,AsmUtil.getType(Coroutine.class),"step",AsmUtil.getDesc(int.class)); // this.step=++maxStep;
							if(Objects.equals(in.desc,AsmUtil.getDesc(void.class,CompletableFuture.class))) // this.await(completableFuture);
							{
								mn.instructions.add(AsmUtil.insnVarLoad(Coroutine.class,0));
								mn.visitFieldInsn(Opcodes.GETFIELD,AsmUtil.getType(Coroutine.class),"runner",AsmUtil.getDesc(BiConsumer.class));
								mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(CompletableFuture.class),"whenComplete",AsmUtil.getDesc(CompletableFuture.class,BiConsumer.class),false);
								mn.instructions.add(AsmUtil.insnPop(CompletableFuture.class)); // completableFuture.whenComplete(this.runner);
							}
							else // this.await(basicAwait);
							{
								mn.instructions.add(AsmUtil.insnVarLoad(Coroutine.class,0));
								mn.visitFieldInsn(Opcodes.GETFIELD,AsmUtil.getType(Coroutine.class),"function",AsmUtil.getDesc(AsyncFunction.class));
								mn.visitFieldInsn(Opcodes.GETFIELD,AsmUtil.getType(AsyncFunction.class),"runner",AsmUtil.getDesc(AsyncFunctionRunner.class));
								mn.instructions.add(AsmUtil.insnSwap(AsyncFunctionRunner.class,BasicAwait.class));
								mn.instructions.add(AsmUtil.insnVarLoad(Coroutine.class,0));
								mn.instructions.add(AsmUtil.insnSwap(Coroutine.class,BasicAwait.class));
								mn.visitMethodInsn(Opcodes.INVOKEINTERFACE,AsmUtil.getType(AsyncFunctionRunner.class),"schedule",AsmUtil.getDesc(void.class,Coroutine.class,BasicAwait.class),true); // this.function.runner.schedule(this,basicAwait);
							}
							mn.instructions.add(AsmUtil.insnPop(Coroutine.class)); // pop this
							mn.instructions.add(AsmUtil.insnReturn(void.class)); // return;
							LabelNode ln=new LabelNode();
							mn.instructions.add(ln);
							sn.labels.add(ln); // next step
							continue;
						}
					}
					else if(insn instanceof InsnNode&&insn.getOpcode()==Opcodes.ARETURN) // return result;
					{
						mn.instructions.add(AsmUtil.insnVarLoad(Coroutine.class,0));
						mn.visitFieldInsn(Opcodes.GETFIELD,AsmUtil.getType(Coroutine.class),"future",AsmUtil.getDesc(CompletableFuture.class));
						mn.instructions.add(AsmUtil.insnSwap(CompletableFuture.class,Object.class));
						mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(CompletableFuture.class),"complete",AsmUtil.getDesc(boolean.class,Object.class),false);
						mn.instructions.add(AsmUtil.insnPop(boolean.class));
						mn.instructions.add(AsmUtil.insnReturn(void.class));
						continue;
					}
					mn.instructions.add(insn);
				}
				mn.visitEnd();
				cn.methods.add(mn);
				
				ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
				cn.accept(cw);
				try
				{
					MethodHandle mh=ClassUtil.findConstructor(ClassUtil.defineClass(new SimpleClassLoader(function.getClass().getClassLoader()),cn.name,cw.toByteArray()),AsyncFunction.class).asType(MethodType.methodType(Coroutine.class,AsyncFunction.class));
					ClassUtil.makeReference(function.getClass().getClassLoader(),mh);
					return new WeakRef<>(mh);
				}
				catch(Throwable e)
				{
					throw RuntimeUtil.sneakilyThrow(e);
				}
			}).get().invokeExact(function);
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
	}
}
