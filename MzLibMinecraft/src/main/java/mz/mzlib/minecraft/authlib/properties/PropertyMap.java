package mz.mzlib.minecraft.authlib.properties;

import com.google.common.collect.Multimap;
import com.google.common.collect.LinkedHashMultimap;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.Option;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.*;

import java.util.Map;

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
    
    static PropertyMap newInstance()
    {
        return FACTORY.getStatic().staticNewInstance();
    }
    PropertyMap staticNewInstance();
    @SpecificImpl("staticNewInstance")
    @WrapConstructor
    @VersionRange(end=2109)
    PropertyMap staticNewInstanceV_2109();
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=2109)
    default PropertyMap staticNewInstanceV2109()
    {
        return this.staticNewInstance0V2109(LinkedHashMultimap.create());
    }
    
    static PropertyMap newInstance(Multimap<String, Property> properties)
    {
        // TODO optimise
        Multimap<String, ?> p0 = LinkedHashMultimap.create();
        for(Map.Entry<String, Property> entry: properties.entries())
        {
            p0.put(entry.getKey(), RuntimeUtil.cast(entry.getValue().getWrapped()));
        }
        return newInstance0(p0);
    }
    static PropertyMap newInstance0(Multimap<String, ?> properties)
    {
        return FACTORY.getStatic().staticNewInstance0(properties);
    }
    PropertyMap staticNewInstance0(Multimap<String, ?> properties);
    @SpecificImpl("staticNewInstance0")
    @WrapConstructor
    @VersionRange(end=2109)
    default PropertyMap staticNewInstance0V_2109(Multimap<String, ?> properties)
    {
        PropertyMap result = newInstance();
        for(Map.Entry<String, ?> entry: properties.entries())
        {
            result.getWrapped().put(entry.getKey(), RuntimeUtil.cast(entry.getValue()));
        }
        return result;
    }
    @SpecificImpl("staticNewInstance0")
    @WrapConstructor
    @VersionRange(begin=2109)
    PropertyMap staticNewInstance0V2109(Multimap<String, ?> properties);
    
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
