package mz.mzlib.util.nothing;

import mz.mzlib.util.delegator.Delegator;

public interface Nothing
{
	static <T extends Delegator> T notReturn()
	{
		return null;
	}
}
