package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.minecraft.nbt.NBTCompound;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.item.ItemStack"))
public interface ItemStack extends WrapperObject
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

    @WrapMinecraftMethod({@VersionName(end=1400,name="getNbt"), @VersionName(begin=1400,end=1701,name="getTag"), @VersionName(begin=1701,name="getNbt")})
    NBTCompound getTagV_2005();
    @WrapMinecraftMethod({@VersionName(end=1400,name="setNbt"), @VersionName(begin=1400,end=1701,name="setTag"), @VersionName(begin=1701,name="setNbt")})
    void setTagV_2005(NBTCompound tag);

    @WrapMinecraftMethod(@VersionName(name="copy"))
    ItemStack copy();
}
