package mz.lib.mzlang;

import com.google.common.collect.*;
import io.github.karlatemp.unsafeaccessor.*;
import mz.asm.*;
import mz.asm.tree.*;
import mz.lib.*;
import mz.lib.wrapper.*;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public interface MzObject
{
	@CallEach
	default void init()
	{
	}
	
	static <T extends MzObject> T newInstance(Class<T> interfase)
	{
		T r=newUninitializedInstance(interfase);
		r.init();
		return r;
	}
	Ref<Map<Class<? extends MzObject>,Supplier<? extends MzObject>>> implCache=new Ref<>(new WeakHashMap<>());
	static <T extends MzObject> T newUninitializedInstance(Class<T> interfase)
	{
		try
		{
			Supplier<? extends MzObject> c=implCache.get().get(interfase);
			if(c==null)
			{
				ClassNode cn=new ClassNode();
				Class<?> extend=Object.class;
				Extends aExtend=interfase.getAnnotation(Extends.class);
				if(aExtend!=null)
					extend=aExtend.value();
				if(WrappedObject.class.isAssignableFrom(extend))
					extend=WrappedObject.getRawClass(TypeUtil.cast(extend));
				List<Class<?>> impls=Arrays.stream(interfase.getAnnotationsByType(Implements.class)).map(Implements::value).map(i->
				{
					if(WrappedObject.class.isAssignableFrom(i))
						return WrappedObject.getRawClass(TypeUtil.cast(i));
					return i;
				}).collect(Collectors.toList());
				impls.add(interfase);
				cn.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,AsmUtil.getType("mz.lib.mzlang.objgen."+interfase.getName()),null,AsmUtil.getType(extend),impls.stream().map(AsmUtil::getType).toArray(String[]::new));
				
				cn.visitField(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC,"superMethods",AsmUtil.getDesc(MethodHandle[].class),null,null);
				List<MethodHandle> superMethods=new ArrayList<>();
				
				cn.visitField(Opcodes.ACC_PUBLIC,"props",AsmUtil.getDesc(Object[].class),null,null);
				int propsNum=0;
				Map<String,Integer> props=new HashMap<>();
				
				MethodNode mn;
				for(Method m:ClassUtil.getAllMethods(interfase))
				{
					PropAccessor pa=m.getDeclaredAnnotation(PropAccessor.class);
					if(pa!=null)
					{
						if(!props.containsKey(pa.value()))
							props.put(pa.value(),propsNum++);
						mn=new MethodNode(Opcodes.ACC_PUBLIC,m.getName(),AsmUtil.getDesc(m),null,new String[0]);
						mn.visitVarInsn(Opcodes.ALOAD,0);
						mn.visitFieldInsn(Opcodes.GETFIELD,AsmUtil.getType(cn.name),"props",AsmUtil.getDesc(Object[].class));
						switch(m.getParameterCount())
						{
							case 0:
								if(m.getReturnType()==void.class)
									throw new IllegalArgumentException("Getter must have a not void return type: "+m+".");
								mn.instructions.add(AsmUtil.arrayLoadNode(Object.class,AsmUtil.toList(AsmUtil.ldcNode(props.get(pa.value())))));
								mn.instructions.add(AsmUtil.castNode(m.getReturnType(),Object.class));
								mn.instructions.add(AsmUtil.returnNode(m.getReturnType()));
								break;
							case 1:
								mn.instructions.add(AsmUtil.arrayStoreNode(Object.class,AsmUtil.toList(AsmUtil.ldcNode(props.get(pa.value()))),AsmUtil.mergeList(AsmUtil.toList(AsmUtil.varLoadNode(m.getParameterTypes()[0],1)),AsmUtil.castNode(Object.class,m.getParameterTypes()[0]))));
								if(m.getReturnType()==void.class)
									mn.instructions.add(AsmUtil.returnNode(void.class));
								else if(m.getReturnType().isAssignableFrom(interfase))
								{
									mn.visitVarInsn(Opcodes.ALOAD,0);
									mn.instructions.add(AsmUtil.returnNode(interfase));
								}
								else
									throw new IllegalArgumentException("Setter must return this or void: "+m+".");
								break;
							default:
								throw new IllegalArgumentException("Accessor must be a getter or a setter: "+m+".");
						}
						cn.methods.add(mn);
					}
				}
				T controller=ClassUtil.newInstance(interfase);
				for(Method m:interfase.getMethods())
				{
					CallEach ce=ClassUtil.getSuperAnnotation(m,CallEach.class);
					if(ce!=null)
					{
						if(m.getReturnType()!=void.class)
							throw new IllegalArgumentException("Method with @CallEach must return void: "+m+".");
						List<Class<?>> notCalls=ClassUtil.getSuperAnnotations(m,NotCallSuper.class).stream().map(NotCallSuper::value).collect(Collectors.toList());
						mn=new MethodNode(Opcodes.ACC_PUBLIC,m.getName(),AsmUtil.getDesc(m),null,new String[0]);
						for(Method e:ClassUtil.getAllMethods(m.getDeclaringClass(),m.getName(),m.getParameterTypes()))
						{
							if(notCalls.contains(e.getDeclaringClass())||Modifier.isAbstract(e.getModifiers()))
								continue;
							if(e.getReturnType()!=void.class)
								throw new IllegalArgumentException("Method for CallEach cannot return anything: "+e+".");
							mn.visitFieldInsn(Opcodes.GETSTATIC,cn.name,"superMethods",AsmUtil.getDesc(MethodHandle[].class));
							mn.instructions.add(AsmUtil.arrayLoadNode(MethodHandle.class,AsmUtil.toList(AsmUtil.ldcNode(superMethods.size()))));
							superMethods.add(ClassUtil.unreflectSpecial(e));
							mn.visitVarInsn(Opcodes.ALOAD,0);
							for(int i=0;i<e.getParameterCount();i++)
								mn.instructions.add(AsmUtil.varLoadNode(e.getParameterTypes()[i],i+1));
							List<Class<?>> args=Lists.newArrayList(e.getDeclaringClass());
							args.addAll(Arrays.asList(e.getParameterTypes()));
							mn.visitMethodInsn(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(MethodHandle.class),"invokeExact",AsmUtil.getDesc(args.toArray(new Class[0]),e.getReturnType()),false);
						}
						mn.instructions.add(AsmUtil.returnNode(void.class));
						cn.methods.add(mn);
					}
					for(String n:controller.getRefactorSignNames(m))
					{
						if(Modifier.isAbstract(m.getModifiers()))
							throw new IllegalArgumentException("The method to refactor cannot be abstract");
						if(Modifier.isStatic(m.getModifiers()))
							throw new IllegalArgumentException("The method to refactor cannot be static");
						Class<?>[] pt=m.getParameterTypes();
						Class<?> r=m.getReturnType();
						if(WrappedObject.class.isAssignableFrom(r))
							r=WrappedObject.getRawClass(TypeUtil.cast(r));
						mn=new MethodNode(Opcodes.ACC_PUBLIC,n,AsmUtil.getDesc(WrappedObject.getRawClasses(pt),r),null,new String[0]);
						mn.visitVarInsn(Opcodes.ALOAD,0);
						for(int i=0;i<pt.length;i++)
						{
							if(WrappedObject.class.isAssignableFrom(pt[i]))
								mn.instructions.add(AsmUtil.ldcNode(pt[i]));
							mn.instructions.add(AsmUtil.varLoadNode(pt[i],i+1));
							if(WrappedObject.class.isAssignableFrom(pt[i]))
								mn.instructions.add(AsmUtil.wrapNode());
						}
						mn.visitMethodInsn(Opcodes.INVOKESPECIAL,AsmUtil.getType(interfase),m.getName(),AsmUtil.getDesc(m),true);
						if(WrappedObject.class.isAssignableFrom(m.getReturnType()))
						{
							mn.instructions.add(AsmUtil.getRawNode());
							mn.instructions.add(AsmUtil.castNode(r,Object.class));
						}
						mn.instructions.add(AsmUtil.returnNode(r));
						cn.methods.add(mn);
					}
				}
				
				cn.visitField(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC,"propsNum",AsmUtil.getDesc(int.class),null,propsNum);
				
				ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
				cn.accept(cw);
				Class<?> t=ClassUtil.loadClass(cn.name,cw.toByteArray(),new SimpleLoader(interfase.getClassLoader()));
				t.getDeclaredField("superMethods").set(null,superMethods.toArray(new MethodHandle[0]));
				MethodHandle propsSetter=Root.getTrusted().findSetter(t,"props",Object[].class);
				int finalPropsNum=propsNum;
				c=()->
				{
					try
					{
						MzObject r=TypeUtil.cast(Root.getUnsafe().allocateInstance(t));
						propsSetter.invokeWithArguments(r,new Object[finalPropsNum]);
						return r;
					}
					catch(Throwable e)
					{
						throw TypeUtil.throwException(e);
					}
				};
				
				synchronized(implCache)
				{
					Map<Class<? extends MzObject>,Supplier<? extends MzObject>> n=new WeakHashMap<>(implCache.get());
					n.put(interfase,c);
					implCache.set(n);
				}
			}
			return TypeUtil.cast(c.get());
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	
	default String[] getRefactorSignNames(Method method)
	{
		RefactorSign a=ClassUtil.getSuperAnnotation(method,RefactorSign.class);
		if(a!=null)
			return a.value();
		return new String[0];
	}
}
