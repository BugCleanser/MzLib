package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.component.ComponentKeyV2005;
import mz.mzlib.minecraft.component.ComponentMapDefaultedV2005;
import mz.mzlib.minecraft.datafixer.DataUpdateTypesV1300;
import mz.mzlib.minecraft.datafixer.DataUpdateTypesV900_1300;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.entity.player.ActionResult;
import mz.mzlib.minecraft.entity.player.Hand;
import mz.mzlib.minecraft.incomprehensible.TypedActionResultV900_2102;
import mz.mzlib.minecraft.nbt.*;
import mz.mzlib.minecraft.registry.entry.RegistryEntryListV1903;
import mz.mzlib.minecraft.registry.tag.TagKeyV1903;
import mz.mzlib.minecraft.serialization.CodecV1600;
import mz.mzlib.minecraft.serialization.DynamicV1300;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.text.TextTranslatable;
import mz.mzlib.minecraft.world.AbstractWorld;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.Result;
import mz.mzlib.util.wrapper.*;

import java.util.Objects;

@WrapMinecraftClass(@VersionName(name="net.minecraft.item.ItemStack"))
public interface ItemStack extends WrapperObject
{
    WrapperFactory<ItemStack> FACTORY = WrapperFactory.of(ItemStack.class);
    @Deprecated
    @WrapperCreator
    static ItemStack create(Object wrapped)
    {
        return WrapperObject.create(ItemStack.class, wrapped);
    }
    
    ItemStack AIR = newInstance(Item.AIR);
    
    static ItemStack empty()
    {
        return FACTORY.getStatic().static$empty();
    }
    ItemStack static$empty();
    @SpecificImpl("static$empty")
    @VersionRange(end=1100)
    default ItemStack static$emptyV_1100()
    {
        return FACTORY.create(null);
    }
    @SpecificImpl("static$empty")
    @VersionRange(begin=1100)
    @WrapMinecraftFieldAccessor(@VersionName(name="EMPTY"))
    ItemStack static$emptyV1100();
    
