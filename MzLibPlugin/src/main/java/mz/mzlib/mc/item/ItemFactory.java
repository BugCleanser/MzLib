package mz.mzlib.mc.item;

import mz.mzlib.mc.Identifier;
import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;

public interface ItemFactory extends Instance
{
    ItemFactory instance = RuntimeUtil.nul();

    Item get(Identifier id);
}
