package mz.mzlib.util.compound;

import mz.mzlib.util.RuntimeUtil;

public interface Compound
{
	void init() throws Throwable;
	
	static <T extends Compound> T create(Class<T> type)
	{
		try
		{
			T result=RuntimeUtil.cast((Compound)CompoundClassInfo.get(type).constructor.invokeExact());
			result.init();
			return result;
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
	}
}
