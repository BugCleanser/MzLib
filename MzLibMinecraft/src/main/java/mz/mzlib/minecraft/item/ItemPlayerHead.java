package mz.mzlib.minecraft.item;

import com.google.gson.JsonObject;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.component.type.GameProfileComponentV2005;
import mz.mzlib.minecraft.component.ComponentKeyV2005;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.nbt.NbtUtil;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.Editor;
import mz.mzlib.util.JsonUtil;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@WrapMinecraftClass({@VersionName(name="net.minecraft.item.SkullItem", end=2002), @VersionName(name="net.minecraft.item.PlayerHeadItem", begin=2002)})
public interface ItemPlayerHead extends Item
{
    @WrapperCreator
    static ItemPlayerHead create(Object wrapped)
    {
        return WrapperObject.create(ItemPlayerHead.class, wrapped);
    }
    
    ComponentKeyV2005.Specialized<GameProfileComponentV2005> COMPONENT_KEY_PROFILE_V2005 = MinecraftPlatform.instance.getVersion()<2005 ? null : ComponentKeyV2005.fromId("profile").specialized(GameProfileComponentV2005::create);
    
    static GameProfile getOwner(ItemStack itemStack)
    {
        return create(null).staticGetOwner(itemStack);
    }
    GameProfile staticGetOwner(ItemStack itemStack);
    @SpecificImpl("staticGetOwner")
    @VersionRange(end=2005)
    default GameProfile staticGetOwnerV_2005(ItemStack itemStack)
    {
        NbtCompound tag = itemStack.getTagV_2005();
        if(tag!=null)
        {
            NbtCompound blockEntityTag = tag.getNBTCompound("BlockEntityTag");
            if(blockEntityTag.isPresent())
            {
                NbtCompound skullOwner = blockEntityTag.getNBTCompound("SkullOwner");
                if(skullOwner.isPresent())
                    return NbtUtil.decodeGameProfileV_2005(skullOwner);
            }
        }
        return GameProfile.create(null);
    }
    @SpecificImpl("staticGetOwner")
    @VersionRange(begin=2005)
    default GameProfile staticGetOwnerV2005(ItemStack itemStack)
    {
        return itemStack.getComponentsV2005().get(COMPONENT_KEY_PROFILE_V2005).getGameProfile();
    }
    
    static void setOwner(ItemStack itemStack, GameProfile profile)
    {
        create(null).staticSetOwner(itemStack, profile);
    }
    void staticSetOwner(ItemStack itemStack, GameProfile profile);
    @SpecificImpl("staticSetOwner")
    @VersionRange(end=2005)
    default void staticSetOwnerV_2005(ItemStack itemStack, GameProfile profile)
    {
        itemStack.tagV_2005().put("SkullOwner", NbtUtil.encodeGameProfileV_2005(profile));
    }
    @SpecificImpl("staticSetOwner")
    @VersionRange(begin=2005)
    default void staticSetOwnerV2005(ItemStack itemStack, GameProfile profile)
    {
        itemStack.getComponentsV2005().set(COMPONENT_KEY_PROFILE_V2005, GameProfileComponentV2005.newInstance(profile));
    }
    
    static GameProfile copyOwner(ItemStack itemStack)
    {
        return getOwner(itemStack); // FIXME V2005
    }
    
    static Editor<GameProfile> editOwner(ItemStack itemStack)
    {
        return Editor.of(itemStack, ItemPlayerHead::copyOwner, ItemPlayerHead::setOwner);
    }
    
    static String texturesFromUrl(String url)
    {
        JsonObject textures=new JsonObject();
        for(JsonObject value: JsonUtil.addChild(textures, "textures"))
            for(JsonObject skin: JsonUtil.addChild(value, "SKIN"))
                skin.addProperty("url", url);
        return Base64.getEncoder().encodeToString(textures.toString().getBytes(StandardCharsets.UTF_8));
    }
}
