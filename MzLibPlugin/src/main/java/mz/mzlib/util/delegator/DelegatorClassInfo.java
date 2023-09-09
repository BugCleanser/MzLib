package mz.mzlib.util.delegator;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.*;
import mz.mzlib.util.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.util.*;
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
	
	public static WeakMap<Class<? extends Delegator>,WeakRef<DelegatorClassInfo>> cache=new WeakMap<>();
	@SuppressWarnings("all")
	public static DelegatorClassInfo get(Class<? extends Delegator> clazz)
	{
		WeakRef<DelegatorClassInfo> result=cache.get(clazz);
		if(result==null)
			synchronized(cache.delegate)
			{
				WeakRef<DelegatorClassInfo> up=cache.get(clazz);
				if(up!=null)
					return up.get();
				DelegatorClassInfo re=new DelegatorClassInfo(clazz);
				cache.put(clazz,new WeakRef<>(re));
				for(DelegatorClassAnalyzer i:DelegatorClassAnalyzerRegistrar.instance.analyzers.toArray(new DelegatorClassAnalyzer[0]))
					i.analyse(re);
				ClassUtil.makeReference(clazz.getClassLoader(),re);
				return re;
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
			cn.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,"0MzDelegatorClass",null,AsmUtil.getType(AbsDelegator.class),new String[]{AsmUtil.getType(getDelegatorClass())});
			MethodNode mn=new MethodNode(Opcodes.ACC_PUBLIC,"<init>",AsmUtil.getDesc(void.class,Object.class),null,new String[0]);
			mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(),0));
			mn.instructions.add(AsmUtil.insnVarLoad(Object.class,1));
			mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,AsmUtil.getType(AbsDelegator.class),mn.name,mn.desc,false));
			mn.instructions.add(AsmUtil.insnReturn(void.class));
			mn.visitEnd();
			cn.methods.add(mn);
			List<MethodHandle> methodHandles=new ArrayList<>();
			String mhSuffix="mzDelegateMH";
			for(Map.Entry<Method,Member> i:delegations.entrySet())
			{
				boolean isPublic=Modifier.isPublic(i.getValue().getDeclaringClass().getModifiers())&&Modifier.isPublic(i.getValue().getModifiers());
				Class<?>[] pts=i.getKey().getParameterTypes();
				mn=new MethodNode(Opcodes.ACC_PUBLIC,i.getKey().getName(),AsmUtil.getDesc(i.getKey()),null,new String[0]);
				if(i.getValue() instanceof Constructor)
				{
					Class<?>[] ptsTar=((Constructor<?>)i.getValue()).getParameterTypes();
					if(isPublic)
					{
						mn.instructions.add(new TypeInsnNode(Opcodes.NEW,AsmUtil.getType(i.getValue().getDeclaringClass())));
						mn.instructions.add(AsmUtil.insnDup(i.getValue().getDeclaringClass()));
						for(int j=0;j<pts.length;j++)
						{
							mn.instructions.add(AsmUtil.insnVarLoad(pts[j],1+j));
							if(Delegator.class.isAssignableFrom(pts[j]))
							{
								mn.instructions.add(AsmUtil.insnGetDelegate());
								mn.instructions.add(AsmUtil.insnCast(ptsTar[j],Object.class));
							}
							else
								mn.instructions.add(AsmUtil.insnCast(ptsTar[j],pts[j]));
						}
						mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,AsmUtil.getType(i.getValue().getDeclaringClass()),"<init>",AsmUtil.getDesc((Constructor<?>)i.getValue())));
					}
					else
					{
						for(int j=0;j<pts.length;j++)
						{
							mn.instructions.add(AsmUtil.insnVarLoad(pts[j],1+j));
							if(Delegator.class.isAssignableFrom(pts[j]))
							{
								mn.instructions.add(AsmUtil.insnGetDelegate());
								ptsTar[j]=Object.class;
							}
							else
								ptsTar[j]=pts[j];
						}
						mn.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC,cn.name,methodHandles.size()+mhSuffix,AsmUtil.getDesc(MethodHandle.class)));
						methodHandles.add(ClassUtil.unreflect((Constructor<?>)i.getValue()).asType(MethodType.methodType(Object.class,ptsTar)));
						mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(MethodHandle.class),"invokeExact",AsmUtil.getDesc(Object.class,ptsTar)));
					}
					mn.instructions.add(AsmUtil.insnCreateDelegator(getDelegatorClass()));
					mn.instructions.add(AsmUtil.insnReturn(getDelegatorClass()));
				}
				else if(i.getValue() instanceof Method)
				{
					Class<?>[] ptsTar=((Method)i.getValue()).getParameterTypes();
					Class<?> rt=((Method)i.getValue()).getReturnType();
					if(isPublic)
					{
						if(!Modifier.isStatic(i.getValue().getModifiers()))
						{
							mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(),0));
							mn.instructions.add(AsmUtil.insnGetDelegate());
							mn.instructions.add(AsmUtil.insnCast(i.getValue().getDeclaringClass(),Object.class));
						}
						for(int j=0;j<pts.length;j++)
						{
							mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(),1+j));
							if(Delegator.class.isAssignableFrom(pts[j]))
							{
								mn.instructions.add(AsmUtil.insnGetDelegate());
								pts[j]=Object.class;
							}
							mn.instructions.add(AsmUtil.insnCast(ptsTar[j],pts[j]));
						}
						mn.instructions.add(new MethodInsnNode(Modifier.isStatic(i.getValue().getModifiers())?Opcodes.INVOKESTATIC:Modifier.isInterface(i.getValue().getDeclaringClass().getModifiers())?Opcodes.INVOKEINTERFACE:Opcodes.INVOKEVIRTUAL,AsmUtil.getType(i.getValue().getDeclaringClass()),i.getValue().getName(),AsmUtil.getDesc((Method)i.getValue())));
					}
					else
					{
						if(!rt.isPrimitive())
							rt=Object.class;
						mn.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC,cn.name,methodHandles.size()+mhSuffix,AsmUtil.getDesc(MethodHandle.class)));
						if(!Modifier.isStatic(i.getValue().getModifiers()))
						{
							mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(),0));
							mn.instructions.add(AsmUtil.insnGetDelegate());
						}
						for(int j=0;j<pts.length;j++)
						{
							mn.instructions.add(AsmUtil.insnVarLoad(pts[j],1+j));
							if(Delegator.class.isAssignableFrom(pts[j]))
							{
								mn.instructions.add(AsmUtil.insnGetDelegate());
								ptsTar[j]=Object.class;
							}
							else
								ptsTar[j]=pts[j];
						}
						if(!Modifier.isStatic(i.getValue().getModifiers()))
							ptsTar=CollectionUtil.addAll(CollectionUtil.newArrayList(Object.class),ptsTar).toArray(new Class[0]);
						methodHandles.add(ClassUtil.unreflect((Method)i.getValue()).asType(MethodType.methodType(rt,ptsTar)));
						mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(MethodHandle.class),"invokeExact",AsmUtil.getDesc(rt,ptsTar)));
					}
					if(Delegator.class.isAssignableFrom(i.getKey().getReturnType()))
					{
						mn.instructions.add(AsmUtil.insnCreateDelegator(RuntimeUtil.<Class<Delegator>>cast(i.getKey().getReturnType())));
						rt=Object.class;
					}
					mn.instructions.add(AsmUtil.insnCast(i.getKey().getReturnType(),rt));
					mn.instructions.add(AsmUtil.insnReturn(i.getKey().getReturnType()));
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
									mn.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC,AsmUtil.getType(i.getValue().getDeclaringClass()),i.getValue().getName(),AsmUtil.getDesc(type)));
								else
								{
									mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(),0));
									mn.instructions.add(AsmUtil.insnGetDelegate());
									mn.instructions.add(AsmUtil.insnCast(i.getValue().getDeclaringClass(),Object.class));
									mn.instructions.add(new FieldInsnNode(Opcodes.GETFIELD,AsmUtil.getType(i.getValue().getDeclaringClass()),i.getValue().getName(),AsmUtil.getDesc(type)));
								}
							}
							else
							{
								if(Modifier.isStatic(i.getValue().getModifiers()))
								{
									mn.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC,cn.name,methodHandles.size()+mhSuffix,AsmUtil.getDesc(MethodHandle.class)));
									MethodType mt=MethodType.methodType(type.isPrimitive()?type:Object.class);
									methodHandles.add(Root.getTrusted(i.getValue().getDeclaringClass()).findStaticGetter(i.getValue().getDeclaringClass(),i.getValue().getName(),type).asType(mt));
									mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(MethodHandle.class),"invokeExact",AsmUtil.getDesc(mt)));
								}
								else
								{
									mn.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC,cn.name,methodHandles.size()+mhSuffix,AsmUtil.getDesc(MethodHandle.class)));
									mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(),0));
									mn.instructions.add(AsmUtil.insnGetDelegate());
									MethodType mt=MethodType.methodType(type.isPrimitive()?type:Object.class,Object.class);
									methodHandles.add(Root.getTrusted(i.getValue().getDeclaringClass()).findGetter(i.getValue().getDeclaringClass(),i.getValue().getName(),type).asType(mt));
									mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(MethodHandle.class),"invokeExact",AsmUtil.getDesc(mt)));
								}
							}
							if(Delegator.class.isAssignableFrom(i.getKey().getReturnType()))
								mn.instructions.add(AsmUtil.insnCreateDelegator(RuntimeUtil.<Class<Delegator>>cast(i.getKey().getReturnType())));
							else
								mn.instructions.add(AsmUtil.insnCast(i.getKey().getReturnType(),type));
							mn.instructions.add(AsmUtil.insnReturn(i.getKey().getReturnType()));
							break;
						case 1:
							Class<?> inputType=i.getKey().getParameterTypes()[0];
							mn.instructions.add(AsmUtil.insnVarLoad(inputType,1));
							if(Delegator.class.isAssignableFrom(inputType))
							{
								mn.instructions.add(AsmUtil.insnGetDelegate());
								inputType=Object.class;
							}
							if(isPublic&&!Modifier.isFinal(i.getValue().getModifiers()))
							{
								mn.instructions.add(AsmUtil.insnCast(type,inputType));
								if(Modifier.isStatic(i.getValue().getModifiers()))
									mn.instructions.add(new FieldInsnNode(Opcodes.PUTSTATIC,AsmUtil.getType(i.getValue().getDeclaringClass()),i.getValue().getName(),AsmUtil.getDesc(type)));
								else
								{
									mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(),0));
									mn.instructions.add(AsmUtil.insnGetDelegate());
									mn.instructions.add(AsmUtil.insnCast(i.getValue().getDeclaringClass(),Object.class));
									mn.instructions.add(AsmUtil.insnSwap(i.getValue().getDeclaringClass(),type));
									mn.instructions.add(new FieldInsnNode(Opcodes.PUTFIELD,AsmUtil.getType(i.getValue().getDeclaringClass()),i.getValue().getName(),AsmUtil.getDesc(type)));
								}
							}
							else
							{
								if(Modifier.isStatic(i.getValue().getModifiers()))
								{
									mn.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC,cn.name,methodHandles.size()+mhSuffix,AsmUtil.getDesc(MethodHandle.class)));
									mn.instructions.add(AsmUtil.insnSwap(MethodHandle.class,inputType));
									MethodType mt=MethodType.methodType(void.class,inputType);
									methodHandles.add(Root.getTrusted(i.getValue().getDeclaringClass()).findStaticSetter(i.getValue().getDeclaringClass(),i.getValue().getName(),type).asType(mt));
									mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(MethodHandle.class),"invokeExact",AsmUtil.getDesc(mt)));
								}
								else
								{
									mn.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC,cn.name,methodHandles.size()+mhSuffix,AsmUtil.getDesc(MethodHandle.class)));
									mn.instructions.add(AsmUtil.insnSwap(MethodHandle.class,inputType));
									mn.instructions.add(AsmUtil.insnVarLoad(getDelegatorClass(),0));
									mn.instructions.add(AsmUtil.insnGetDelegate());
									mn.instructions.add(AsmUtil.insnSwap(getDelegatorClass(),inputType));
									MethodType mt=MethodType.methodType(void.class,Object.class,inputType);
									methodHandles.add(Root.getTrusted(i.getValue().getDeclaringClass()).findSetter(i.getValue().getDeclaringClass(),i.getValue().getName(),type).asType(mt));
									mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(MethodHandle.class),"invokeExact",AsmUtil.getDesc(mt)));
								}
							}
							mn.instructions.add(AsmUtil.insnReturn(void.class));
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
			throw RuntimeUtil.sneakilyThrow(e);
		}
	}
}
