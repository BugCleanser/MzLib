package mz.mzlib.minecraft.item;

import com.google.gson.JsonElement;
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
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WrapMinecraftClass(@VersionName(name="net.minecraft.item.Item"))
public interface Item extends WrapperObject
{
    WrapperFactory<Item> FACTORY = WrapperFactory.of(Item.class);
    @Deprecated
    @WrapperCreator
    static Item create(Object wrapped)
    {
        return WrapperObject.create(Item.class, wrapped);
    }
    
    static Item fromId(Identifier id)
    {
        return getRegistry().get(id).castTo(Item.FACTORY);
    }
    
    static Item fromId(String id)
    {
        return fromId(Identifier.newInstance(id));
    }
    
    Item AIR = fromId(Identifier.ofMinecraft("air"));
    
    ComponentKeyV2005.Specialized<NbtCompoundComponentV2005> COMPONENT_KEY_CUSTOM_DATA_V2005 = MinecraftPlatform.instance.getVersion()<2005 ? null : ComponentKeyV2005.fromId("custom_data").specialize(NbtCompoundComponentV2005.FACTORY);
    ComponentKeyV2005.Specialized<Text> COMPONENT_KEY_CUSTOM_NAME_V2005 = MinecraftPlatform.instance.getVersion()<2005 ? null : ComponentKeyV2005.fromId("custom_name").specialize(Text.FACTORY);
    ComponentKeyV2005.Specialized<LoreComponentV2005> COMPONENT_KEY_LORE_V2005 = MinecraftPlatform.instance.getVersion()<2005 ? null : ComponentKeyV2005.fromId("lore").specialize(LoreComponentV2005.FACTORY);
    
    Identifier getId();
    
    @SpecificImpl("getId")
    @VersionRange(end=1300)
    default Identifier getIdV_1300()
    {
        return getRegistryV_1300().getId(this);
    }
    
    @SpecificImpl("getId")
    @VersionRange(begin=1300)
    default Identifier getIdV1300()
    {
        return getRegistryV1300().getIdV1300(this);
    }
    
    static Option<NbtCompound> getCustomData(ItemStack itemStack)
    {
        return create(null).staticGetCustomData(itemStack);
    }
    
    Option<NbtCompound> staticGetCustomData(ItemStack itemStack);
    
    @SpecificImpl("staticGetCustomData")
    @VersionRange(end=2005)
    default Option<NbtCompound> staticGetCustomDataV_2005(ItemStack itemStack)
    {
        return itemStack.getTagV_2005();
    }
    
    @SpecificImpl("staticGetCustomData")
    @VersionRange(begin=2005)
    default Option<NbtCompound> staticGetCustomDataV2005(ItemStack itemStack)
    {
        return itemStack.getComponentsV2005().get(COMPONENT_KEY_CUSTOM_DATA_V2005).map(NbtCompoundComponentV2005::getNbtCompound);
    }
    
    static void setCustomData(ItemStack itemStack, Option<NbtCompound> value)
    {
        FACTORY.getStatic().staticSetCustomData(itemStack, value);
    }
    
    void staticSetCustomData(ItemStack itemStack, Option<NbtCompound> value);
    
    @SpecificImpl("staticSetCustomData")
    @VersionRange(end=2005)
    default void staticSetCustomDataV_2005(ItemStack itemStack, Option<NbtCompound> value)
    {
        itemStack.setTagV_2005(value);
    }
    
    @SpecificImpl("staticSetCustomData")
    @VersionRange(begin=2005)
    default void staticSetCustomDataV2005(ItemStack itemStack, Option<NbtCompound> value)
    {
        itemStack.getComponentsV2005().set(COMPONENT_KEY_CUSTOM_DATA_V2005, value.map(NbtCompoundComponentV2005::newInstance));
    }
    
