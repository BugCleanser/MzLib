package mz.mzlib.minecraft.authlib;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.properties.PropertyMap;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.Option;
import mz.mzlib.util.ThrowableFunction;
import mz.mzlib.util.ThrowableSupplier;
import mz.mzlib.util.wrapper.*;

import java.util.UUID;

@WrapMinecraftClass(@VersionName(name="com.mojang.authlib.GameProfile"))
public interface GameProfile extends WrapperObject
{
    WrapperFactory<GameProfile> FACTORY = WrapperFactory.of(GameProfile.class);
    @Deprecated
    @WrapperCreator
    static GameProfile create(Object wrapped)
    {
        return WrapperObject.create(GameProfile.class, wrapped);
    }
    
    UUID NIL_UUID_V2002 = new UUID(0L, 0L);
    
    /**
     * id and name cannot be null at the same time.
     */
    static GameProfile newInstance(Option<UUID> id, Option<String> name)
    {
        return create(null).staticNewInstance(id, name);
    }
    
    @WrapConstructor
    GameProfile staticNewInstance0(UUID id, String name);
    
    GameProfile staticNewInstance(Option<UUID> id, Option<String> name);
    
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=2002)
    default GameProfile staticNewInstanceV_2002(Option<UUID> id, Option<String> name)
    {
        return this.staticNewInstance0(id.toNullable(), name.toNullable());
    }
    
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=2002)
    default GameProfile staticNewInstanceV2002(Option<UUID> id, Option<String> name)
    {
        return this.staticNewInstance0(id.unwrapOr(NIL_UUID_V2002), name.unwrapOr(""));
    }
    
    static GameProfile newInstanceV2109(UUID id, String name, PropertyMap properties)
    {
        return FACTORY.getStatic().staticNewInstanceV2109(id, name, properties);
    }
    @WrapConstructor
    @VersionRange(begin=2109)
    GameProfile staticNewInstanceV2109(UUID id, String name, PropertyMap properties);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="id"))
    UUID getId0();
    
    Option<UUID> getId();
    
    @SpecificImpl("getId")
    @VersionRange(end=2002)
    default Option<UUID> getIdV_2002()
    {
        return Option.fromNullable(getId0());
    }
    
    @SpecificImpl("getId")
    @VersionRange(begin=2002)
    default Option<UUID> getIdV2002()
    {
        return Option.some(getId0()).then(ThrowableFunction.switcher(NIL_UUID_V2002::equals, ThrowableSupplier.<UUID>nul().ignore()).thenApply(InvertibleFunction.option()));
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="name"))
    String getName0();
    Option<String> getName();
    @SpecificImpl("getName")
    @VersionRange(end=2002)
    default Option<String> getNameV_2002()
    {
        return Option.fromNullable(getName0());
    }
    @SpecificImpl("getName")
    @VersionRange(begin=2002)
    default Option<String> getNameV2002()
    {
        return Option.some(getName0()).then(ThrowableFunction.switcher(String::isEmpty, ThrowableSupplier.<String>nul().ignore()).thenApply(InvertibleFunction.option()));
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="properties"))
    PropertyMap getProperties();
}
