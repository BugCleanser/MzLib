package mz.mzlib.minecraft.item;

import com.google.gson.JsonElement;
import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.component.ComponentKeyV2005;
import mz.mzlib.minecraft.item.component.ComponentLoreV2005;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.nbt.NbtList;
import mz.mzlib.minecraft.nbt.NbtString;
import mz.mzlib.minecraft.registry.DefaultedRegistryV_1300__1400;
import mz.mzlib.minecraft.registry.RegistriesV1903;
import mz.mzlib.minecraft.registry.Registry;
import mz.mzlib.minecraft.registry.SimpleRegistry;
import mz.mzlib.minecraft.registry.entry.RegistryEntryV1802;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

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
    
    static void setDisplayName(ItemStack itemStack, Text displayName)
    {
        create(null).staticSetDisplayName(itemStack, displayName);
    }
    void staticSetDisplayName(ItemStack itemStack, Text displayName);
    @SpecificImpl("staticSetDisplayName")
    @VersionRange(end=1300)
    default void staticSetDisplayNameV_1300(ItemStack itemStack, Text displayName)
    {
        itemStack.customData().getOrPut("display", NbtCompound::create, NbtCompound::newInstance).put("Name", NbtString.newInstance(displayName.toLiteral()));
    }
    @SpecificImpl("staticSetDisplayName")
    @VersionRange(begin=1300, end=2005)
    default void staticSetDisplayNameV1300_2005(ItemStack itemStack, Text displayName)
    {
        itemStack.customData().getOrPut("display", NbtCompound::create, NbtCompound::newInstance).put("Name", NbtString.newInstance(displayName.encode().toString()));
    }
    @SpecificImpl("staticSetDisplayName")
    @VersionRange(begin=2005)
    default void staticSetDisplayNameV2005(ItemStack itemStack, Text displayName)
    {
        itemStack.setComponentV2005(ComponentKeyV2005.fromId("custom_name"), displayName);
    }
    
    static void setLore(ItemStack itemStack, List<Text> lore)
    {
        create(null).staticSetLore(itemStack, lore);
    }
    void staticSetLore(ItemStack itemStack, List<Text> lore);
    @SpecificImpl("staticSetLore")
    @VersionRange(end=1300)
    default void staticSetLoreV_1300(ItemStack itemStack, List<Text> lore)
    {
        itemStack.customData().getOrPut("display", NbtCompound::create, NbtCompound::newInstance).put("Lore", NbtList.newInstance(lore.stream().map(Text::toLiteral).map(NbtString::newInstance).toArray(NbtString[]::new)));
    }
    @SpecificImpl("staticSetLore")
    @VersionRange(begin=1300, end=2005)
    default void staticSetLoreV1300_2005(ItemStack itemStack, List<Text> lore)
    {
        itemStack.customData().getOrPut("display", NbtCompound::create, NbtCompound::newInstance).put("Lore", NbtList.newInstance(lore.stream().map(Text::encode).map(JsonElement::toString).map(NbtString::newInstance).toArray(NbtString[]::new)));
    }
    @SpecificImpl("staticSetLore")
    @VersionRange(begin=2005)
    default void staticSetLoreV2005(ItemStack itemStack, List<Text> lore)
    {
        itemStack.setComponentV2005(ComponentKeyV2005.fromId("lore"), ComponentLoreV2005.newInstance(lore));
    }
    

    Identifier getId();
    @SpecificImpl("getId")
    @VersionRange(end=1903)
    default Identifier getIdV_1903()
    {
        return getRegistryV_1903().getId(this);
    }
    @SpecificImpl("getId")
    @VersionRange(begin=1903)
    default Identifier getIdV1903()
    {
        return getRegistryV1903().getIdV1300(this);
    }

    Registry staticGetRegistry();
    static Registry getRegistry()
    {
        return create(null).staticGetRegistry();
    }

    static SimpleRegistry getRegistryV_1903()
    {
        return create(null).staticGetRegistryV_1903();
    }

    @SpecificImpl("staticGetRegistry")
    @VersionRange(end=1903)
    SimpleRegistry staticGetRegistryV_1903();

    @SpecificImpl("staticGetRegistryV_1903")
    @WrapMinecraftFieldAccessor(@VersionName(name="REGISTRY", end=1300))
    SimpleRegistry staticGetRegistryV_1300();

    @SpecificImpl("staticGetRegistryV_1903")
    @VersionRange(begin=1300,end=1903)
    default SimpleRegistry staticGetRegistryV1300_1903()
    {
        return Registry.itemV1300_1903();
    }

    @SpecificImpl("staticGetRegistry")
    @VersionRange(begin=1903)
    default DefaultedRegistryV_1300__1400 staticGetRegistryV1903()
    {
        return RegistriesV1903.item();
    }
    static DefaultedRegistryV_1300__1400 getRegistryV1903()
    {
        return create(null).staticGetRegistryV1903();
    }

    @WrapMinecraftMethod(@VersionName(name="getRegistryEntry", begin=1802))
    RegistryEntryV1802 getRegistryEntryV1802();
}
