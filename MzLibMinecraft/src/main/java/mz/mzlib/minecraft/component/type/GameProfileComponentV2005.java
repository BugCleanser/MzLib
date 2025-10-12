package mz.mzlib.minecraft.component.type;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.authlib.properties.PropertyMap;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.*;

import java.util.Optional;
import java.util.UUID;

@VersionRange(begin=2005)
@WrapMinecraftClass(@VersionName(name="net.minecraft.component.type.ProfileComponent"))
public interface GameProfileComponentV2005 extends WrapperObject
{
    WrapperFactory<GameProfileComponentV2005> FACTORY = WrapperFactory.of(GameProfileComponentV2005.class);
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
    
    GameProfileComponentV2005 staticNewInstance(GameProfile gameProfile);
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=2109)
    default GameProfileComponentV2005 staticNewInstanceV_2109(GameProfile gameProfile)
    {
        return staticNewInstanceV_2109(gameProfile.getName().toOptional(), gameProfile.getId().toOptional(), gameProfile.getProperties());
    }
    @WrapConstructor
    @VersionRange(end=2109)
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    GameProfileComponentV2005 staticNewInstanceV_2109(Optional<String> name, Optional<UUID> id, PropertyMap properties);
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=2109)
    @WrapMinecraftMethod(@VersionName(name="ofStatic"))
    GameProfileComponentV2005 staticNewInstanceV2109(GameProfile gameProfile);
    
    @WrapMinecraftFieldAccessor({@VersionName(name="comp_2413", end=2109), @VersionName(name="profile", begin=2109)})
    GameProfile getGameProfile();
}
