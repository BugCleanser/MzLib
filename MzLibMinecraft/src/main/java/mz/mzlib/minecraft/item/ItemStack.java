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
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.item.ItemStack"))
public interface ItemStack extends WrapperObject
{
    @WrapperCreator
    static ItemStack create(Object wrapped)
    {
        return WrapperObject.create(ItemStack.class, wrapped);
    }
    
    ItemStack staticNewInstance(Item item);
    @WrapConstructor
    @VersionRange(end=1300)
    @SpecificImpl("staticNewInstance")
    ItemStack staticNewInstanceV_1300(Item item);
    @WrapConstructor
    @VersionRange(begin=1300)
    ItemStack staticNewInstanceV1300(ItemConvertibleV1300 item);
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=1300)
    default ItemStack staticNewInstanceV1300(Item item)
    {
        return staticNewInstanceV1300((ItemConvertibleV1300) item.castTo(ItemV1300::create));
    }
    static ItemStack newInstance(Item item)
    {
        return create(null).staticNewInstance(item);
    }
    static ItemStack newInstance(Item item, int count)
    {
        ItemStack result = newInstance(item);
        result.setCount(count);
        return result;
    }
    
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
    
    @WrapMinecraftFieldAccessor(@VersionName(name="damage", end=1300))
    int getDamageV_1300();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="damage", end=1300))
    void setDamageV_1300(int damage);
    
    NbtCompound getCustomData();
    
    void setCustomData(NbtCompound value);
    
    @SpecificImpl("getCustomData")
    @WrapMinecraftMethod({@VersionName(end=1400, name="getNbt"), @VersionName(begin=1400, end=1701, name="getTag"), @VersionName(begin=1701, end=2005, name="getNbt")})
    NbtCompound getTagV_2005();
    
    @SpecificImpl("setCustomData")
    @WrapMinecraftMethod({@VersionName(end=1400, name="setNbt"), @VersionName(begin=1400, end=1701, name="setTag"), @VersionName(begin=1701, end=2005, name="setNbt")})
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
    
    @WrapMinecraftMethod(@VersionName(name="isEmpty"))
    boolean isEmpty();
    
    @WrapMinecraftMethod(@VersionName(name="isStackable"))
    boolean isStackable();
    
    default void grow(int count)
    {
        this.setCount(this.getCount()+count);
    }
    
    default void shrink(int count)
    {
        this.grow(-count);
    }
    
    default ItemStack split(int count)
    {
        ItemStack result = this.copy(Math.min(this.getCount(), count));
        this.shrink(result.getCount());
        return result;
    }
    
    @WrapMinecraftMethod(@VersionName(name="copy"))
    ItemStack copy();
    
    default ItemStack copy(int count)
    {
        ItemStack result = this.copy();
        result.setCount(count);
        return result;
    }
    
    boolean staticIsStackable(ItemStack a, ItemStack b);
    
    @SpecificImpl("staticIsStackable")
    @VersionRange(end=1300)
    default boolean staticIsStackableV_1300(ItemStack a, ItemStack b)
    {
        if(a.isEmpty() && b.isEmpty())
            return true;
        if(a.isEmpty() || b.isEmpty())
            return false;
        return a.getItem().equals(b.getItem()) && a.getDamageV_1300()==b.getDamageV_1300() && a.getTagV_2005().equals(b.getTagV_2005());
    }
    
    @SpecificImpl("staticIsStackable")
    @VersionRange(begin=1300, end=1700)
    default boolean staticIsStackableV1300_1700(ItemStack a, ItemStack b)
    {
        if(a.isEmpty() && b.isEmpty())
            return true;
        if(a.isEmpty() || b.isEmpty())
            return false;
        return a.getItem().equals(b.getItem()) && a.getTagV_2005().equals(b.getTagV_2005());
    }
    
    @SpecificImpl("staticIsStackable")
    @WrapMinecraftMethod(@VersionName(name="areItemsAndComponentsEqual", begin=1700))
    boolean staticIsStackableV1700(ItemStack a, ItemStack b);
    
    static boolean isStackable(ItemStack a, ItemStack b)
    {
        return create(null).staticIsStackable(a, b);
    }
}
