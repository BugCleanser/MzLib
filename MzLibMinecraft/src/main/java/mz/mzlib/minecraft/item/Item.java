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
import mz.mzlib.util.Editor;
import mz.mzlib.util.Option;
import mz.mzlib.util.Ref;
import mz.mzlib.util.StrongRef;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WrapMinecraftClass(@VersionName(name="net.minecraft.item.Item"))
public interface Item extends WrapperObject
{
    @WrapperCreator
    static Item create(Object wrapped)
    {
        return WrapperObject.create(Item.class, wrapped);
    }
    
    static Item fromId(Identifier id)
    {
        return getRegistry().get(id).castTo(Item::create);
    }
    
    static Item fromId(String id)
    {
        return fromId(Identifier.newInstance(id));
    }
    
    Item AIR = fromId(Identifier.ofMinecraft("air"));
    
    ComponentKeyV2005.Specialized<NbtCompoundComponentV2005> COMPONENT_KEY_CUSTOM_DATA_V2005 = MinecraftPlatform.instance.getVersion()<2005 ? null : ComponentKeyV2005.fromId("custom_data").specialized(NbtCompoundComponentV2005::create);
    ComponentKeyV2005.Specialized<Text> COMPONENT_KEY_CUSTOM_NAME_V2005 = MinecraftPlatform.instance.getVersion()<2005 ? null : ComponentKeyV2005.fromId("custom_name").specialized(Text::create);
    ComponentKeyV2005.Specialized<LoreComponentV2005> COMPONENT_KEY_LORE_V2005 = MinecraftPlatform.instance.getVersion()<2005 ? null : ComponentKeyV2005.fromId("lore").specialized(LoreComponentV2005::create);
    
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
    
    static NbtCompound getCustomData(ItemStack itemStack)
    {
        return create(null).staticGetCustomData(itemStack);
    }
    
    NbtCompound staticGetCustomData(ItemStack itemStack);
    
    @SpecificImpl("staticGetCustomData")
    @VersionRange(end=2005)
    default NbtCompound staticGetCustomDataV_2005(ItemStack itemStack)
    {
        return itemStack.getTagV_2005().unwrapOrGet(NbtCompound::newInstance);
    }
    
    @SpecificImpl("staticGetCustomData")
    @VersionRange(begin=2005)
    default NbtCompound staticGetCustomDataV2005(ItemStack itemStack)
    {
        return itemStack.getComponentsV2005().get(COMPONENT_KEY_CUSTOM_DATA_V2005).getNbtCompound();
    }
    
    static void setCustomData(ItemStack itemStack, NbtCompound customData)
    {
        create(null).staticSetCustomData(itemStack, customData);
    }
    
    void staticSetCustomData(ItemStack itemStack, NbtCompound customData);
    
    @SpecificImpl("staticSetCustomData")
    @VersionRange(end=2005)
    default void staticSetCustomDataV_2005(ItemStack itemStack, NbtCompound customData)
    {
        itemStack.setTagV_2005(Option.some(customData));
    }
    
    @SpecificImpl("staticSetCustomData")
    @VersionRange(begin=2005)
    default void staticSetCustomDataV2005(ItemStack itemStack, NbtCompound customData)
    {
        itemStack.getComponentsV2005().set(COMPONENT_KEY_CUSTOM_DATA_V2005, NbtCompoundComponentV2005.newInstance(customData));
    }
    
    static NbtCompound editCustomData(ItemStack itemStack)
    {
        return create(null).staticEditCustomData(itemStack);
    }
    
    NbtCompound staticEditCustomData(ItemStack itemStack);
    
    @SpecificImpl("staticEditCustomData")
    @VersionRange(end=2005)
    default NbtCompound staticEditCustomDataV_2005(ItemStack itemStack)
    {
        return getCustomData(itemStack);
    }
    
    @SpecificImpl("staticEditCustomData")
    @VersionRange(begin=2005)
    default NbtCompound staticEditCustomDataV2005(ItemStack itemStack)
    {
        NbtCompound result = getCustomData(itemStack).copy();
        setCustomData(itemStack, result);
        return result;
    }
    
    static Text getCustomName(ItemStack itemStack)
    {
        return create(null).staticGetCustomName(itemStack);
    }
    
    Text staticGetCustomName(ItemStack itemStack);
    
    @SpecificImpl("staticGetCustomName")
    @VersionRange(end=1300)
    default Text staticGetCustomNameV_1300(ItemStack itemStack)
    {
        for(NbtCompound tag: itemStack.getTagV_2005())
            for(NbtCompound display: tag.getNBTCompound("display"))
                for(String name: display.getString("Name"))
                    return Text.fromLiteral(name);
        return Text.create(null);
    }
    
    @SpecificImpl("staticGetCustomName")
    @VersionRange(begin=1300, end=2005)
    default Text staticGetCustomNameV1300_2005(ItemStack itemStack)
    {
        for(NbtCompound nbt: itemStack.getTagV_2005())
            for(NbtCompound display: nbt.getNBTCompound("display"))
                for(String name: display.getString("Name"))
                    return Text.decode(name);
        return Text.create(null);
    }
    
    @SpecificImpl("staticGetCustomName")
    @VersionRange(begin=2005)
    default Text staticGetCustomNameV2005(ItemStack itemStack)
    {
        return itemStack.getComponentsV2005().get(COMPONENT_KEY_CUSTOM_NAME_V2005);
    }
    
    
    static void setCustomName(ItemStack itemStack, Text customName)
    {
        create(null).staticSetCustomName(itemStack, customName);
    }
    
