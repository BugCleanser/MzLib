package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.io.DataInput;
import java.util.function.Function;
import java.util.function.Supplier;

@WrapMinecraftClass({@VersionName(end=1400, name="net.minecraft.nbt.NbtCompound"), @VersionName(begin=1400, end=1605, name="net.minecraft.nbt.CompoundTag"), @VersionName(begin=1605, name="net.minecraft.nbt.NbtCompound")})
public interface NbtCompound extends NbtElement
{
    @WrapperCreator
    static NbtCompound create(Object wrapped)
    {
        return WrapperObject.create(NbtCompound.class, wrapped);
    }
    
    int typeId=newInstance().getTypeId();
    NbtElementTypeV1500 typeV1500= MinecraftPlatform.instance.getVersion()<1500?null:newInstance().getTypeV1500();
    
    NbtCompound staticLoad(DataInput input);
    static NbtCompound load(DataInput input)
    {
        return create(null).staticLoad(input);
    }
    @SpecificImpl("staticLoad")
    @VersionRange(end=1500)
    default NbtCompound staticLoadV_1500(DataInput input)
    {
        NbtCompound result=newInstance();
        result.loadV_1500(input, 0, NbtReadingCounter.newInstance());
        return result;
    }
    @SpecificImpl("staticLoad")
    @VersionRange(begin=1500)
    default NbtCompound staticLoadV1500(DataInput input)
    {
        return typeV1500.load(input, NbtReadingCounter.newInstance()).castTo(NbtCompound::create);
    }
    
    static NbtCompound parse(String str)
    {
        return NbtScanner.parseCompound(str);
//        throw new UnsupportedOperationException();
    }
    
    @WrapConstructor
    NbtCompound staticNewInstance();
    
    static NbtCompound newInstance()
    {
        return create(null).staticNewInstance();
    }
    
    @WrapMinecraftMethod(@VersionName(name="get"))
    NbtElement get(String key);
    
    default <T extends NbtElement> T get(String key, Function<Object, T> wrapperCreator)
    {
        return this.get(key).castTo(wrapperCreator);
    }
    
    @WrapMinecraftMethod(@VersionName(name="put"))
    void put(String key, NbtElement value);
    
    default <T extends NbtElement> T getOrPut(String key, Function<Object, T> wrapperCreator, Supplier<T> newer)
    {
        T result = this.get(key, wrapperCreator);
        if(!result.isPresent())
        {
            result = newer.get();
            this.put(key, result);
        }
        return result;
    }
    
    default NbtCompound getNBTCompound(String key)
    {
        return this.get(key, NbtCompound::create);
    }
    
    default byte getByte(String key)
    {
        return this.get(key, NbtByte::create).getValue();
    }
    
    default boolean getBoolean(String key)
    {
        return this.getByte(key)!=0;
    }
    
    default int getInt(String key)
    {
        return this.get(key, NbtInt::create).getValue();
    }
    
    default long getLong(String key)
    {
        return this.get(key, NbtLong::create).getValue();
    }
    
    default float getFloat(String key)
    {
        return this.get(key, NbtFloat::create).getValue();
    }
    
    default double getDouble(String key)
    {
        return this.get(key, NbtDouble::create).getValue();
    }
    
    default String getString(String key)
    {
        return this.get(key, NbtString::create).getValue();
    }
    
    default NbtList getNBTList(String key)
    {
        return this.get(key, NbtList::create);
    }
    
    default NbtByteArray getByteArray(String key)
    {
        return this.get(key, NbtByteArray::create);
    }
    
    default NbtIntArray getIntArray(String key)
    {
        return this.get(key, NbtIntArray::create);
    }
    
    default NbtLongArray getLongArray(String key)
    {
        return this.get(key, NbtLongArray::create);
    }
}
