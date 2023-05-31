package mz.lib.nothing;

import mz.asm.ClassReader;
import mz.asm.ClassWriter;
import mz.asm.tree.ClassNode;
import mz.asm.tree.MethodNode;
import mz.lib.*;
import mz.lib.wrapper.WrappedObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NothingClass
{
	public static Map<Class<?>,NothingClass> classes=new ConcurrentHashMap<>();
	
	public Class<?> rawClass;
	public byte[] rawData;
	public List<Class<? extends Nothing>> installedNothings;
	public List<Integer> handles=new ArrayList<>();
	public NothingClass(Class<?> rawClass)
	{
		try
		{
			this.rawClass=rawClass;
			rawData=FileUtil.readInputStream(ClassUtil.read(rawClass));
			installedNothings=new ArrayList<>();
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public <T extends Nothing&WrappedObject> void install(Class<T> nothing)
	{
		installedNothings.add(nothing);
		update();
	}
	public <T extends Nothing&WrappedObject> void uninstall(Class<T> nothing)
	{
		installedNothings.remove(nothing);
		if(installedNothings.isEmpty())
			uninstallAll();
		else
			update();
	}
	public void uninstallAll()
	{
		installedNothings.clear();
		free();
		ClassUtil.loadClass(rawClass.getName(),rawData,rawClass.getClassLoader());
	}
	public int test(int[] v)
	{
		return v[114514];
	}
	public void free()
	{
		for(int h:handles)
			Nothing.data.remove(h);
		handles.clear();
	}
	public void update()
	{
		free();
		try
		{
			ClassNode cn=new ClassNode();
			new ClassReader(rawData).accept(cn,0);
			
			Map<MethodNode,NothingMethod> nothingMethods=new HashMap<>();
			for(Class<? extends Nothing> n: installedNothings)
			{
				for(Method m: n.getDeclaredMethods())
				{
					NothingInject[] is=TypeUtil.<Nothing,Object>cast(WrappedObject.getStatic(TypeUtil.cast(n))).getInjects(m);
					if(is!=null)
						for(NothingInject i: is)
						{
							Class<?>[] args=i.args();
							for(int j=0;j<args.length;j++)
							{
								if(WrappedObject.class.isAssignableFrom(args[j]))
									args[j]=WrappedObject.getRawClass(TypeUtil.cast(args[j]));
							}
							Method tar=null;
							for(String s:i.name())
							{
								try
								{
									tar=rawClass.getDeclaredMethod(s,args);
								}
								catch(NoSuchMethodException ignored)
								{
								}
							}
							if(tar==null)
							{
								if(i.optional()||m.getDeclaredAnnotation(Optional.class)!=null)
									break;
								else
									throw new NoSuchMethodException("Target of "+m+".");
							}
							MethodNode mn=AsmUtil.getMethodNode(cn,tar.getName(),AsmUtil.getDesc(tar));
							if(!nothingMethods.containsKey(mn))
								nothingMethods.put(mn,new NothingMethod(tar,mn));
							nothingMethods.get(mn).inject(i,m);
						}
				}
			}
			for(NothingMethod nm:nothingMethods.values())
			{
				handles.addAll(nm.apply());
			}
			
			ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES)
			{
				@Override
				protected String getCommonSuperClass(String type1,String type2)
				{
					ClassLoader cl = rawClass.getClassLoader();
					Class<?> var3;
					Class<?> var4;
					try
					{
						var3 = Class.forName(type1.replace('/', '.'), false, cl);
						var4 = Class.forName(type2.replace('/', '.'), false, cl);
					}
					catch (Exception e)
					{
						throw TypeUtil.throwException(e);
					}
					if(var3.isAssignableFrom(var4))
						return type1;
					else if (var4.isAssignableFrom(var3))
						return type2;
					else if (!var3.isInterface() && !var4.isInterface())
					{
						do
						{
							var3=var3.getSuperclass();
						}while(!var3.isAssignableFrom(var4));
						return var3.getName().replace('.', '/');
					}
					else
						return "java/lang/Object";
				}
			};
			cn.accept(cw);
			ClassUtil.loadClass(rawClass.getName(),cw.toByteArray(),rawClass.getClassLoader());
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
}
