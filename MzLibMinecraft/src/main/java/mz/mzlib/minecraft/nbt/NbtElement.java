package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.io.DataInput;
import java.io.DataOutput;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.nbt.NbtElement", end = 1400),
                @VersionName(name = "net.minecraft.nbt.Tag", begin = 1400, end = 1605),
                @VersionName(name = "net.minecraft.nbt.NbtElement", begin = 1605)
        })
public interface NbtElement extends WrapperObject
{
    @WrapperCreator
    static NbtElement create(Object wrapped)
    {
        return WrapperObject.create(NbtElement.class, wrapped);
    }

    @WrapMinecraftMethod(@VersionName(name="getType"))
    byte getTypeId();
    
    @WrapMinecraftMethod({@VersionName(name="getReader", begin=1500, end=1605), @VersionName(name="getNbtType", begin=1605)})
    NbtElementTypeV1500 getTypeV1500();
    
    @WrapMinecraftMethod(@VersionName(name="copy"))
    NbtElement copy();
    
    @WrapMinecraftMethod(@VersionName(name="read", end=1500))
    void loadV_1500(DataInput input, int depth, NbtReadingCounter counter);
    
    @WrapMinecraftMethod(@VersionName(name="write"))
    void save(DataOutput output);
}
