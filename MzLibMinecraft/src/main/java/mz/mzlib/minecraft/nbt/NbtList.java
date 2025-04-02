package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.*;

import java.util.List;
import java.util.function.Function;

@WrapMinecraftClass({@VersionName(end=1400, name="net.minecraft.nbt.NbtList"), @VersionName(begin=1400, end=1605, name="net.minecraft.nbt.ListTag"), @VersionName(begin=1605, name="net.minecraft.nbt.NbtList")})
public interface NbtList extends NbtElement
{
    WrapperFactory<NbtList> FACTORY = WrapperFactory.find(NbtList.class);
    @Deprecated
    @WrapperCreator
    static NbtList create(Object wrapped)
    {
        return WrapperObject.create(NbtList.class, wrapped);
    }
    
    @WrapConstructor
    NbtList staticNewInstance();
    
    static NbtList newInstance()
    {
        return create(null).staticNewInstance();
    }
    
    static NbtList newInstance(NbtElement... elements)
    {
        NbtList result = newInstance();
        for(NbtElement i: elements)
        {
            result.add(i);
        }
        return result;
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="value"))
    List<Object> getValue();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="value"))
    void setValue(List<?> value);
    
    default List<NbtElement> asList()
    {
        return this.asList(NbtElement.FACTORY);
    }
    
    default <T extends NbtElement> List<T> asList(WrapperFactory<T> factory)
    {
        return new ListProxy<>(this.getValue(), InvertibleFunction.wrapper(factory));
    }
    @Deprecated
    default <T extends NbtElement> List<T> asList(Function<Object, T> creator)
    {
        return this.asList(new WrapperFactory<>(creator));
    }
    
    @VersionRange(end=2105)
    @WrapMinecraftFieldAccessor(@VersionName(name="type"))
    byte getElementTypeIdV_2105();
    
    @VersionRange(end=2105)
    @WrapMinecraftFieldAccessor(@VersionName(name="type"))
    void setElementTypeIdV_2105(byte value);
    
    default NbtElement get(int index)
    {
        return NbtElement.FACTORY.create(this.getValue().get(index));
    }
    
    default <T extends NbtElement> T get(int index, WrapperFactory<T> factory)
    {
        return this.get(index).castTo(factory);
    }
    @Deprecated
    default <T extends NbtElement> T get(int index, Function<Object, T> creator)
    {
        return this.get(index, new WrapperFactory<>(creator));
    }
    
    default void set(int index, NbtElement value)
    {
        this.getValue().set(index, RuntimeUtil.cast(value.getWrapped()));
    }
    
    void add(NbtElement value);
    @VersionRange(end=2105)
    @SpecificImpl("add")
    default void addV_2105(NbtElement value)
    {
        this.setElementTypeIdV_2105(value.getTypeId());
        this.addV2105(value);
    }
    @VersionRange(begin=2105)
    @SpecificImpl("add")
    default void addV2105(NbtElement value)
    {
        this.getValue().add(RuntimeUtil.cast(value.getWrapped()));
    }
    
    default NbtCompound getNBTCompound(int index)
    {
        return this.get(index).castTo(NbtCompound.FACTORY);
    }
    
    default byte getByte(int index)
    {
        return this.get(index).castTo(NbtByte.FACTORY).getValue();
    }
    
    default int getInt(int index)
    {
        return this.get(index).castTo(NbtInt.FACTORY).getValue();
    }
    
    default long getLong(int index)
    {
        return this.get(index).castTo(NbtLong.FACTORY).getValue();
    }
    
    default float getFloat(int index)
    {
        return this.get(index).castTo(NbtFloat.FACTORY).getValue();
    }
    
    default double getDouble(int index)
    {
        return this.get(index).castTo(NbtDouble.FACTORY).getValue();
    }
    
    default String getString(int index)
    {
        return this.get(index).castTo(NbtString.FACTORY).getValue();
    }
    
    default NbtList getNBTList(int index)
    {
        return this.get(index).castTo(NbtList.FACTORY);
    }
    
    default NbtByteArray getByteArray(int index)
    {
        return this.get(index).castTo(NbtByteArray.FACTORY);
    }
    
    default NbtIntArray getIntArray(int index)
    {
        return this.get(index).castTo(NbtIntArray.FACTORY);
    }
    
    @VersionRange(begin=1200)
    default NbtLongArrayV1200 getLongArrayV1200(int index)
    {
        return this.get(index).castTo(NbtLongArrayV1200.FACTORY);
    }
    
    @Override
    default NbtList copy()
    {
        return ((NbtList)NbtElement.super.copy());
    }
}
