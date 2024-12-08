package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtLong"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.LongTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtLong")})
public interface NbtLong extends NbtElement
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static NbtLong create(Object wrapped)
    {
        return WrapperObject.create(NbtLong.class, wrapped);
    }

    @WrapConstructor
    NbtLong staticNewInstance(long value);
    static NbtLong newInstance(long value)
    {
        return create(null).staticNewInstance(value);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    long getValue();
    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(long value);
}
