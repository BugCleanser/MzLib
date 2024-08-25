package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.Function;


@WrapMinecraftClass({@VersionName(name="net.minecraft.util.registry.SimpleRegistry", end=1903),@VersionName(name = "net.minecraft.registry.entry.SimpleRegistry", begin = 1903)})
public interface SimpleRegistry extends WrapperObject
{
    @WrapperCreator
    static SimpleRegistry create(Object wrapped)
    {
        return WrapperObject.create(SimpleRegistry.class, wrapped);
    }

    @WrapMinecraftMethod(@VersionName(name="getIdentifier"))
    Object getId0(Object value);
    default <K extends WrapperObject> K getId(WrapperObject value, Function<Object, K> idWrapperCreator)
    {
        return idWrapperCreator.apply(this.getId0(value.getWrapped()));
    }
}
