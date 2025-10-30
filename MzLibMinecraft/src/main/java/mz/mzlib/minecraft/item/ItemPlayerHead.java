package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.component.ComponentKeyV2005;
import mz.mzlib.minecraft.component.type.GameProfileComponentV2005;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.nbt.NbtUtil;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.item.SkullItem", end=2002), @VersionName(name="net.minecraft.item.PlayerHeadItem", begin=2002)})
public interface ItemPlayerHead extends Item
{
    WrapperFactory<ItemPlayerHead> FACTORY = WrapperFactory.of(ItemPlayerHead.class);
    @Deprecated
    @WrapperCreator
    static ItemPlayerHead create(Object wrapped)
    {
        return WrapperObject.create(ItemPlayerHead.class, wrapped);
    }
    
    ComponentKeyV2005.Wrapper<GameProfileComponentV2005> COMPONENT_KEY_PROFILE_V2005 = MinecraftPlatform.instance.getVersion()<2005 ? null : ComponentKeyV2005.fromId("profile", GameProfileComponentV2005.FACTORY);
    
    static Option<GameProfile.Description> getOwner(ItemStack itemStack)
    {
        return create(null).staticGetOwner(itemStack);
    }
    
    Option<GameProfile.Description> staticGetOwner(ItemStack itemStack);
    
    @SpecificImpl("staticGetOwner")
    @VersionRange(end=2005)
    default Option<GameProfile.Description> staticGetOwnerV_2005(ItemStack itemStack)
    {
        for(NbtCompound tag: itemStack.getTagV_2005())
            for(NbtCompound skullOwner: tag.getNbtCompound("SkullOwner"))
                return Option.some(NbtUtil.decodeGameProfileV_2005(skullOwner).toDescription());
        return Option.none();
    }
    
    @SpecificImpl("staticGetOwner")
    @VersionRange(begin=2005)
    default Option<GameProfile.Description> staticGetOwnerV2005(ItemStack itemStack)
    {
        return itemStack.getComponentsV2005().get(COMPONENT_KEY_PROFILE_V2005).map(GameProfileComponentV2005::toDescription);
    }
    
    static void setOwner(ItemStack itemStack, Option<GameProfile.Description> value) // TODO
    {
        create(null).staticSetOwner(itemStack, value);
    }
    
    void staticSetOwner(ItemStack itemStack, Option<GameProfile.Description> value);
    
    @SpecificImpl("staticSetOwner")
    @VersionRange(end=2005)
    default void staticSetOwnerV_2005(ItemStack itemStack, Option<GameProfile.Description> value)
    {
        for(GameProfile.Description profile: value)
        {
            itemStack.tagV_2005().put("SkullOwner", NbtUtil.encodeGameProfileV_2005(GameProfile.fromDescription(profile)));
            return;
        }
        for(NbtCompound tag: itemStack.getTagV_2005())
            tag.remove("SkullOwner");
    }
    
    @SpecificImpl("staticSetOwner")
    @VersionRange(begin=2005)
    default void staticSetOwnerV2005(ItemStack itemStack, Option<GameProfile.Description> value)
    {
        itemStack.getComponentsV2005().set(COMPONENT_KEY_PROFILE_V2005, value.map(GameProfileComponentV2005::newInstance));
    }
    
    @Deprecated
    static String texturesFromUrl(String url)
    {
        return GameProfile.Description.urlToTextures(url);
    }
}
