package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.AutoWrapper;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass({@VersionName(name = "net.minecraft.nbt.NbtElement", end = 1400), @VersionName(name = "net.minecraft.nbt.Tag", begin = 1400, end = 1605), @VersionName(name = "net.minecraft.nbt.NbtElement", begin = 1605)})
public interface NBTElement extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static NBTElement create(Object wrapped)
    {
        return WrapperObject.create(NBTElement.class, wrapped);
    }

    AutoWrapper<NBTElement> autoWrapper = new AutoWrapper<>(NBTElement.class, NBTCompound.class, NBTByte.class, NBTByteArray.class, NBTDouble.class, NBTFloat.class, NBTInt.class, NBTIntArray.class, NBTList.class, NBTLong.class, NBTLongArray.class, NBTShort.class, NBTString.class);

    @WrapMinecraftMethod(@VersionName(name="getType"))
    byte getType();
}
