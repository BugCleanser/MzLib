package mz.mzlib.mc.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftFieldAccessor;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;

import java.util.List;

@DelegatorMinecraftClass({@VersionName(end = 1400, name = "net.minecraft.nbt.NbtList"), @VersionName(begin = 1400, end = 1605, name = "net.minecraft.nbt.ListTag"), @VersionName(begin = 1605, name = "net.minecraft.nbt.NbtList")})
public interface NBTList extends NBTElement
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static NBTList create(Object delegate)
    {
        return Delegator.create(NBTList.class, delegate);
    }

    @DelegatorConstructor
    NBTList staticNewInstance();
    static NBTList newInstance()
    {
        return create(null).staticNewInstance();
    }

    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    List<?> getValue();
    @DelegatorMinecraftFieldAccessor(@VersionName(name = "value"))
    void setValue(List<?> value);

    @DelegatorMinecraftFieldAccessor(@VersionName(name="type"))
    byte getElementType();

    default NBTElement get0(int index)
    {
        return NBTElement.create(this.getValue().get(index));
    }
    default NBTElement get(int index)
    {
        return NBTElement.autoDelegator.cast(this.get0(index));
    }
    default void set(int index,NBTElement value)
    {
        this.getValue().set(index, RuntimeUtil.cast(value.getDelegate()));
    }
    default void add(NBTElement value)
    {
        this.getValue().add(RuntimeUtil.cast(value.getDelegate()));
    }

    default NBTCompound getNBTCompound(int index)
    {
        return NBTCompound.create(this.get0(index).getDelegate());
    }
    default byte getByte(int index)
    {
        return NBTByte.create(this.get0(index).getDelegate()).getValue();
    }
    default int getInt(int index)
    {
        return NBTInt.create(this.get0(index).getDelegate()).getValue();
    }
    default long getLong(int index)
    {
        return NBTLong.create(this.get0(index).getDelegate()).getValue();
    }
    default float getFloat(int index)
    {
        return NBTFloat.create(this.get0(index).getDelegate()).getValue();
    }
    default double getDouble(int index)
    {
        return NBTDouble.create(this.get0(index).getDelegate()).getValue();
    }
    default String getString(int index)
    {
        return NBTString.create(this.get0(index).getDelegate()).getValue();
    }
    default NBTList getNBTList(int index)
    {
        return NBTList.create(this.get0(index).getDelegate());
    }
    default byte[] getByteArray(int index)
    {
        return NBTByteArray.create(this.get0(index).getDelegate()).getValue();
    }
    default int[] getIntArray(int index)
    {
        return NBTIntArray.create(this.get0(index).getDelegate()).getValue();
    }
    default long[] getLongArray(int index)
    {
        return NBTLongArray.create(this.get0(index).getDelegate()).getValue();
    }
}
