package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.component.ComponentKeyV2005;
import mz.mzlib.minecraft.component.type.LoreComponentV2005;
import mz.mzlib.minecraft.component.type.NbtCompoundComponentV2005;
import mz.mzlib.minecraft.i18n.VanillaI18nV_1300;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.nbt.NbtList;
import mz.mzlib.minecraft.nbt.NbtString;
import mz.mzlib.minecraft.registry.RegistriesV1300;
import mz.mzlib.minecraft.registry.Registry;
import mz.mzlib.minecraft.registry.SimpleRegistry;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.*;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.item.Item"))
public interface Item extends WrapperObject
{
    WrapperFactory<Item> FACTORY = WrapperFactory.of(Item.class);

    static Item fromId(Identifier id)
    {
        return getRegistry().get(id).castTo(Item.FACTORY);
    }

    static Item fromId(String id)
    {
        return fromId(Identifier.newInstance(id));
    }

    Item AIR = fromId(Identifier.minecraft("air"));

    ComponentKeyV2005.Wrapper<NbtCompoundComponentV2005> COMPONENT_KEY_CUSTOM_DATA_V2005 =
        MinecraftPlatform.instance.getVersion() < 2005 ?
            null :
            ComponentKeyV2005.fromId("custom_data", NbtCompoundComponentV2005.FACTORY);

    ComponentKeyV2005.Wrapper<Text> COMPONENT_KEY_CUSTOM_NAME_V2005 =
        MinecraftPlatform.instance.getVersion() < 2005 ? null : ComponentKeyV2005.fromId("custom_name", Text.FACTORY);

    ComponentKeyV2005.Wrapper<LoreComponentV2005> COMPONENT_KEY_LORE_V2005 =
        MinecraftPlatform.instance.getVersion() < 2005 ?
            null :
            ComponentKeyV2005.fromId("lore", LoreComponentV2005.FACTORY);


    Identifier getId();
    @SpecificImpl("getId")
    @VersionRange(end = 1300)
    default Identifier getIdV_1300()
    {
        return getRegistryV_1300().getId(this);
    }
    @SpecificImpl("getId")
    @VersionRange(begin = 1300)
    default Identifier getIdV1300()
    {
        return getRegistryV1300().getIdV1300(this);
    }

    static Option<NbtCompound> getCustomData(ItemStack itemStack)
    {
        return FACTORY.getStatic().static$getCustomData(itemStack);
    }
    Option<NbtCompound> static$getCustomData(ItemStack itemStack);
    @SpecificImpl("static$getCustomData")
    @VersionRange(end = 2005)
    default Option<NbtCompound> static$getCustomDataV_2005(ItemStack itemStack)
    {
        return itemStack.getTagV_2005();
    }
    @SpecificImpl("static$getCustomData")
    @VersionRange(begin = 2005)
    default Option<NbtCompound> static$getCustomDataV2005(ItemStack itemStack)
    {
        return itemStack.getComponentsV2005().get(COMPONENT_KEY_CUSTOM_DATA_V2005)
            .map(NbtCompoundComponentV2005::getNbtCompound);
    }

    static void setCustomData(ItemStack itemStack, Option<NbtCompound> value)
    {
        FACTORY.getStatic().static$setCustomData(itemStack, value);
    }
    void static$setCustomData(ItemStack itemStack, Option<NbtCompound> value);
    @SpecificImpl("static$setCustomData")
    @VersionRange(end = 2005)
    default void static$setCustomDataV_2005(ItemStack itemStack, Option<NbtCompound> value)
    {
        itemStack.setTagV_2005(value);
    }
    @SpecificImpl("static$setCustomData")
    @VersionRange(begin = 2005)
    default void static$setCustomDataV2005(ItemStack itemStack, Option<NbtCompound> value)
    {
        itemStack.getComponentsV2005()
            .set(COMPONENT_KEY_CUSTOM_DATA_V2005, value.map(NbtCompoundComponentV2005::newInstance));
    }

    static Editor<NbtCompound> reviseCustomData(ItemStack itemStack)
    {
        return Editor.of(
            () -> getCustomData(itemStack).map(NbtCompound::clone0).unwrapOrGet(NbtCompound::newInstance),
            nbt -> setCustomData(
                itemStack,
                Option.some(nbt).filter(ThrowablePredicate.ofPredicate(NbtCompound::isEmpty).negate())
            )
        );
    }

