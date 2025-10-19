package mz.mzlib.minecraft.component.type;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.authlib.properties.PropertyMap;
import mz.mzlib.minecraft.entity.player.SkinTexturesV2109;
import mz.mzlib.minecraft.util.EitherV1300;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
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
        return FACTORY.getStatic().staticNewInstance(gameProfile);
    }
    
    GameProfileComponentV2005 staticNewInstance(GameProfile gameProfile);
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=2109)
    default GameProfileComponentV2005 staticNewInstanceV_2109(GameProfile gameProfile)
    {
        return newInstanceV_2109(gameProfile.getName(), gameProfile.getId(), gameProfile.getProperties());
    }
    static GameProfileComponentV2005 newInstanceV_2109(Option<String> name, Option<UUID> id, PropertyMap properties)
    {
        return FACTORY.getStatic().staticNewInstance0V_2109(name.toOptional(), id.toOptional(), properties);
    }
    @WrapConstructor
    @VersionRange(end=2109)
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    GameProfileComponentV2005 staticNewInstance0V_2109(Optional<String> name, Optional<UUID> id, PropertyMap properties);
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=2109)
    @WrapMinecraftMethod(@VersionName(name="ofStatic"))
    GameProfileComponentV2005 staticNewInstanceV2109(GameProfile gameProfile);
    
    static GameProfileComponentV2005 newInstance(GameProfile.Description description)
    {
        return FACTORY.getStatic().staticNewInstance(description);
    }
    GameProfileComponentV2005 staticNewInstance(GameProfile.Description description);
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=2109)
    default GameProfileComponentV2005 staticNewInstanceV_2109(GameProfile.Description description)
    {
        return newInstanceV_2109(description.getName(), description.getId(), description.getProperties().unwrapOrGet(PropertyMap::newInstance));
    }
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=2109)
    default GameProfileComponentV2005 staticNewInstanceV2109(GameProfile.Description description)
    {
        return newInstanceV2109(description, SkinTexturesV2109.SkinOverride.empty());
    }
    
    static GameProfileComponentV2005 newInstanceV2109(GameProfile.Description description, SkinTexturesV2109.SkinOverride override)
    {
        if(description.getId().isSome() && description.getName().isSome() && description.getProperties().isSome())
            return newInstanceV2109(EitherV1300.first(GameProfile.fromDescription(description)), override);
        return newInstanceV2109(EitherV1300.second(DataV2109.fromDescription(description)), override);
    }
    static GameProfileComponentV2005 newInstanceV2109(EitherV1300<GameProfile, DataV2109> profileOrData, SkinTexturesV2109.SkinOverride override)
    {
        return FACTORY.getStatic().staticNewInstance0V2109(EitherV1300.fromWrapper(profileOrData), override);
    }
    @WrapMinecraftMethod(@VersionName(name="ofDispatched"))
    @VersionRange(begin=2109)
    GameProfileComponentV2005 staticNewInstance0V2109(EitherV1300<?, ?> profileOrData, SkinTexturesV2109.SkinOverride override);
    
    @WrapMinecraftFieldAccessor({@VersionName(name="comp_2413", end=2109), @VersionName(name="profile", begin=2109)})
    GameProfile getGameProfile();
    
    @VersionRange(begin=2109)
    default EitherV1300<GameProfile, DataV2109> getV2109()
    {
        return this.get0V2109().toWrapper(GameProfile.FACTORY, DataV2109.FACTORY);
    }
    @WrapMinecraftMethod(@VersionName(name="get"))
    @VersionRange(begin=2109)
    EitherV1300<?, ?> get0V2109();
    
    default GameProfile.Description toDescription()
    {
        return this.getV2109().map(GameProfile::toDescription, DataV2109::toDescription);
    }
    
    @VersionRange(begin=2109)
    @WrapMinecraftInnerClass(outer=GameProfileComponentV2005.class, name=@VersionName(name="Data"))
    interface DataV2109 extends WrapperObject
    {
        WrapperFactory<DataV2109> FACTORY = WrapperFactory.of(DataV2109.class);
        
        static DataV2109 newInstance(Option<String> name, Option<UUID> id, PropertyMap properties)
        {
            return FACTORY.getStatic().staticNewInstance0(name.toOptional(), id.toOptional(), properties);
        }
        @WrapConstructor
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        DataV2109 staticNewInstance0(Optional<String> name, Optional<UUID> id, PropertyMap properties);
        
        @WrapMinecraftFieldAccessor(@VersionName(name="name"))
        Optional<String> getName0();
        @WrapMinecraftFieldAccessor(@VersionName(name="id"))
        Optional<UUID> getId0();
        @WrapMinecraftFieldAccessor(@VersionName(name="properties"))
        PropertyMap getProperties();
        
        default Option<String> getName()
        {
            return Option.fromOptional(this.getName0());
        }
        default Option<UUID> getId()
        {
            return Option.fromOptional(this.getId0());
        }
        
        static DataV2109 fromDescription(GameProfile.Description description)
        {
            return newInstance(description.getName(), description.getId(), description.getProperties().unwrapOrGet(PropertyMap::newInstance));
        }
        
        default GameProfile.Description toDescription()
        {
            return new GameProfile.Description(this.getName(), this.getId(), this.getProperties().getWrapped().isEmpty() ? Option.none() : Option.some(this.getProperties()));
        }
    }
}
