package mz.mzlib.util.nothing;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.mzlib.asm.ClassReader;
import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Handle;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.*;
import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.MapEntry;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClassInfo;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

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
	public static String getMetafactoryName(String className)
	{
		return className+"$0NothingMetafactory";
	}
	public static void defineMetafactory(Class<?> clazz,CallSite[] callSites)
	{
		ClassNode cn=new ClassNode();
		cn.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,getMetafactoryName(AsmUtil.getType(clazz.getName())),null,AsmUtil.getType(Object.class),new String[0]);
		cn.outerClass=AsmUtil.getType(clazz.getName());
		
		cn.visitField(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC,"callSites",AsmUtil.getDesc(CallSite[].class),null,null).visitEnd();
		
		MethodNode mn=new MethodNode(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC,"metafactory",AsmUtil.getDesc(CallSite.class,MethodHandles.Lookup.class,String.class,MethodType.class,int.class),null,new String[0]);
		mn.visitFieldInsn(Opcodes.GETSTATIC,cn.name,"callSites",AsmUtil.getDesc(CallSite[].class));
		mn.instructions.add(AsmUtil.insnArrayLoad(CallSite.class,AsmUtil.toList(AsmUtil.insnVarLoad(int.class,3))));
		mn.instructions.add(AsmUtil.insnReturn(CallSite.class));
		mn.visitEnd();
		cn.methods.add(mn);
		
		ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		try
		{
			ClassUtil.defineClass(clazz.getClassLoader(),cn.name,cw.toByteArray()).getField("callSites").set(null,callSites);
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
	}
	public static AbstractInsnNode insnInvokeDynamic(String className,int callSiteIndex,String desc)
	{
		return new InvokeDynamicInsnNode("invokeDynamic",desc,new Handle(Opcodes.H_INVOKESTATIC,getMetafactoryName(className),"metafactory",AsmUtil.getDesc(CallSite.class,MethodHandles.Lookup.class,String.class,MethodType.class,int.class),false),callSiteIndex);
	}
	public void apply()
	{
		List<CallSite> callSites=new ArrayList<>();
		try
		{
			callSites.add(ClassUtil.getMethodCallSite(Root.getTrusted(Delegator.class),"getDelegate",MethodType.methodType(Object.class,Object.class),Delegator.class,MethodType.methodType(Object.class),0));
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
		ClassNode cn=new ClassNode();
		new ClassReader(rawByteCode).accept(cn,0);
		Map<MethodNode,AbstractInsnNode[]> raws=new HashMap<>();
		for(MethodNode m:cn.methods)
			raws.put(m,m.instructions.toArray());
		Map<MethodNode,Map<String,Integer>> customVars=new HashMap<>();
		PriorityQueue<MapEntry<Float,Runnable>> operations=new PriorityQueue<>(Map.Entry.comparingByKey());
		for(Class<? extends Nothing> nothing:nothings)
		{
			if(!ElementSwitcher.isEnabled(nothing))
				continue;
			int nothingConstructor=callSites.size();
			callSites.add(Delegator.getConstructorCallSite(Root.getTrusted(nothing),"create",MethodType.methodType(Object.class,Object.class),RuntimeUtil.cast(nothing)));
			for(Method i: nothing.getDeclaredMethods())
			{
				if(!ElementSwitcher.isEnabled(i))
					continue;
				for(NothingInject ni: i.getDeclaredAnnotationsByType(NothingInject.class))
				{
					Executable m;
					try
					{
						m=(Executable)DelegatorClassInfo.get(RuntimeUtil.cast(nothing)).delegations.get(Arrays.stream(nothing.getMethods()).filter(j->j.getName().equals(ni.delegatorMethod())).findFirst().orElse(null));
						Objects.requireNonNull(m);
					}
					catch(NullPointerException e)
					{
						throw RuntimeUtil.sneakilyThrow(new NoSuchMethodException("Target of "+i).initCause(e));
					}
					operations.add(new MapEntry<>(ni.priority(),()->
					{
						MethodNode mn=AsmUtil.getMethodNode(cn,m.getName(),AsmUtil.getDesc(m));
						assert mn!=null;
						AbstractInsnNode[] raw=raws.get(mn);
						Set<Integer> locs=new HashSet<>();
						locs.add(0);
						for(LocatingStep j: ni.locatingSteps())
						{
							Set<Integer> lastLocs=locs;
							locs=new HashSet<>();
							for(int k: lastLocs)
								switch(j.type())
								{
									case OFFSET:
										if(k+j.arg()>=0 && k+j.arg()<raw.length)
											locs.add(k+j.arg());
										break;
									case AFTER_FIRST:
									case AFTER_ALL:
										for(int l=1;l<=j.maxDistance() && k+l<raw.length;l++)
											if(raw[k+l].getOpcode()==j.arg())
											{
												locs.add(k+l);
												if(j.type()==LocatingStepType.AFTER_FIRST)
													break;
											}
										break;
								}
						}
						for(int j: locs)
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
									InsnList loadingVars=new InsnList(), afterCall=new InsnList(), afterPop=new InsnList();
									Class<?>[] paramTypes=i.getParameterTypes();
									Class<?>[] argTypes=new Class[paramTypes.length];
									Parameter[] ps=i.getParameters();
									int[] args=new int[paramTypes.length];
									for(int k=0;k<i.getParameterCount();k++)
									{
										args[k]=mn.maxLocals;
										mn.maxLocals+=AsmUtil.getCategory(paramTypes[k]);
										LocalVar lv=ps[k].getDeclaredAnnotation(LocalVar.class);
										if(lv!=null)
										{
											Class<?> lvt=paramTypes[k];
											if(Delegator.class.isAssignableFrom(lvt))
												lvt=Delegator.getDelegateClass(RuntimeUtil.cast(lvt));
											loadingVars.add(AsmUtil.insnVarLoad(lvt,lv.value()));
											if(Delegator.class.isAssignableFrom(paramTypes[k]))
											{
												loadingVars.add(AsmUtil.insnCast(Object.class,lvt));
												loadingVars.add(insnInvokeDynamic(cn.name,callSites.size(),AsmUtil.getDesc(Object.class,Object.class)));
												callSites.add(Delegator.getConstructorCallSite(Root.getTrusted(paramTypes[k]),"create",MethodType.methodType(Object.class,Object.class),RuntimeUtil.cast(paramTypes[k])));
												int delegator=mn.maxLocals++;
												loadingVars.add(AsmUtil.insnDup(Delegator.class));
												loadingVars.add(AsmUtil.insnVarStore(Delegator.class,delegator));
												argTypes[k]=Object.class;
												afterCall.add(AsmUtil.insnVarLoad(Delegator.class,delegator));
												afterCall.add(insnInvokeDynamic(cn.name,0,AsmUtil.getDesc(Object.class,Object.class)));
												afterCall.add(AsmUtil.insnCast(lvt,Object.class));
												afterCall.add(AsmUtil.insnVarStore(lvt,lv.value()));
											}
											else
												argTypes[k]=lvt;
										}
										CustomVar cv=ps[k].getDeclaredAnnotation(CustomVar.class);
										if(cv!=null)
										{
											int index=customVars.computeIfAbsent(mn,it->new HashMap<>()).computeIfAbsent(cv.value(),it->
											{
												mn.instructions.insert(AsmUtil.insnVarStore(Object.class,mn.maxLocals));
												mn.instructions.insert(AsmUtil.insnConst(null));
												return mn.maxLocals++;
											});
											loadingVars.add(AsmUtil.insnVarLoad(Object.class,index));
											if(Delegator.class.isAssignableFrom(paramTypes[k]))
											{
												loadingVars.add(insnInvokeDynamic(cn.name,callSites.size(),AsmUtil.getDesc(Object.class,Object.class)));
												callSites.add(Delegator.getConstructorCallSite(Root.getTrusted(paramTypes[k]),"create",MethodType.methodType(Object.class,Object.class),RuntimeUtil.cast(paramTypes[k])));
												int delegator=mn.maxLocals++;
												loadingVars.add(AsmUtil.insnDup(Delegator.class));
												loadingVars.add(AsmUtil.insnVarStore(Delegator.class,delegator));
												argTypes[k]=Object.class;
												afterCall.add(AsmUtil.insnVarLoad(Delegator.class,delegator));
												afterCall.add(insnInvokeDynamic(cn.name,0,AsmUtil.getDesc(Object.class,Object.class)));
												afterCall.add(AsmUtil.insnVarStore(Object.class,index));
											}
											else
												argTypes[k]=Object.class;
										}
										StackTop st=ps[k].getDeclaredAnnotation(StackTop.class);
										if(st!=null)
										{
											Class<?> stt=paramTypes[k];
											if(Delegator.class.isAssignableFrom(stt))
												stt=Delegator.getDelegateClass(RuntimeUtil.cast(stt));
											if(Delegator.class.isAssignableFrom(paramTypes[k]))
											{
												loadingVars.add(AsmUtil.insnCast(Object.class,stt));
												loadingVars.add(insnInvokeDynamic(cn.name,callSites.size(),AsmUtil.getDesc(Object.class,Object.class)));
												callSites.add(Delegator.getConstructorCallSite(Root.getTrusted(paramTypes[k]),"create",MethodType.methodType(Object.class,Object.class),RuntimeUtil.cast(paramTypes[k])));
												loadingVars.add(AsmUtil.insnDup(Delegator.class));
												int delegator=mn.maxLocals++;
												loadingVars.add(AsmUtil.insnVarStore(Delegator.class,delegator));
												argTypes[k]=Object.class;
												afterPop.add(AsmUtil.insnVarLoad(Delegator.class,delegator));
												afterPop.add(insnInvokeDynamic(cn.name,0,AsmUtil.getDesc(Object.class,Object.class)));
												afterPop.add(AsmUtil.insnCast(stt,Object.class));
											}
											else
											{
												int temp=mn.maxLocals;
												mn.maxLocals+=AsmUtil.getCategory(stt);
												loadingVars.add(AsmUtil.insnDup(stt));
												loadingVars.add(AsmUtil.insnVarStore(stt,temp));
												argTypes[k]=stt;
												afterPop.add(AsmUtil.insnVarLoad(stt,temp));
											}
										}
										if(argTypes[k]==null)
											throw new UnsupportedOperationException(i+" "+ps[k].getType().toString());
										loadingVars.add(AsmUtil.insnVarStore(argTypes[k],args[k]));
									}
									InsnList caller=new InsnList();
									caller.add(loadingVars);
									Class<?>[] invokeType=argTypes;
									if(!Modifier.isStatic(m.getModifiers()))
									{
										caller.add(AsmUtil.insnVarLoad(Object.class,0));
										caller.add(insnInvokeDynamic(cn.name,nothingConstructor,AsmUtil.getDesc(Object.class,Object.class)));
										invokeType=Stream.concat(Stream.of(Object.class),Stream.of(invokeType)).toArray(Class<?>[]::new);
									}
									for(int k=0;k<args.length;k++)
										caller.add(AsmUtil.insnVarLoad(argTypes[k],args[k]));
									caller.add(insnInvokeDynamic(cn.name,callSites.size(),AsmUtil.getDesc(Object.class,invokeType)));
									callSites.add(new ConstantCallSite(ClassUtil.unreflect(i).asType(MethodType.methodType(Object.class,invokeType))));
									caller.add(afterCall);
									caller.add(AsmUtil.insnDup(Object.class));
									LabelNode later=new LabelNode();
									caller.add(new JumpInsnNode(Opcodes.IFNULL,later));
									caller.add(insnInvokeDynamic(cn.name,0,AsmUtil.getDesc(Object.class,Object.class)));
									caller.add(AsmUtil.insnCast(ClassUtil.getReturnType(m),Object.class));
									caller.add(AsmUtil.insnReturn(ClassUtil.getReturnType(m)));
									caller.add(later);
									caller.add(AsmUtil.insnPop(Object.class));
									caller.add(afterPop);
									switch(ni.type())
									{
										case INSERT_BEFORE:
											mn.instructions.insertBefore(raw[j],caller);
											break;
										case CATCH:
											LabelNode ln1=new LabelNode(), ln2=new LabelNode(), ln3=new LabelNode();
											mn.instructions.insertBefore(raw[j],ln1);
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
			}
		}
		while(!operations.isEmpty())
			operations.poll().getValue().run();
		defineMetafactory(this.target,callSites.toArray(new CallSite[0]));
		ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		ClassUtil.defineClass(target.getClassLoader(),cn.name,cw.toByteArray());
	}
}
