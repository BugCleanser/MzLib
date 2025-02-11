package mz.mzlib.minecraft.authlib;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.authlib.properties.PropertyMap;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.Option;
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
    UUID getId0();
    default Option<UUID> getId()
    {
        return Option.fromNullable(getId0());
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="name"))
    String getName0();
    default Option<String> getName()
    {
        return Option.fromNullable(getName0());
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="properties"))
    PropertyMap getProperties();
    
    /**
     * id and name cannot be null at the same time.
     */
    static GameProfile newInstance(Option<UUID> id, Option<String> name)
    {
        return create(null).staticNewInstance0(id.toNullable(), name.toNullable());
    }
    @WrapConstructor
    GameProfile staticNewInstance0(UUID id, String name);
}
