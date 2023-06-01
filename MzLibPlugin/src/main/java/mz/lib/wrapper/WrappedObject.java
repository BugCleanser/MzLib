package mz.lib.wrapper;

import com.google.common.collect.Lists;
import io.github.karlatemp.unsafeaccessor.Root;
import mz.asm.ClassWriter;
import mz.asm.Opcodes;
import mz.asm.Type;
import mz.asm.tree.ClassNode;
import mz.asm.tree.FieldNode;
import mz.asm.tree.MethodNode;
import mz.lib.Optional;
import mz.lib.*;

import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static mz.lib.AsmUtil.*;

/**
 * wrapped nms class or craft bukkit class
 */
@WrappedClass("java.lang.Object")
public interface WrappedObject
{
	default <T extends WrappedObject> boolean is(Class<T> wrapper)
	{
		return getRawClass(wrapper).isAssignableFrom(getRaw().getClass());
	}
	default boolean isNull()
	{
		return getRaw()==null;
	}
	static boolean isNull(WrappedObject wo)
	{
		return wo==null||wo.isNull();
	}
	void setRaw(Object raw);
	Object getRaw();
	
	static Object getRaw(WrappedObject wrappedObject)
	{
		if(wrappedObject==null)
			return null;
		else
			return wrappedObject.getRaw();
	}
	
	static <T extends WrappedObject> T allocInstance(Class<T> wrapper)
	{
		return wrap(wrapper,ClassUtil.newInstance(getRawClass(wrapper)));
	}
	
	@WrappedMethod("clone")
	WrappedObject clone00();
	default WrappedObject clone0()
	{
		return clone00();
	}
	default int hashCode0()
	{
		return Objects.hashCode(getRaw());
	}
	default String toString0()
	{
		return Objects.toString(getRaw());
	}
	default WrappedObject shallowClone()
	{
		return wrap(getWrapper(),ClassUtil.clone(getRaw()));
	}
	default boolean equals0(Object obj)
	{
		if(obj instanceof WrappedObject)
			return Objects.equals(getRaw(),((WrappedObject)obj).getRaw());
		return false;
	}
	
	default AbsWrappedObject toAbsWrappedObject()
	{
		return TypeUtil.<AbsWrappedObject,Object>cast(this);
	}
	
	Map<Class<? extends WrappedObject>,Class<?>> buildingWrappers=new ConcurrentHashMap<>();
	Class<?> getRawClass();
	static Class<?> getRawClass(Class<? extends WrappedObject> wrapper)
	{
		Class<?> t=buildingWrappers.get(wrapper);
		if(t!=null)
			return t;
		WrappedObject s=getStatic(wrapper);
		if(s==null)
			return null;
		return s.getRawClass();
	}
	default <T extends WrappedObject> T cast(Class<T> wrapper)
	{
		return wrap(wrapper,this.getRaw());
	}
	default Class<? extends WrappedObject> getWrapper()
	{
		return TypeUtil.cast(this.getClass().getInterfaces()[0]);
	}
	static <T extends WrappedObject> T getStatic(Class<T> wrapper)
	{
		return wrap(wrapper,null);
	}
	
