package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.nbt.NbtUtil;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.Editor;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.ThrowableBiConsumer;
import mz.mzlib.util.ThrowableSupplier;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

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
        if(tag==null || !tag.containsKey("SkullOwner"))
            return GameProfile.create(null);
        return NbtUtil.decodeGameProfileV_2005(tag.getNBTCompound("SkullOwner"));
    }
    @SpecificImpl("staticGetOwner")
    @VersionRange(begin=2002)
    default GameProfile staticGetOwnerV2002(ItemStack itemStack)
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
    @VersionRange(begin=2002)
    default void staticSetOwnerV2002(ItemStack itemStack, GameProfile profile)
    {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    static GameProfile copyOwner(ItemStack itemStack)
    {
        return getOwner(itemStack); // FIXME V2002
    }
    
    static Editor<GameProfile> editOwner(ItemStack itemStack)
    {
        return Editor.of(itemStack, ItemSkull::copyOwner, ItemSkull::setOwner);
    }
}
