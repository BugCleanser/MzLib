package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtInt"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.IntTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtInt")})
public interface NbtInt extends NbtElement
{
    WrapperFactory<NbtInt> FACTORY = WrapperFactory.find(NbtInt.class);
    @Deprecated
    @WrapperCreator
    static NbtInt create(Object wrapped)
    {
        return WrapperObject.create(NbtInt.class, wrapped);
    }

    @WrapConstructor
    NbtInt staticNewInstance(int value);
    static NbtInt newInstance(int value)
    {
        return create(null).staticNewInstance(value);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    int getValue();
    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(int value);
}
