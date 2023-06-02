package mz.mzlib.util.delegator;

import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ExtendedDelegatorClassAnalyzer implements DelegatorClassAnalyzer,Instance
{
	public static ExtendedDelegatorClassAnalyzer instance=new ExtendedDelegatorClassAnalyzer();
	
	@Override
	public void analyse(DelegatorClassInfo info)
	{
		for(Method i:info.getDelegatorClass().getMethods())
			if(Modifier.isAbstract(i.getModifiers())&&i.getDeclaringClass()!=info.getDelegatorClass()&&Delegator.class.isAssignableFrom(i.getDeclaringClass()))
			{
				Member tar=DelegatorClassInfo.get(RuntimeUtil.forceCast(i.getDeclaringClass())).delegations.get(i);
				if(tar!=null)
					info.delegations.put(i,tar);
			}
	}
}
