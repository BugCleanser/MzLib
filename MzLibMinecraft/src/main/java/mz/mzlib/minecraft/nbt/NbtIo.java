package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.nbt.NbtIo"))
public interface NbtIo extends WrapperObject
{
    @WrapperCreator
    static NbtIo create(Object wrapped)
    {
        return WrapperObject.create(NbtIo.class, wrapped);
    }
    
//    @WrapMinecraftMethod(@VersionName(name="read"))
//    NbtElement staticRead(DataInput input, NbtReadingCounter counter);
    // TODO
}
