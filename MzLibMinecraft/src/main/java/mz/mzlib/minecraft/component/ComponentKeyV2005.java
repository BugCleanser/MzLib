package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.registry.RegistriesV1300;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.Function;

@WrapMinecraftClass({@VersionName(name="net.minecraft.component.DataComponentType", begin=2005, end=2100), @VersionName(name="net.minecraft.component.ComponentType", begin=2100)})
public interface ComponentKeyV2005 extends WrapperObject
{
    @WrapperCreator
    static ComponentKeyV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentKeyV2005.class, wrapped);
    }
    
    static ComponentKeyV2005 fromId(Identifier id)
    {
        return RegistriesV1300.componentKeyV2005().get(id).castTo(ComponentKeyV2005::create);
    }
    
    static <T extends WrapperObject> Specialized<T> fromId(Identifier id, Function<Object, T> wrapperCreator)
    {
        return new Specialized<>(fromId(id), wrapperCreator);
    }
    
    static ComponentKeyV2005 fromId(String id)
    {
        return fromId(Identifier.newInstance(id));
    }
    
    static <T extends WrapperObject> Specialized<T> fromId(String id, Function<Object, T> wrapperCreator)
    {
        return fromId(Identifier.newInstance(id), wrapperCreator);
    }
    
    class Specialized<T extends WrapperObject>
    {
        protected ComponentKeyV2005 key;
        protected Function<Object, T> wrapperCreator;
        
        public Specialized(ComponentKeyV2005 key, Function<Object, T> wrapperCreator)
        {
            this.key = key;
            this.wrapperCreator = wrapperCreator;
        }
        
        public T get(ComponentMapV2005 map)
        {
            return map.get(this.key, this.wrapperCreator);
        }
        
        public T set(ComponentMapDefaultedV2005 map, T value)
        {
            return map.set(this.key, value).castTo(this.wrapperCreator);
        }
    }
}
