package mz.lib.nothing;

import com.google.common.collect.Lists;
import mz.asm.Label;
import mz.asm.Opcodes;
import mz.asm.tree.*;
import mz.lib.*;
import mz.lib.wrapper.WrappedObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A temp storage while NothingClass is updated
 */
public class NothingMethod
{
	public Method method;
	public MethodNode node;
	public Map<NothingInject,Method>[] injects;
	public NothingMethod(Method method,MethodNode node)
	{
		this.method=method;
		this.node=node;
		injects=TypeUtil.cast(new HashMap<?,?>[NothingPriority.values().length]);
		for(int i=0;i<injects.length;i++)
			injects[i]=new HashMap<>();
	}
	
	Map<String,Integer> customVars=new HashMap<>();
	public void inject(NothingInject ann,Method method)
	{
		injects[ann.priority().ordinal()].put(ann,method);
	}
	public InsnList injectNode(Method method,Map<Class<? extends Annotation>,BiFunction<Annotation,Class<?>,InsnList>> argNodeParsers)
	{
		InsnList r=new InsnList();
		List<Class<? extends WrappedObject>> argsWrappers=new ArrayList<>();
		if(!Modifier.isStatic(method.getModifiers()))
			argsWrappers.add(TypeUtil.cast(method.getDeclaringClass()));
		for(Class<?> a: method.getParameterTypes())
			argsWrappers.add(WrappedObject.class.isAssignableFrom(a) ? TypeUtil.cast(a) : null);
		int index=Nothing.data.alloc(new NothingInjectInvoker(ClassUtil.unreflectSpecial(method),TypeUtil.cast(argsWrappers.toArray(new Class[0]))));
		
		List<InsnList> args=new ArrayList<>(argsWrappers.size());
		if(!Modifier.isStatic(method.getModifiers()))
			args.add(AsmUtil.arrayNode(Object.class,AsmUtil.toList(AsmUtil.varLoadNode(Object.class,0))));
		
		InsnList later=new InsnList();
		Annotation[][] pas=method.getParameterAnnotations();
		Class<?>[] pts=method.getParameterTypes();
		for(int i=0;i<pas.length;i++)
		{
			for(Annotation a:pas[i])L1:
			{
				for(Map.Entry<Class<? extends Annotation>,BiFunction<Annotation,Class<?>,InsnList>> p:argNodeParsers.entrySet())
				{
					if(p.getKey().isAssignableFrom(a.getClass()))
					{
						args.add(p.getValue().apply(a,pts[i]));
						break L1;
					}
				}
				if(a instanceof LocalVar)
				{
					Class<?> type=pts[i];
					if(WrappedObject.class.isAssignableFrom(type))
						type=WrappedObject.getRawClass(TypeUtil.cast(type));
					assert type!=null;
					InsnList l=new InsnList();
					l.add(AsmUtil.varLoadNode(type,((LocalVar)a).value()));
					l.add(AsmUtil.castNode(Object.class,type));
					if(WrappedObject.class.isAssignableFrom(pts[i]))
					{
						l=AsmUtil.arrayNode(Object.class,l);
						l.add(AsmUtil.dupNode(Object[].class));
						int t=node.maxLocals++;
						l.add(AsmUtil.varStoreNode(Object[].class,t));
						
						later.add(AsmUtil.varLoadNode(Object[].class,t));
						later.add(AsmUtil.arrayLoadNode(Object.class,AsmUtil.toList(AsmUtil.ldcNode(0))));
						later.add(AsmUtil.castNode(type,Object.class));
						later.add(AsmUtil.varStoreNode(type,((LocalVar)a).value()));
					}
					args.add(l);
				}
				else if(a instanceof CustomVar)
				{
					InsnList l=new InsnList();
					if(!customVars.containsKey(((CustomVar)a).value()))
					{
						int t=node.maxLocals++;
						customVars.put(((CustomVar)a).value(),t);
						node.instructions.insert(AsmUtil.varStoreNode(Object[].class,t));
						node.instructions.insert(AsmUtil.arrayNode(AsmUtil.toList(AsmUtil.ldcNode(1)),Object.class));
					}
					l.add(AsmUtil.varLoadNode(Object[].class,customVars.get(((CustomVar)a).value())));
					args.add(l);
				}
				else if(a instanceof StackTop)
				{
					Class<?> type=((StackTop)a).value();
					if(WrappedObject.class.isAssignableFrom(type))
						type=WrappedObject.getRawClass(TypeUtil.cast(type));
					assert type!=null;
					int t=node.maxLocals++;
					r.add(AsmUtil.castNode(Object.class,type));
					InsnList lt=new InsnList();
					lt.add(AsmUtil.varLoadNode(Object.class,t));
					if(WrappedObject.class.isAssignableFrom(pts[i]))
					{
						r.add(AsmUtil.arrayNode(AsmUtil.toList(AsmUtil.ldcNode(1)),Object.class));
						r.add(new InsnNode(Opcodes.DUP_X1));
						r.add(new InsnNode(Opcodes.SWAP));
						r.add(AsmUtil.ldcNode(0));
						r.add(new InsnNode(Opcodes.SWAP));
						r.add(new InsnNode(Opcodes.AASTORE));
						
						lt.add(AsmUtil.arrayLoadNode(Object.class,AsmUtil.toList(AsmUtil.ldcNode(0))));
					}
					r.add(AsmUtil.varStoreNode(Object.class,t));
					
					lt.add(AsmUtil.castNode(type,Object.class));
					later.insert(lt);
					args.add(AsmUtil.toList(AsmUtil.varLoadNode(Object.class,t)));
				}
				else
					continue;
				break;
			}
		}
		if(args.size()<argsWrappers.size())
			throw new IllegalArgumentException("Unknown arg of "+method);
		r.add(new FieldInsnNode(Opcodes.GETSTATIC,AsmUtil.getType(NothingData.class),"data",AsmUtil.getDesc(List.class)));
		r.add(AsmUtil.ldcNode(index));
		r.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE,AsmUtil.getType(List.class),"get",AsmUtil.getDesc(new Class[]{int.class},Object.class),true));
		r.add(AsmUtil.castNode(Function.class,Object.class));
		r.add(AsmUtil.arrayNode(Object.class,args.toArray(new InsnList[0])));
		
		r.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE,AsmUtil.getType(Function.class),"apply",AsmUtil.getDesc(new Class[]{Object.class},Object.class),true));
		r.add(AsmUtil.dupNode(Optional.class));
		Label end=new Label();
		end.info=new LabelNode(end);
		r.add(new JumpInsnNode(Opcodes.IFNULL,(LabelNode)end.info));
		r.add(AsmUtil.castNode(Optional.class,Object.class));
		r.add(AsmUtil.ldcNode(null));
		r.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,AsmUtil.getType(Optional.class),"orElse",AsmUtil.getDesc(new Class[]{Object.class},Object.class),false));
		r.add(AsmUtil.castNode(this.method.getReturnType(),Object.class));
		r.add(AsmUtil.returnNode(this.method.getReturnType()));
		r.add((LabelNode)end.info);
		r.add(AsmUtil.popNode(Object.class));
		r.add(later);
		return r;
	}
	public List<Integer> apply()
	{
		try
		{
			List<Integer> handles=new ArrayList<>();
			List<AbstractInsnNode> raw=Lists.newArrayList(node.instructions.toArray());
			List<Label> labels=new ArrayList<>();
			for(AbstractInsnNode i:raw)
			{
				if(i instanceof LabelNode)
					labels.add(((LabelNode)i).getLabel());
			}
			for(int p=0;p<NothingPriority.values().length;p++)
			{
				for(Map.Entry<NothingInject,Method> i: injects[p].entrySet())
				{
					switch(i.getKey().location())
					{
						case BYTECODE:
						case FRONT:
							int loc=0;
							if(i.getKey().location()==NothingLocation.BYTECODE)
							{
								int index=i.getKey().byteCode().index();
								for(;loc<raw.size();loc++)
								{
									if(raw.get(loc).getOpcode()==i.getKey().byteCode().opcode())
									{
										if(raw.get(loc) instanceof MethodInsnNode)
										{
											Class<?> owner=i.getKey().byteCode().owner();
											if(WrappedObject.class.isAssignableFrom(owner))
												owner=WrappedObject.getRawClass(TypeUtil.cast(owner));
											assert owner!=null;
											if(!AsmUtil.getType(owner).equals(((MethodInsnNode)raw.get(loc)).owner))
												continue;
											if(!Lists.newArrayList(i.getKey().byteCode().name()).contains(((MethodInsnNode)raw.get(loc)).name))
												continue;
											Class<?>[] args=i.getKey().byteCode().methodArgs();
											for(int j=0;j<args.length;j++)
											{
												if(WrappedObject.class.isAssignableFrom(args[j]))
													args[j]=WrappedObject.getRawClass(TypeUtil.cast(args[j]));
											}
											if(!AsmUtil.getDesc(args,void.class).split("\\)")[0].equals(((MethodInsnNode)raw.get(loc)).desc.split("\\)")[0]))
												continue;
										}
										else if(raw.get(loc) instanceof FieldInsnNode)
										{
											Class<?> owner=i.getKey().byteCode().owner();
											if(WrappedObject.class.isAssignableFrom(owner))
												owner=WrappedObject.getRawClass(TypeUtil.cast(owner));
											assert owner!=null;
											if(!AsmUtil.getType(owner).equals(((FieldInsnNode)raw.get(loc)).owner))
												continue;
											if(!Lists.newArrayList(i.getKey().byteCode().name()).contains(((FieldInsnNode)raw.get(loc)).name))
												continue;
										}
										else if(raw.get(loc) instanceof VarInsnNode)
										{
											if(i.getKey().byteCode().var()!=((VarInsnNode)raw.get(loc)).var)
												continue;
										}
										else if(raw.get(loc) instanceof LabelNode)
										{
											if(i.getKey().byteCode().label()!=labels.indexOf(((LabelNode)raw.get(loc)).getLabel()))
												continue;
										}
										else if(raw.get(loc) instanceof JumpInsnNode)
										{
											if(i.getKey().byteCode().label()!=labels.indexOf(((JumpInsnNode)raw.get(loc)).label.getLabel()))
												continue;
										}
										if(--index<0)
											break;
									}
								}
								if(loc==raw.size())
								{
									if(i.getKey().optional()||i.getValue().getDeclaredAnnotation(mz.lib.Optional.class)!=null)
										break;
									else
										throw new IllegalArgumentException(i.getKey()+" "+i.getValue());
								}
							}
							loc+=i.getKey().shift();
							if(i.getValue().getDeclaredAnnotation(ManualByteCode.class)!=null)
								i.getValue().invoke(null,this,raw,raw.get(loc));
							else
								node.instructions.insertBefore(raw.get(loc),injectNode(i.getValue(),new HashMap<>()));
							break;
						case RETURN:
							ListIterator<AbstractInsnNode> it1=node.instructions.iterator();
							while(it1.hasNext())
							{
								AbstractInsnNode insn=it1.next();
								switch(insn.getOpcode())
								{
									case Opcodes.IRETURN:
									case Opcodes.LRETURN:
									case Opcodes.DRETURN:
									case Opcodes.ARETURN:
									case Opcodes.RETURN:
										AbstractInsnNode loc1=insn;
										int shift=i.getKey().shift();
										while(shift!=0)
										{
											if(shift>0)
											{
												loc1=loc1.getNext();
												shift--;
											}
											else
											{
												loc1=loc1.getPrevious();
												shift++;
											}
										}
										if(i.getValue().getDeclaredAnnotation(ManualByteCode.class)!=null)
											i.getValue().invoke(null,this,raw,loc1);
										else
											node.instructions.insertBefore(loc1,injectNode(i.getValue(),new HashMap<>()));
										break;
								}
							}
							break;
						case CATCH:
							Label begin=new Label(), end=new Label(), end1=new Label();
							begin.info=new LabelNode(begin);
							end.info=new LabelNode(end);
							end1.info=new LabelNode(end1);
							node.instructions.insert((LabelNode)begin.info);
							node.visitLabel(end);
							node.visitTryCatchBlock(begin,end,end,AsmUtil.getType(Throwable.class));
							node.visitLabel(end1);
							if(i.getValue().getDeclaredAnnotation(ManualByteCode.class)!=null)
								i.getValue().invoke(null,this,raw,end1.info);
							else
							{
								int caught=node.maxLocals++;
								node.instructions.insertBefore((AbstractInsnNode)end1.info,AsmUtil.varStoreNode(Throwable.class,caught));
								
								node.instructions.insertBefore((AbstractInsnNode)end1.info,injectNode(i.getValue(),ListUtil.toMap(new MapEntry<>(CaughtValue.class,(a,t)->AsmUtil.toList(new VarInsnNode(Opcodes.ALOAD,caught))))));
								
								node.instructions.insertBefore((AbstractInsnNode)end1.info,AsmUtil.ldcNode(null));
								node.instructions.insertBefore((AbstractInsnNode)end1.info,AsmUtil.castNode(method.getReturnType(),Object.class));
								node.instructions.insertBefore((AbstractInsnNode)end1.info,AsmUtil.returnNode(method.getReturnType()));
							}
							break;
					}
				}
			}
			return handles;
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
}