	Ref<Map<Class<? extends WrappedObject>,MethodHandle>> cache=new Ref<>(new WeakHashMap<>());
	static <T extends WrappedObject> T wrap(Class<T> wrapper,Object raw)
	{
		try
		{
			MethodHandle c=cache.get().get(wrapper);
			if(c==null)
			{
				if(!wrapper.isInterface())
					throw new IllegalArgumentException("Wrapper "+wrapper.getName()+" must be an interface.");
				if(!WrappedObject.class.isAssignableFrom(wrapper))
					throw new IllegalArgumentException("Wrapper "+wrapper.getName()+" must be a subclass of WrappedObject.");
				T controller=ClassUtil.newInstance(wrapper);
				Class<?> rawClass=controller.getAnnotationClass(wrapper);
				if(rawClass==null)
				{
					if(wrapper.getDeclaredAnnotation(Optional.class)==null)
						throw new IllegalArgumentException("Wrapper "+wrapper.getName()+" must annotate its raw class.");
					return null;
				}
				buildingWrappers.put(wrapper,rawClass);
				try
				{
					ClassNode cn=new ClassNode();
					cn.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,AsmUtil.getType("mz.lib.wrapper.gen."+wrapper.getName()),null,getType(AbsWrappedObject.class),new String[]{getType(wrapper)});
					
					cn.fields.add(new FieldNode(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC,"objects",getDesc(Object[].class),null,null));
					List<Object> objects=new ArrayList<>();
					
					MethodNode mn=new MethodNode(Opcodes.ACC_PUBLIC,"<init>",getDesc(new Class[]{Object.class},void.class),null,new String[0]);
					mn.visitVarInsn(Opcodes.ALOAD,0);
					mn.instructions.add(AsmUtil.varLoadNode(Object.class,1));
					mn.visitMethodInsn(Opcodes.INVOKESPECIAL,getType(AbsWrappedObject.class),"<init>",getDesc(new Class[]{Object.class},void.class),false);
					mn.visitInsn(Opcodes.RETURN);
					cn.methods.add(mn);
					
					mn=new MethodNode(Opcodes.ACC_PUBLIC,"getRawClass",AsmUtil.getDesc(new Class[0],Class.class),null,new String[0]);
					mn.instructions.add(AsmUtil.ldcNode(rawClass));
					mn.instructions.add(AsmUtil.returnNode(Class.class));
					cn.methods.add(mn);
					
					for(Method m: wrapper.getDeclaredMethods())
					{
						Member rm=null;
						for(Annotation a:m.getDeclaredAnnotations())
							if(rm==null)
								rm=controller.getRawMember(rawClass,m,a);
						if(rm==null)
							continue;
						if(Modifier.isStatic(m.getModifiers()))
							throw new IllegalArgumentException("Can not wrapper to a static method: "+m+".");
						mn=new MethodNode(Opcodes.ACC_PUBLIC,m.getName(),getDesc(m),null,new String[0]);
						boolean accessByForce=m.getDeclaredAnnotation(AccessByForce.class)!=null || (!Modifier.isPublic(rawClass.getModifiers())) || (!Modifier.isPublic(rm.getDeclaringClass().getModifiers())) || (!Modifier.isPublic(rm.getModifiers()));
						if(rm instanceof Field)
						{
							switch(m.getParameterCount())
							{
								case 0:
									if(WrappedObject.class.isAssignableFrom(m.getReturnType()))
										mn.visitLdcInsn(Type.getType(m.getReturnType()));
									if(accessByForce)
									{
										mn.visitFieldInsn(Opcodes.GETSTATIC,getType(cn.name),"objects",getDesc(Object[].class));
										mn.instructions.add(arrayLoadNode(Object.class,toList(ldcNode(objects.size()))));
										mn.instructions.add(castNode(MethodHandle.class,Object.class));
										if(!Modifier.isStatic(rm.getModifiers()))
										{
											mn.visitVarInsn(Opcodes.ALOAD,0);
											mn.instructions.add(getRawNode());
											mn.instructions.add(castNode(rm.getDeclaringClass(),Object.class));
										}
										mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL,getType(MethodHandle.class),"invokeExact",getDesc(Modifier.isStatic(rm.getModifiers())?new Class[0]:new Class[]{rm.getDeclaringClass()},((Field)rm).getType()),false);
										if(WrappedObject.class.isAssignableFrom(m.getReturnType()))
										{
											mn.instructions.add(castNode(Object.class,((Field)rm).getType()));
											mn.instructions.add(wrapNode());
											mn.instructions.add(castNode(m.getReturnType(),WrappedObject.class));
										}
										else
											mn.instructions.add(castNode(m.getReturnType(),((Field)rm).getType()));
										mn.instructions.add(returnNode(m.getReturnType()));
										objects.add(ClassUtil.unreflectGetter((Field)rm));
									}
									else
									{
										if(!Modifier.isStatic(rm.getModifiers()))
										{
											mn.visitVarInsn(Opcodes.ALOAD,0);
											mn.instructions.add(getRawNode());
											mn.instructions.add(castNode(rm.getDeclaringClass(),Object.class));
										}
										mn.visitFieldInsn(Modifier.isStatic(rm.getModifiers())?Opcodes.GETSTATIC:Opcodes.GETFIELD,getType(rm.getDeclaringClass()),rm.getName(),getDesc(((Field)rm).getType()));
										if(WrappedObject.class.isAssignableFrom(m.getReturnType()))
										{
											mn.instructions.add(castNode(Object.class,((Field)rm).getType()));
											mn.instructions.add(wrapNode());
											mn.instructions.add(castNode(m.getReturnType(),WrappedObject.class));
										}
										else
											mn.instructions.add(castNode(m.getReturnType(),((Field)rm).getType()));
										mn.instructions.add(returnNode(m.getReturnType()));
									}
									break;
								case 1:
									if(accessByForce||Modifier.isFinal(rm.getModifiers()))
									{
										mn.visitFieldInsn(Opcodes.GETSTATIC,getType(cn.name),"objects",getDesc(Object[].class));
										mn.instructions.add(arrayLoadNode(Object.class,toList(ldcNode(objects.size()))));
										mn.instructions.add(castNode(MethodHandle.class,Object.class));
										if(!Modifier.isStatic(rm.getModifiers()))
										{
											mn.visitVarInsn(Opcodes.ALOAD,0);
											mn.instructions.add(getRawNode());
											mn.instructions.add(castNode(rm.getDeclaringClass(),Object.class));
										}
										mn.instructions.add(AsmUtil.varLoadNode(m.getParameterTypes()[0],1));
										if(WrappedObject.class.isAssignableFrom(m.getParameterTypes()[0]))
										{
											mn.instructions.add(AsmUtil.getRawNode());
											mn.instructions.add(AsmUtil.castNode(((Field)rm).getType(),Object.class));
										}
										else
											mn.instructions.add(AsmUtil.castNode(((Field)rm).getType(),m.getParameterTypes()[0]));
										mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL,getType(MethodHandle.class),"invokeExact",getDesc(Modifier.isStatic(rm.getModifiers())?new Class[]{((Field)rm).getType()}:new Class[]{rm.getDeclaringClass(),((Field)rm).getType()},void.class),false);
										if(m.getReturnType()==void.class)
											mn.visitInsn(Opcodes.RETURN);
										else if(m.getReturnType()==wrapper)
										{
											mn.visitVarInsn(Opcodes.ALOAD,0);
											mn.instructions.add(AsmUtil.returnNode(m.getReturnType()));
										}
										else
											throw new IllegalArgumentException("Return type of setter must be This or void. On "+m);
										objects.add(ClassUtil.unreflectSetter((Field)rm));
									}
									else
									{
										if(!Modifier.isStatic(rm.getModifiers()))
										{
											mn.visitVarInsn(Opcodes.ALOAD,0);
											mn.instructions.add(getRawNode());
											mn.instructions.add(castNode(((Field)rm).getDeclaringClass(),Object.class));
										}
										mn.instructions.add(AsmUtil.varLoadNode(m.getParameterTypes()[0],1));
										if(WrappedObject.class.isAssignableFrom(m.getParameterTypes()[0]))
										{
											mn.instructions.add(AsmUtil.getRawNode());
											mn.instructions.add(AsmUtil.castNode(((Field)rm).getType(),Object.class));
										}
										else
											mn.instructions.add(AsmUtil.castNode(((Field)rm).getType(),m.getParameterTypes()[0]));
										mn.visitFieldInsn(Modifier.isStatic(rm.getModifiers())?Opcodes.PUTSTATIC:Opcodes.PUTFIELD,AsmUtil.getType(rm.getDeclaringClass()),rm.getName(),AsmUtil.getDesc(((Field)rm).getType()));
										if(m.getReturnType()!=void.class)
										{
											mn.visitVarInsn(Opcodes.ALOAD,0);
											mn.instructions.add(AsmUtil.castNode(m.getReturnType(),wrapper));
											mn.instructions.add(AsmUtil.returnNode(m.getReturnType()));
										}
										else
											mn.visitInsn(Opcodes.RETURN);
									}
									break;
								default:
									throw new IllegalArgumentException("Field accessor can only be getter(void) or setter(value). On "+m+".");
							}
						}
						else if(rm instanceof Constructor)
						{
							if(m.getReturnType()!=wrapper)
								throw new IllegalArgumentException("Wrapped constructor must return wrapped this. On "+m);
							mn.visitLdcInsn(Type.getType(wrapper));
							if(accessByForce)
							{
								mn.visitFieldInsn(Opcodes.GETSTATIC,getType(cn.name),"objects",getDesc(Object[].class));
								mn.instructions.add(arrayLoadNode(Object.class,toList(ldcNode(objects.size()))));
								mn.instructions.add(castNode(MethodHandle.class,Object.class));
								for(int i=0;i<m.getParameterCount();i++)
								{
									mn.instructions.add(AsmUtil.varLoadNode(m.getParameterTypes()[i],i+1));
									if(WrappedObject.class.isAssignableFrom(m.getParameterTypes()[i]))
									{
										mn.instructions.add(AsmUtil.getRawNode());
										mn.instructions.add(AsmUtil.castNode(((Constructor<?>)rm).getParameterTypes()[i],Object.class));
									}
									else
										mn.instructions.add(AsmUtil.castNode(((Constructor<?>)rm).getParameterTypes()[i],m.getParameterTypes()[i]));
								}
								mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL,getType(MethodHandle.class),"invokeExact",getDesc(((Constructor<?>)rm).getParameterTypes(),rawClass),false);
								objects.add(ClassUtil.unreflect((Constructor<?>)rm));
							}
							else
							{
								mn.visitTypeInsn(Opcodes.NEW,AsmUtil.getType(rawClass));
								mn.instructions.add(AsmUtil.dupNode(rawClass));
								for(int i=0;i<m.getParameterCount();i++)
								{
									mn.instructions.add(AsmUtil.varLoadNode(m.getParameterTypes()[i],i+1));
									if(WrappedObject.class.isAssignableFrom(m.getParameterTypes()[i]))
									{
										mn.instructions.add(AsmUtil.getRawNode());
										mn.instructions.add(AsmUtil.castNode(((Constructor<?>)rm).getParameterTypes()[i],Object.class));
									}
									else
										mn.instructions.add(AsmUtil.castNode(((Constructor<?>)rm).getParameterTypes()[i],m.getParameterTypes()[i]));
								}
								mn.visitMethodInsn(Opcodes.INVOKESPECIAL,AsmUtil.getType(rawClass),"<init>",AsmUtil.getDesc((Constructor<?>)rm),false);
							}
							mn.instructions.add(AsmUtil.wrapNode());
							mn.instructions.add(AsmUtil.castNode(wrapper,WrappedObject.class));
							mn.instructions.add(AsmUtil.returnNode(wrapper));
						}
						else if(rm instanceof Method)
						{
							if(WrappedObject.class.isAssignableFrom(m.getReturnType()))
								mn.visitLdcInsn(Type.getType(m.getReturnType()));
							if(accessByForce)
							{
								mn.visitFieldInsn(Opcodes.GETSTATIC,getType(cn.name),"objects",getDesc(Object[].class));
								mn.instructions.add(arrayLoadNode(Object.class,toList(ldcNode(objects.size()))));
								mn.instructions.add(castNode(MethodHandle.class,Object.class));
								if(!Modifier.isStatic(rm.getModifiers()))
								{
									mn.visitVarInsn(Opcodes.ALOAD,0);
									mn.instructions.add(getRawNode());
									mn.instructions.add(castNode(((Method)rm).getDeclaringClass(),Object.class));
								}
								for(int i=0;i<m.getParameterCount();i++)
								{
									mn.instructions.add(AsmUtil.varLoadNode(m.getParameterTypes()[i],i+1));
									if(WrappedObject.class.isAssignableFrom(m.getParameterTypes()[i]))
									{
										mn.instructions.add(AsmUtil.getRawNode());
										mn.instructions.add(AsmUtil.castNode(((Method)rm).getParameterTypes()[i],Object.class));
									}
									else
										mn.instructions.add(AsmUtil.castNode(((Method)rm).getParameterTypes()[i],m.getParameterTypes()[i]));
								}
								mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL,getType(MethodHandle.class),"invokeExact",getDesc(Modifier.isStatic(rm.getModifiers())?((Method)rm).getParameterTypes():ListUtil.mergeLists(Lists.newArrayList(rm.getDeclaringClass()),Lists.newArrayList(((Method)rm).getParameterTypes())).toArray(new Class[0]),((Method)rm).getReturnType()),false);
								if(WrappedObject.class.isAssignableFrom(m.getReturnType()))
								{
									mn.instructions.add(castNode(Object.class,((Method)rm).getReturnType()));
									mn.instructions.add(wrapNode());
									mn.instructions.add(castNode(m.getReturnType(),WrappedObject.class));
								}
								else
									mn.instructions.add(castNode(m.getReturnType(),((Method)rm).getReturnType()));
								mn.instructions.add(returnNode(m.getReturnType()));
								objects.add(ClassUtil.unreflect((Method)rm));
							}
							else
							{
								if(!Modifier.isStatic(rm.getModifiers()))
								{
									mn.visitVarInsn(Opcodes.ALOAD,0);
									mn.instructions.add(getRawNode());
									mn.instructions.add(castNode(((Method)rm).getDeclaringClass(),Object.class));
								}
								for(int i=0;i<m.getParameterCount();i++)
								{
									mn.instructions.add(AsmUtil.varLoadNode(m.getParameterTypes()[i],i+1));
									if(WrappedObject.class.isAssignableFrom(m.getParameterTypes()[i]))
									{
										mn.instructions.add(AsmUtil.getRawNode());
										mn.instructions.add(AsmUtil.castNode(((Method)rm).getParameterTypes()[i],Object.class));
									}
									else
										mn.instructions.add(AsmUtil.castNode(((Method)rm).getParameterTypes()[i],m.getParameterTypes()[i]));
								}
								mn.visitMethodInsn(Modifier.isStatic(rm.getModifiers())?Opcodes.INVOKESTATIC:(rm.getDeclaringClass().isInterface()?Opcodes.INVOKEINTERFACE:Opcodes.INVOKEVIRTUAL),AsmUtil.getType(rm.getDeclaringClass()),rm.getName(),AsmUtil.getDesc((Method)rm),rm.getDeclaringClass().isInterface());
								if(WrappedObject.class.isAssignableFrom(m.getReturnType()))
								{
									mn.instructions.add(castNode(Object.class,((Method)rm).getReturnType()));
									mn.instructions.add(wrapNode());
									mn.instructions.add(castNode(m.getReturnType(),WrappedObject.class));
								}
								else
									mn.instructions.add(castNode(m.getReturnType(),((Method)rm).getReturnType()));
								mn.instructions.add(returnNode(m.getReturnType()));
							}
						}
						else
							throw new IllegalArgumentException("Unknown raw member "+rm+" for "+m+".");
						cn.methods.add(mn);
					}
					controller.apply(cn,wrapper);
					for(Method m: wrapper.getMethods())
					{
						if(!Modifier.isAbstract(m.getModifiers())||Modifier.isStatic(m.getModifiers())||!WrappedObject.class.isAssignableFrom(m.getDeclaringClass()))
							continue;
						if(AsmUtil.getMethodNode(cn,m.getName(),AsmUtil.getDesc(m))!=null)
							continue;
						if(m.getName().equals("getRaw")&&m.getParameterCount()==0)
						{
							mn=new MethodNode(Opcodes.ACC_PUBLIC,m.getName(),getDesc(m),null,new String[0]);
							mn.visitVarInsn(Opcodes.ALOAD,0);
							mn.visitMethodInsn(Opcodes.INVOKESPECIAL,getType(AbsWrappedObject.class),"getRaw",getDesc(new Class[0],Object.class),false);
							mn.instructions.add(castNode(m.getReturnType(),Object.class));
							mn.instructions.add(returnNode(m.getReturnType()));
							cn.methods.add(mn);
							continue;
						}
						if(m.getDeclaringClass()==wrapper)
							continue;
						mn=new MethodNode(Opcodes.ACC_PUBLIC,m.getName(),getDesc(m),null,new String[0]);
						mn.visitVarInsn(Opcodes.ALOAD,0);
						mn.visitLdcInsn(Type.getType(m.getDeclaringClass()));
						mn.visitMethodInsn(Opcodes.INVOKEINTERFACE,AsmUtil.getType(WrappedObject.class),"cast",AsmUtil.getDesc(new Class[]{Class.class},WrappedObject.class),true);
						mn.instructions.add(AsmUtil.castNode(m.getDeclaringClass(),WrappedObject.class));
						for(int i=0;i<m.getParameterCount();i++)
							mn.instructions.add(AsmUtil.varLoadNode(m.getParameterTypes()[i],i+1));
						mn.visitMethodInsn(Opcodes.INVOKEINTERFACE,AsmUtil.getType(m.getDeclaringClass()),m.getName(),AsmUtil.getDesc(m),true);
						mn.instructions.add(AsmUtil.returnNode(m.getReturnType()));
						cn.methods.add(mn);
					}
					
					ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
					cn.accept(cw);
					Class<?> cl=ClassUtil.loadClass(cn.name,cw.toByteArray(),new SimpleLoader(wrapper.getClassLoader()));
					try
					{
						c=Root.getTrusted().findConstructor(cl,MethodType.methodType(void.class,new Class[]{Object.class}));
					}
					catch(IllegalAccessException e)
					{
						if(e.getCause() instanceof VerifyError)
						{
							try(FileOutputStream fos=new FileOutputStream(cn.name.replace('/','.')+".class"))
							{
								fos.write(cw.toByteArray());
							}
							throw e.getCause();
						}
						else
							throw e;
					}
					cl.getDeclaredField("objects").set(null,objects.toArray());
					synchronized(cache)
					{
						Map<Class<? extends WrappedObject>,MethodHandle> cacheCopy=new WeakHashMap<>(cache.get());
						cacheCopy.put(wrapper,c);
						cache.set(cacheCopy);
					}
				}
				finally
				{
					buildingWrappers.remove(wrapper);
				}
			}
			T r=TypeUtil.cast(c.invoke(raw));
			if(raw!=null && !getRawClass(wrapper).isAssignableFrom(ClassUtil.getClass(raw)))
				throw new ClassCastException(raw+" is not "+getRawClass(wrapper));
			return r;
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	
	default Member getRawMember(Class<?> rawClass,Method m,Annotation a)
	{
		if(a instanceof WrappedFieldAccessor)
		{
			Class<?> type;
			if(m.getParameterCount()==0)
				type=m.getReturnType();
			else
				type=m.getParameterTypes()[0];
			if(WrappedObject.class.isAssignableFrom(type))
				type=WrappedObject.getRawClass(TypeUtil.cast(type));
			Class<?> finalType=type;
			for(String name: ((WrappedFieldAccessor)a).value())
			{
				try
				{
					if(name.startsWith("#"))
					{
						return Arrays.stream(rawClass.getDeclaredFields()).filter(f->Modifier.isStatic(f.getModifiers())).filter(f->f.getType()==finalType).collect(Collectors.toList()).get(Integer.parseInt(name.substring(1)));
					}
					else if(name.startsWith("@"))
					{
						return Arrays.stream(rawClass.getDeclaredFields()).filter(f->!Modifier.isStatic(f.getModifiers())).filter(f->f.getType()==finalType).collect(Collectors.toList()).get(Integer.parseInt(name.substring(1)));
					}
					else
						return rawClass.getDeclaredField(name);
				}
				catch(NoSuchFieldException|IndexOutOfBoundsException ignored)
				{
				}
			}
			if(m.getDeclaredAnnotation(Optional.class)==null)
				throw TypeUtil.throwException(new NoSuchFieldException("Field of accessor "+m+" is not found."));
		}
		else if(a instanceof WrappedConstructor)
		{
			try
			{
				return rawClass.getDeclaredConstructor(getRawClasses(m.getParameterTypes()));
			}
			catch(NoSuchMethodException ignored)
			{
			}
			if(m.getDeclaredAnnotation(Optional.class)==null)
				throw TypeUtil.throwException(new NoSuchMethodException("Method of wrapper "+m+" is not found."));
		}
		else if(a instanceof WrappedMethod)
		{
			for(String name: ((WrappedMethod)a).value())
			{
				if(name.startsWith("@")||name.startsWith("#"))
					return Arrays.stream(rawClass.getDeclaredMethods()).filter(t->(name.charAt(0)=='#')==Modifier.isStatic(t.getModifiers())).filter(t->t.getReturnType().equals(getRawClasses(m.getReturnType())[0])&&Arrays.equals(t.getParameterTypes(),getRawClasses(m.getParameterTypes()))).toArray(Method[]::new)[Integer.parseInt(name.substring(1))];
				else
					try
					{
						return rawClass.getDeclaredMethod(name,getRawClasses(m.getParameterTypes()));
					}
					catch(NoSuchMethodException ignored)
					{
					}
			}
			if(m.getDeclaredAnnotation(Optional.class)==null)
				throw TypeUtil.throwException(new NoSuchMethodException("Method of wrapper "+m+" is not found."));
		}
		return null;
	}
	
	default void apply(ClassNode cn,Class<? extends WrappedObject> wrapper)
	{
	}
	
	default Class<?> getAnnotationClass(Class<? extends WrappedObject> wrapper)
	{
		WrappedClass a=wrapper.getDeclaredAnnotation(WrappedClass.class);
		if(a!=null)
			for(String n:a.value())
			{
				try
				{
					return Class.forName(n,false,this.getClass().getClassLoader());
				}
				catch(ClassNotFoundException ignored)
				{
				}
			}
		return null;
	}
	
	static Class<?>[] getRawClasses(Class<?> ...classes)
	{
		Class<?>[] r=Arrays.copyOf(classes,classes.length);
		for(int i=0;i<r.length;i++)
		{
			if(WrappedObject.class.isAssignableFrom(r[i]))
				r[i]=WrappedObject.getRawClass(TypeUtil.cast(r[i]));
		}
		return r;
	}
}
