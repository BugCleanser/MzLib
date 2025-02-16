package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.component.ComponentKeyV2005;
import mz.mzlib.minecraft.component.ComponentMapDefaultedV2005;
import mz.mzlib.minecraft.component.type.NbtCompoundComponentV2005;
import mz.mzlib.minecraft.datafixer.DataUpdateTypesV1300;
import mz.mzlib.minecraft.datafixer.DataUpdateTypesV900_1300;
import mz.mzlib.minecraft.nbt.*;
import mz.mzlib.minecraft.serialization.CodecV1600;
import mz.mzlib.minecraft.serialization.DynamicV1300;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.Result;
import mz.mzlib.util.ThrowableFunction;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Objects;

@WrapMinecraftClass(@VersionName(name="net.minecraft.item.ItemStack"))
public interface ItemStack extends WrapperObject
{
    @WrapperCreator
    static ItemStack create(Object wrapped)
    {
        return WrapperObject.create(ItemStack.class, wrapped);
    }
    
    ItemStack AIR = newInstance(Item.AIR);
    
    static ItemStack empty()
    {
        return create(null).staticEmpty();
    }
    
    ItemStack staticEmpty();
    
    @SpecificImpl("staticEmpty")
    @VersionRange(end=1100)
    default ItemStack staticEmptyV_1100()
    {
        return create(null);
    }
    
    @SpecificImpl("staticEmpty")
    @VersionRange(begin=1100)
    @WrapMinecraftFieldAccessor(@VersionName(name="EMPTY"))
    ItemStack staticEmptyV1100();
    
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
        return staticNewInstanceV1300((ItemConvertibleV1300)item.castTo(ItemV1300::create));
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
    
    static ItemStack newInstance(ItemType type, int count)
    {
        return new ItemStackBuilder(ItemStack.copy(type.itemStack)).setCount(count).get();
    }
    
    @VersionRange(begin=1600)
    @WrapMinecraftFieldAccessor(@VersionName(name="CODEC"))
    CodecV1600 staticCodecV1600();
    
    static CodecV1600 codecV1600()
    {
        return create(null).staticCodecV1600();
    }
    
    Result<Option<ItemStack>, String> staticDecode0(NbtCompound nbt);
    
    ItemStack staticNewInstanceV_2005(NbtCompound nbt);
    
    @SpecificImpl("staticNewInstanceV_2005")
    @VersionRange(end=1100)
    @WrapMinecraftMethod(@VersionName(name="fromNbt"))
    ItemStack staticNewInstanceV_1100(NbtCompound nbt);
    
    @SpecificImpl("staticNewInstanceV_2005")
    @VersionRange(begin=1100, end=2005)
    @WrapConstructor
    ItemStack staticNewInstanceV1100_2005(NbtCompound nbt);
    
    @SpecificImpl("staticDecode0")
    @VersionRange(end=2005)
    default Result<Option<ItemStack>, String> staticDecode0V_2005(NbtCompound nbt)
    {
        try
        {
            return Result.success(Option.some(this.staticNewInstanceV_2005(nbt)));
        }
        catch(Throwable e)
        {
            return Result.failure(Option.none(), e.toString());
        }
    }
    
    @SpecificImpl("staticDecode0")
    @VersionRange(begin=2005)
    default Result<Option<ItemStack>, String> staticDecode0V2005(NbtCompound nbt)
    {
        //noinspection RedundantTypeArguments
        return codecV1600().parse(NbtOpsV1300.withRegistriesV1903(), nbt.getWrapped()).toResult().mapValue(ThrowableFunction.<Object, ItemStack, RuntimeException>optionMap(ItemStack::create));
    }
    
    static Result<Option<ItemStack>, String> decode0(NbtCompound nbt)
    {
        return create(null).staticDecode0(nbt);
    }
    
    /**
     * Decode and convert version
     */
    static Result<Option<ItemStack>, String> decode(NbtCompound nbt)
    {
        try
        {
            for(String id: nbt.getString("id"))
                if(Identifier.newInstance(id).equals(Identifier.ofMinecraft("air")))
                    return Result.success(Option.some(ItemStack.empty()));
        }
        catch(Throwable e)
        {
            return Result.failure(Option.none(), e.toString());
        }
        return decode0(upgrade(nbt));
    }
    
