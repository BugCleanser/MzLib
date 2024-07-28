package mz.mzlib.util.nothing;

import mz.mzlib.asm.ClassReader;
import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.*;
import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.MapEntry;
import mz.mzlib.util.PublicValues;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.delegator.Delegator;

import java.lang.reflect.Array;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NothingRegistration
{
	public Class<?> target;
	public Set<Class<? extends Nothing>> nothings;
	public Queue<Integer> resources;
	public byte[] rawByteCode;
	
	public NothingRegistration(Class<?> target)
	{
		this.target=target;
		this.nothings=ConcurrentHashMap.newKeySet();
		this.resources=new ArrayDeque<>();
		this.rawByteCode=ClassUtil.getByteCode(target);
	}
	
	public synchronized void add(Class<? extends Nothing> nothing)
	{
		if(!nothings.add(nothing))
			throw new IllegalStateException("Duplicately add Nothing class: "+nothing);
		this.apply();
	}
	public synchronized void remove(Class<? extends Nothing> nothing)
	{
		if(!nothings.remove(nothing))
			throw new IllegalStateException("Remove the unadded Nothing class: "+nothing);
		this.apply();
	}
	
	public boolean isEmpty()
	{
		return nothings.isEmpty();
	}
	
	public void apply()
	{
		free();
		ClassNode cn=new ClassNode();
		new ClassReader(rawByteCode).accept(cn,0);
		Map<MethodNode,AbstractInsnNode[]> raws=new HashMap<>();
		for(MethodNode m:cn.methods)
			raws.put(m,m.instructions.toArray());
		PriorityQueue<MapEntry<Float,Runnable>> operations=new PriorityQueue<>(Map.Entry.comparingByKey());
		for(Class<? extends Nothing> nothing:nothings)
			for(Method i:nothing.getDeclaredMethods())
				for(NothingInject ni:i.getDeclaredAnnotationsByType(NothingInject.class))
				{
					Executable m=Delegator.findExecutable(target,ni.methodNames(),ni.methodArgs());
					if(m==null)
						throw RuntimeUtil.sneakilyThrow(new NoSuchMethodException("Target of "+i));
					operations.add(new MapEntry<>(ni.priority(),()->
					{
						MethodNode mn=AsmUtil.getMethodNode(cn,m.getName(),AsmUtil.getDesc(m));
						assert mn!=null;
						AbstractInsnNode[] raw=raws.get(mn);
						Set<Integer> locs=new HashSet<>();
						locs.add(0);
						for(LocatingStep j:ni.locatingSteps())
						{
							Set<Integer> lastLocs=locs;
							locs=new HashSet<>();
							for(int k:lastLocs)
								switch(j.type())
								{
								case OFFSET:
									if(k+j.arg()>=0&&k+j.arg()<raw.length)
										locs.add(k+j.arg());
									break;
								case AFTER_FIRST:
								case AFTER_ALL:
									for(int l=1;l<=j.maxDistance()&&k+l<raw.length;l++)
										if(raw[k+l].getOpcode()==j.arg())
										{
											locs.add(k+l);
											if(j.type()==LocatingStepType.AFTER_FIRST)
												break;
										}
									break;
								}
						}
						if(locs.size()<ni.expectedMin()||locs.size()>ni.expectedMax())
							throw new IllegalStateException("Illegal Nothing injecting locations num: "+i);
						for(int j:locs)
							switch(ni.type())
							{
								case RAW:
									try
									{
										i.invoke(null,m,mn,raw[j]);
									}
									catch(Throwable e)
									{
										throw RuntimeUtil.sneakilyThrow(e);
									}
									break;
								case SKIP:
									LabelNode ln=new LabelNode();
									mn.instructions.insertBefore(raw[j],new JumpInsnNode(Opcodes.GOTO,ln));
									mn.instructions.insertBefore(raw[j+ni.length()],ln);
									break;
								default:
									InsnList caller=new InsnList();
									InsnList loadingVars=new InsnList(),afterCall=new InsnList();
									for(int k=0;k<i.getParameterCount();k++)
									{
										Class<?> t=i.getParameterTypes()[k].getComponentType();
										if(t==null)
											throw new IllegalArgumentException("Non-array parameter on "+i);
										int tn=-1;
										if(Delegator.class.isAssignableFrom(t))
											tn=alloc(t);
										if(tn==-1)
											caller.add(AsmUtil.insnArray(AsmUtil.toList(AsmUtil.insnConst(1)),t));
										else
										{
											caller.add(AsmUtil.insnGetPublicValue(tn));
											caller.add(AsmUtil.insnCast(Class.class,Object.class));
											caller.add(AsmUtil.insnConst(1));
											caller.add(new MethodInsnNode(Opcodes.INVOKESTATIC,AsmUtil.getType(Array.class),"newInstance",AsmUtil.getDesc(Object.class,Class.class,int.class),false));
											caller.add(AsmUtil.insnCast(Object[].class,Object.class));
										}
										caller.add(AsmUtil.insnDup(Object[].class));
										int num=mn.maxLocals++;
										caller.add(AsmUtil.insnVarStore(Object.class,num));
										loadingVars.add(AsmUtil.insnVarLoad(Object.class,num));
										LocalVar lv=i.getAnnotatedParameterTypes()[k].getDeclaredAnnotation(LocalVar.class);
										if(lv!=null)
										{
											InsnList value=AsmUtil.toList(AsmUtil.insnVarLoad(t,lv.value()));
											if(tn!=-1)
												value.add(AsmUtil.insnCreateDelegator(AsmUtil.insnGetPublicValue(tn)));
											caller.add(AsmUtil.insnArrayStore(t,AsmUtil.toList(AsmUtil.insnConst(0)),value));
											continue;
										}
										throw new IllegalArgumentException("Parameter without annotation on "+i);
									}
									caller.add(loadingVars);
									//TODO
									caller.add(afterCall);
									caller.add(AsmUtil.insnDup(Object.class));
									LabelNode later=new LabelNode();
									caller.add(new JumpInsnNode(Opcodes.IFNULL,later));
									caller.add(AsmUtil.insnArrayLoad(i.getReturnType().getComponentType(),AsmUtil.toList(AsmUtil.insnConst(0))));
									if(Delegator.class.isAssignableFrom(i.getReturnType().getComponentType()))
									{
										caller.add(AsmUtil.insnGetDelegate());
										caller.add(AsmUtil.insnCast(ClassUtil.getReturnType(m),Object.class));
									}
									else
										caller.add(AsmUtil.insnCast(ClassUtil.getReturnType(m),i.getReturnType().getComponentType()));
									caller.add(AsmUtil.insnReturn(ClassUtil.getReturnType(m)));
									caller.add(later);
									caller.add(AsmUtil.insnPop(Object.class));
									switch(ni.type())
									{
									case INSERT_BEFORE:
										mn.instructions.insertBefore(raw[j],caller);
										break;
									case CATCH:
										LabelNode ln1=new LabelNode(), ln2=new LabelNode(), ln3=new LabelNode();
										mn.instructions.insertBefore(raw[j],new JumpInsnNode(Opcodes.GOTO,ln1));
										InsnList il=new InsnList();
										il.add(new JumpInsnNode(Opcodes.GOTO,ln3));
										il.add(ln2);
										il.add(caller);
										caller.add(AsmUtil.insnPop(Throwable.class));
										il.add(ln3);
										mn.instructions.insertBefore(raw[j+ni.length()],il);
										mn.visitTryCatchBlock(ln1.getLabel(),ln2.getLabel(),ln2.getLabel(),AsmUtil.getType(Throwable.class));
										break;
									}
									break;
							}
					}));
				}
		while(!operations.isEmpty())
			operations.poll().getValue().run();
		ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		ClassUtil.defineClass(target.getClassLoader(),cn.name,cw.toByteArray());
	}
	
	public int alloc(Object obj)
	{
		int result=PublicValues.alloc();
		PublicValues.set(result,obj);
		this.resources.add(result);
		return result;
	}
	public void free()
	{
		while(!this.resources.isEmpty())
			PublicValues.free(this.resources.poll());
	}
}
