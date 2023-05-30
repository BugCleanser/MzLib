package mz.mzlib.util.delegator;

import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.asm.tree.MethodInsnNode;
import mz.mzlib.asm.tree.MethodNode;
import mz.mzlib.util.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DelegatorClassInfo
{
	public Class<? extends Delegator> delegatorClass;
	public Class<?> delegateClass=null;
	public Map<Method,Member> delegations=new ConcurrentHashMap<>();
	public DelegatorClassInfo(Class<? extends Delegator> delegatorClass)
	{
		this.delegatorClass=delegatorClass;
	}
	
	public Class<? extends Delegator> getDelegatorClass()
	{
		return delegatorClass;
	}
	
	public Class<?> getDelegateClass()
	{
		return delegateClass;
	}
	
	public static Map<Class<? extends Delegator>,WeakRef<DelegatorClassInfo>> cache=new WeakMap<>();
	public static DelegatorClassInfo get(Class<? extends Delegator> clazz)
	{
		WeakRef<DelegatorClassInfo> result=cache.get(clazz);
		if(result==null)
			synchronized(DelegatorClassInfo.class)
			{
				if(!cache.containsKey(clazz))
				{
					DelegatorClassInfo re=new DelegatorClassInfo(clazz);
					cache.put(clazz,new WeakRef<>(re));
					for(DelegatorClassAnalyzer i:DelegatorClassAnalyzerRegistrar.instance.analyzers.toArray(new DelegatorClassAnalyzer[0]))
						i.analyse(re);
					ClassUtil.makeReference(clazz.getClassLoader(),re);
					return re;
				}
				else
					return cache.get(clazz).get();
			}
		return result.get();
	}
	
	@SuppressWarnings("DeprecatedIsStillUsed")
	@Deprecated
	public MethodHandle constructorCache=null;
	@SuppressWarnings("DeprecatedIsStillUsed")
	@Deprecated
	public volatile MethodHandle constructor=null;
	public MethodHandle getConstructor()
	{
		MethodHandle result=constructorCache;
		if(result==null)
		{
			synchronized(this)
			{
				result=constructorCache=constructor;
				if(result==null)
				{
					genAClassAndPhuckTheJvm();
					result=constructorCache=constructor;
				}
			}
		}
		return result;
	}
	void genAClassAndPhuckTheJvm()
	{
		try
		{
			ClassNode cn=new ClassNode();
			cn.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,"0MzDelegatorClass",null,AsmUtil.getDesc(AbsDelegator.class),new String[0]);
			MethodNode mn=new MethodNode(Opcodes.ACC_PUBLIC,"<init>",AsmUtil.getDesc(void.class,Object.class),null,new String[0]);
			mn.instructions.add(AsmUtil.nodeVarLoad(Object.class,0));
			mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,AsmUtil.getDesc(AbsDelegator.class),"<init>",mn.desc,false));
			mn.visitEnd();
			cn.methods.add(mn);
			//TODO
			cn.visitEnd();
			ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
			cn.accept(cw);
			SimpleClassLoader cl=new SimpleClassLoader();
			constructor=ClassUtil.unreflect(cl.defineClass1(cn.name,cw.toByteArray()).getDeclaredConstructor(Object.class)).asType(MethodType.methodType(Object.class,new Class[]{Object.class}));
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
}
