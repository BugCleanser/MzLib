package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtFloat"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.FloatTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtFloat")})
public interface NbtFloat extends NbtElement
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static NbtFloat create(Object wrapped)
    {
        return WrapperObject.create(NbtFloat.class, wrapped);
    }

    @WrapConstructor
    NbtFloat staticNewInstance(float value);

    static NbtFloat newInstance(float value)
    {
        return create(null).staticNewInstance(value);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    float getValue();

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(float value);
}
