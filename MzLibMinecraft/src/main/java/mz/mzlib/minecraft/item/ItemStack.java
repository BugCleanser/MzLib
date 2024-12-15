package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.component.ComponentKeyV2005;
import mz.mzlib.minecraft.component.ComponentKeysV2005;
import mz.mzlib.minecraft.component.ComponentMapV2005;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.nbt.NbtInt;
import mz.mzlib.minecraft.nbt.NbtOpsV1400;
import mz.mzlib.minecraft.serialization.CodecV1600;
import mz.mzlib.minecraft.serialization.DynamicV1400;
import mz.mzlib.minecraft.version.DataUpdateTypesV1400;
import mz.mzlib.minecraft.version.DataUpdateTypesV_1400;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
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
    
    @WrapMinecraftFieldAccessor(@VersionName(name="EMPTY"))
    ItemStack staticEmpty();
    static ItemStack empty()
    {
        return create(null).staticEmpty();
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
    
    @WrapMinecraftFieldAccessor(@VersionName(name="CODEC", begin=1600))
    CodecV1600 staticCodecV1600();
    static CodecV1600 codecV1600()
    {
        return create(null).staticCodecV1600();
    }
    
    ItemStack staticDecode0(NbtCompound nbt);
    @WrapConstructor
    @SpecificImpl("staticDecode0")
    @VersionRange(end=2005)
    ItemStack staticNewInstanceV_2005(NbtCompound nbt);
    @SpecificImpl("staticDecode0")
    @VersionRange(begin=2005)
    default ItemStack staticDecode0V2005(NbtCompound nbt)
    {
        return create(codecV1600().parse(NbtOpsV1400.instance(), nbt.getWrapped()).getOrThrow(()->new IllegalArgumentException(nbt.toString())));
    }
    
    static ItemStack decode0(NbtCompound nbt)
    {
        return create(null).staticDecode0(nbt);
    }
    
    /**
     * Decode and convert version
     */
    static ItemStack decode(NbtCompound nbt)
    {
        return decode0(update(nbt));
    }
    
    default NbtCompound encode()
    {
        NbtCompound result = encode0();
        result.put("DataVersion", NbtInt.newInstance(MinecraftServer.instance.getDataVersion()));
        return result;
    }
    NbtCompound encode0();
    @WrapMinecraftMethod({@VersionName(name="toNbt", end=1400), @VersionName(name="toTag", begin=1400, end=1605), @VersionName(name="writeNbt", begin=1605, end=2005)})
    NbtCompound encode0V_2005(NbtCompound nbt);
    @SpecificImpl("encode0")
    @VersionRange(end=2005)
    default NbtCompound encode0V_2005()
    {
        return encode0V_2005(NbtCompound.newInstance());
    }
    @SpecificImpl("encode0")
    @VersionRange(begin=2005)
    default NbtCompound encode0V2005()
    {
        return NbtCompound.create(codecV1600().encodeStart(NbtOpsV1400.instance(), this.getWrapped()).getOrThrow(RuntimeException::new));
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
    
    default NbtCompound customData()
    {
        NbtCompound result = this.getCustomData();
        if(!result.isPresent())
        {
            result = NbtCompound.newInstance();
            this.setCustomData(result);
        }
        return result;
    }
    
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
        return this.getComponentsV2005().get(ComponentKeysV2005.get("custom_data"), NbtCompound::create);
    }
    
    @SpecificImpl("setCustomData")
    @VersionRange(begin=2005)
    default void setCustomDataV2005(NbtCompound value)
    {
        this.setComponentV2005(ComponentKeysV2005.get("custom_data"), value);
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
    
    static NbtCompound update(NbtCompound nbt)
    {
        int dataVersion;
        NbtInt nbtVersion=nbt.get("DataVersion", NbtInt::create);
        if(nbtVersion.isPresent())
            dataVersion=nbtVersion.getValue();
        else
        {
            if(nbt.get("Damage").isPresent())
                dataVersion=1343; // 1.12.2
            else
            {
                dataVersion=1631; //1.13.2
                if(nbt.get("count").isPresent()) // if(nbt.get("components").isPresent())
                    dataVersion=3837; // 1.20.5
            }
        }
        return update(nbt, dataVersion);
    }
    NbtCompound staticUpdate(NbtCompound nbt, int from);
    @SpecificImpl("staticUpdate")
    @VersionRange(end=1400)
    default NbtCompound staticUpdateV_1400(NbtCompound nbt, int from)
    {
        return MinecraftServer.instance.getDataUpdaterV_1400().update(DataUpdateTypesV_1400.itemStack(), nbt, from);
    }
    @SpecificImpl("staticUpdate")
    @VersionRange(begin=1400)
    default NbtCompound staticUpdateV1400(NbtCompound nbt, int from)
    {
        return NbtCompound.create(MinecraftServer.instance.getDataUpdaterV1400().update(DataUpdateTypesV1400.itemStack(), DynamicV1400.newInstance(NbtOpsV1400.instance(), nbt.getWrapped()), from, MinecraftServer.instance.getDataVersion()).getValue());
    }
    static NbtCompound update(NbtCompound nbt, int from)
    {
        return create(null).staticUpdate(nbt, from);
    }
}
