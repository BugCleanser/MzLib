package mz.mzlib.util.nothing;

import mz.mzlib.asm.ClassReader;
import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.*;
import mz.mzlib.util.*;
import mz.mzlib.util.delegator.Delegator;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
						throw RuntimeUtil.forceThrow(new NoSuchMethodException("Target of "+i));
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
										throw RuntimeUtil.forceThrow(e);
									}
									break;
								case SKIP:
									LabelNode ln=new LabelNode();
									mn.instructions.insertBefore(raw[j],new JumpInsnNode(Opcodes.GOTO,ln));
									mn.instructions.insertBefore(raw[j+ni.length()],ln);
									break;
								case CATCH:
									LabelNode ln1=new LabelNode(),ln2=new LabelNode(),ln3=new LabelNode();
									mn.instructions.insertBefore(raw[j],new JumpInsnNode(Opcodes.GOTO,ln1));
									InsnList il=new InsnList();
									il.add(new JumpInsnNode(Opcodes.GOTO,ln3));
									il.add(ln2);
									MethodType methodType=MethodType.methodType(Optional.class,CollectionUtil.addAll(CollectionUtil.newArrayList(Object.class),m.getParameterTypes()).stream().map(ClassUtil::baseType).collect(Collectors.toList()));
									il.add(AsmUtil.insnConst(alloc()));
									il.add(new MethodInsnNode(Opcodes.INVOKESTATIC,AsmUtil.getType(PublicValues.class),"get",AsmUtil.getDesc(Object.class,int.class)));
									il.add(AsmUtil.insnCast(MethodHandle.class,Object.class));
									if(!Modifier.isStatic(m.getModifiers()))
										il.add(AsmUtil.insnVarLoad(Object.class,0));
									else
										il.add(AsmUtil.insnConst(null));
									for(int k=0;k<methodType.parameterCount();k++)
										il.add(AsmUtil.insnVarLoad(methodType.parameterType(k),k+1));
									il.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(MethodHandle.class),"invokeExact",AsmUtil.getDesc(methodType)));
									il.add(AsmUtil.insnDup(Optional.class));
									LabelNode ln4=new LabelNode();
									il.add(new JumpInsnNode(Opcodes.IFNULL,ln4));
									il.add(AsmUtil.insnConst(null));
									il.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(Optional.class),"orElse",AsmUtil.getDesc(Object.class,Object.class)));
									il.add(AsmUtil.insnCast(ClassUtil.getReturnType(m),Object.class));
									il.add(AsmUtil.insnReturn(ClassUtil.getReturnType(m)));
									il.add(ln4);
									il.add(AsmUtil.insnPop(Optional.class));
									il.add(ln3);
									mn.instructions.insertBefore(raw[j+ni.length()],il);
									mn.visitTryCatchBlock(ln1.getLabel(),ln2.getLabel(),ln2.getLabel(),AsmUtil.getType(Throwable.class));
									break;
								case INSERT_BEFORE:
									//TODO
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
