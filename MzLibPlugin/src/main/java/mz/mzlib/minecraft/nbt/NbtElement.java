package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

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
}
