package mz.mzlib.util;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.asm.tree.ClassNode;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.Installer;

import javax.tools.ToolProvider;
import java.io.FileOutputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ClassUtil
{
	private ClassUtil() {}
	
	public static ClassLoader sysClassLoader=ClassLoader.getSystemClassLoader();
	public static ClassLoader extClassLoader=sysClassLoader.getParent();
	
	public static Method getDeclaredMethod(Class<?> clazz,Method method)
	{
		try
		{
			return clazz.getDeclaredMethod(method.getName(),method.getParameterTypes());
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	
	public static MethodHandle findMethod(Class<?> declaringClass,boolean isStatic,String name,Class<?> returnType,Class<?> ...parameterTypes)
	{
		try
		{
			if(isStatic)
				return Root.getTrusted(declaringClass).findStatic(declaringClass,name,MethodType.methodType(returnType,parameterTypes));
			else
				return Root.getTrusted(declaringClass).findVirtual(declaringClass,name,MethodType.methodType(returnType,parameterTypes));
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	public static MethodHandle findMethodSpecial(Class<?> declaringClass,boolean isStatic,String name,Class<?> returnType,Class<?> ...parameterTypes)
	{
		try
		{
			return Root.getTrusted(declaringClass).findSpecial(declaringClass,name,MethodType.methodType(returnType,parameterTypes),declaringClass);
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	
	public static MethodHandle unreflect(Constructor<?> constructor)
	{
		try
		{
			return Root.getTrusted(constructor.getDeclaringClass()).unreflectConstructor(constructor);
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	public static MethodHandle unreflect(Method method)
	{
		try
		{
			return Root.getTrusted(method.getDeclaringClass()).unreflect(method);
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	public static MethodHandle unreflectSpecial(Method method)
	{
		try
		{
			return Root.getTrusted(method.getDeclaringClass()).unreflectSpecial(method,method.getDeclaringClass());
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	
	public static void forEachSuper(Class<?> clazz,Consumer<Class<?>> proc)
	{
		proc.accept(clazz);
		if(clazz!=Object.class)
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
	
	public static Instrumentation agentInstrumentation;
	public static class AgentInstrumentationGetter
	{
		public static Instrumentation agentInstrumentation;
		static
		{
			agentInstrumentation=Installer.getInstrumentation();
		}
	}
	public static Instrumentation getAgentInstrumentation()
	{
		if(agentInstrumentation==null)
		{
			try
			{
				agentInstrumentation=(Instrumentation)Class.forName("mz.mzlib.util.InstrumentationGetter",false,sysClassLoader).getDeclaredField("instrumentation").get(null);
			}
			catch(ClassNotFoundException|NoSuchFieldException|IllegalAccessException ignore)
			{
				try
				{
					try
					{
						ByteBuddyAgent.install();
					}
					catch(Throwable e)
					{
						System.err.println("无法注入JavaAgent");
						if(ToolProvider.getSystemJavaCompiler()==null)
							System.err.println("请使用JDK，推荐zulu jdk(zulu.org)");
						System.err.println("请删除启动参数-XX:+DisableAttachMechanism和-Djdk.attach.allowAttachSelf=false");
						System.err.println("也可以尝试安装MzLibAgent（注：这不是一个插件）");
						throw RuntimeUtil.forceThrow(e);
					}
					String className=ClassUtil.class.getName()+"$AgentInstrumentationGetter";
					if(RuntimeUtil.runAndCatch(()->Class.forName(className,false,ClassLoader.getSystemClassLoader())) instanceof ClassNotFoundException)
					{
						byte[] bs=IOUtil.readAll(ClassUtil.class.getClassLoader().getResourceAsStream(className.replace('.','/')+".class"));
						Root.getUnsafe().defineClass(className,bs,0,bs.length,ClassLoader.getSystemClassLoader(),null);
					}
					Class<?> clazz=Class.forName(className,true,ClassLoader.getSystemClassLoader());
					agentInstrumentation=RuntimeUtil.forceCast(clazz.getField("agentInstrumentation").get(null));
				}
				catch(Throwable e)
				{
					RuntimeUtil.forceThrow(e);
				}
			}
		}
		return agentInstrumentation;
	}
	
	public static Class<?> defineClass(ClassLoader classLoader,String name,byte[] byteCode)
	{
		synchronized(ClassUtil.class)
		{
			try
			{
				try
				{
					try
					{
						getAgentInstrumentation().redefineClasses(new ClassDefinition(Class.forName(name.replace('/','.'),false,classLoader),byteCode));
						return Class.forName(name.replace('/','.'),false,classLoader);
					}
					catch(VerifyError e)
					{
						try(FileOutputStream fos=new FileOutputStream(name.replace('/','.')+".class"))
						{
							fos.write(byteCode);
						}
						throw e;
					}
				}
				catch(ClassNotFoundException ignore)
				{
					return Root.getUnsafe().defineClass(name,byteCode,0,byteCode.length,classLoader,null);
				}
			}
			catch(Throwable e)
			{
				throw RuntimeUtil.forceThrow(e);
			}
		}
	}
	
	public static void makeReference(Class<?> clazz,Object target)
	{
		try
		{
			String attachedName=clazz.getName()+"$0MzAttachedObjects";
			Class<?> attached;
			try
			{
				attached=Class.forName(attachedName,false,clazz.getClassLoader());
			}
			catch(ClassNotFoundException e)
			{
				ClassNode cn=new ClassNode();
				cn.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,attachedName,null,AsmUtil.getDesc(Object.class),new String[]{});
				cn.visitField(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC,"instance",AsmUtil.getDesc(Set.class),null,null).visitEnd();
				cn.visitEnd();
				ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
				cn.accept(cw);
				attached=defineClass(clazz.getClassLoader(),attachedName,cw.toByteArray());
				attached.getDeclaredField("instance").set(null,ConcurrentHashMap.newKeySet());
			}
			RuntimeUtil.<Set<Object>>forceCast(attached.getDeclaredField("instance").get(null)).add(target);
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.forceThrow(e);
		}
	}
	
	public static <T> Class<T> getPrimitive(Class<T> src)
	{
		if(src==Character.class)
			return RuntimeUtil.forceCast(char.class);
		else if(src==Boolean.class)
			return RuntimeUtil.forceCast(boolean.class);
		else if(src==Byte.class)
			return RuntimeUtil.forceCast(byte.class);
		else if(src==Short.class)
			return RuntimeUtil.forceCast(short.class);
		else if(src==Integer.class)
			return RuntimeUtil.forceCast(int.class);
		else if(src==Long.class)
			return RuntimeUtil.forceCast(long.class);
		else if(src==Float.class)
			return RuntimeUtil.forceCast(float.class);
		else if(src==Double.class)
			return RuntimeUtil.forceCast(double.class);
		else
			return src;
	}
	public static <T> Class<T> getWrapper(Class<T> src)
	{
		if(src==char.class)
			return RuntimeUtil.forceCast(Character.class);
		else if(src==boolean.class)
			return RuntimeUtil.forceCast(Boolean.class);
		else if(src==byte.class)
			return RuntimeUtil.forceCast(Byte.class);
		else if(src==short.class)
			return RuntimeUtil.forceCast(Short.class);
		else if(src==int.class)
			return RuntimeUtil.forceCast(Integer.class);
		else if(src==long.class)
			return RuntimeUtil.forceCast(Long.class);
		else if(src==float.class)
			return RuntimeUtil.forceCast(Float.class);
		else if(src==double.class)
			return RuntimeUtil.forceCast(Double.class);
		else
			return src;
	}
}
