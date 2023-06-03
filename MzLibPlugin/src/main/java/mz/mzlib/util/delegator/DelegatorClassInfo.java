package mz.mzlib.util.delegator;

import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.*;
import mz.mzlib.util.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
			cn.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,"0MzDelegatorClass",null,AsmUtil.getType(AbsDelegator.class),new String[0]);
			MethodNode mn=new MethodNode(Opcodes.ACC_PUBLIC,"<init>",AsmUtil.getDesc(void.class,Object.class),null,new String[0]);
			mn.instructions.add(AsmUtil.nodeVarLoad(Object.class,0));
			mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,AsmUtil.getType(AbsDelegator.class),mn.name,mn.desc,false));
			mn.visitEnd();
			cn.methods.add(mn);
			List<MethodHandle> methodHandles=new ArrayList<>();
			String mhSuffix="mzDelegateMH";
			for(Map.Entry<Method,Member> i:delegations.entrySet())
			{
				boolean isPublic=Modifier.isPublic(getDelegateClass().getModifiers())&&Modifier.isPublic(i.getValue().getModifiers());
				Class<?>[] pts=i.getKey().getParameterTypes();
				mn=new MethodNode(Opcodes.ACC_PUBLIC,i.getKey().getName(),AsmUtil.getDesc(i.getKey()),null,new String[0]);
				if(i.getValue() instanceof Constructor)
				{
					Class<?>[] ptsTar=((Constructor<?>)i.getValue()).getParameterTypes();
					if(isPublic)
					{
						mn.instructions.add(new TypeInsnNode(Opcodes.NEW,AsmUtil.getType(getDelegateClass())));
						mn.instructions.add(AsmUtil.nodeDup(getDelegateClass()));
						for(int j=0;j<pts.length;j++)
						{
							mn.instructions.add(AsmUtil.nodeVarLoad(pts[j],1+j));
							if(Delegator.class.isAssignableFrom(pts[j]))
							{
								mn.instructions.add(AsmUtil.nodeGetDelegate());
								mn.instructions.add(AsmUtil.nodeCast(ptsTar[j],Object.class));
							}
							else
								mn.instructions.add(AsmUtil.nodeCast(ptsTar[j],pts[j]));
						}
						mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,AsmUtil.getType(getDelegateClass()),"<init>",AsmUtil.getDesc((Constructor<?>)i.getValue())));
					}
					else
					{
						for(int j=0;j<pts.length;j++)
						{
							mn.instructions.add(AsmUtil.nodeVarLoad(pts[j],1+j));
							if(Delegator.class.isAssignableFrom(pts[j]))
							{
								mn.instructions.add(AsmUtil.nodeGetDelegate());
								ptsTar[j]=Object.class;
							}
							else
								ptsTar[j]=pts[j];
						}
						mn.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC,cn.name,methodHandles.size()+mhSuffix,AsmUtil.getDesc(MethodHandle.class)));
						methodHandles.add(ClassUtil.unreflect((Constructor<?>)i.getValue()).asType(MethodType.methodType(Object.class,ptsTar)));
						mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(MethodHandle.class),"invokeExact",AsmUtil.getDesc(Object.class,ptsTar)));
					}
					mn.instructions.add(AsmUtil.nodeCreateDelegator(getDelegatorClass()));
				}
				else if(i.getValue() instanceof Method)
				{
					//TODO
				}
				else if(i.getValue() instanceof Field)
				{
					Class<?> type=((Field)i.getValue()).getType();
					switch(pts.length)
					{
						case 0:
							if(isPublic)
							{
								if(Modifier.isStatic(i.getValue().getModifiers()))
									mn.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC,AsmUtil.getType(getDelegateClass()),i.getValue().getName(),AsmUtil.getDesc(type)));
								else
								{
									mn.instructions.add(AsmUtil.nodeVarLoad(getDelegatorClass(),0));
									mn.instructions.add(AsmUtil.nodeGetDelegate());
									mn.instructions.add(AsmUtil.nodeCast(getDelegateClass(),Object.class));
									mn.instructions.add(new FieldInsnNode(Opcodes.GETFIELD,AsmUtil.getType(getDelegateClass()),i.getValue().getName(),AsmUtil.getDesc(type)));
								}
							}
							else
							{
								
							}
							if(Delegator.class.isAssignableFrom(i.getKey().getReturnType()))
								mn.instructions.add(AsmUtil.nodeCreateDelegator(RuntimeUtil.forceCast(i.getKey().getReturnType())));
							else
								mn.instructions.add(AsmUtil.nodeCast(i.getKey().getReturnType(),type));
							mn.instructions.add(AsmUtil.nodeReturn(i.getKey().getReturnType()));
							break;
						case 1:
							break;
						default:
							throw new AssertionError();
					}
				}
				else
					throw new UnsupportedOperationException(Objects.toString(i.getValue()));
				mn.visitEnd();
				cn.methods.add(mn);
			}
			for(int i=0;i<methodHandles.size();i++)
				cn.visitField(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC,i+mhSuffix,AsmUtil.getDesc(MethodHandle.class),null,null).visitEnd();
			cn.visitEnd();
			ClassWriter cw=new ClassWriter(delegatorClass.getClassLoader());
			cn.accept(cw);
			SimpleClassLoader cl=new SimpleClassLoader();
			Class<?> c=cl.defineClass1(cn.name,cw.toByteArray());
			for(int i=0;i<methodHandles.size();i++)
				c.getDeclaredField(i+mhSuffix).set(null,methodHandles.get(i));
			constructor=ClassUtil.unreflect(c.getDeclaredConstructor(Object.class)).asType(MethodType.methodType(Object.class,new Class[]{Object.class}));
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
}
