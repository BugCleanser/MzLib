package mz.mzlib.mc.item;

import mz.mzlib.mc.Identifier;
import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftMethod;
import mz.mzlib.mc.nbt.NBTCompound;
import mz.mzlib.util.delegator.Delegator;

@DelegatorMinecraftClass(@VersionName(name="net.minecraft.item.ItemStack"))
public interface ItemStack extends Delegator
{
    // TODO
    Item getItem();
    // TODO
    void setItem(Item item);

    // TODO
    default Identifier getId()
    {
        return getItem().getId();
    }
    // TODO
    default void setId(Identifier id)
    {
        ItemFactory.instance.get(id);
    }

    // TODO
    int getCount();
    // TODO
    void setCount(int count);

    // TODO
    int getDamage();
    // TODO
    void setDamage(int damage);

    @DelegatorMinecraftMethod({@VersionName(end=1400,name="getNbt"), @VersionName(begin=1400,end=1701,name="getTag"), @VersionName(begin=1701,name="getNbt")})
    NBTCompound getTagV_2005();
    @DelegatorMinecraftMethod({@VersionName(end=1400,name="setNbt"), @VersionName(begin=1400,end=1701,name="setTag"), @VersionName(begin=1701,name="setNbt")})
    void setTagV_2005(NBTCompound tag);

    @DelegatorMinecraftMethod(@VersionName(name="copy"))
    ItemStack copy();
}
