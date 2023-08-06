package mz.mzlib.util.nothing;

import mz.mzlib.asm.ClassReader;
import mz.mzlib.asm.ClassWriter;
import mz.mzlib.asm.tree.AbstractInsnNode;
import mz.mzlib.asm.tree.ClassNode;
import mz.mzlib.asm.tree.MethodNode;
import mz.mzlib.util.AsmUtil;
import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.MapEntry;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.delegator.Delegator;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NothingRegistration
{
	public Class<?> target;
	public Set<Class<? extends Nothing>> nothings;
	public byte[] rawByteCode;
	
	public NothingRegistration(Class<?> target)
	{
		this.target=target;
		this.nothings=ConcurrentHashMap.newKeySet();
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
	
	public void apply()
	{
		ClassNode cn=new ClassNode();
		new ClassReader(rawByteCode).accept(cn,0);
		Map<MethodNode,AbstractInsnNode[]> raws=new HashMap<>();
		for(MethodNode m:cn.methods)
			raws.put(m,m.instructions.toArray());
		PriorityQueue<MapEntry<Float,Runnable>> operations=new PriorityQueue<>(Map.Entry.comparingByKey());
		for(Class<? extends Nothing> nothing:nothings)
			for(Method i:nothing.getDeclaredMethods())
			{
				NothingInject ni=i.getDeclaredAnnotation(NothingInject.class);
				if(ni==null)
					continue;
				Member m=Delegator.findMember(target,ni.methodNames(),ni.methodArgs());
				if(m==null)
					throw RuntimeUtil.forceThrow(new NoSuchMethodException("Target of "+i));
				operations.add(new MapEntry<>(ni.priority(),()->
				{
					MethodNode mn=AsmUtil.getMethodNode(cn,m.getName(),AsmUtil.getDesc(m));
					AbstractInsnNode[] raw=raws.get(mn);
					Set<Integer> locs=new HashSet<>();
					locs.add(0);
					for(LocatingStep j:ni.locatingSteps())
					{
						Set<Integer> lastLocs=locs;
						locs=new HashSet<>();
						for(int k:lastLocs)
							switch(j.type())
							{
							case OFFSET:
								if(k+j.arg()>=0&&k+j.arg()<raw.length)
									locs.add(k+j.arg());
								break;
							case AFTER_FIRST:
							case AFTER_ALL:
								for(int l=1;l<=j.maxDistance()&&k+l<raw.length;l++)
									if(raw[k+l].getOpcode()==j.arg())
									{
										locs.add(k+l);
										if(j.type()==LocatingStepType.AFTER_FIRST)
											break;
									}
								break;
							}
					}
					if(locs.size()<ni.expectedMin()||locs.size()>ni.expectedMax())
						throw new IllegalStateException("Illegal Nothing injecting locations num: "+i);
					for(int j:locs)
						switch(ni.type())
						{
							case RAW:
								try
								{
									i.invoke(null,m,mn,raw[j]);
								}
								catch(Throwable e)
								{
									throw RuntimeUtil.forceThrow(e);
								}
								break;
							case SKIP:
								//TODO
								break;
							case CATCH:
								//TODO
								break;
							case INSERT_BEFORE:
								//TODO
								break;
						}
				}));
			}
		while(!operations.isEmpty())
			operations.poll().getValue().run();
		ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		ClassUtil.defineClass(target.getClassLoader(),cn.name,cw.toByteArray());
	}
}