    static Editor<Ref<Option<NbtCompound>>> editCustomData(ItemStack itemStack)
    {
        return FACTORY.getStatic().staticEditCustomData(itemStack);
    }
    Editor<Ref<Option<NbtCompound>>> staticEditCustomData(ItemStack itemStack);
    @SpecificImpl("staticEditCustomData")
    @VersionRange(end=2005)
    default Editor<Ref<Option<NbtCompound>>> staticEditCustomDataV_2005(ItemStack itemStack)
    {
        return Editor.ofRef(itemStack, Item::getCustomData, Item::setCustomData);
    }
    @SpecificImpl("staticEditCustomData")
    @VersionRange(begin=2005)
    default Editor<Ref<Option<NbtCompound>>> staticEditCustomDataV2005(ItemStack itemStack)
    {
        return Editor.ofRef(itemStack, ThrowableFunction.of(Item::getCustomData).thenApply(ThrowableFunction.optionMap(NbtCompound::copy)), Item::setCustomData);
    }
    
    static Option<Text> getCustomName(ItemStack itemStack)
    {
        return create(null).staticGetCustomName(itemStack);
    }
    
    Option<Text> staticGetCustomName(ItemStack itemStack);
    
    @SpecificImpl("staticGetCustomName")
    @VersionRange(end=1300)
    default Option<Text> staticGetCustomNameV_1300(ItemStack itemStack)
    {
        for(NbtCompound tag: itemStack.getTagV_2005())
            for(NbtCompound display: tag.getNBTCompound("display"))
                for(String name: display.getString("Name"))
                    return Option.some(Text.fromLegacy(name));
        return Option.none();
    }
    
    @SpecificImpl("staticGetCustomName")
    @VersionRange(begin=1300, end=2005)
    default Option<Text> staticGetCustomNameV1300_2005(ItemStack itemStack)
    {
        for(NbtCompound nbt: itemStack.getTagV_2005())
            for(NbtCompound display: nbt.getNBTCompound("display"))
                for(String name: display.getString("Name"))
                    return Option.some(Text.decode(name));
        return Option.none();
    }
    
    @SpecificImpl("staticGetCustomName")
    @VersionRange(begin=2005)
    default Option<Text> staticGetCustomNameV2005(ItemStack itemStack)
    {
        return itemStack.getComponentsV2005().get(COMPONENT_KEY_CUSTOM_NAME_V2005);
    }
    
    static void setCustomName(ItemStack itemStack, Option<Text> value)
    {
        create(null).staticSetCustomName(itemStack, value);
    }
    
    void staticSetCustomName(ItemStack itemStack, Option<Text> value);
    
    @SpecificImpl("staticSetCustomName")
    @VersionRange(end=1300)
    default void staticSetCustomNameV_1300(ItemStack itemStack, Option<Text> value)
    {
        for(Text customName: value)
        {
            itemStack.tagV_2005().getOrPut("display", NbtCompound.FACTORY, NbtCompound::newInstance).put("Name", NbtString.newInstance(customName.toLegacy()));
            return;
        }
        for(NbtCompound tag: itemStack.getTagV_2005())
            for(NbtCompound display: tag.get("display", NbtCompound.FACTORY))
                display.remove("Name");
    }
    
    @SpecificImpl("staticSetCustomName")
    @VersionRange(begin=1300, end=2005)
    default void staticSetCustomNameV1300_2005(ItemStack itemStack, Option<Text> value)
    {
        for(Text customName: value)
        {
            itemStack.tagV_2005().getOrPut("display", NbtCompound.FACTORY, NbtCompound::newInstance).put("Name", NbtString.newInstance(customName.encode().toString()));
            return;
        }
        for(NbtCompound tag: itemStack.getTagV_2005())
            for(NbtCompound display: tag.get("display", NbtCompound.FACTORY))
                display.remove("Name");
    }
    
