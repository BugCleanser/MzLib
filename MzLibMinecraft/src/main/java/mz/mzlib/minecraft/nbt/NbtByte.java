package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtByte"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.ByteTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtByte")})
public interface NbtByte extends NbtElement
{
    WrapperFactory<NbtByte> FACTORY = WrapperFactory.of(NbtByte.class);
    @Deprecated
    @WrapperCreator
    static NbtByte create(Object wrapped)
    {
        return WrapperObject.create(NbtByte.class, wrapped);
    }
    
    static NbtByte newInstance(boolean value)
    {
        return newInstance(value ? (byte) 1 : (byte) 0);
    }
    static NbtByte newInstance(byte value)
    {
        return FACTORY.getStatic().static$newInstance(value);
    }
    @WrapConstructor
    NbtByte static$newInstance(byte value);

    @WrapMinecraftFieldAccessor({@VersionName(name="value", end=2105), @VersionName(name="comp_3817", begin=2105)})
    byte getValue();
    
    @Deprecated
    @WrapMinecraftFieldAccessor({@VersionName(name="value", end=2105), @VersionName(name="comp_3817", begin=2105)})
    void setValue(byte value);
}
