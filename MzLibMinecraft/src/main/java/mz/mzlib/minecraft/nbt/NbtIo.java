package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.io.*;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.nbt.NbtIo"))
public interface NbtIo extends WrapperObject
{
    WrapperFactory<NbtIo> FACTORY = WrapperFactory.of(NbtIo.class);

    static NbtElement read(DataInput input, NbtSizeTracker tracker) throws IOException
    {
        return FACTORY.getStatic().static$read(input, tracker);
    }
    static void write(NbtElement nbt, DataOutput output) throws IOException
    {
        FACTORY.getStatic().static$write(nbt, output);
    }

    static NbtCompound readCompoundCompressed(InputStream stream) throws IOException
    {
        return FACTORY.getStatic().static$readCompoundCompressed(stream);
    }
    static void writeCompoundCompressed(NbtCompound nbt, OutputStream stream) throws IOException
    {
        FACTORY.getStatic().static$writeCompoundCompressed(nbt, stream);
    }

    static NbtCompound readCompoundCompressedV2003(InputStream stream, NbtSizeTracker tracker) throws IOException
    {
        return FACTORY.getStatic().static$readCompoundCompressedV2003(stream, tracker);
    }


    @WrapMinecraftMethod(@VersionName(name = "read"))
    NbtElement static$read(DataInput input, NbtSizeTracker tracker) throws IOException;

    @WrapMinecraftMethod(@VersionName(name = "write"))
    void static$write(NbtElement nbt, DataOutput output) throws IOException;

    NbtCompound static$readCompoundCompressed(InputStream stream) throws IOException;
    @SpecificImpl("static$readCompoundCompressed")
    @VersionRange(end = 2003)
    @WrapMinecraftMethod(@VersionName(name = "readCompressed"))
    NbtCompound static$readCompoundCompressedV_2003(InputStream stream) throws IOException;
    @SpecificImpl("static$readCompoundCompressed")
    @VersionRange(begin = 2003)
    default NbtCompound static$readCompoundCompressedV2003(InputStream stream) throws IOException
    {
        return readCompoundCompressedV2003(stream, NbtSizeTracker.newInstance());
    }

    @WrapMinecraftMethod(@VersionName(name = "writeCompressed"))
    void static$writeCompoundCompressed(NbtCompound nbt, OutputStream stream) throws IOException;

    @VersionRange(begin = 2003)
    @WrapMinecraftMethod(@VersionName(name = "readCompressed"))
    NbtCompound static$readCompoundCompressedV2003(InputStream stream, NbtSizeTracker tracker) throws IOException;
}
