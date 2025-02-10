package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.nbt.NbtHelper"))
public interface NbtUtil extends WrapperObject
{
    @VersionRange(end=2005)
    @WrapMinecraftMethod(@VersionName(name="toGameProfile"))
    GameProfile decodeGameProfileV_2005(NbtCompound nbt);
    
    @VersionRange(end=2005)
    @WrapMinecraftMethod({@VersionName(name="fromGameProfile", end=1400), @VersionName(name="method_10684", begin=1400)})
    NbtCompound encodeGameProfileV_2005(NbtCompound nbt, GameProfile profile);
    default NbtCompound encodeGameProfileV_2005(GameProfile profile)
    {
        return encodeGameProfileV_2005(NbtCompound.newInstance(), profile);
    }
}
