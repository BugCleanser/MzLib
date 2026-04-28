package mz.mzlib.minecraft.authlib.properties;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.jetbrains.annotations.Nullable;

@WrapMinecraftClass(@VersionName(name = "com.mojang.authlib.properties.Property"))
public interface Property extends WrapperObject
{
    WrapperFactory<Property> FACTORY = WrapperFactory.of(Property.class);

    @WrapMinecraftFieldAccessor(@VersionName(name = "name"))
    String getName();
    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    String getValue();
    @WrapMinecraftFieldAccessor(@VersionName(name = "signature"))
    @Nullable String getSignature();

    static Property of(String name, String value)
    {
        return of(name, value, null);
    }
    static Property of(String name, String value, @Nullable String signature)
    {
        return FACTORY.getStatic().static$of(name, value, signature);
    }


    @WrapConstructor
    Property static$of(String name, String value, @Nullable String signature);
}
