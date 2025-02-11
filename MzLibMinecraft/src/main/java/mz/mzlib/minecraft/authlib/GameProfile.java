package mz.mzlib.minecraft.authlib;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.authlib.properties.PropertyMap;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
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
    
    @WrapMinecraftFieldAccessor(@VersionName(name="id"))
    UUID getId();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="name"))
    String getName();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="properties"))
    PropertyMap getProperties();
    
    static GameProfile newInstance(UUID id, String name)
    {
        return create(null).staticNewInstance(id, name);
    }
    @WrapConstructor
    GameProfile staticNewInstance(UUID id, String name);
}
