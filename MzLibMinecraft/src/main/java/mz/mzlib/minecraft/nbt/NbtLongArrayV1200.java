package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1200)
@WrapMinecraftClass({@VersionName(end=1400, name="net.minecraft.nbt.NbtLongArray"), @VersionName(begin=1400, end=1605, name="net.minecraft.nbt.LongArrayTag"), @VersionName(begin=1605, name="net.minecraft.nbt.NbtLongArray")})
public interface NbtLongArrayV1200 extends NbtElement
{
    WrapperFactory<NbtLongArrayV1200> FACTORY = WrapperFactory.of(NbtLongArrayV1200.class);
    @Deprecated
    @WrapperCreator
    static NbtLongArrayV1200 create(Object wrapped)
    {
        return WrapperObject.create(NbtLongArrayV1200.class, wrapped);
    }
    
    @WrapConstructor
    NbtLongArrayV1200 staticNewInstance(long[] value);
    
    static NbtLongArrayV1200 newInstance(long[] value)
    {
        return FACTORY.getStatic().staticNewInstance(value);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="value"))
    long[] getValue();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="value"))
    void setValue(long[] value);
    
    default long get(int index)
    {
        return this.getValue()[index];
    }
    
    default void set(int index, long value)
    {
        this.getValue()[index] = value;
    }
}
