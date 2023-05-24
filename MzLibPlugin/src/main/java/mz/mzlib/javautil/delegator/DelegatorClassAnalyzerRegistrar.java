package mz.mzlib.javautil.delegator;

import mz.mzlib.javautil.ClassUtil;
import mz.mzlib.javautil.Instance;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DelegatorClassAnalyzerRegistrar implements IRegistrar<DelegatorClassAnalyzer>, Instance
{
	public static DelegatorClassAnalyzerRegistrar instance=new DelegatorClassAnalyzerRegistrar();
	
	public Set<DelegatorClassAnalyzer> analyzers=ConcurrentHashMap.newKeySet();
	
	@Override
	public Class<DelegatorClassAnalyzer> getType()
	{
		//TODO: ClassUtil
		return DelegatorClassAnalyzer.class;
	}
	@Override
	public void register(MzModule module,DelegatorClassAnalyzer object)
	{
		analyzers.add(object);
	}
	@Override
	public void unregister(MzModule module,DelegatorClassAnalyzer object)
	{
		analyzers.remove(object);
	}
}
