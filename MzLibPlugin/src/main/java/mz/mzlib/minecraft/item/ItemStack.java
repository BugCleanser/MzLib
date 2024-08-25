package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.component.ComponentMapV2005;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.minecraft.nbt.NBTCompound;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.item.ItemStack"))
public interface ItemStack extends WrapperObject
{
    @WrapMinecraftFieldAccessor(@VersionName(name="item"))
    Item getItem();
    @WrapMinecraftFieldAccessor(@VersionName(name="item"))
    void setItem(Item item);

    default Identifier getId()
    {
        return getItem().getId();
    }
    default void setId(Identifier id)
    {
        ItemFactory.instance.get(id);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name="count"))
    int getCount();
    @WrapMinecraftFieldAccessor(@VersionName(name="count"))
    void setCount(int count);

    @WrapMinecraftFieldAccessor(@VersionName(name="damage"))
    int getDamageV_1300();
    @WrapMinecraftFieldAccessor(@VersionName(name="damage"))
    void setDamageV_1300(int damage);

    @WrapMinecraftMethod({@VersionName(end=1400,name="getNbt"), @VersionName(begin=1400,end=1701,name="getTag"), @VersionName(begin=1701,end=2005,name="getNbt")})
    NBTCompound getTagV_2005();
    @WrapMinecraftMethod({@VersionName(end=1400,name="setNbt"), @VersionName(begin=1400,end=1701,name="setTag"), @VersionName(begin=1701,end=2005,name="setNbt")})
    void setTagV_2005(NBTCompound value);

    @WrapMinecraftMethod(@VersionName(name="getComponents"))
    ComponentMapV2005 getComponentsV2005();

    // TODO
    NBTCompound getCustomDataV2005();
    void setCustomDataV2005(NBTCompound value);

    @WrapMinecraftMethod(@VersionName(name="copy"))
    ItemStack copy();
}
