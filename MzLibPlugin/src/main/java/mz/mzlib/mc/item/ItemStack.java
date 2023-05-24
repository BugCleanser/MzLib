package mz.mzlib.mc.item;

import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.mc.Identifier;

public interface ItemStack extends Delegator
{
	Identifier getId();
	void setId(Identifier id);
}
