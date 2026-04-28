package mz.mzlib.minecraft.authlib.properties;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@ApiStatus.Experimental
@WrapMinecraftClass(@VersionName(name = "com.mojang.authlib.properties.PropertyMap"))
public interface PropertyMap extends WrapperObject
{
    WrapperFactory<PropertyMap> FACTORY = WrapperFactory.of(PropertyMap.class);
    @Override
    Multimap<String, ?> getWrapped();

    static PropertyMap of()
    {
        return FACTORY.getStatic().static$of();
    }
    PropertyMap static$of();
    @SpecificImpl("static$of")
    @WrapConstructor
    @VersionRange(end = 2109)
    PropertyMap static$ofV_2109();
    @SpecificImpl("static$of")
    @VersionRange(begin = 2109)
    default PropertyMap static$ofV2109()
    {
        return this.static$of0V2109(LinkedHashMultimap.create());
    }

    static PropertyMap of(Multimap<String, Property> properties)
    {
        // TODO optimise
        Multimap<String, ?> p0 = LinkedHashMultimap.create();
        for(Map.Entry<String, Property> entry : properties.entries())
        {
            p0.put(entry.getKey(), RuntimeUtil.cast(entry.getValue().getWrapped()));
        }
        return of0(p0);
    }
    static PropertyMap of0(Multimap<String, ?> properties)
    {
        return FACTORY.getStatic().static$of0(properties);
    }
    PropertyMap static$of0(Multimap<String, ?> properties);
    @SpecificImpl("static$of0")
    @WrapConstructor
    @VersionRange(end = 2109)
    default PropertyMap static$of0V_2109(Multimap<String, ?> properties)
    {
        PropertyMap result = of();
        for(Map.Entry<String, ?> entry : properties.entries())
        {
            result.getWrapped().put(entry.getKey(), RuntimeUtil.cast(entry.getValue()));
        }
        return result;
    }
    @SpecificImpl("static$of0")
    @WrapConstructor
    @VersionRange(begin = 2109)
    PropertyMap static$of0V2109(Multimap<String, ?> properties);

    default void putV_2109(String key, Property value)
    {
        this.getWrapped().put(key, RuntimeUtil.cast(value.getWrapped()));
    }
    default void putV_2109(String key, String value, @Nullable String signature)
    {
        this.putV_2109(key, Property.of(key, value, signature));
    }
    default void putV_2109(String key, String value)
    {
        this.putV_2109(key, value, null);
    }
}

