package mz.mzlib.minecraft.authlib.properties;

import com.google.common.collect.Multimap;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="com.mojang.authlib.properties.PropertyMap"))
public interface PropertyMap extends WrapperObject
{
    WrapperFactory<PropertyMap> FACTORY = WrapperFactory.find(PropertyMap.class);
    @Deprecated
    @WrapperCreator
    static PropertyMap create(Object wrapped)
    {
        return WrapperObject.create(PropertyMap.class, wrapped);
    }
    
    @Override
    Multimap<String, Object> getWrapped();
    
//    default Map<String, Property> asMap()
//    {
//        return new MapProxy<>(this.getWrapped(), InvertibleFunction.empty(), InvertibleFunction.wrap(Property::create).invert());
//    }
    
    default void put(String key, Property value)
    {
        this.getWrapped().put(key, value.getWrapped());
    }
    default void put(String key, String value, Option<String> signature)
    {
        this.put(key, Property.newInstance(key, value, signature));
    }
    default void put(String key, String value)
    {
        this.put(key, value, Option.none());
    }
}