    /**
     * @see #reviseCustomData
     * @deprecated slow; cannot break
     */
    @Deprecated
    static Editor<Ref<Option<NbtCompound>>> editCustomData(ItemStack itemStack)
    {
        return FACTORY.getStatic().static$editCustomData(itemStack);
    }
    Editor<Ref<Option<NbtCompound>>> static$editCustomData(ItemStack itemStack);
    @SpecificImpl("static$editCustomData")
    @VersionRange(end = 2005)
    default Editor<Ref<Option<NbtCompound>>> static$editCustomDataV_2005(ItemStack itemStack)
    {
        return Editor.ofRef(itemStack, Item::getCustomData, Item::setCustomData);
    }
    @SpecificImpl("static$editCustomData")
    @VersionRange(begin = 2005)
    default Editor<Ref<Option<NbtCompound>>> static$editCustomDataV2005(ItemStack itemStack)
    {
        return Editor.ofRef(
            itemStack,
            ThrowableFunction.of(Item::getCustomData).thenApply(ThrowableFunction.optionMap(NbtCompound::copy)),
            Item::setCustomData
        );
    }

    static Option<Text> getCustomName(ItemStack itemStack)
    {
        return FACTORY.getStatic().static$getCustomName(itemStack);
    }
    Option<Text> static$getCustomName(ItemStack itemStack);
    @SpecificImpl("static$getCustomName")
    @VersionRange(end = 1300)
    default Option<Text> static$getCustomNameV_1300(ItemStack itemStack)
    {
        for(NbtCompound tag : itemStack.getTagV_2005())
        {
            for(NbtCompound display : tag.getNbtCompound("display"))
            {
                for(String name : display.getString("Name"))
                {
                    return Option.some(Text.fromLegacy(name));
                }
            }
        }
        return Option.none();
    }
    @SpecificImpl("static$getCustomName")
    @VersionRange(begin = 1300, end = 2005)
    default Option<Text> static$getCustomNameV1300_2005(ItemStack itemStack)
    {
        for(NbtCompound nbt : itemStack.getTagV_2005())
        {
            for(NbtCompound display : nbt.getNbtCompound("display"))
            {
                for(String name : display.getString("Name"))
                {
                    return Option.some(Text.decode(name));
                }
            }
        }
        return Option.none();
    }
    @SpecificImpl("static$getCustomName")
    @VersionRange(begin = 2005)
    default Option<Text> static$getCustomNameV2005(ItemStack itemStack)
    {
        return itemStack.getComponentsV2005().get(COMPONENT_KEY_CUSTOM_NAME_V2005);
    }

    static void setCustomName(ItemStack itemStack, Option<Text> value)
    {
        FACTORY.getStatic().static$setCustomName(itemStack, value);
    }
    void static$setCustomName(ItemStack itemStack, Option<Text> value);
    @SpecificImpl("static$setCustomName")
    @VersionRange(end = 1300)
    default void static$setCustomNameV_1300(ItemStack itemStack, Option<Text> value)
    {
        for(NbtCompound tag : reviseCustomData(itemStack))
        {
            for(NbtCompound display : tag.reviseNbtCompoundOrNew("display"))
            {
                if(value.isSome())
                    display.put("Name", value.unwrap().toLegacy());
                else
                    display.remove("Name");
            }
        }
    }
    @SpecificImpl("static$setCustomName")
    @VersionRange(begin = 1300, end = 2005)
    default void static$setCustomNameV1300_2005(ItemStack itemStack, Option<Text> value)
    {
        for(NbtCompound tag : reviseCustomData(itemStack))
        {
            for(NbtCompound display : tag.reviseNbtCompoundOrNew("display"))
            {
                if(value.isSome())
                    display.put("Name", value.unwrap().encode().toString());
                else
                    display.remove("Name");
            }
        }
    }
    @SpecificImpl("static$setCustomName")
    @VersionRange(begin = 2005)
    default void static$setCustomNameV2005(ItemStack itemStack, Option<Text> value)
    {
        itemStack.getComponentsV2005().set(COMPONENT_KEY_CUSTOM_NAME_V2005, value);
    }

    /**
     * @see #setCustomName
     */
    @Deprecated
    static void removeCustomName(ItemStack itemStack)
    {
        setCustomName(itemStack, Option.none());
    }

