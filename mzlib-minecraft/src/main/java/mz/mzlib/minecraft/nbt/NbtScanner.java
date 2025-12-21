package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({ @VersionName(name = "net.minecraft.nbt.StringNbtReader") })
public interface NbtScanner extends WrapperObject
{
    WrapperFactory<NbtScanner> FACTORY = WrapperFactory.of(NbtScanner.class);
    @Deprecated
    @WrapperCreator
    static NbtScanner create(Object wrapped)
    {
        return WrapperObject.create(NbtScanner.class, wrapped);
    }

    static NbtCompound parseCompound(String context)
    {
        return FACTORY.getStatic().static$parseCompound(context);
    }

    @WrapMinecraftMethod({
        @VersionName(name = "parse", end = 2105),
        @VersionName(name = "readCompound", begin = 2105)
    })
    NbtCompound static$parseCompound(String context);
}


