package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({
    @VersionName(end = 1400, name = "net.minecraft.nbt.NbtByteArray"),
    @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.ByteArrayTag"),
    @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtByteArray")
})
public interface NbtByteArray extends NbtElement
{
    WrapperFactory<NbtByteArray> FACTORY = WrapperFactory.of(NbtByteArray.class);
    @Deprecated
    @WrapperCreator
    static NbtByteArray create(Object wrapped)
    {
        return WrapperObject.create(NbtByteArray.class, wrapped);
    }

    @WrapConstructor
    NbtByteArray static$newInstance(byte[] value);
    static NbtByteArray newInstance(byte[] value)
    {
        return FACTORY.getStatic().static$newInstance(value);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    byte[] getValue();
    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(byte[] value);

    default byte get(int index)
    {
        return this.getValue()[index];
    }

    default void set(int index, byte value)
    {
        this.getValue()[index] = value;
    }
}
