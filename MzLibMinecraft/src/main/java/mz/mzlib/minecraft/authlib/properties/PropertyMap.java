package mz.mzlib.minecraft.authlib.properties;

import com.google.common.collect.Multimap;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.Option;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="com.mojang.authlib.properties.PropertyMap"))
public interface PropertyMap extends WrapperObject
{
    WrapperFactory<PropertyMap> FACTORY = WrapperFactory.of(PropertyMap.class);
    @Deprecated
    @WrapperCreator
    static PropertyMap create(Object wrapped)
    {
        return WrapperObject.create(PropertyMap.class, wrapped);
    }
    
    @Override
    Multimap<String, ?> getWrapped();
    
    @VersionRange(begin=2109)
    static PropertyMap newInstanceV2109(Multimap<String, ?> properties)
    {
        return FACTORY.getStatic().staticNewInstanceV2109(properties);
    }
    @WrapConstructor
    @VersionRange(begin=2109)
    PropertyMap staticNewInstanceV2109(Multimap<String, ?> properties);
    
//    default Map<String, Property> asMap()
//    {
//        return new MapProxy<>(this.getWrapped(), InvertibleFunction.empty(), InvertibleFunction.wrap(Property.FACTORY).invert());
//    }
    
    default void putV_2109(String key, Property value)
    {
        this.getWrapped().put(key, RuntimeUtil.cast(value.getWrapped()));
    }
    default void putV_2109(String key, String value, Option<String> signature)
    {
        this.putV_2109(key, Property.newInstance(key, value, signature));
    }
    default void putV_2109(String key, String value)
    {
        this.putV_2109(key, value, Option.none());
    }
}
