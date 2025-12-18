package mz.mzlib.minecraft.fastutil;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Map;

@VersionRange(begin = 900)
@WrapClassForName("it.unimi.dsi.fastutil.ints.Int2ObjectMap")
public interface Int2ObjectMapV900<V> extends WrapperObject
{
    WrapperFactory<Int2ObjectMapV900<?>> FACTORY = WrapperFactory.of(RuntimeUtil.castClass(Int2ObjectMapV900.class));

    @Override
    Map<Integer, V> getWrapped();

    static <V> Int2ObjectMapV900<V> openHash()
    {
        return RuntimeUtil.cast(FACTORY.create(new Int2ObjectOpenHashMap<>()));
    }
}
