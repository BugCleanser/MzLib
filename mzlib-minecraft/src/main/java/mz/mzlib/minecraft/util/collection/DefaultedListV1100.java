package mz.mzlib.minecraft.util.collection;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@VersionRange(begin = 1100)
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.util.collection.DefaultedList", end = 1400),
    @VersionName(name = "net.minecraft.util.DefaultedList", begin = 1400, end = 1600),
    @VersionName(name = "net.minecraft.util.collection.DefaultedList", begin = 1600)
})
public interface DefaultedListV1100<T> extends WrapperObject
{
    WrapperFactory<DefaultedListV1100<?>> FACTORY = RuntimeUtil.cast(WrapperFactory.of(DefaultedListV1100.class));

    @Override
    List<T> getWrapped();

    static <T> DefaultedListV1100<T> newInstance(int size, T defaultValue)
    {
        return FACTORY.getStatic().static$newInstance(size, defaultValue);
    }
    static <T> DefaultedListV1100<T> newInstance(List<T> list, T defaultValue)
    {
        DefaultedListV1100<T> result = newInstance(list.size(), defaultValue);
        for(int i = 0; i < list.size(); ++i)
        {
            result.getWrapped().set(i, list.get(i));
        }
        return result;
    }
    static <T extends WrapperObject> DefaultedListV1100<?> fromWrapper(List<T> list, T defaultValue)
    {
        return newInstance(new ListProxy<>(list, FunctionInvertible.wrapper(WrapperFactory.of(defaultValue)).inverse()), defaultValue.getWrapped());
    }

    @WrapMinecraftMethod(@VersionName(name = "ofSize"))
    <T1> DefaultedListV1100<T1> static$newInstance(int size, T1 defaultValue);
}
