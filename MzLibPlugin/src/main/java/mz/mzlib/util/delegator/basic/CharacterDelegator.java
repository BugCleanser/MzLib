package mz.mzlib.util.delegator.basic;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClass;
import mz.mzlib.util.delegator.DelegatorFieldAccessor;

@DelegatorClass(Character.class)
public interface CharacterDelegator extends Delegator
{
	static CharacterDelegator newInstance(char value)
	{
		return Delegator.create(CharacterDelegator.class,value);
	}
	
	@Override
	Character getDelegate();
	
	@Deprecated
	@DelegatorFieldAccessor("value")
	void setValue(char value);
}
