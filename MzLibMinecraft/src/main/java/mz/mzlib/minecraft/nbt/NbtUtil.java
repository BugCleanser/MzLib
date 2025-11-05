package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.nbt.NbtHelper"))
public interface NbtUtil extends WrapperObject
{
    WrapperFactory<NbtUtil> FACTORY = WrapperFactory.of(NbtUtil.class);
    @Deprecated
    @WrapperCreator
    static NbtUtil create(Object wrapped)
    {
        return WrapperObject.create(NbtUtil.class, wrapped);
    }
    
    static GameProfile decodeGameProfileV_2005(NbtCompound nbt)
    {
        return FACTORY.getStatic().staticDecodeGameProfileV_2005(nbt);
    }
    @VersionRange(end=2005)
    @WrapMinecraftMethod(@VersionName(name="toGameProfile"))
    GameProfile staticDecodeGameProfileV_2005(NbtCompound nbt);
    
    static NbtCompound encodeGameProfileV_2005(GameProfile profile)
    {
        return FACTORY.getStatic().staticEncodeGameProfileV_2005(profile);
    }
    @VersionRange(end=2005)
    @WrapMinecraftMethod({@VersionName(name="fromGameProfile", end=1400), @VersionName(name="method_10684", begin=1400)})
    NbtCompound staticEncodeGameProfileV_2005(NbtCompound nbt, GameProfile profile);
    default NbtCompound staticEncodeGameProfileV_2005(GameProfile profile)
    {
        return staticEncodeGameProfileV_2005(NbtCompound.newInstance(), profile);
    }
}
