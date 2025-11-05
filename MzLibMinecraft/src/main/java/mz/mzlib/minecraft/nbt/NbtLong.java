package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtLong"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.LongTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtLong")})
public interface NbtLong extends NbtElement
{
    WrapperFactory<NbtLong> FACTORY = WrapperFactory.of(NbtLong.class);
    @Deprecated
    @WrapperCreator
    static NbtLong create(Object wrapped)
    {
        return WrapperObject.create(NbtLong.class, wrapped);
    }

    @WrapConstructor
    NbtLong staticNewInstance(long value);
    static NbtLong newInstance(long value)
    {
        return FACTORY.getStatic().staticNewInstance(value);
    }

    @WrapMinecraftFieldAccessor({@VersionName(name="value", end=2105), @VersionName(name="comp_3821", begin=2105)})
    long getValue();
    
    @Deprecated
    @WrapMinecraftFieldAccessor({@VersionName(name="value", end=2105), @VersionName(name="comp_3821", begin=2105)})
    void setValue(long value);
}