    static Result<Option<NbtCompound>, String> encode(ItemStack is)
    {
        return is.encode();
    }
    
    /**
     * @see #encode(ItemStack)
     */
    @Deprecated
    default Result<Option<NbtCompound>, String> encode()
    {
        if(ItemStack.isEmpty(this))
        {
            NbtCompound result = NbtCompound.newInstance();
            result.put("id", NbtString.newInstance("minecraft:air"));
            return Result.success(Option.some(result));
        }
        Result<Option<NbtCompound>, String> result = encode0();
        for(NbtCompound nbt: result.getValue())
            nbt.put("DataVersion", NbtInt.newInstance(MinecraftServer.instance.getDataVersion()));
        return result;
    }
    
    Result<Option<NbtCompound>, String> encode0();
    
    @WrapMinecraftMethod({@VersionName(name="toNbt", end=1400), @VersionName(name="toTag", begin=1400, end=1605), @VersionName(name="writeNbt", begin=1605, end=2005)})
    NbtCompound encode0V_2005(NbtCompound nbt);
    
    @SpecificImpl("encode0")
    @VersionRange(end=2005)
    default Result<Option<NbtCompound>, String> encode0V_2005()
    {
        try
        {
            return Result.success(Option.some(this.encode0V_2005(NbtCompound.newInstance())));
        }
        catch(Throwable e)
        {
            return Result.failure(Option.none(), e.toString());
        }
    }
    
