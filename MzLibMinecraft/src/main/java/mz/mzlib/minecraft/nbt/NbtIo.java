package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.nbt.NbtIo"))
public interface NbtIo extends WrapperObject
{
    WrapperFactory<NbtIo> FACTORY = WrapperFactory.of(NbtIo.class);
    @Deprecated
    @WrapperCreator
    static NbtIo create(Object wrapped)
    {
        return WrapperObject.create(NbtIo.class, wrapped);
    }
    
//    @WrapMinecraftMethod(@VersionName(name="read"))
//    NbtElement static$read(DataInput input, NbtReadingCounter counter);
    // TODO
}
