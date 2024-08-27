package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.component.ComponentKeysV2005;
import mz.mzlib.minecraft.component.ComponentMapV2005;
import mz.mzlib.minecraft.component.ComponentKeyV2005;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.util.wrapper.SpecificImpl;
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

    @WrapMinecraftFieldAccessor(@VersionName(name="count"))
    int getCount();
    @WrapMinecraftFieldAccessor(@VersionName(name="count"))
    void setCount(int count);

    @WrapMinecraftFieldAccessor(@VersionName(name="damage"))
    int getDamageV_1300();
    @WrapMinecraftFieldAccessor(@VersionName(name="damage"))
    void setDamageV_1300(int damage);

    NbtCompound getCustomData();
    void setCustomData(NbtCompound value);

    @SpecificImpl("getCustomData")
    @WrapMinecraftMethod({@VersionName(end=1400,name="getNbt"), @VersionName(begin=1400,end=1701,name="getTag"), @VersionName(begin=1701,end=2005,name="getNbt")})
    NbtCompound getTagV_2005();
    @SpecificImpl("setCustomData")
    @WrapMinecraftMethod({@VersionName(end=1400,name="setNbt"), @VersionName(begin=1400,end=1701,name="setTag"), @VersionName(begin=1701,end=2005,name="setNbt")})
    void setTagV_2005(NbtCompound value);

    @SpecificImpl("getCustomData")
    @VersionRange(begin=2005)
    default NbtCompound getCustomDataV2005()
    {
        return this.getComponentsV2005().get(ComponentKeysV2005.customData(), NbtCompound::create);
    }
    @SpecificImpl("setCustomData")
    @VersionRange(begin=2005)
    default void setCustomDataV2005(NbtCompound value)
    {
        this.setComponentV2005(ComponentKeysV2005.customData(), value);
    }

    @WrapMinecraftMethod(@VersionName(name="getComponents", begin=2005))
    ComponentMapV2005 getComponentsV2005();
    @WrapMinecraftMethod(@VersionName(name="set", begin=2005))
    WrapperObject setComponentV2005(ComponentKeyV2005 key, WrapperObject value);

    @WrapMinecraftMethod(@VersionName(name="copy"))
    ItemStack copy();
}
