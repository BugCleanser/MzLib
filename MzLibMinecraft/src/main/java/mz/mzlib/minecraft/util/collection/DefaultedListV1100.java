package mz.mzlib.minecraft.util.collection;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@VersionRange(begin = 1100)
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.util.collection.DefaultedList", end = 1400),
    @VersionName(name = "net.minecraft.util.DefaultedList", begin = 1400, end = 1600),
    @VersionName(name = "net.minecraft.util.collection.DefaultedList", begin = 1600)
})
public interface DefaultedListV1100 extends WrapperObject
{
    WrapperFactory<WrapperObject> FACTORY = WrapperFactory.of(WrapperObject.class);
    @Deprecated
    @WrapperCreator
    static WrapperObject create(Object wrapped)
    {
        return WrapperObject.create(WrapperObject.class, wrapped);
    }

    @Override
    List<Object> getWrapped();
}
