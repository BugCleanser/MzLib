package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.Function;


@WrapMinecraftClass({@VersionName(name="net.minecraft.util.registry.SimpleRegistry", end=1903), @VersionName(name="net.minecraft.registry.SimpleRegistry", begin=1903)})
public interface SimpleRegistry extends Registry
{
    WrapperFactory<SimpleRegistry> FACTORY = WrapperFactory.find(SimpleRegistry.class);
    @Deprecated
    @WrapperCreator
    static SimpleRegistry create(Object wrapped)
    {
        return WrapperObject.create(SimpleRegistry.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getIdentifier", end=1300))
    Object getKey0V_1300(Object value);
    
    default <K extends WrapperObject> K getKeyV_1300(WrapperObject value, WrapperFactory<K> factory)
    {
        return factory.create(this.getKey0V_1300(value.getWrapped()));
    }
    @Deprecated
    default <K extends WrapperObject> K getKeyV_1300(WrapperObject value, Function<Object, K> idWrapperCreator)
    {
        return idWrapperCreator.apply(this.getKey0V_1300(value.getWrapped()));
    }
    
    Identifier getId(WrapperObject value);
    
    @SpecificImpl("getId")
    @VersionRange(end=1300)
    default Identifier getIdV_1300(WrapperObject value)
    {
        return this.getKeyV_1300(value, Identifier.FACTORY);
    }
    
    @SpecificImpl("getId")
    @VersionRange(begin=1300)
    default Identifier getIdSpecificImplV1300(WrapperObject value)
    {
        return this.getIdV1300(value);
    }
}
