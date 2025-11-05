package mz.mzlib.minecraft.authlib.properties;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="com.mojang.authlib.properties.Property"))
public interface Property extends WrapperObject
{
    WrapperFactory<Property> FACTORY = WrapperFactory.of(Property.class);
    @Deprecated
    @WrapperCreator
    static Property create(Object wrapped)
    {
        return WrapperObject.create(Property.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="name"))
    String getName();
    @WrapMinecraftFieldAccessor(@VersionName(name="value"))
    String getValue();
    @WrapMinecraftFieldAccessor(@VersionName(name="signature"))
    String getSignature0();
    default Option<String> getSignature()
    {
        return Option.fromNullable(this.getSignature0());
    }
    
    static Property newInstance(String name, String value)
    {
        return newInstance(name, value, Option.none());
    }
    static Property newInstance(String name, String value, Option<String> signature)
    {
        return FACTORY.getStatic().static$newInstance0(name, value, signature.toNullable());
    }
    @WrapConstructor
    Property static$newInstance0(String name, String value, String signature);
}
