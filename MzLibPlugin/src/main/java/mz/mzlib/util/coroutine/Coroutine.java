package mz.mzlib.util.coroutine;

import mz.mzlib.asm.*;
import mz.mzlib.asm.tree.*;
import mz.mzlib.util.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.*;
import java.util.function.BiConsumer;

public abstract class Coroutine
{
	protected Object data;
	public Coroutine nextCoroutine;
	
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
			if(isRunning()) //start
			{
				((Coroutine)data).template().run((Coroutine)data);
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
						mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,AsmUtil.getType(raw.name),mn.name,mn.desc,false));
						mn.instructions.add(AsmUtil.insnReturn(void.class));
						mn.visitEnd();
						cn.methods.add(mn);
						mn=AsmUtil.getMethodNode(raw,"template",AsmUtil.getDesc(Yield.class,new Class[0]));
						assert mn!=null;
						Map<LabelNode,List<Pair<Integer,String>>> startVars=new HashMap<>();
						Map<LabelNode,List<Integer>> endVars=new HashMap<>();
						for(LocalVariableNode i:mn.localVariables)
						{
							startVars.computeIfAbsent(i.start,k->new ArrayList<>()).add(new Pair<>(i.index,i.desc));
							endVars.computeIfAbsent(i.end,k->new ArrayList<>()).add(i.index);
						}
						StrongRef<Integer> varsAmount=new StrongRef<>(0);
						Map<Integer,String> varNames=new HashMap<>();
						BiConsumer<Integer,String> varAllocator=(i,t)->
						{
							String name="0coroutineVar"+varsAmount.get();
							cn.visitField(Opcodes.ACC_PRIVATE,name,t,null,null).visitEnd();
							varNames.put(i,name);
							varsAmount.target++;
						};
						for(int i=0;i<mn.instructions.size();i++)
						{
							AbstractInsnNode now=mn.instructions.get(i);
							if(now instanceof LabelNode)
							{
								List<Integer> v=endVars.get(now);
								if(v!=null)
									for(Integer j:v)
										varNames.remove(j);
								List<Pair<Integer,String>> v1=startVars.get(now);
								if(v1!=null)
									for(Pair<Integer,String> j:v1)
										varAllocator.accept(j.first,j.second);
							}
							else if(AsmUtil.opcodesVarLoad.contains(now.getOpcode()) || AsmUtil.opcodesVarStore.contains(now.getOpcode()))
							{
								int index=((VarInsnNode)now).var;
								if(index!=0)
								{
									switch(now.getOpcode())
									{
										case Opcodes.ILOAD:
										case Opcodes.ISTORE:
											if(!varNames.containsKey(index) || !Objects.requireNonNull(AsmUtil.getFieldNode(cn,varNames.get(index))).desc.equals(AsmUtil.getDesc(int.class)))
												varAllocator.accept(index,AsmUtil.getDesc(int.class));
											break;
										case Opcodes.ALOAD:
										case Opcodes.ASTORE:
											if(!varNames.containsKey(index) || Objects.requireNonNull(AsmUtil.getFieldNode(cn,varNames.get(index))).desc.charAt(0)!='L')
												varAllocator.accept(index,AsmUtil.getDesc(Iterator.class));
											break;
									}
									String type=Objects.requireNonNull(AsmUtil.getFieldNode(cn,varNames.get(index))).desc;
									((VarInsnNode)now).var=0;
									if(AsmUtil.opcodesVarLoad.contains(now.getOpcode()))
										mn.instructions.insert(now,new FieldInsnNode(Opcodes.GETFIELD,cn.name,varNames.get(index),type));
									else
									{
										InsnList next=AsmUtil.insnSwap(Object.class,AsmUtil.getClass(type,cl));
										next.add(new FieldInsnNode(Opcodes.PUTFIELD,cn.name,varNames.get(index),type));
										mn.instructions.insert(now,next);
									}
								}
							}
						}
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
				((Coroutine)data).template().run((Coroutine)data);
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