    @SpecificImpl("staticSetCustomName")
    @VersionRange(begin=2005)
    default void staticSetCustomNameV2005(ItemStack itemStack, Option<Text> value)
    {
        itemStack.getComponentsV2005().set(COMPONENT_KEY_CUSTOM_NAME_V2005, value);
    }
    
    static void removeCustomName(ItemStack itemStack)
    {
        create(null).staticRemoveCustomName(itemStack);
    }
    void staticRemoveCustomName(ItemStack itemStack);
    
    @SpecificImpl("staticRemoveCustomName")
    @VersionRange(end=2005)
    default void staticRemoveCustomNameV_1300(ItemStack itemStack)
    {
        for(NbtCompound tag: itemStack.getTagV_2005())
            for(NbtCompound display: tag.get("display", NbtCompound.FACTORY))
                display.remove("Name");
    }
    
    @SpecificImpl("staticRemoveCustomName")
    @VersionRange(begin=2005)
    default void staticRemoveCustomNameV2005(ItemStack itemStack)
    {
        itemStack.getComponentsV2005().remove(COMPONENT_KEY_CUSTOM_NAME_V2005);
    }
    
    static Editor<Ref<Option<Text>>> editCustomName(ItemStack itemStack)
    {
        return Editor.ofRef(itemStack, Item::getCustomName, Item::setCustomName);
    }
    
    static Option<List<Text>> getLore(ItemStack itemStack)
    {
        return create(null).staticGetLore(itemStack);
    }
    
    Option<List<Text>> staticGetLore(ItemStack itemStack);
    
    @SpecificImpl("staticGetLore")
    @VersionRange(end=1400)
    default Option<List<Text>> staticGetLoreV_1400(ItemStack itemStack)
    {
        for(NbtCompound tag: itemStack.getTagV_2005())
            for(NbtCompound display: tag.getNBTCompound("display"))
                for(NbtList lore: display.getNBTList("Lore"))
                    return Option.some(lore.asList(NbtString.FACTORY).stream().map(NbtString::getValue).map(Text::fromLegacy).collect(Collectors.toList()));
        return Option.none();
    }
    
    @SpecificImpl("staticGetLore")
    @VersionRange(begin=1400, end=2005)
    default Option<List<Text>> staticGetLoreV1400_2005(ItemStack itemStack)
    {
        for(NbtCompound tag: itemStack.getTagV_2005())
            for(NbtCompound display: tag.getNBTCompound("display"))
                for(NbtList lore: display.getNBTList("Lore"))
                    return Option.some(lore.asList(NbtString.FACTORY).stream().map(NbtString::getValue).map(Text::decode).collect(Collectors.toList()));
        return Option.none();
    }
    
    @SpecificImpl("staticGetLore")
    @VersionRange(begin=2005)
    default Option<List<Text>> staticGetLoreV2005(ItemStack itemStack)
    {
        return itemStack.getComponentsV2005().get(COMPONENT_KEY_LORE_V2005).map(LoreComponentV2005::getLines);
    }
    
    static Option<List<Text>> copyLore(ItemStack itemStack)
    {
        return create(null).staticCopyLore(itemStack);
    }
    
    Option<List<Text>> staticCopyLore(ItemStack itemStack);
    
    @SpecificImpl("staticCopyLore")
    @VersionRange(end=2005)
    default Option<List<Text>> staticCopyLoreV_2005(ItemStack itemStack)
    {
        return getLore(itemStack);
    }
    
    @SpecificImpl("staticCopyLore")
    @VersionRange(begin=2005)
    default Option<List<Text>> staticCopyLoreV2005(ItemStack itemStack)
    {
        return getLore(itemStack).map(ArrayList::new);
    }
    
    static void setLore(ItemStack itemStack, Option<List<Text>> value)
    {
        create(null).staticSetLore(itemStack, value);
    }
    
    void staticSetLore(ItemStack itemStack, Option<List<Text>> value);
    