    static Editor<Ref<Option<Text>>> editCustomName(ItemStack itemStack)
    {
        return Editor.ofRef(itemStack, Item::getCustomName, Item::setCustomName);
    }

    static Text decodeLoreLineV_2005(String value)
    {
        return FACTORY.getStatic().static$decodeLoreLineV_2005(value);
    }
    Text static$decodeLoreLineV_2005(String value);
    @SpecificImpl("static$decodeLoreLineV_2005")
    @VersionRange(end = 1400)
    default Text static$decodeLoreLineV_1400(String value)
    {
        return Text.fromLegacy(value);
    }
    @SpecificImpl("static$decodeLoreLineV_2005")
    @VersionRange(begin = 1400, end = 2005)
    default Text static$decodeLoreLineV1400_2005(String value)
    {
        return Text.decode(value);
    }

    static Option<List<Text>> getLore(ItemStack itemStack)
    {
        return FACTORY.getStatic().static$getLore(itemStack);
    }
    Option<List<Text>> static$getLore(ItemStack itemStack);
    @SpecificImpl("static$getLore")
    @VersionRange(end = 2005)
    default Option<List<Text>> static$getLoreV_2005(ItemStack itemStack)
    {
        for(NbtCompound tag : itemStack.getTagV_2005())
        {
            for(NbtCompound display : tag.getNbtCompound("display"))
            {
                for(NbtList lore : display.getNbtList("Lore"))
                {
                    return Option.some(
                        lore.asList(NbtString.FACTORY).stream().map(NbtString::getValue).map(Item::decodeLoreLineV_2005)
                            .collect(Collectors.toList()));
                }
            }
        }
        return Option.none();
    }
    @SpecificImpl("static$getLore")
    @VersionRange(begin = 2005)
    default Option<List<Text>> static$getLoreV2005(ItemStack itemStack)
    {
        return itemStack.getComponentsV2005().get(COMPONENT_KEY_LORE_V2005).map(LoreComponentV2005::getLines);
    }

    @Deprecated
    static Option<List<Text>> copyLore(ItemStack itemStack)
    {
        return FACTORY.getStatic().static$copyLore(itemStack);
    }
    Option<List<Text>> static$copyLore(ItemStack itemStack);
    @SpecificImpl("static$copyLore")
    @VersionRange(end = 2005)
    default Option<List<Text>> static$copyLoreV_2005(ItemStack itemStack)
    {
        return getLore(itemStack);
    }
    @SpecificImpl("static$copyLore")
    @VersionRange(begin = 2005)
    default Option<List<Text>> static$copyLoreV2005(ItemStack itemStack)
    {
        return getLore(itemStack).map(ArrayList::new);
    }

    @VersionRange(end = 2005)
    static String encodeLoreLineV_2005(Text value)
    {
        return FACTORY.getStatic().static$encodeLoreLineV_2005(value);
    }
    String static$encodeLoreLineV_2005(Text value);
    @SpecificImpl("static$encodeLoreLineV_2005")
    @VersionRange(end = 1400)
    default String static$encodeLoreLineV_1400(Text value)
    {
        return value.toLegacy();
    }
    @SpecificImpl("static$encodeLoreLineV_2005")
    @VersionRange(begin = 1400, end = 2005)
    default String static$encodeLoreLineV1400_2005(Text value)
    {
        return value.encode().toString();
    }

    static void setLore(ItemStack itemStack, Option<List<Text>> value)
    {
        FACTORY.getStatic().static$setLore(itemStack, value);
    }
    void static$setLore(ItemStack itemStack, Option<List<Text>> value);
    @SpecificImpl("static$setLore")
    @VersionRange(end = 2005)
    default void static$setLoreV_2005(ItemStack itemStack, Option<List<Text>> value)
    {
        for(NbtCompound tag : reviseCustomData(itemStack))
        {
            for(NbtCompound display : tag.reviseNbtCompoundOrNew("display"))
            {
                if(value.isSome())
                    display.put(
                        "Lore", NbtList.newInstance(
                            value.unwrap().stream().map(Item::encodeLoreLineV_2005).map(NbtString::newInstance)
                                .toArray(NbtString[]::new))
                    );
                else
                    display.remove("Lore");
            }
        }
    }
    @SpecificImpl("static$setLore")
    @VersionRange(begin = 2005)
    default void static$setLoreV2005(ItemStack itemStack, Option<List<Text>> value)
    {
        itemStack.getComponentsV2005().set(COMPONENT_KEY_LORE_V2005, value.map(LoreComponentV2005::newInstance));
    }

