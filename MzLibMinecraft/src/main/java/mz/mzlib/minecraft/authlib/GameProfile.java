package mz.mzlib.minecraft.authlib;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.UUID;

@WrapMinecraftClass(@VersionName(name="com.mojang.authlib.GameProfile"))
public interface GameProfile extends WrapperObject
{
    @WrapperCreator
    static GameProfile create(Object wrapped)
    {
        return WrapperObject.create(GameProfile.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getId"))
    UUID getId();
    
    @WrapMinecraftMethod(@VersionName(name="getName"))
    String getName();
}
