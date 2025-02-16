package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.RuntimeUtil;
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
    
    int TYPE_ID = newInstance().getTypeId();
    NbtElementTypeV1500 TYPE_V1500 = MinecraftPlatform.instance.getVersion()<1500 ? null : newInstance().getTypeV1500();
    
    @Deprecated
    static NbtCompound load(DataInput input)
    {
        return create(null).staticLoad(input);
    }
    
    NbtCompound staticLoad(DataInput input);
    
    @SpecificImpl("staticLoad")
    @VersionRange(end=1500)
    default NbtCompound staticLoadV_1500(DataInput input)
    {
        NbtCompound result = newInstance();
        result.loadV_1500(input, 0, NbtReadingCounter.newInstance());
        return result;
    }
    
    @SpecificImpl("staticLoad")
    @VersionRange(begin=1500)
    default NbtCompound staticLoadV1500(DataInput input)
    {
        return TYPE_V1500.load(input, NbtReadingCounter.newInstance()).castTo(NbtCompound::create);
    }
    
    static NbtCompound parse(String str)
    {
        return NbtScanner.parseCompound(str);
    }
    
    @WrapConstructor
    NbtCompound staticNewInstance();
    
    static NbtCompound newInstance()
    {
        return create(null).staticNewInstance();
    }
    
    @WrapMinecraftMethod(@VersionName(name="get"))
    NbtElement get(String key);
    
    default boolean containsKey(String key)
    {
        return this.get(key).isPresent();
    }
    
    default <T extends NbtElement> Option<T> get(String key, Function<Object, T> wrapperCreator)
    {
        NbtElement result = this.get(key);
        if(result.isInstanceOf(wrapperCreator))
            return Option.some(result.castTo(wrapperCreator));
        else
            return Option.none();
    }
    
    @WrapMinecraftMethod(@VersionName(name="put"))
    void put(String key, NbtElement value);
    
    default <T extends NbtElement> T getOrPut(String key, Function<Object, T> wrapperCreator, Supplier<T> newer)
    {
        for(T result: this.get(key, wrapperCreator))
            return result;
        T result = newer.get();
        this.put(key, result);
        return result;
    }
    
    default NbtCompound getOrPutNewCompound(String key)
    {
        return this.getOrPut(key, NbtCompound::create, NbtCompound::newInstance);
    }
    
    default Option<NbtCompound> getNBTCompound(String key)
    {
        return this.get(key, NbtCompound::create);
    }
    
    default Option<Byte> getByte(String key)
    {
        return this.get(key, NbtByte::create).map(NbtByte::getValue);
    }
    
    default Option<Boolean> getBoolean(String key)
    {
        return this.getByte(key).map(RuntimeUtil::castByteToBoolean);
    }
    
    default Option<Integer> getInt(String key)
    {
        return this.get(key, NbtInt::create).map(NbtInt::getValue);
    }
    
    default Option<Long> getLong(String key)
    {
        return this.get(key, NbtLong::create).map(NbtLong::getValue);
    }
    
    default Option<Float> getFloat(String key)
    {
        return this.get(key, NbtFloat::create).map(NbtFloat::getValue);
    }
    
    default Option<Double> getDouble(String key)
    {
        return this.get(key, NbtDouble::create).map(NbtDouble::getValue);
    }
    
    default Option<String> getString(String key)
    {
        return this.get(key, NbtString::create).map(NbtString::getValue);
    }
    
    default Option<NbtList> getNBTList(String key)
    {
        return this.get(key, NbtList::create);
    }
    
    default Option<NbtByteArray> getByteArray(String key)
    {
        return this.get(key, NbtByteArray::create);
    }
    
    default Option<NbtIntArray> getIntArray(String key)
    {
        return this.get(key, NbtIntArray::create);
    }
    
    @VersionRange(begin=1200)
    default Option<NbtLongArrayV1200> getLongArrayV1200(String key)
    {
        return this.get(key, NbtLongArrayV1200::create);
    }
    
    @Override
    default NbtCompound copy()
    {
        return (NbtCompound)NbtElement.super.copy();
    }
}
