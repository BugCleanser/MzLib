package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({
    @VersionName(end = 1400, name = "net.minecraft.nbt.NbtFloat"),
    @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.FloatTag"),
    @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtFloat")
})
public interface NbtFloat extends NbtElement
{
    WrapperFactory<NbtFloat> FACTORY = WrapperFactory.of(NbtFloat.class);
    @Deprecated
    @WrapperCreator
    static NbtFloat create(Object wrapped)
    {
        return WrapperObject.create(NbtFloat.class, wrapped);
    }

    @WrapConstructor
    NbtFloat static$newInstance(float value);

    static NbtFloat newInstance(float value)
    {
        return FACTORY.getStatic().static$newInstance(value);
    }

    @WrapMinecraftFieldAccessor({
        @VersionName(name = "value", end = 2105),
        @VersionName(name = "comp_3819", begin = 2105)
    })
    float getValue();

    @Deprecated
    @WrapMinecraftFieldAccessor({
        @VersionName(name = "value", end = 2105),
        @VersionName(name = "comp_3819", begin = 2105)
    })
    void setValue(float value);
}
