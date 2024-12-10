package mz.mzlib.minecraft.util.collection;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@WrapMinecraftClass({@VersionName(name="net.minecraft.util.collection.DefaultedList", end=1400), @VersionName(name="net.minecraft.util.DefaultedList", begin=1400, end=1600), @VersionName(name="net.minecraft.util.collection.DefaultedList", begin=1600)})
public interface DefaultedList extends WrapperObject
{
    @WrapperCreator
    static WrapperObject create(Object wrapped)
    {
        return WrapperObject.create(WrapperObject.class, wrapped);
    }
    
    @Override
    List<?> getWrapped();
}
