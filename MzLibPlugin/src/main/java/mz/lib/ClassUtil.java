package mz.lib;

import com.google.common.collect.Lists;
import io.github.karlatemp.unsafeaccessor.Root;
import mz.asm.tree.*;
import mz.lib.wrapper.WrappedObject;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.Installer;
import mz.asm.ClassWriter;
import mz.asm.MethodVisitor;
import mz.asm.Opcodes;

import javax.tools.ToolProvider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.util.*;

public class ClassUtil
{
	public static Instrumentation agentInstrumentation;
	public static ClassLoader sysClassLoader=ClassLoader.getSystemClassLoader();
	public static ClassLoader extClassLoader=sysClassLoader.getParent();
	/**
	 * 得到一个类的所有父类（除了Object.class）
	 *
	 * @param clazz 一根类
	 * @return 这个类的所有父类（除了Object.class）
	 */
	public static List<Class<?>> getSuperClasses(Class<?> clazz)
	{
		return getSuperClasses(clazz,Object.class);
	}
	/**
	 * 得到所有是一个类的父类且是一个类的子类的类
	 *
	 * @param clazz 过滤这个类的父类
	 * @param l     过滤这个类的子类
	 * @return 一个类的父类 交 另一个类的子类
	 */
	public static <E,T> List<Class<? extends E>> getSuperClasses(Class<T> clazz,Class<E> l)
	{
		if(!l.isAssignableFrom(clazz))
			return new LinkedList<>();
		List<Class<? extends E>> r=new LinkedList<>();
		r.add(TypeUtil.cast(clazz));
		if(clazz.getSuperclass()!=null)
			r.addAll(getSuperClasses(clazz.getSuperclass(),l));
		for(Class<?> c: clazz.getInterfaces())
			r.addAll(getSuperClasses(TypeUtil.cast(c),l));
		return r;
	}
	public static List<Class<?>> getSuperClassesTopological(Class<?> clazz)
	{
		return getSuperClassesTopological(clazz,Object.class);
	}
	public static <E,T> List<Class<? extends E>> getSuperClassesTopological(Class<T> clazz,Class<E> l)
	{
		Map<Class<?>,Integer> supersNum=new HashMap<>();
		Map<Class<?>,Set<Class<?>>> children=new HashMap<>();
		Queue<Class<?>> q=new LinkedList<>();
		for(Class<?> c:getSuperClasses(clazz,l))
		{
			supersNum.put(c,0);
			children.put(c,new HashSet<>());
		}
		for(Class<?> c:supersNum.keySet())
		{
			Set<Class<?>> s=new HashSet<>();
			if(c.getSuperclass()!=null)
				s.add(c.getSuperclass());
			s.addAll(Arrays.asList(c.getInterfaces()));
			for(Class<?> i:s)
				if(supersNum.containsKey(i))
				{
					supersNum.put(c,supersNum.get(c)+1);
					children.get(i).add(c);
				}
			if(supersNum.get(c)==0)
				q.add(c);
		}
		List<Class<? extends E>> r=new ArrayList<>();
		while(q.size()>0)
		{
			Class<?> head=q.poll();
			r.add(TypeUtil.cast(head));
			for(Class<?> c:children.get(head))
			{
				supersNum.put(c,supersNum.get(c)-1);
				if(supersNum.get(c)==0)
					q.add(c);
			}
		}
		return r;
	}
	public static Class<?> get(String name)
	{
		try
		{
			return Class.forName(name);
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public static Class<?> fromName(ClassLoader cl,String name)
	{
		if(name.endsWith("[]"))
			return Array.newInstance(fromName(cl,name.substring(0,name.length()-2)),0).getClass();
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
		}
		try
		{
			return Class.forName(name,false,cl);
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
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
				agentInstrumentation=(Instrumentation)Class.forName("mz.lib.InstrumentationGetter",false,sysClassLoader).getDeclaredField("instrumentation").get(null);
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
						throw TypeUtil.throwException(e);
					}
					String className=ClassUtil.class.getName()+"$AgentInstrumentationGetter";
					if(TypeUtil.getThrowable(()->
					{
						Class.forName(className,false,ClassLoader.getSystemClassLoader());
					}) instanceof ClassNotFoundException)
					{
						byte[] bs=FileUtil.readInputStream(ClassUtil.class.getClassLoader().getResourceAsStream(className.replace('.','/')+".class"));
						Root.getUnsafe().defineClass(className,bs,0,bs.length,ClassLoader.getSystemClassLoader(),null);
					}
					Class<?> clazz=Class.forName(className,true,ClassLoader.getSystemClassLoader());
					agentInstrumentation=TypeUtil.cast(clazz.getField("agentInstrumentation").get(null));
				}
				catch(Throwable e)
				{
					TypeUtil.throwException(e);
				}
			}
		}
		return agentInstrumentation;
	}
	public static Class<?> loadClass(String className,byte[] bs,ClassLoader classLoader)
	{
		synchronized(ClassUtil.class)
		{
			try
			{
				try
				{
					try
					{
						getAgentInstrumentation().redefineClasses(new ClassDefinition(Class.forName(className.replace('/','.'),false,classLoader),bs));
						return Class.forName(className.replace('/','.'),false,classLoader);
					}
					catch(VerifyError e)
					{
						try(FileOutputStream fos=new FileOutputStream(new File(className.replace('/','.')+".class")))
						{
							fos.write(bs);
						}
						throw e;
					}
				}
				catch(ClassNotFoundException ignore)
				{
					return Root.getUnsafe().defineClass(className,bs,0,bs.length,classLoader,null);
				}
			}
			catch(Throwable e)
			{
				throw TypeUtil.throwException(e);
			}
		}
	}
	
	public static Field getSuperField(Class<?> dc,String name)
	{
		if(dc==null)
			throw TypeUtil.throwException(new NoSuchFieldException(name));
		try
		{
			return dc.getDeclaredField(name);
		}
		catch(NoSuchFieldException e)
		{
			Field r=getSuperField(dc.getSuperclass(),name);
			if(!Modifier.isStatic(r.getModifiers()))
				return r;
			throw TypeUtil.throwException(e);
		}
	}
	public static Method getSuperMethod(Class<?> dc,String name,Class<?>... argTypes)
	{
		try
		{
			return dc.getDeclaredMethod(name,argTypes);
		}
		catch(NoSuchMethodException e)
		{
			if(dc.getSuperclass()!=null)
				try
				{
					TypeUtil.<NoSuchMethodException>fakeThrow();
					return getSuperMethod(dc.getSuperclass(),name,argTypes);
				}
				catch(NoSuchMethodException e1)
				{
				}
			for(Class<?> i:dc.getInterfaces())
			{
				try
				{
					TypeUtil.<NoSuchMethodException>fakeThrow();
					return getSuperMethod(i,name,argTypes);
				}
				catch(NoSuchMethodException e1)
				{
				}
			}
			throw TypeUtil.throwException(e);
		}
	}
	public static MethodHandle unreflectSetter(Field field)
	{
		try
		{
			if(Modifier.isStatic(field.getModifiers()))
				return Root.getTrusted().findStaticSetter(field.getDeclaringClass(),field.getName(),field.getType());
			else
				return Root.getTrusted().findSetter(field.getDeclaringClass(),field.getName(),field.getType());
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public static MethodHandle unreflectGetter(Field field)
	{
		try
		{
			if(Modifier.isStatic(field.getModifiers()))
				return Root.getTrusted().findStaticGetter(field.getDeclaringClass(),field.getName(),field.getType());
			else
				return Root.getTrusted().findGetter(field.getDeclaringClass(),field.getName(),field.getType());
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public static MethodHandle unreflect(Constructor<?> constructor)
	{
		try
		{
			return Root.getTrusted().findConstructor(constructor.getDeclaringClass(),MethodType.methodType(void.class,constructor.getParameterTypes()));
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public static MethodHandle unreflect(Method method)
	{
		try
		{
			if(Modifier.isStatic(method.getModifiers()))
				return unreflectSpecial(method);
			else
				return Root.getTrusted().findVirtual(method.getDeclaringClass(),method.getName(),MethodType.methodType(method.getReturnType(),method.getParameterTypes()));
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public static MethodHandle unreflectSpecial(Method method)
	{
		try
		{
			if(Modifier.isStatic(method.getModifiers()))
				return Root.getTrusted().findStatic(method.getDeclaringClass(),method.getName(),MethodType.methodType(method.getReturnType(),method.getParameterTypes()));
			else
				return Root.getTrusted().findSpecial(method.getDeclaringClass(),method.getName(),MethodType.methodType(method.getReturnType(),method.getParameterTypes()),method.getDeclaringClass());
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public static List<Method> getAllMethods(Class<?> clazz)
	{
		List<Method> r=new ArrayList<>();
		for(Class<?> c:getSuperClassesTopological(clazz))
			r.addAll(Lists.newArrayList(clazz.getDeclaredMethods()));
		return r;
	}
	public static Set<Method> getAllMethods(Class<?> dc,String name,Class<?>... argTypes)
	{
		LinkedHashSet<Method> r=new LinkedHashSet<>();
		for(Class<?> c:getSuperClassesTopological(dc))
			try
			{
				r.add(dc.getDeclaredMethod(name,argTypes));
			}
			catch(NoSuchMethodException ignored)
			{
			}
		return r;
	}
	public static boolean isAbstract(Method m,Class<?> clazz)
	{
		try
		{
			if(!Modifier.isAbstract(clazz.getDeclaredMethod(m.getName(),m.getParameterTypes()).getModifiers()))
				return false;
		}
		catch(NoSuchMethodException ignored)
		{
		}
		if((!clazz.isInterface())&&clazz!=Object.class&&!isAbstract(m,clazz.getSuperclass()))
			return false;
		for(Class<?> i:clazz.getInterfaces())
		{
			if(!isAbstract(m,i))
				return false;
		}
		return true;
	}
	public static Method getInvokable(Method m,Class<?> clazz)
	{
		try
		{
			Method r=clazz.getDeclaredMethod(m.getName(),m.getParameterTypes());
			if(!Modifier.isAbstract(r.getModifiers()))
				return r;
		}
		catch(NoSuchMethodException e)
		{
		}
		if(clazz!=Object.class&&!clazz.isInterface())
		{
			Method r=getInvokable(m,clazz.getSuperclass());
			if(r!=null)
				return r;
		}
		for(Class<?> i:clazz.getInterfaces())
		{
			Method r=getInvokable(m,i);
			if(r!=null)
				return r;
		}
		return null;
	}
	public static String getName(Class<?> clazz)
	{
		if(clazz.isArray())
			return getName(clazz.getComponentType())+"[]";
		return clazz.getName().replace('$','.');
	}
	
	public static Class<?> getClass(Object obj)
	{
		return obj==null?Void.class:obj.getClass();
	}
	
	public static Map<Class<?>,MethodHandle> constructorsCache=new WeakHashMap<>();
	public static <T> T newInstance(Class<T> clazz)
	{
		try
		{
			if(clazz.isInterface())
			{
				MethodHandle c=constructorsCache.get(clazz);
				if(c==null)
				{
					SimpleLoader cl=new SimpleLoader(clazz.getClassLoader());
					ClassNode cn=new ClassNode();
					cn.visit(Opcodes.V1_8,Modifier.PUBLIC,AsmUtil.getType("mz.lib.implgen."+clazz.getName()),null,AsmUtil.getType(Object.class),new String[]{AsmUtil.getType(clazz)});
					MethodVisitor m=cn.visitMethod(Modifier.PUBLIC,"<init>",AsmUtil.getDesc(new Class[0],void.class),null,new String[0]);
					m.visitCode();
					m.visitVarInsn(Opcodes.ALOAD,0);
					m.visitMethodInsn(Opcodes.INVOKESPECIAL,AsmUtil.getType(Object.class),"<init>",AsmUtil.getDesc(new Class[0],void.class),false);
					m.visitInsn(Opcodes.RETURN);
					m.visitEnd();
					ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
					cn.accept(cw);
					c=Root.getTrusted().unreflectConstructor(loadClass(cn.name,cw.toByteArray(),cl).getDeclaredConstructor());
					synchronized(ClassUtil.class)
					{
						WeakHashMap<Class<?>,MethodHandle> t=new WeakHashMap<>(constructorsCache);
						t.put(clazz,c);
						constructorsCache=t;
					}
				}
				return TypeUtil.cast(c.invoke());
			}
			else
			{
				try
				{
					return Root.openAccess(clazz.getDeclaredConstructor()).newInstance();
				}
				catch(NoSuchMethodException t)
				{
					return Root.allocate(clazz);
				}
			}
		}
		catch(Throwable t)
		{
			throw TypeUtil.throwException(t);
		}
	}
	
	public static <T extends Annotation> T getSuperAnnotation(Method method,Class<T> annType)
	{
		for(Class<?> c:getSuperClasses(method.getDeclaringClass()))
		{
			try
			{
				T r=c.getDeclaredMethod(method.getName(),method.getParameterTypes()).getDeclaredAnnotation(annType);
				if(r!=null)
					return r;
			}
			catch(NoSuchMethodException e)
			{
			}
		}
		return null;
	}
	
	public static <T extends Annotation> List<T> getSuperAnnotations(Method method,Class<T> annType)
	{
		List<T> r=new ArrayList<>();
		for(Class<?> c:getSuperClasses(method.getDeclaringClass()))
		{
			try
			{
				r.addAll(Lists.newArrayList(c.getDeclaredMethod(method.getName(),method.getParameterTypes()).getDeclaredAnnotationsByType(annType)));
			}
			catch(NoSuchMethodException e)
			{
			}
		}
		return r;
	}
	
	public static <T,U extends T> void setFields(T tar,U src)
	{
		setFields(TypeUtil.cast(tar.getClass()),tar,src);
	}
	public static <D,T extends D,U extends T> void setFields(Class<D> c,T tar,U src)
	{
		try
		{
			for(Field f:c.getDeclaredFields())
			{
				if(!Modifier.isStatic(f.getModifiers()))
				{
					Root.getTrusted().findSetter(c,f.getName(),f.getType()).invoke(tar,Root.getTrusted().unreflectGetter(f).invoke(src));
				}
			}
			if(c.getSuperclass()!=null)
			{
				setFields(c.getSuperclass(),tar,src);
			}
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public static <T> T clone(T obj)
	{
		try
		{
			T r=TypeUtil.cast(Root.getUnsafe().allocateInstance(obj.getClass()));
			setFields(r,obj);
			return r;
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	
	public static <T> Class<T> findLoadedClass(ClassLoader cl,String className)
	{
		return WrappedObject.wrap(WrappedClassLoader.class,cl).findLoadedClass(className);
	}
	
	public static int getDistance(Class<?> superClass,Class<?> subClass)
	{
		if(superClass==subClass)
			return 0;
		if(superClass.isAssignableFrom(subClass))
		{
			if(subClass!=Object.class && !subClass.isInterface())
			{
				int r=getDistance(superClass,subClass.getSuperclass());
				if(r!=-1)
					return r+1;
			}
			if(superClass.isInterface())
			{
				for(Class<?> i: subClass.getInterfaces())
				{
					int r=getDistance(superClass,i);
					if(r!=-1)
						return r+1;
				}
			}
		}
		return -1;
	}
	
	public static InputStream read(Class<?> clazz)
	{
		return clazz.getClassLoader().getResourceAsStream(clazz.getName().replace('.','/')+".class");
	}
}