    static ItemStack newInstance(Item item)
    {
        return FACTORY.getStatic().static$newInstance(item);
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
    ItemStack static$newInstance(Item item);
    @WrapConstructor
    @VersionRange(end=1300)
    @SpecificImpl("static$newInstance")
    ItemStack static$newInstanceV_1300(Item item);
    @WrapConstructor
    @VersionRange(begin=1300)
    ItemStack static$newInstanceV1300(ItemConvertibleV1300 item);
    @SpecificImpl("static$newInstance")
    @VersionRange(begin=1300)
    default ItemStack static$newInstanceV1300(Item item)
    {
        return static$newInstanceV1300((ItemConvertibleV1300)item.castTo(ItemV1300.FACTORY));
    }
    
    @VersionRange(begin=1600)
    @WrapMinecraftFieldAccessor(@VersionName(name="CODEC"))
    CodecV1600<?> static$codec0V1600();
    static CodecV1600<?> codec0V1600()
    {
        return FACTORY.getStatic().static$codec0V1600();
    }
    static CodecV1600.Wrapper<ItemStack> codecV1600()
    {
        return new CodecV1600.Wrapper<>(codec0V1600(), FACTORY);
    }
    
    Result<Option<ItemStack>, String> static$decode0(NbtCompound nbt);
    
    ItemStack static$newInstanceV_2005(NbtCompound nbt);
    
    @SpecificImpl("static$newInstanceV_2005")
    @VersionRange(end=1100)
    @WrapMinecraftMethod(@VersionName(name="fromNbt"))
    ItemStack static$newInstanceV_1100(NbtCompound nbt);
    
    @SpecificImpl("static$newInstanceV_2005")
    @VersionRange(begin=1100, end=2005)
    @WrapConstructor
    ItemStack static$newInstanceV1100_2005(NbtCompound nbt);
    
    @SpecificImpl("static$decode0")
    @VersionRange(end=2005)
    default Result<Option<ItemStack>, String> static$decode0V_2005(NbtCompound nbt)
    {
        try
        {
            return Result.success(Option.some(this.static$newInstanceV_2005(nbt)));
        }
        catch(Throwable e)
        {
            return Result.failure(Option.none(), e.toString());
        }
    }
    
    @SpecificImpl("static$decode0")
    @VersionRange(begin=2005)
    default Result<Option<ItemStack>, String> static$decode0V2005(NbtCompound nbt)
    {
        return codecV1600().parse(NbtOpsV1300.withRegistriesV1903(), nbt).toResult();
    }
    
    static Result<Option<ItemStack>, String> decode0(NbtCompound nbt)
    {
        return FACTORY.getStatic().static$decode0(nbt);
    }
    
    /**
     * Decode and convert version
     */
    static Result<Option<ItemStack>, String> decode(NbtCompound nbt)
    {
        try
        {
            for(String id: nbt.getString("id"))
                if(Identifier.newInstance(id).equals(Identifier.minecraft("air")))
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
        return codecV1600().encodeStart(NbtOpsV1300.withRegistriesV1903(), this).toResult();
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
    
    @VersionRange(end=2005)
    @WrapMinecraftMethod({@VersionName(end=1400, name="getNbt"), @VersionName(begin=1400, end=1701, name="getTag"), @VersionName(begin=1701, name="getNbt")})
    NbtCompound getTag0V_2005();
    default Option<NbtCompound> getTagV_2005()
    {
        return Option.fromWrapper(this.getTag0V_2005());
    }
    
    @VersionRange(end=2005)
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
    
    @VersionRange(begin=2005)
    @WrapMinecraftFieldAccessor(@VersionName(name="components"))
    ComponentMapDefaultedV2005 getComponentsV2005();
    
    /**
     * @see #getComponentsV2005()
     */
    @Deprecated
    @VersionRange(begin=2005)
    @WrapMinecraftMethod(@VersionName(name="set"))
    <T> T setComponentV2005(ComponentKeyV2005<T> key, T value);
    
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
        return this.getItem().getDefaultNameV1300(this).castTo(TextTranslatable.FACTORY).getKey();
    }
    
    default ItemType getType()
    {
        return new ItemType(this);
    }
    
    static boolean isStackable(ItemStack a, ItemStack b)
    {
        return FACTORY.getStatic().static$isStackable(a, b);
    }
    
    boolean static$isStackable(ItemStack a, ItemStack b);
    
    @SpecificImpl("static$isStackable")
    @VersionRange(end=1300)
    default boolean static$isStackableV_1300(ItemStack a, ItemStack b)
    {
        if(isEmpty(a) && isEmpty(b))
            return true;
        if(isEmpty(a) || isEmpty(b))
            return false;
        return a.getItem().equals(b.getItem()) && a.getDamageV_1300()==b.getDamageV_1300() && a.getTagV_2005().equals(b.getTagV_2005());
    }
    
    @SpecificImpl("static$isStackable")
    @VersionRange(begin=1300, end=1700)
    default boolean static$isStackableV1300_1700(ItemStack a, ItemStack b)
    {
        if(isEmpty(a) && isEmpty(b))
            return true;
        if(isEmpty(a) || isEmpty(b))
            return false;
        return a.getItem().equals(b.getItem()) && a.getTagV_2005().equals(b.getTagV_2005());
    }
    
    @SpecificImpl("static$isStackable")
    @VersionRange(begin=1700)
    @WrapMinecraftMethod({@VersionName(name="canCombine", end=2005), @VersionName(name="areItemsAndComponentsEqual", begin=2005)})
    boolean static$isStackableV1700(ItemStack a, ItemStack b);
    
    @VersionRange(end=900)
    @WrapMinecraftMethod(@VersionName(name="onStartUse"))
    ItemStack useV_900(AbstractWorld world, AbstractEntityPlayer player);
    @VersionRange(begin=900, end=2102)
    @WrapMinecraftMethod({@VersionName(name="method_11390", end=1400), @VersionName(name="use", begin=1400)})
    TypedActionResultV900_2102<?> use0V900_2102(AbstractWorld world, AbstractEntityPlayer player, Hand hand);
    @VersionRange(begin=2102)
    @WrapMinecraftMethod(@VersionName(name="use"))
    ActionResult useV2102(AbstractWorld world, AbstractEntityPlayer player, Hand hand);
    
    @VersionRange(begin=1903)
    @WrapMinecraftMethod(@VersionName(name="isIn"))
    boolean hasTagV1903(TagKeyV1903<?> tag);
    
    @VersionRange(begin=2002)
    @WrapMinecraftMethod(@VersionName(name="isIn"))
    boolean isInV2002(RegistryEntryListV1903 registryEntries);
    
    /**
     * Shadow clone
     */
    @Override
    ItemStack clone0();
    @SpecificImpl("clone0")
    @VersionRange(end=1300)
    default ItemStack impl$clone0V_1300()
    {
        ItemStack result = this.impl$clone0V1300_2005();
        result.setDamageV_1300(this.getDamageV_1300());
        return result;
    }
    @SpecificImpl("clone0")
    @VersionRange(begin=1300, end=2005)
    default ItemStack impl$clone0V1300_2005()
    {
        ItemStack result = newInstance(this.getItem(), this.getCount());
        result.setTagV_2005(this.getTagV_2005());
        return result;
    }
    @SpecificImpl("clone0")
    @VersionRange(begin=2005)
    default ItemStack impl$clone0V2005()
    {
        return this.copy();
    }
    
    @Override
    int hashCode0();
    @SpecificImpl("hashCode0")
    @VersionRange(end=1300)
    default int impl$hashCode0V_1300()
    {
        return Objects.hash(this.impl$hashCode0V1300_2005(), this.getDamageV_1300());
    }
    @SpecificImpl("hashCode0")
    @VersionRange(begin=1300, end=2005)
    default int impl$hashCode0V1300_2005()
    {
        return Objects.hash(this.getItem(), this.getCount(), this.getTagV_2005());
    }
    @SpecificImpl("hashCode0")
    @VersionRange(begin=2005)
    default int impl$hashCode0V2005()
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
            for(NbtInt nbtVersion: nbt.get("DataVersion", NbtInt.FACTORY))
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
                for(NbtCompound tag: nbt.get("tag", NbtCompound.FACTORY))
                {
                    for(NbtCompound display: tag.get("display", NbtCompound.FACTORY))
                    {
                        for(NbtList lore: display.get("Lore", NbtList.FACTORY))
                        {
                            for(NbtString l: lore.asList(NbtString.FACTORY))
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
    
    NbtCompound static$upgrade(NbtCompound nbt, int from);
    
    @SpecificImpl("static$upgrade")
    @VersionRange(end=900)
    default NbtCompound static$upgradeV_900(NbtCompound nbt, int from)
    {
        return nbt;
    }
    
    @SpecificImpl("static$upgrade")
    @VersionRange(begin=900, end=1300)
    default NbtCompound static$upgradeV900_1300(NbtCompound nbt, int from)
    {
        return MinecraftServer.instance.getDataUpdaterV900_1300().update(DataUpdateTypesV900_1300.itemStack(), nbt, from);
    }
    
    @SpecificImpl("static$upgrade")
    @VersionRange(begin=1300)
    default NbtCompound static$upgradeV1300(NbtCompound nbt, int from)
    {
        return MinecraftServer.instance.getDataUpdaterV1300().update(DataUpdateTypesV1300.itemStack(), DynamicV1300.newInstance(NbtOpsV1300.instance(), nbt), from, MinecraftServer.instance.getDataVersion()).getValue();
    }
    
    static NbtCompound upgrade(NbtCompound nbt, int from)
    {
        return FACTORY.getStatic().static$upgrade(nbt, from);
    }
}
