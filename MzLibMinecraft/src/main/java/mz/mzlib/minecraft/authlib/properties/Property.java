package mz.mzlib.minecraft.authlib.properties;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="com.mojang.authlib.properties.Property"))
public interface Property extends WrapperObject
{
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
    String getSignature();
    
    static Property newInstance(String name, String value)
    {
        return newInstance(name, value, null);
    }
    static Property newInstance(String name, String value, String signature)
    {
        return create(null).staticNewInstance(name, value, signature);
    }
    @WrapConstructor
    Property staticNewInstance(String name, String value, String signature);
}
