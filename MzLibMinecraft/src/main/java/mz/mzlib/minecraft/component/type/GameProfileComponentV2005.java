package mz.mzlib.minecraft.component.type;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.authlib.properties.PropertyMap;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;
import java.util.UUID;

@VersionRange(begin=2005)
@WrapMinecraftClass(@VersionName(name="net.minecraft.component.type.ProfileComponent"))
public interface GameProfileComponentV2005 extends WrapperObject
{
    WrapperFactory<GameProfileComponentV2005> FACTORY = WrapperFactory.find(GameProfileComponentV2005.class);
    @Deprecated
    @WrapperCreator
    static GameProfileComponentV2005 create(Object wrapped)
    {
        return WrapperObject.create(GameProfileComponentV2005.class, wrapped);
    }
    
    static GameProfileComponentV2005 newInstance(GameProfile gameProfile)
    {
        return create(null).staticNewInstance(gameProfile);
    }
    
    @WrapConstructor
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    GameProfileComponentV2005 staticNewInstance(Optional<String> name, Optional<UUID> id, PropertyMap properties);
    default GameProfileComponentV2005 staticNewInstance(GameProfile gameProfile)
    {
        return staticNewInstance(gameProfile.getName().toOptional(), gameProfile.getId().toOptional(), gameProfile.getProperties());
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="comp_2413"))
    GameProfile getGameProfile();
}
