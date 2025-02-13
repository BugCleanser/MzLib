package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;
import java.util.function.Function;

@WrapMinecraftClass({@VersionName(end=1400, name="net.minecraft.nbt.NbtList"), @VersionName(begin=1400, end=1605, name="net.minecraft.nbt.ListTag"), @VersionName(begin=1605, name="net.minecraft.nbt.NbtList")})
public interface NbtList extends NbtElement
{
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
        return this.asList(NbtElement::create);
    }
    
    default <T extends NbtElement> List<T> asList(Function<Object, T> wrapperCreator)
    {
        return new ListProxy<>(this.getValue(), InvertibleFunction.wrapper(wrapperCreator));
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="type"))
    byte getElementTypeId();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="type"))
    void setElementTypeId(byte value);
    
    default NbtElement get(int index)
    {
        return NbtElement.create(this.getValue().get(index));
    }
    
    default <T extends NbtElement> T get(int index, Function<Object, T> wrapperCreator)
    {
        return this.get(index).castTo(wrapperCreator);
    }
    
    default void set(int index, NbtElement value)
    {
        this.getValue().set(index, RuntimeUtil.cast(value.getWrapped()));
    }
    
    default void add(NbtElement value)
    {
        this.setElementTypeId(value.getTypeId());
        this.getValue().add(RuntimeUtil.cast(value.getWrapped()));
    }
    
    default NbtCompound getNBTCompound(int index)
    {
        return this.get(index).castTo(NbtCompound::create);
    }
    
    default byte getByte(int index)
    {
        return this.get(index).castTo(NbtByte::create).getValue();
    }
    
    default int getInt(int index)
    {
        return this.get(index).castTo(NbtInt::create).getValue();
    }
    
    default long getLong(int index)
    {
        return this.get(index).castTo(NbtLong::create).getValue();
    }
    
    default float getFloat(int index)
    {
        return this.get(index).castTo(NbtFloat::create).getValue();
    }
    
    default double getDouble(int index)
    {
        return this.get(index).castTo(NbtDouble::create).getValue();
    }
    
    default String getString(int index)
    {
        return this.get(index).castTo(NbtString::create).getValue();
    }
    
    default NbtList getNBTList(int index)
    {
        return this.get(index).castTo(NbtList::create);
    }
    
    default NbtByteArray getByteArray(int index)
    {
        return this.get(index).castTo(NbtByteArray::create);
    }
    
    default NbtIntArray getIntArray(int index)
    {
        return this.get(index).castTo(NbtIntArray::create);
    }
    
    @VersionRange(begin=1200)
    default NbtLongArrayV1200 getLongArrayV1200(int index)
    {
        return this.get(index).castTo(NbtLongArrayV1200::create);
    }
    
    @Override
    default NbtList copy()
    {
        return ((NbtList)NbtElement.super.copy());
    }
}
