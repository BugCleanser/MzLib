package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.*;
import mz.mzlib.minecraft.datafixer.DataUpdateTypesV1300;
import mz.mzlib.minecraft.datafixer.DataUpdateTypesV900_1300;
import mz.mzlib.minecraft.component.ComponentCustomDataV2005;
import mz.mzlib.minecraft.component.ComponentKeyV2005;
import mz.mzlib.minecraft.component.ComponentMapV2005;
import mz.mzlib.minecraft.nbt.*;
import mz.mzlib.minecraft.serialization.CodecV1600;
import mz.mzlib.minecraft.serialization.DynamicV1300;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.RuntimeUtil;
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
    
    ItemStack staticDecode0(NbtCompound nbt);
    
    @SpecificImpl("staticDecode0")
    @VersionRange(end=1100)
    @WrapMinecraftMethod(@VersionName(name="fromNbt"))
    ItemStack staticNewInstanceV_1100(NbtCompound nbt);
    
    @SpecificImpl("staticDecode0")
    @VersionRange(begin=1100, end=2005)
    @WrapConstructor
    ItemStack staticNewInstanceV1100_2005(NbtCompound nbt);
    
    @SpecificImpl("staticDecode0")
    @VersionRange(begin=2005)
    default ItemStack staticDecode0V2005(NbtCompound nbt)
    {
        return create(codecV1600().parse(NbtOpsV1300.withRegistriesV1903(), nbt.getWrapped()).resultOrPartial(e->System.err.println("Invalid data when decode item stack: "+e)).orElseThrow(()->new IllegalArgumentException(nbt.toString())));
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
        if(Identifier.newInstance(nbt.getString("id")).equals(Identifier.ofMinecraft("air")))
            return empty();
        return decode0(upgrade(nbt));
    }
    
    static NbtCompound encode(ItemStack is)
    {
        return is.encode();
    }
    
    /**
     * @see #encode(ItemStack)
     */
    @Deprecated
    default NbtCompound encode()
    {
        if(ItemStack.isEmpty(this))
        {
            NbtCompound result = NbtCompound.newInstance();
            result.put("id", NbtString.newInstance("minecraft:air"));
            return result;
        }
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
        return NbtCompound.create(codecV1600().encodeStart(NbtOpsV1300.withRegistriesV1903(), this.getWrapped()).getOrThrow(RuntimeException::new));
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
    NbtCompound getTagV_2005();
    
    @VersionRange(end=2005)
    @SpecificImpl("setCustomData")
    @WrapMinecraftMethod({@VersionName(end=1400, name="setNbt"), @VersionName(begin=1400, end=1701, name="setTag"), @VersionName(begin=1701, name="setNbt")})
    void setTagV_2005(NbtCompound value);
    
    default NbtCompound tagV_2005()
    {
        NbtCompound result = this.getTagV_2005();
        if(!result.isPresent())
            this.setTagV_2005(result = NbtCompound.newInstance());
        return result;
    }
    
    @SpecificImpl("getCustomData")
    @VersionRange(begin=2005)
    default NbtCompound getCustomDataV2005()
    {
        return this.getComponentsV2005().get(ComponentKeyV2005.fromId("custom_data"), ComponentCustomDataV2005::create).getData();
    }
    
    @SpecificImpl("setCustomData")
    @VersionRange(begin=2005)
    default void setCustomDataV2005(NbtCompound value)
    {
        WrapperObject ignored = this.setComponentV2005(ComponentKeyV2005.fromId("custom_data"), ComponentCustomDataV2005.newInstance(value));
    }
    
    @VersionRange(begin=2005)
    @WrapMinecraftFieldAccessor(@VersionName(name="components"))
    ComponentMapV2005 getComponentsV2005();
    
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
        NbtInt nbtVersion = nbt.get("DataVersion", NbtInt::create);
        if(nbtVersion.isPresent())
            dataVersion = nbtVersion.getValue();
        else
        {
            if(nbt.get("Damage").isPresent())
                dataVersion = 1343; // 1.12.2
            else
            {
                if(nbt.get("count").isPresent()) // if(nbt.get("components").isPresent())
                    dataVersion = 3837; // 1.20.5
                else
                {
                    dataVersion = 1952; // 1.14
                    NbtCompound tag = nbt.get("tag", NbtCompound::create);
                    if(tag.isPresent())
                    {
                        NbtCompound display = tag.get("display", NbtCompound::create);
                        if(display.isPresent())
                        {
                            NbtList lore = display.get("Lore", NbtList::create);
                            if(lore.isPresent())
                                for(NbtString l: lore.asList(NbtString::create))
                                {
                                    if(RuntimeUtil.runAndCatch(()->Text.decode(l.getValue()))!=null)
                                    {
                                        dataVersion = 1631; // 1.13.2
                                        break;
                                    }
                                }
                        }
                    }
                }
            }
        }
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
