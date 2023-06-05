package mz.mzlib.util.coroutine;

import mz.mzlib.asm.ClassReader;
import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.*;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.Map;

public abstract class Coroutine
{
	protected Object data;
	public Coroutine nextCoroutine;
	
	public abstract MzModule getModule();
	
	/**
	 * Implement this template method
	 * You can only use local var with type int or Object (not including subclasses)
	 * You can use "if(RuntimeUtil.TRUE) return yield;" to yield, where "yield" determines when the coroutine will continue
	 * Return new YieldBreak() to end the coroutine
	 */
	public abstract Yield template();
	
	public boolean isRunning()
	{
		return data!=null;
	}
	
	public static Map<Class<?>,WeakRef<MethodHandle>> cache=new WeakMap<>();
	public Yield run()
	{
		try
		{
			if(isRunning())
			{
				((Coroutine)data).template().run(this);
				return null;
			}
			else
			{
				data=(Object)cache.computeIfAbsent(this.getClass(),c->
				{
					try
					{
						SimpleClassLoader cl=new SimpleClassLoader(Coroutine.this.getClass().getClassLoader());
						ClassNode raw=new ClassNode();
						new ClassReader(ClassUtil.getByteCode(Coroutine.this.getClass())).accept(raw,0);
						ClassNode cn=new ClassNode();
						cn.visit(raw.version,Opcodes.ACC_PUBLIC,"0MzCoroutine",null,raw.name,new String[0]);
						MethodNode mn=new MethodNode(Opcodes.ACC_PUBLIC,"<init>",AsmUtil.getDesc(void.class,new Class[0]),null,new String[0]);
						mn.instructions.add(AsmUtil.insnVarLoad(Object.class,0));
						mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,AsmUtil.getType(raw.name),mn.name,mn.desc,false));
						mn.instructions.add(AsmUtil.insnReturn(void.class));
						mn.visitEnd();
						cn.methods.add(mn);
						cn.visitField(Opcodes.ACC_PUBLIC,"0mzCoroutineIndex",AsmUtil.getDesc(int.class),null,0);
						MethodNode mn0=AsmUtil.getMethodNode(raw,"template",AsmUtil.getDesc(Yield.class,new Class[0]));
						assert mn0!=null;
						mn=new MethodNode(Opcodes.ACC_PUBLIC,mn0.name,mn0.desc,null,new String[0]);
						mn.instructions.add(AsmUtil.insnVarLoad(Object.class,0));
						mn.instructions.add(new FieldInsnNode(Opcodes.GETFIELD,cn.name,"0mzCoroutineIndex",AsmUtil.getDesc(int.class)));
						TableSwitchInsnNode table=new TableSwitchInsnNode(0,-1,new LabelNode());
						mn.instructions.add(table);
						table.max++;
						table.labels.add(table.dflt);
						mn.instructions.add(table.dflt);
						for(int i=0;i<mn0.instructions.size();i++)
						{
							AbstractInsnNode now=mn0.instructions.get(i);
							if(now.getOpcode()==Opcodes.ARETURN)
							{
								table.max++;
								mn.instructions.add(AsmUtil.insnVarLoad(Object.class,0));
								mn.instructions.add(AsmUtil.insnConst(table.max));
								mn.instructions.add(new FieldInsnNode(Opcodes.PUTFIELD,cn.name,"0mzCoroutineIndex",AsmUtil.getDesc(int.class)));
								mn.instructions.add(now);
								LabelNode l=new LabelNode();
								mn.instructions.add(l);
								table.labels.add(l);
							}
							else if(now.getOpcode()==Opcodes.IINC)
							{
								String varName="0mzCoroutineVarInt"+((IincInsnNode)now).var;
								mn.instructions.add(AsmUtil.insnVarLoad(Object.class,0));
								mn.instructions.add(AsmUtil.insnVarLoad(Object.class,0));
								mn.instructions.add(new FieldInsnNode(Opcodes.GETFIELD,cn.name,varName,AsmUtil.getDesc(int.class)));
								mn.instructions.add(AsmUtil.insnConst(((IincInsnNode)now).incr));
								mn.instructions.add(new InsnNode(Opcodes.IADD));
								mn.instructions.add(new FieldInsnNode(Opcodes.PUTFIELD,cn.name,varName,AsmUtil.getDesc(int.class)));
							}
							else if(AsmUtil.opcodesVarLoad.contains(now.getOpcode()) || AsmUtil.opcodesVarStore.contains(now.getOpcode()))
							{
								int index=((VarInsnNode)now).var;
								String type,varName;
								if(now.getOpcode()==Opcodes.ILOAD||now.getOpcode()==Opcodes.ISTORE)
								{
									type=AsmUtil.getDesc(int.class);
									varName="0mzCoroutineVarInt"+index;
								}
								else
								{
									type=AsmUtil.getDesc(Object.class);
									varName="0mzCoroutineVarObject"+index;
								}
								if(AsmUtil.getFieldNode(cn,varName)==null)
									cn.visitField(Opcodes.ACC_PRIVATE,varName,type,null,null);
								mn.instructions.add(AsmUtil.insnVarLoad(Object.class,0));
								if(index!=0)
								{
									if(AsmUtil.opcodesVarLoad.contains(now.getOpcode()))
										mn.instructions.add(new FieldInsnNode(Opcodes.GETFIELD,cn.name,varName,type));
									else
									{
										mn.instructions.add(AsmUtil.insnSwap(Object.class,AsmUtil.getClass(type,cl)));
										mn.instructions.add(new FieldInsnNode(Opcodes.PUTFIELD,cn.name,varName,type));
									}
								}
							}
							else
								mn.instructions.add(now);
						}
						mn.instructions.add(new TypeInsnNode(Opcodes.NEW,AsmUtil.getType(YieldBreak.class)));
						mn.instructions.add(AsmUtil.insnDup(YieldBreak.class));
						mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,AsmUtil.getType(YieldBreak.class),"<init>",AsmUtil.getDesc(void.class,new Class[0])));
						mn.instructions.add(AsmUtil.insnReturn(Yield.class));
						mn.visitEnd();
						cn.methods.add(mn);
						cn.visitEnd();
						ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
						cn.accept(cw);
						MethodHandle result=ClassUtil.unreflect(ClassUtil.defineClass(cl,cn.name,cw.toByteArray()).getDeclaredConstructor()).asType(MethodType.methodType(Object.class));
						ClassUtil.makeReference(Coroutine.this.getClass().getClassLoader(),result);
						return new WeakRef<>(result);
					}
					catch(Throwable e)
					{
						throw RuntimeUtil.forceThrow(e);
					}
				}).get().invokeExact();
				((Coroutine)data).template().run(this);
				if(isRunning())
					return c->nextCoroutine=c;
				else
					return Coroutine::run;
			}
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
}
