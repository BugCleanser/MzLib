package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtDouble"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.DoubleTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtDouble")})
public interface NbtDouble extends NbtElement
{
    WrapperFactory<NbtDouble> FACTORY = WrapperFactory.find(NbtDouble.class);
    @Deprecated
    @WrapperCreator
    static NbtDouble create(Object wrapped)
    {
        return WrapperObject.create(NbtDouble.class, wrapped);
    }

    @WrapConstructor
    NbtDouble staticNewInstance(double value);

    static NbtDouble newInstance(double value)
    {
        return create(null).staticNewInstance(value);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    double getValue();

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(double value);
}