    @SpecificImpl("staticSetLore")
    @VersionRange(end=1400)
    default void staticSetLoreV_1400(ItemStack itemStack, Option<List<Text>> value)
    {
        for(List<Text> lore: value)
        {
            itemStack.tagV_2005().getOrPut("display", NbtCompound.FACTORY, NbtCompound::newInstance).put("Lore", NbtList.newInstance(lore.stream().map(Text::toLegacy).map(NbtString::newInstance).toArray(NbtString[]::new)));
            return;
        }
        for(NbtCompound tag: itemStack.getTagV_2005())
            for(NbtCompound display: tag.get("display", NbtCompound.FACTORY))
                display.remove("Lore");
    }
    
    @SpecificImpl("staticSetLore")
    @VersionRange(begin=1400, end=2005)
    default void staticSetLoreV1400_2005(ItemStack itemStack, Option<List<Text>> value)
    {
        for(List<Text> lore: value)
        {
            itemStack.tagV_2005().getOrPut("display", NbtCompound.FACTORY, NbtCompound::newInstance).put("Lore", NbtList.newInstance(lore.stream().map(Text::encode).map(JsonElement::toString).map(NbtString::newInstance).toArray(NbtString[]::new)));
            return;
        }
        for(NbtCompound tag: itemStack.getTagV_2005())
            for(NbtCompound display: tag.get("display", NbtCompound.FACTORY))
                display.remove("Lore");
    }
    
    @SpecificImpl("staticSetLore")
    @VersionRange(begin=2005)
    default void staticSetLoreV2005(ItemStack itemStack, Option<List<Text>> value)
    {
        itemStack.getComponentsV2005().set(COMPONENT_KEY_LORE_V2005, value.map(LoreComponentV2005::newInstance));
    }
    
    static Editor<Ref<Option<List<Text>>>> editLore(ItemStack itemStack)
    {
        return Editor.ofRef(itemStack, Item::copyLore, Item::setLore);
    }
    
    static Registry getRegistry()
    {
        return create(null).staticGetRegistry();
    }
    
    Registry staticGetRegistry();
    
    static SimpleRegistry getRegistryV_1300()
    {
        return create(null).staticGetRegistryV_1300();
    }
    
    @SpecificImpl("staticGetRegistry")
    @VersionRange(end=1300)
    @WrapMinecraftFieldAccessor(@VersionName(name="REGISTRY"))
    SimpleRegistry staticGetRegistryV_1300();
    
    static Registry getRegistryV1300()
    {
        return create(null).staticGetRegistryV1300();
    }
    
    @SpecificImpl("staticGetRegistry")
    @VersionRange(begin=1300)
    default Registry staticGetRegistryV1300()
    {
        return RegistriesV1300.item();
    }
    
    @VersionRange(end=1400)
    @WrapMinecraftMethod(@VersionName(name="getDisplayName"))
    String getNameV_1400(ItemStack itemStack);
    
    String getTranslationKeyV_2102(ItemStack itemStack);
    
    @SpecificImpl("getTranslationKeyV_2102")
    @VersionRange(end=1300)
    default String getTranslationKeyV_1300(ItemStack itemStack)
    {
        VanillaI18nV_1300.lastKey.set(new RefStrong<>(null));
        try
        {
            String ignored = this.getNameV_1400(itemStack);
            return VanillaI18nV_1300.lastKey.get().get();
        }
        finally
        {
            VanillaI18nV_1300.lastKey.remove();
        }
    }
    
    @SpecificImpl("getTranslationKeyV_2102")
    @VersionRange(begin=1300, end=2102)
    @WrapMinecraftMethod(@VersionName(name="getTranslationKey"))
    String getTranslationKeyV1300_2102(ItemStack itemStack);
    
    @VersionRange(begin=1300)
    @WrapMinecraftMethod({@VersionName(name="getDisplayName", end=1400), @VersionName(name="getName", begin=1400)})
    Text getDefaultNameV1300(ItemStack itemStack);
}
