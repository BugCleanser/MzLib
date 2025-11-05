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
    WrapperFactory<NbtInt> FACTORY = WrapperFactory.of(NbtInt.class);
    @Deprecated
    @WrapperCreator
    static NbtInt create(Object wrapped)
    {
        return WrapperObject.create(NbtInt.class, wrapped);
    }

    @WrapConstructor
    NbtInt static$newInstance(int value);
    static NbtInt newInstance(int value)
    {
        return FACTORY.getStatic().static$newInstance(value);
    }

    @WrapMinecraftFieldAccessor({@VersionName(name="value", end=2105), @VersionName(name="comp_3820", begin=2105)})
    int getValue();
    
    @Deprecated
    @WrapMinecraftFieldAccessor({@VersionName(name="value", end=2105), @VersionName(name="comp_3820", begin=2105)})
    void setValue(int value);
}
