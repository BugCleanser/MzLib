package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({
    @VersionName(end = 1400, name = "net.minecraft.nbt.NbtDouble"),
    @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.DoubleTag"),
    @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtDouble")
})
public interface NbtDouble extends NbtElement
{
    WrapperFactory<NbtDouble> FACTORY = WrapperFactory.of(NbtDouble.class);

    @Deprecated
    @WrapperCreator
    static NbtDouble create(Object wrapped)
    {
        return WrapperObject.create(NbtDouble.class, wrapped);
    }

    @WrapConstructor
    NbtDouble static$newInstance(double value);

    static NbtDouble newInstance(double value)
    {
        return FACTORY.getStatic().static$newInstance(value);
    }

    @WrapMinecraftFieldAccessor({
        @VersionName(name = "value", end = 2105),
        @VersionName(name = "comp_3818", begin = 2105)
    })
    double getValue();

    @Deprecated
    @WrapMinecraftFieldAccessor({
        @VersionName(name = "value", end = 2105),
        @VersionName(name = "comp_3818", begin = 2105)
    })
    void setValue(double value);
}