    @SpecificImpl("encode0")
    @VersionRange(begin=2005)
    default Result<Option<NbtCompound>, String> encode0V2005()
    {
        //noinspection RedundantTypeArguments
        return codecV1600().encodeStart(NbtOpsV1300.withRegistriesV1903(), this.getWrapped()).toResult()
                .mapValue(ThrowableFunction.<Object, NbtCompound, RuntimeException>optionMap(NbtCompound::create));
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
    
    @VersionRange(end=1300)
    @WrapMinecraftFieldAccessor(@VersionName(name="damage"))
    int getDamageV_1300();
    
    @VersionRange(end=1300)
    @WrapMinecraftFieldAccessor(@VersionName(name="damage"))
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
    
    @VersionRange(end=2005)
    @SpecificImpl("getCustomData")
    @WrapMinecraftMethod({@VersionName(end=1400, name="getNbt"), @VersionName(begin=1400, end=1701, name="getTag"), @VersionName(begin=1701, name="getNbt")})
    NbtCompound getTag0V_2005();
    default Option<NbtCompound> getTagV_2005()
    {
        return Option.fromWrapper(this.getTag0V_2005());
    }
    
    @VersionRange(end=2005)
    @SpecificImpl("setCustomData")
    @WrapMinecraftMethod({@VersionName(end=1400, name="setNbt"), @VersionName(begin=1400, end=1701, name="setTag"), @VersionName(begin=1701, name="setNbt")})
    void setTag0V_2005(NbtCompound value);
    default void setTagV_2005(Option<NbtCompound> value)
    {
        this.setTag0V_2005(value.toNullable());
    }
    
    default NbtCompound tagV_2005()
    {
        for(NbtCompound result: this.getTagV_2005())
            return result;
        NbtCompound result = NbtCompound.newInstance();
        this.setTagV_2005(Option.some(result));
        return result;
    }
    
    @SpecificImpl("getCustomData")
    @VersionRange(begin=2005)
    default NbtCompound getCustomDataV2005()
    {
        return this.getComponentsV2005().get(ComponentKeyV2005.fromId("custom_data"), NbtCompoundComponentV2005::create).getNbtCompound();
    }
    
    @SpecificImpl("setCustomData")
    @VersionRange(begin=2005)
    default void setCustomDataV2005(NbtCompound value)
    {
        WrapperObject ignored = this.getComponentsV2005().set(ComponentKeyV2005.fromId("custom_data"), NbtCompoundComponentV2005.newInstance(value));
    }
    
    @VersionRange(begin=2005)
    @WrapMinecraftFieldAccessor(@VersionName(name="components"))
    ComponentMapDefaultedV2005 getComponentsV2005();
    
    /**
     * @see #getComponentsV2005()
     */
    @Deprecated
    @VersionRange(begin=2005)
    @WrapMinecraftMethod(@VersionName(name="set"))
    WrapperObject setComponentV2005(ComponentKeyV2005 key, WrapperObject value);
    
    static boolean isEmpty(ItemStack is)
    {
        return is.getWrapped()==empty().getWrapped() || is.isEmpty();
    }
    
    /**
     * @see #isEmpty(ItemStack)
     * @deprecated This method is deprecated because it may cause a null pointer exception before Minecraft 1.11.
     */
    @Deprecated
    boolean isEmpty();
    
    @SpecificImpl("isEmpty")
    @VersionRange(end=1100)
    default boolean isEmptyV_1100()
    {
        return !this.getItem().isPresent() || this.getCount()==0 || this.getItem().equals(Item.AIR);
    }
    
    @SpecificImpl("isEmpty")
    @VersionRange(begin=1100)
    @WrapMinecraftMethod(@VersionName(name="isEmpty"))
    boolean isEmptyV1100();
    
    @WrapMinecraftMethod(@VersionName(name="isStackable"))
    boolean isStackable();
    
    default void grow(int count)
    {
        int n = this.getCount()+count;
        this.setCount(n);
        if(n<=0)
            this.setItem(Item.AIR);
    }
    
    default void shrink(int count)
    {
        this.grow(-count);
    }
    
    default ItemStack split(int count)
    {
        ItemStack result = ItemStack.copy(this, Math.min(this.getCount(), count));
        this.shrink(result.getCount());
        return result;
    }
    
    static ItemStack copy(ItemStack is)
    {
        if(isEmpty(is))
            return empty();
        return is.copy();
    }
    
    /**
     * @see #copy(ItemStack)
     */
    @Deprecated
    @WrapMinecraftMethod(@VersionName(name="copy"))
    ItemStack copy();
    
    static ItemStack copy(ItemStack is, int count)
    {
        if(isEmpty(is))
            return empty();
        return is.copy(count);
    }
    
    /**
     * @see #copy(ItemStack, int)
     */
    @Deprecated
    default ItemStack copy(int count)
    {
        ItemStack result = this.copy();
        result.setCount(count);
        return result;
    }
    
    @WrapMinecraftMethod(@VersionName(name="getMaxCount"))
    int getMaxStackCount();
    
    
    static String getTranslationKey(ItemStack is)
    {
        if(isEmpty(is))
            return AIR.getTranslationKey();
        return is.getTranslationKey();
    }
    
    /**
     * @see #getTranslationKey(ItemStack)
     */
    @Deprecated
    String getTranslationKey();
    
    @SpecificImpl("getTranslationKey")
    @VersionRange(end=2102)
    default String getTranslationKeyV_2102()
    {
        return this.getItem().getTranslationKeyV_2102(this);
    }
    
    @SpecificImpl("getTranslationKey")
    @VersionRange(begin=2102)
    default String getTranslationKeyV2102()
    {
        return this.getItem().getDefaultNameV1300(this).getTranslatableKey();
    }
    
    default ItemType getType()
    {
        return new ItemType(this);
    }
    
    static boolean isStackable(ItemStack a, ItemStack b)
    {
        return create(null).staticIsStackable(a, b);
    }
    
    boolean staticIsStackable(ItemStack a, ItemStack b);
    
    @SpecificImpl("staticIsStackable")
    @VersionRange(end=1300)
    default boolean staticIsStackableV_1300(ItemStack a, ItemStack b)
    {
        if(isEmpty(a) && isEmpty(b))
            return true;
        if(isEmpty(a) || isEmpty(b))
            return false;
        return a.getItem().equals(b.getItem()) && a.getDamageV_1300()==b.getDamageV_1300() && a.getTagV_2005().equals(b.getTagV_2005());
    }
    
    @SpecificImpl("staticIsStackable")
    @VersionRange(begin=1300, end=1700)
    default boolean staticIsStackableV1300_1700(ItemStack a, ItemStack b)
    {
        if(isEmpty(a) && isEmpty(b))
            return true;
        if(isEmpty(a) || isEmpty(b))
            return false;
        return a.getItem().equals(b.getItem()) && a.getTagV_2005().equals(b.getTagV_2005());
    }
    
    @SpecificImpl("staticIsStackable")
    @VersionRange(begin=1700)
    @WrapMinecraftMethod({@VersionName(name="canCombine", end=2005), @VersionName(name="areItemsAndComponentsEqual", begin=2005)})
    boolean staticIsStackableV1700(ItemStack a, ItemStack b);
    
    @Override
    int hashCode0();
    
    @SpecificImpl("hashCode0")
    @VersionRange(end=1300)
    default int hashCodeV_1300()
    {
        return Objects.hash(this.hashCodeV1300_2005(), this.getDamageV_1300());
    }
    
    @SpecificImpl("hashCode0")
    @VersionRange(begin=1300, end=2005)
    default int hashCodeV1300_2005()
    {
        return Objects.hash(this.getItem(), this.getCount(), this.getTagV_2005());
    }
    
    @SpecificImpl("hashCode0")
    @VersionRange(begin=2005)
    default int hashCodeV2005()
    {
        return Objects.hash(this.getItem(), this.getCount(), this.getComponentsV2005());
    }
    
    @Override
    default boolean equals0(WrapperObject object)
    {
        if(!(object instanceof ItemStack))
            return false;
        ItemStack that = (ItemStack)object;
        if(isEmpty(this) && isEmpty(that))
            return true;
        if(isEmpty(this) || isEmpty(that))
            return false;
        return this.getCount()==that.getCount() && isStackable(this, that);
    }
    
    static NbtCompound upgrade(NbtCompound nbt)
    {
        int dataVersion;
        l1:
        do
        {
            for(NbtInt nbtVersion: nbt.get("DataVersion", NbtInt::create))
            {
                dataVersion = nbtVersion.getValue();
                break l1;
            }
            if(nbt.containsKey("Damage"))
                dataVersion = 1343; // 1.12.2
            else if(nbt.containsKey("count")) // if(nbt.containsKey("components"))
                dataVersion = 3837; // 1.20.5
            else
            {
                dataVersion = 1952; // 1.14
                for(NbtCompound tag: nbt.get("tag", NbtCompound::create))
                {
                    for(NbtCompound display: tag.get("display", NbtCompound::create))
                    {
                        for(NbtList lore: display.get("Lore", NbtList::create))
                        {
                            for(NbtString l: lore.asList(NbtString::create))
                            {
                                try
                                {
                                    Text.decode(l.getValue());
                                }
                                catch(Throwable ignored)
                                {
                                    dataVersion = 1631; // 1.13.2
                                    break l1;
                                }
                            }
                        }
                    }
                }
            }
        }while(false);
        return upgrade(nbt, dataVersion);
    }
    
    NbtCompound staticUpgrade(NbtCompound nbt, int from);
    
    @SpecificImpl("staticUpgrade")
    @VersionRange(end=900)
    default NbtCompound staticUpgradeV_900(NbtCompound nbt, int from)
    {
        return nbt;
    }
    
    @SpecificImpl("staticUpgrade")
    @VersionRange(begin=900, end=1300)
    default NbtCompound staticUpgradeV900_1300(NbtCompound nbt, int from)
    {
        return MinecraftServer.instance.getDataUpdaterV900_1300().update(DataUpdateTypesV900_1300.itemStack(), nbt, from);
    }
    
    @SpecificImpl("staticUpgrade")
    @VersionRange(begin=1300)
    default NbtCompound staticUpgradeV1300(NbtCompound nbt, int from)
    {
        return NbtCompound.create(MinecraftServer.instance.getDataUpdaterV1300().update(DataUpdateTypesV1300.itemStack(), DynamicV1300.newInstance(NbtOpsV1300.instance(), nbt.getWrapped()), from, MinecraftServer.instance.getDataVersion()).getValue());
    }
    
    static NbtCompound upgrade(NbtCompound nbt, int from)
    {
        return create(null).staticUpgrade(nbt, from);
    }
}
