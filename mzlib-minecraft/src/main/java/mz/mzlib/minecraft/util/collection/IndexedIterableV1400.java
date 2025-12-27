package mz.mzlib.minecraft.util.collection;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1400)
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.util.IndexedIterable", end = 1600),
    @VersionName(name = "net.minecraft.util.collection.IndexedIterable", begin = 1600)
})
public interface IndexedIterableV1400<T> extends WrapperObject
{
    WrapperFactory<IndexedIterableV1400<?>> FACTORY = WrapperFactory.of(RuntimeUtil.castClass(IndexedIterableV1400.class));

    @WrapMinecraftMethod(@VersionName(name = "get"))
    WrapperObject get(int index);

    @VersionRange(begin = 1602)
    @WrapMinecraftMethod(@VersionName(name = "getRawId"))
    int getRawIdV1602(WrapperObject object);
}
