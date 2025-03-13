package mz.mzlib.minecraft.util.collection;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1400)
@WrapMinecraftClass({@VersionName(name="net.minecraft.util.IndexedIterable", end=1600), @VersionName(name="net.minecraft.util.collection.IndexedIterable", begin=1600)})
public interface IndexedIterableV1400 extends WrapperObject
{
    WrapperFactory<IndexedIterableV1400> FACTORY = WrapperFactory.find(IndexedIterableV1400.class);
    @Deprecated
    @WrapperCreator
    static IndexedIterableV1400 create(Object wrapped)
    {
        return WrapperObject.create(IndexedIterableV1400.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="get"))
    WrapperObject get(int index);
}