    void staticSetCustomName(ItemStack itemStack, Text customName);
    
    @SpecificImpl("staticSetCustomName")
    @VersionRange(end=1300)
    default void staticSetCustomNameV_1300(ItemStack itemStack, Text customName)
    {
        itemStack.tagV_2005().getOrPut("display", NbtCompound::create, NbtCompound::newInstance).put("Name", NbtString.newInstance(customName.toLiteral()));
    }
    
    @SpecificImpl("staticSetCustomName")
    @VersionRange(begin=1300, end=2005)
    default void staticSetCustomNameV1300_2005(ItemStack itemStack, Text customName)
    {
        itemStack.tagV_2005().getOrPut("display", NbtCompound::create, NbtCompound::newInstance).put("Name", NbtString.newInstance(customName.encode().toString()));
    }
    
    @SpecificImpl("staticSetCustomName")
    @VersionRange(begin=2005)
    default void staticSetCustomNameV2005(ItemStack itemStack, Text customName)
    {
        itemStack.getComponentsV2005().set(COMPONENT_KEY_CUSTOM_NAME_V2005, customName);
    }
    
    static Editor<Ref<Text>> editCustomName(ItemStack itemStack)
    {
        return Editor.ofRef(itemStack, Item::getCustomName, Item::setCustomName);
    }
    
    static List<Text> getLore(ItemStack itemStack)
    {
        return create(null).staticGetLore(itemStack);
    }
    
    List<Text> staticGetLore(ItemStack itemStack);
    
    @SpecificImpl("staticGetLore")
    @VersionRange(end=1400)
    default List<Text> staticGetLoreV_1400(ItemStack itemStack)
    {
        for(NbtCompound tag: itemStack.getTagV_2005())
            for(NbtCompound display: tag.getNBTCompound("display"))
                for(NbtList lore: display.getNBTList("Lore"))
                    return lore.asList(NbtString::create).stream().map(NbtString::getValue).map(Text::fromLiteral).collect(Collectors.toList());
        return new ArrayList<>();
    }
    
    @SpecificImpl("staticGetLore")
    @VersionRange(begin=1400, end=2005)
    default List<Text> staticGetLoreV1400_2005(ItemStack itemStack)
    {
        for(NbtCompound tag: itemStack.getTagV_2005())
            for(NbtCompound display: tag.getNBTCompound("display"))
                for(NbtList lore: display.getNBTList("Lore"))
                    return lore.asList(NbtString::create).stream().map(NbtString::getValue).map(Text::decode).collect(Collectors.toList());
        return new ArrayList<>();
    }
    
    @SpecificImpl("staticGetLore")
    @VersionRange(begin=2005)
    default List<Text> staticGetLoreV2005(ItemStack itemStack)
    {
        return itemStack.getComponentsV2005().get(COMPONENT_KEY_LORE_V2005).getLines();
    }
    
    static List<Text> copyLore(ItemStack itemStack)
    {
        return create(null).staticCopyLore(itemStack);
    }
    
    List<Text> staticCopyLore(ItemStack itemStack);
    
    @SpecificImpl("staticCopyLore")
    @VersionRange(end=2005)
    default List<Text> staticCopyLoreV_2005(ItemStack itemStack)
    {
        return getLore(itemStack);
    }
    
    @SpecificImpl("staticCopyLore")
    @VersionRange(begin=2005)
    default List<Text> staticCopyLoreV2005(ItemStack itemStack)
    {
        return new ArrayList<>(getLore(itemStack));
    }
    
    static void setLore(ItemStack itemStack, List<Text> lore)
    {
        create(null).staticSetLore(itemStack, lore);
    }
    
    void staticSetLore(ItemStack itemStack, List<Text> lore);
    
    @SpecificImpl("staticSetLore")
    @VersionRange(end=1400)
    default void staticSetLoreV_1400(ItemStack itemStack, List<Text> lore)
    {
        itemStack.tagV_2005().getOrPut("display", NbtCompound::create, NbtCompound::newInstance).put("Lore", NbtList.newInstance(lore.stream().map(Text::toLiteral).map(NbtString::newInstance).toArray(NbtString[]::new)));
    }
    
    @SpecificImpl("staticSetLore")
    @VersionRange(begin=1400, end=2005)
    default void staticSetLoreV1400_2005(ItemStack itemStack, List<Text> lore)
    {
        itemStack.tagV_2005().getOrPut("display", NbtCompound::create, NbtCompound::newInstance).put("Lore", NbtList.newInstance(lore.stream().map(Text::encode).map(JsonElement::toString).map(NbtString::newInstance).toArray(NbtString[]::new)));
    }
    
    @SpecificImpl("staticSetLore")
    @VersionRange(begin=2005)
    default void staticSetLoreV2005(ItemStack itemStack, List<Text> lore)
    {
        itemStack.getComponentsV2005().set(COMPONENT_KEY_LORE_V2005, LoreComponentV2005.newInstance(lore));
    }
    
    static Editor<List<Text>> editLore(ItemStack itemStack)
    {
        return Editor.of(itemStack, Item::copyLore, Item::setLore);
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
        VanillaI18nV_1300.lastKey.set(new StrongRef<>(null));
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
