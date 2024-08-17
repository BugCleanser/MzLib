package mz.mzlib.mc.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.bukkit.delegator.DelegatorOBCClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftMethod;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorOBCClass({@VersionName(end = 1700, name = "nms.NBTTagCompound"), @VersionName(begin = 1700, name = "net.minecraft.nbt.NBTTagCompound")})
public interface NBTCompound extends NBTElement
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static NBTCompound create(Object delegate)
    {
        return Delegator.create(NBTCompound.class, delegate);
    }

    static NBTCompound newInstance()
    {
        return create(null).staticNewInstance();
    }

    @DelegatorConstructor
    NBTCompound staticNewInstance();

    @DelegatorMinecraftMethod({@VersionName(name = "get"), @VersionName(name = "@0")})
    NBTElement get0(String name);

    default NBTElement get(String name)
    {
        return NBTElement.autoDelegator.cast(this.get0(name));
    }

    @DelegatorMinecraftMethod(@VersionName(name = "set"))
    void set(String name, NBTElement value);

    default NBTCompound getCompound(String name)
    {
        return (NBTCompound) this.get(name);
    }

    default byte getByte(String name)
    {
        return ((NBTByte) this.get(name)).getValue();
    }

    default short getShort(String name)
    {
        return ((NBTShort) this.get(name)).getValue();
    }

    default int getInt(String name)
    {
        return ((NBTInt) this.get(name)).getValue();
    }

    default long getLong(String name)
    {
        return ((NBTLong) this.get(name)).getValue();
    }

    default float getFloat(String name)
    {
        return ((NBTFloat) this.get(name)).getValue();
    }

    default double getDouble(String name)
    {
        return ((NBTDouble) this.get(name)).getValue();
    }

    default String getString(String name)
    {
        return ((NBTString) this.get(name)).getValue();
    }

    default byte[] getByteArray(String name)
    {
        return ((NBTByteArray) this.get(name)).getValue();
    }

    default int[] getIntArray(String name)
    {
        return ((NBTIntArray) this.get(name)).getValue();
    }

    default long[] getLongArray(String name)
    {
        return ((NBTLongArray) this.get(name)).getValue();
    }

    // TODO
//    default void set(String name, byte value)
//    {
//        this.set(name, NBTByte.create(value));
//    }
//
//    default void set(String name, short value)
//    {
//        this.set(name, NBTFactory.instance.create(value));
//    }
//
//    default void set(String name, int value)
//    {
//        this.set(name, NBTFactory.instance.create(value));
//    }
//
//    default void set(String name, long value)
//    {
//        this.set(name, NBTFactory.instance.create(value));
//    }
//
//    default void set(String name, float value)
//    {
//        this.set(name, NBTFactory.instance.create(value));
//    }
//
//    default void set(String name, double value)
//    {
//        this.set(name, NBTFactory.instance.create(value));
//    }
//
//    default void set(String name, String value)
//    {
//        this.set(name, NBTFactory.instance.create(value));
//    }
//
//    default void set(String name, byte[] value)
//    {
//        this.set(name, NBTFactory.instance.create(value));
//    }
//
//    default void set(String name, int[] value)
//    {
//        this.set(name, NBTFactory.instance.create(value));
//    }
//
//    default void set(String name, long[] value)
//    {
//        this.set(name, NBTFactory.instance.create(value));
//    }
}
