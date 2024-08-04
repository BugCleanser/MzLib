package mz.mzlib.util;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.asm.tree.MethodNode;
import mz.mzlib.util.asm.AsmUtil;
import net.bytebuddy.agent.ByteBuddyAgent;

import java.io.FileOutputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ClassUtil
{
	private ClassUtil() {}
	
	public static ClassLoader sysClassLoader=ClassLoader.getSystemClassLoader();
	public static ClassLoader extClassLoader=sysClassLoader.getParent();
	
	public static Class<?> forName(String name,ClassLoader cl)
	{
		switch(name)
		{
			case "void":
				return void.class;
			case "byte":
				return byte.class;
			case "short":
				return short.class;
			case "int":
				return int.class;
			case "long":
				return long.class;
			case "float":
				return float.class;
			case "double":
				return double.class;
			case "boolean":
				return boolean.class;
			case "char":
				return char.class;
			default:
				try
				{
					return Class.forName(name,false,cl);
				}
				catch(Throwable e)
				{
					throw RuntimeUtil.sneakilyThrow(e);
				}
		}
	}
	
	public static Class<?> baseType(Class<?> type)
	{
		if(type.isPrimitive())
			return type;
		else
			return Object.class;
	}
	
	public static Class<?> getReturnType(Member m)
	{
		if(m instanceof Method)
			return ((Method)m).getReturnType();
		assert m instanceof Constructor;
		return void.class;
	}
	
	public static List<? extends Member> getDeclaredMembers(Class<?> clazz)
	{
		List<Member> result=new ArrayList<>(Arrays.asList(clazz.getDeclaredConstructors()));
		result.addAll(Arrays.asList(clazz.getDeclaredFields()));
		result.addAll(Arrays.asList(clazz.getDeclaredMethods()));
		return result;
	}
	public static Method getDeclaredMethod(Class<?> clazz,Method method)
	{
		try
		{
			return clazz.getDeclaredMethod(method.getName(),method.getParameterTypes());
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
	}
	
	public static MethodHandle findConstructor(Class<?> declaringClass,Class<?> ...parameterTypes) throws NoSuchMethodException, IllegalAccessException
	{
		return Root.getTrusted(declaringClass).findConstructor(declaringClass,MethodType.methodType(void.class,parameterTypes));
	}
	public static MethodHandle findMethod(Class<?> declaringClass,boolean isStatic,String name,Class<?> returnType,Class<?> ...parameterTypes) throws NoSuchMethodException, IllegalAccessException
	{
		if(isStatic)
			return Root.getTrusted(declaringClass).findStatic(declaringClass,name,MethodType.methodType(returnType,parameterTypes));
		else
			return Root.getTrusted(declaringClass).findVirtual(declaringClass,name,MethodType.methodType(returnType,parameterTypes));
	}
	public static MethodHandle findMethodSpecial(Class<?> declaringClass,String name,Class<?> returnType,Class<?> ...parameterTypes) throws NoSuchMethodException, IllegalAccessException
	{
		return Root.getTrusted(declaringClass).findSpecial(declaringClass,name,MethodType.methodType(returnType,parameterTypes),declaringClass);
	}
	public static MethodHandle findFieldGetter(Class<?> declaringClass,boolean isStatic,String name,Class<?> type) throws NoSuchFieldException, IllegalAccessException
	{
		if(isStatic)
			return Root.getTrusted(declaringClass).findStaticGetter(declaringClass,name,type);
		else
			return Root.getTrusted(declaringClass).findGetter(declaringClass,name,type);
	}
	public static MethodHandle findFieldSetter(Class<?> declaringClass,boolean isStatic,String name,Class<?> type) throws NoSuchFieldException, IllegalAccessException
	{
		if(isStatic)
			return Root.getTrusted(declaringClass).findStaticSetter(declaringClass,name,type);
		else
			return Root.getTrusted(declaringClass).findSetter(declaringClass,name,type);
	}
	public static MethodHandle unreflect(Constructor<?> constructor)
	{
		try
		{
			return Root.getTrusted(constructor.getDeclaringClass()).unreflectConstructor(constructor);
		}
		catch(IllegalAccessException e)
		{
			throw new AssertionError(e);
		}
	}
	public static MethodHandle unreflectGetter(Field field)
	{
		try
		{
			return Root.getTrusted(field.getDeclaringClass()).unreflectGetter(field);
		}
		catch(IllegalAccessException e)
		{
			throw new AssertionError(e);
		}
	}
	public static MethodHandle unreflectSetter(Field field)
	{
		try
		{
			return Root.getTrusted(field.getDeclaringClass()).unreflectSetter(field);
		}
		catch(IllegalAccessException e)
		{
			throw new AssertionError(e);
		}
	}
	public static MethodHandle unreflect(Method method)
	{
		try
		{
			return Root.getTrusted(method.getDeclaringClass()).unreflect(method);
		}
		catch(IllegalAccessException e)
		{
			throw new AssertionError(e);
		}
	}
	public static MethodHandle unreflectSpecial(Method method)
	{
		try
		{
			return Root.getTrusted(method.getDeclaringClass()).unreflectSpecial(method,method.getDeclaringClass());
		}
		catch(IllegalAccessException e)
		{
			throw new AssertionError(e);
		}
	}
	
	public static void forEachSuper(Class<?> clazz,Consumer<Class<?>> proc)
	{
		proc.accept(clazz);
		if(clazz!=Object.class&&!clazz.isInterface())
			forEachSuper(clazz.getSuperclass(),proc);
		for(Class<?> i:clazz.getInterfaces())
			forEachSuper(i,proc);
	}
	public static void forEachSuperUnique(Class<?> clazz,Consumer<Class<?>> proc)
	{
		Set<Class<?>> history=new HashSet<>();
		forEachSuper(clazz,c->
		{
			if(history.add(c))
				proc.accept(c);
		});
	}
	
	/**
	 * Iterate through all super classes in topological order
	 * From super to children
	 */
	public static void forEachSuperTopology(Class<?> clazz,Consumer<Class<?>> proc)
	{
		Map<Class<?>,Integer> degreeIn=new HashMap<>();
		Map<Class<?>,Set<Class<?>>> edgeOut=new HashMap<>();
		forEachSuperUnique(clazz,c->
		{
			if(c!=Object.class)
			{
				edgeOut.computeIfAbsent(c.getSuperclass(),k->new HashSet<>()).add(c);
				degreeIn.compute(c,(k,v)->(v!=null?v:0)+1);
			}
			for(Class<?> i:clazz.getInterfaces())
				edgeOut.computeIfAbsent(i,k->new HashSet<>()).add(c);
			degreeIn.compute(c,(k,v)->(v!=null?v:0)+clazz.getInterfaces().length);
		});
		Queue<Class<?>> q=new ArrayDeque<>();
		q.add(Object.class);
		while(!q.isEmpty())
		{
			Class<?> now=q.poll();
			proc.accept(now);
			Set<Class<?>> es=edgeOut.get(now);
			if(es!=null)
				for(Class<?> c:es)
					if(degreeIn.compute(c,(k,v)->Objects.requireNonNull(v)-1)==0)
						q.add(c);
		}
	}
	
	public static Instrumentation instrumentation;
	public static Instrumentation getInstrumentation()
	{
		if(instrumentation==null)
		{
			try
			{
				ByteBuddyAgent.install();
			}
			catch(Throwable e)
			{
				System.err.println("Unable to inject JavaAgent");
				System.err.println("Please remove the startup parameters -XX:+DisableAttachMechanism and -Djdk.attach.allowAttachSelf=false");
				System.err.println("You can also try installing ByteBuddyAgent manually (this is not a plugin, check the installation method on the MzLib official website)");
				System.err.println("无法注入JavaAgent");
				System.err.println("请删除启动参数-XX:+DisableAttachMechanism和-Djdk.attach.allowAttachSelf=false");
				System.err.println("也可以尝试手动安装ByteBuddyAgent（这不是一个插件，在MzLib官网查看安装方法）");
				throw e;
			}
			instrumentation=ByteBuddyAgent.getInstrumentation();
		}
		return instrumentation;
	}
	
	public static byte[] getByteCode(Class<?> clazz)
	{
		try
		{
			StrongRef<byte[]> result=new StrongRef<>(null);
			ClassFileTransformer tr=new ClassFileTransformer() // Can not replace with lambda!
			{
				@Override
				public byte[] transform(ClassLoader cl,String name,Class<?> c,ProtectionDomain d,byte[] byteCode)
				{
					if(c==clazz)
						result.set(byteCode);
					getInstrumentation().removeTransformer(this);
					return null;
				}
			};
			getInstrumentation().addTransformer(tr,true);
			getInstrumentation().retransformClasses(clazz);
			return result.get();
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
	}
	
	public static Class<?> defineClass(ClassLoader classLoader,String name,byte[] byteCode)
	{
		synchronized(ClassUtil.class)
		{
			try
			{
				try
				{
					getInstrumentation().redefineClasses(new ClassDefinition(Class.forName(name.replace('/','.'),false,classLoader),byteCode));
					return Class.forName(name.replace('/','.'),false,classLoader);
				}
				catch(Throwable e)
				{
					try
					{
						return Root.getUnsafe().defineClass(name,byteCode,0,byteCode.length,classLoader,null);
					}
					catch(Throwable e1)
					{
						if(e instanceof ClassNotFoundException)
							throw e1;
						else
							throw e;
					}
				}
			}
			catch(VerifyError e)
			{
				try(FileOutputStream fos=new FileOutputStream("test.class"))
				{
					fos.write(byteCode);
				}
				catch(Throwable e1)
				{
					throw RuntimeUtil.sneakilyThrow(e1);
				}
				throw RuntimeUtil.sneakilyThrow(e);
			}
			catch(Throwable e)
			{
				throw RuntimeUtil.sneakilyThrow(e);
			}
		}
	}
	
	public static void makeReference(ClassLoader classLoader,Object target)
	{
		try
		{
			String attachedName="0MzAttachedObjects";
			Class<?> attached;
			try
			{
				attached=Class.forName(attachedName,false,classLoader);
			}
			catch(ClassNotFoundException e)
			{
				ClassNode cn=new ClassNode();
				cn.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,attachedName,null,AsmUtil.getType(Object.class),new String[]{});
				cn.visitField(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC,"instance",AsmUtil.getDesc(Set.class),null,null).visitEnd();
				cn.visitEnd();
				ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
				cn.accept(cw);
				attached=defineClass(classLoader,attachedName,cw.toByteArray());
				attached.getDeclaredField("instance").set(null,ConcurrentHashMap.newKeySet());
			}
			RuntimeUtil.<Set<Object>>cast(attached.getDeclaredField("instance").get(null)).add(target);
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
	}
	
	public static <T> Class<T> getPrimitive(Class<T> src)
	{
		if(src==Character.class)
			return RuntimeUtil.cast(char.class);
		else if(src==Boolean.class)
			return RuntimeUtil.cast(boolean.class);
		else if(src==Byte.class)
			return RuntimeUtil.cast(byte.class);
		else if(src==Short.class)
			return RuntimeUtil.cast(short.class);
		else if(src==Integer.class)
			return RuntimeUtil.cast(int.class);
		else if(src==Long.class)
			return RuntimeUtil.cast(long.class);
		else if(src==Float.class)
			return RuntimeUtil.cast(float.class);
		else if(src==Double.class)
			return RuntimeUtil.cast(double.class);
		else
			return src;
	}
	public static <T> Class<T> getWrapper(Class<T> src)
	{
		if(src==char.class)
			return RuntimeUtil.cast(Character.class);
		else if(src==boolean.class)
			return RuntimeUtil.cast(Boolean.class);
		else if(src==byte.class)
			return RuntimeUtil.cast(Byte.class);
		else if(src==short.class)
			return RuntimeUtil.cast(Short.class);
		else if(src==int.class)
			return RuntimeUtil.cast(Integer.class);
		else if(src==long.class)
			return RuntimeUtil.cast(Long.class);
		else if(src==float.class)
			return RuntimeUtil.cast(Float.class);
		else if(src==double.class)
			return RuntimeUtil.cast(Double.class);
		else
			return src;
	}
	
	public static MethodHandle defineMethod(ClassLoader cl,MethodNode mn)
	{
		ClassNode cn=new ClassNode();
		cn.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,"$Method",null,AsmUtil.getType(Object.class),new String[0]);
		cn.methods.add(mn);
		ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		return unreflect(defineClass(new SimpleClassLoader(cl),cn.name,cw.toByteArray()).getDeclaredMethods()[0]);
	}
	
	public static CallSite getConstructorCallSite(MethodHandles.Lookup caller,String invokedName,MethodType invokedType,Class<?> owner,MethodType methodType) throws NoSuchMethodException, IllegalAccessException
	{
		return new ConstantCallSite(findConstructor(owner,methodType.parameterArray()).asType(invokedType));
	}
	public static CallSite getMethodCallSite(MethodHandles.Lookup caller,String invokedName,MethodType invokedType,Class<?> owner,MethodType methodType,int isStatic) throws NoSuchMethodException, IllegalAccessException
	{
		return new ConstantCallSite(findMethod(owner,isStatic!=0,invokedName,methodType.returnType(),methodType.parameterArray()).asType(invokedType));
	}
	public static CallSite getFieldGetterCallSite(MethodHandles.Lookup caller,String invokedName,MethodType invokedType,Class<?> owner,MethodType methodType) throws IllegalAccessException, NoSuchFieldException
	{
		return new ConstantCallSite(findFieldGetter(owner,invokedType.parameterCount()==0,invokedName,methodType.returnType()).asType(invokedType));
	}
	public static CallSite getFieldSetterCallSite(MethodHandles.Lookup caller,String invokedName,MethodType invokedType,Class<?> owner,MethodType methodType) throws IllegalAccessException, NoSuchFieldException
	{
		return new ConstantCallSite(findFieldSetter(owner,invokedType.parameterCount()==1,invokedName,methodType.parameterType(methodType.parameterCount()-1)).asType(invokedType));
	}
}
