package mz.mzlib.minecraft;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.minecraft.mappings.MappingMethod;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.WrapClass;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.Wrapper_void;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

// TODO
public class ModuleMapStackTrace extends MzModule
{
	public static ModuleMapStackTrace instance=new ModuleMapStackTrace();
	
	@WrapClass(Throwable.class)
	public interface NothingThrowable extends WrapperObject, Nothing
	{
		@Override
		Throwable getWrapped();
		
		@WrapMethod("printStackTrace")
		void printStackTrace(PrintStream s);
		@WrapMethod("getOurStackTrace")
		StackTraceElement[] getOurStackTrace();
		
		ThreadLocal<WeakHashMap<Throwable, Boolean>> isStackTracePrinting=ThreadLocal.withInitial(WeakHashMap::new);
		
		@NothingInject(wrapperMethod="printStackTrace", locateMethod="", type= NothingInjectType.INSERT_BEFORE)
		default Wrapper_void printStackTraceBefore()
		{
			isStackTracePrinting.get().put(this.getWrapped(), true);
			return Nothing.notReturn();
		}
		static void locatePrintStackTraceAfter(NothingInjectLocating locating)
		{
			locating.allAfter(Opcodes.RETURN);
			assert !locating.locations.isEmpty();
		}
		@NothingInject(wrapperMethod="printStackTrace", locateMethod="locatePrintStackTraceAfter", type= NothingInjectType.INSERT_BEFORE)
		default Wrapper_void printStackTraceAfter()
		{
			isStackTracePrinting.get().remove(this.getWrapped());
			return Nothing.notReturn();
		}
		static void locateGetOurStackTraceAfter(NothingInjectLocating locating)
		{
			locating.allAfter(Opcodes.ARETURN);
			assert !locating.locations.isEmpty();
		}
		@NothingInject(wrapperMethod="getOurStackTrace", locateMethod="locateGetOurStackTraceAfter", type= NothingInjectType.INSERT_BEFORE)
		default WrapperObject getOurStackTraceAfter(@StackTop StackTraceElement[] returnValue)
		{
			if(!isStackTracePrinting.get().getOrDefault(this.getWrapped(), false))
				return Nothing.notReturn();
			StackTraceElement[] result=new StackTraceElement[returnValue.length];
			for(int i=0;i<result.length;i++)
			{
				String name=MinecraftPlatform.instance.getMappingsP2Y().mapClass0(returnValue[i].getClassName());
				if(name!=null)
				{
					String methodName=returnValue[i].getMethodName();
					if(!Objects.equals(methodName, "<init>") && !Objects.equals(methodName, "<clinit>"))
					{
						try
						{
							String finalMethodName=methodName;
							Set<Method> methods=Arrays.stream(Class.forName(returnValue[i].getClassName()).getDeclaredMethods()).filter(m->m.getName().equals(finalMethodName)).collect(Collectors.toSet());
							if(methods.size()!=1)
								throw new NoSuchMethodException();
							Method method=methods.iterator().next();
							methodName=MinecraftPlatform.instance.getMappingsP2Y().mapMethod(name, new MappingMethod(method.getName(),Arrays.stream(method.getParameterTypes()).map(AsmUtil::getType).toArray(String[]::new)));
						}
						catch(ClassNotFoundException | NoSuchMethodException e)
						{
							methodName="?"+methodName;
						}
					}
					result[i]=new StackTraceElement(name,methodName,returnValue[i].getFileName(),returnValue[i].getLineNumber());
				}
				else
					result[i]=returnValue[i];
			}
			return WrapperObject.create(result);
		}
	}
	
	@Override
	public void onLoad()
	{
		this.register(NothingThrowable.class);
	}
}
