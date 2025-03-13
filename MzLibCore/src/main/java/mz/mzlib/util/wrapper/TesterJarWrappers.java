package mz.mzlib.util.wrapper;

import mz.mzlib.tester.Tester;
import mz.mzlib.tester.TesterContext;
import mz.mzlib.util.ElementSwitcher;
import mz.mzlib.util.RuntimeUtil;

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
		return CompletableFuture.supplyAsync(()->
		{
			List<String> classNames=new ArrayList<>();
			try(JarFile jar=new JarFile(this.file))
			{
				for(JarEntry entry: jar.stream().toArray(JarEntry[]::new))
				{
					if(!entry.getName().endsWith(".class"))
						continue;
					classNames.add(entry.getName().substring(0, entry.getName().length()-".class".length()).replace('/', '.'));
				}
			}
			catch(IOException e)
			{
				throw RuntimeUtil.sneakilyThrow(e);
			}
			List<Throwable> exceptions=new ArrayList<>();
			for(String className: classNames)
			{
				Class<?> clazz;
				try
				{
					clazz = Class.forName(className, false, classLoader);
				}
				catch(Throwable ignored)
				{
					continue;
				}
				if(WrapperObject.class.isAssignableFrom(clazz) && ElementSwitcher.isEnabled(clazz))
				{
					try
					{
						WrapperFactory.find(RuntimeUtil.<Class<? extends WrapperObject>>cast(clazz));
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
