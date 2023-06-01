package mz.lib;

import io.github.karlatemp.unsafeaccessor.*;

import java.io.*;
import java.lang.instrument.*;

public class InstrumentationGetterAgent
{
	public static void premain(String agentArgs,Instrumentation inst) throws NoSuchFieldException, IllegalAccessException
	{
		String c="mz/lib/InstrumentationGetter";
		byte[] bs=readInputStream(InstrumentationGetterAgent.class.getClassLoader().getResourceAsStream(c+".class"));
		Root.getUnsafe().defineClass(c,bs,0,bs.length,ClassLoader.getSystemClassLoader(),null).getDeclaredField("instrumentation").set(null,inst);
	}
	public static byte[] readInputStream(InputStream inputStream)
	{
		byte[] buffer=new byte[4096];
		int len=0;
		try(ByteArrayOutputStream bos=new ByteArrayOutputStream())
		{
			while((len=inputStream.read(buffer))!=-1)
			{
				bos.write(buffer,0,len);
			}
			return bos.toByteArray();
		}
		catch(Throwable e)
		{
			throw new RuntimeException(e);
		}
	}
}
