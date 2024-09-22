package mz.mzlib.util.wrapper;

import mz.mzlib.test.Tester;
import mz.mzlib.test.TesterContext;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.ThrowableSupplier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class TesterJarWrappers implements Tester<TesterContext>
{
	public File file;
	public ClassLoader classLoader;
	public TesterJarWrappers(File file, ClassLoader classLoader)
	{
		this.file=file;
		this.classLoader=classLoader;
	}
	
	@Override
	public Class<TesterContext> getContextType()
	{
		return TesterContext.class;
	}

	@Override
	public String getName()
	{
		return "JarWrappers: "+file.getName();
	}

	@Override
	public CompletableFuture<List<Throwable>> test(TesterContext context)
	{
		JarFile jar;
		try
		{
			jar=new JarFile(this.file);
		}
		catch(IOException e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
		return CompletableFuture.supplyAsync((ThrowableSupplier<List<Throwable>, Throwable>) ()->
		{
			List<Throwable> exceptions=new ArrayList<>();
			for(JarEntry entry: jar.stream().toArray(JarEntry[]::new))
			{
				if(!entry.getName().endsWith(".class"))
					continue;
				Class<?> clazz=Class.forName(entry.getName().substring(0, entry.getName().length()-".class".length()), true, classLoader);
				if(WrapperObject.class.isAssignableFrom(clazz))
				{
					try
					{
						WrapperObject.create(RuntimeUtil.cast(clazz), null);
					}
					catch(Throwable e)
					{
						exceptions.add(e);
					}
				}
			}
			return exceptions;
		});
	}
}
