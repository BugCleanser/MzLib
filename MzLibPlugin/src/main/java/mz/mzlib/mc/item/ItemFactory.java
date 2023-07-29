package mz.mzlib.mc.item;

import mz.mzlib.mc.Identifier;
import mz.mzlib.util.Instance;

public interface ItemFactory extends Instance
{
	Item get(Identifier id);
}
