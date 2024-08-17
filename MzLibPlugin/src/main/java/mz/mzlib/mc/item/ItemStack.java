package mz.mzlib.mc.item;

import mz.mzlib.mc.Identifier;
import mz.mzlib.mc.nbt.NBTCompound;
import mz.mzlib.util.delegator.Delegator;

public interface ItemStack extends Delegator
{
    Item getItem();

    void setItem(Item item);

    default Identifier getId()
    {
        return getItem().getId();
    }

    default void setId(Identifier id)
    {
        ItemFactory.instance.get(id);
    }

    int getCount();

    void setCount(int count);

    int getDamage();

    void setDamage(int damage);

    NBTCompound getTagV_2100();

    void setTagV_2100(NBTCompound tag);
}
