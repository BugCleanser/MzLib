package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;

import java.util.List;

@WrapMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtList"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.ListTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtList")})
public interface NBTList extends NBTElement
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static NBTList create(Object wrapped)
    {
        return WrapperObject.create(NBTList.class, wrapped);
    }

    @WrapConstructor
    NBTList staticNewInstance();
    static NBTList newInstance()
    {
        return create(null).staticNewInstance();
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    List<?> getValue();
    @WrapMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(List<?> value);

    @WrapMinecraftFieldAccessor(@VersionName(name="type"))
    byte getElementType();

    default NBTElement get0(int index)
    {
        return NBTElement.create(this.getValue().get(index));
    }
    default NBTElement get(int index)
    {
        return NBTElement.autoWrapper.cast(this.get0(index));
    }
    default void set(int index,NBTElement value)
    {
        this.getValue().set(index, RuntimeUtil.cast(value.getWrapped()));
    }
    default void add(NBTElement value)
    {
        this.getValue().add(RuntimeUtil.cast(value.getWrapped()));
    }

    default NBTCompound getNBTCompound(int index)
    {
        return NBTCompound.create(this.get0(index).getWrapped());
    }
    default byte getByte(int index)
    {
        return NBTByte.create(this.get0(index).getWrapped()).getValue();
    }
    default int getInt(int index)
    {
        return NBTInt.create(this.get0(index).getWrapped()).getValue();
    }
    default long getLong(int index)
    {
        return NBTLong.create(this.get0(index).getWrapped()).getValue();
    }
    default float getFloat(int index)
    {
        return NBTFloat.create(this.get0(index).getWrapped()).getValue();
    }
    default double getDouble(int index)
    {
        return NBTDouble.create(this.get0(index).getWrapped()).getValue();
    }
    default String getString(int index)
    {
        return NBTString.create(this.get0(index).getWrapped()).getValue();
    }
    default NBTList getNBTList(int index)
    {
        return NBTList.create(this.get0(index).getWrapped());
    }
    default byte[] getByteArray(int index)
    {
        return NBTByteArray.create(this.get0(index).getWrapped()).getValue();
    }
    default int[] getIntArray(int index)
    {
        return NBTIntArray.create(this.get0(index).getWrapped()).getValue();
    }
    default long[] getLongArray(int index)
    {
        return NBTLongArray.create(this.get0(index).getWrapped()).getValue();
    }
}