    static Editor<List<Text>> reviseLore(ItemStack itemStack)
    {
        return FACTORY.getStatic().static$reviseLore(itemStack);
    }
    Editor<List<Text>> static$reviseLore(ItemStack itemStack);
    @SpecificImpl("static$reviseLore")
    @VersionRange(end = 2005)
    default Editor<List<Text>> static$reviseLoreV_2005(ItemStack itemStack)
    {
        return reviseCustomData(itemStack)
            .then(tag -> tag.reviseNbtCompoundOrNew("display"))
            .then(display -> Editor.of(
                () -> new ListProxy<>(
                    display.getNbtList("Lore").unwrapOrGet(NbtList::newInstance).asList(NbtString.FACTORY).stream()
                        .map(NbtString::getValue).collect(Collectors.toList()), // clone to String List
                    InvertibleFunction.of(Item::decodeLoreLineV_2005, Item::encodeLoreLineV_2005)
                ),
                list ->
                {
                    if(list.isEmpty())
                        display.remove("Lore");
                    else
                        display.put(
                            "Lore", NbtList.newInstance(
                                ((ListProxy<Text, String>) list).getDelegate().stream().map(NbtString::newInstance)
                                    .toArray(NbtString[]::new))
                        );
                }
            ));
    }
    @SpecificImpl("static$reviseLore")
    @VersionRange(begin = 2005)
    default Editor<List<Text>> static$reviseLoreV2005(ItemStack itemStack)
    {
        return Editor.of(
            () -> getLore(itemStack).map(ArrayList::new).unwrapOrGet(ArrayList::new),
            list -> setLore(itemStack, Option.some(list))
        );
    }

    /**
     * @see #reviseLore
     * @deprecated slow
     */
    @Deprecated
    static Editor<Ref<Option<List<Text>>>> editLore(ItemStack itemStack)
    {
        return Editor.ofRef(itemStack, Item::copyLore, Item::setLore);
    }

    static Registry getRegistry()
    {
        return FACTORY.getStatic().static$getRegistry();
    }
    Registry static$getRegistry();
    static SimpleRegistry getRegistryV_1300()
    {
        return FACTORY.getStatic().static$getRegistryV_1300();
    }
    @SpecificImpl("static$getRegistry")
    @VersionRange(end = 1300)
    @WrapMinecraftFieldAccessor(@VersionName(name = "REGISTRY"))
    SimpleRegistry static$getRegistryV_1300();
    static Registry getRegistryV1300()
    {
        return FACTORY.getStatic().static$getRegistryV1300();
    }
    @SpecificImpl("static$getRegistry")
    @VersionRange(begin = 1300)
    default Registry static$getRegistryV1300()
    {
        return RegistriesV1300.item();
    }

    @VersionRange(end = 1400)
    @WrapMinecraftMethod(@VersionName(name = "getDisplayName"))
    String getNameLocalizedV_1400(ItemStack itemStack);

    String getTranslationKeyV_2102(ItemStack itemStack);
    @SpecificImpl("getTranslationKeyV_2102")
    @VersionRange(end = 1300)
    default String getTranslationKeyV_1300(ItemStack itemStack)
    {
        VanillaI18nV_1300.lastKey.set(new RefStrong<>(null));
        try
        {
            String ignored = this.getNameLocalizedV_1400(itemStack);
            return VanillaI18nV_1300.lastKey.get().get();
        }
        finally
        {
            VanillaI18nV_1300.lastKey.remove();
        }
    }
    @SpecificImpl("getTranslationKeyV_2102")
    @VersionRange(begin = 1300, end = 2102)
    @WrapMinecraftMethod(@VersionName(name = "getTranslationKey"))
    String getTranslationKeyV1300_2102(ItemStack itemStack);

    @VersionRange(begin = 1300)
    @WrapMinecraftMethod({
        @VersionName(name = "getDisplayName", end = 1400),
        @VersionName(name = "getName", begin = 1400)
    })
    Text getNameV1300(ItemStack itemStack);
}
