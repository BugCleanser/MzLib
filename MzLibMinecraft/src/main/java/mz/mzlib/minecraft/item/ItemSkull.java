package mz.mzlib.minecraft.item;

import com.google.gson.JsonObject;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.GameProfile;
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

@WrapMinecraftClass({@VersionName(name="net.minecraft.item.SkullItem", end=2002), @VersionName(name="net.minecraft.item.BlockItem", begin=2002)})
public interface ItemSkull extends Item
{
    @WrapperCreator
    static ItemSkull create(Object wrapped)
    {
        return WrapperObject.create(ItemSkull.class, wrapped);
    }
    
    static GameProfile getOwner(ItemStack itemStack)
    {
        return create(null).staticGetOwner(itemStack);
    }
    GameProfile staticGetOwner(ItemStack itemStack);
    @SpecificImpl("staticGetOwner")
    @VersionRange(end=2002)
    default GameProfile staticGetOwnerV_2002(ItemStack itemStack)
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
    @VersionRange(begin=2002, end=2005)
    default GameProfile staticGetOwnerV2002_2005(ItemStack itemStack)
    {
        NbtCompound tag = itemStack.getTagV_2005();
        if(tag!=null)
        {
            NbtCompound skullOwner = tag.getNBTCompound("SkullOwner");
            if(skullOwner.isPresent())
                return NbtUtil.decodeGameProfileV_2005(skullOwner);
        }
        return GameProfile.create(null);
    }
    @SpecificImpl("staticGetOwner")
    @VersionRange(begin=2005)
    default GameProfile staticGetOwnerV2005(ItemStack itemStack)
    {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    static void setOwner(ItemStack itemStack, GameProfile profile)
    {
        create(null).staticSetOwner(itemStack, profile);
    }
    void staticSetOwner(ItemStack itemStack, GameProfile profile);
    @SpecificImpl("staticSetOwner")
    @VersionRange(end=2002)
    default void staticSetOwnerV_2002(ItemStack itemStack, GameProfile profile)
    {
        itemStack.tagV_2005().put("SkullOwner", NbtUtil.encodeGameProfileV_2005(profile));
    }
    @SpecificImpl("staticSetOwner")
    @VersionRange(begin=2002, end=2005)
    default void staticSetOwnerV2002_2005(ItemStack itemStack, GameProfile profile)
    {
        itemStack.tagV_2005().getOrPutNewCompound("BlockEntityTag").put("SkullOwner", NbtUtil.encodeGameProfileV_2005(profile));
    }
    @SpecificImpl("staticSetOwner")
    @VersionRange(begin=2005)
    default void staticSetOwnerV2005(ItemStack itemStack, GameProfile profile)
    {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    static GameProfile copyOwner(ItemStack itemStack)
    {
        return getOwner(itemStack); // FIXME V2005
    }
    
    static Editor<GameProfile> editOwner(ItemStack itemStack)
    {
        return Editor.of(itemStack, ItemSkull::copyOwner, ItemSkull::setOwner);
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
