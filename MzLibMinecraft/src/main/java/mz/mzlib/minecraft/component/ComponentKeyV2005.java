package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.nbt.NbtOpsV1300;
import mz.mzlib.minecraft.registry.RegistriesV1300;
import mz.mzlib.minecraft.serialization.CodecV1600;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.Result;
import mz.mzlib.util.ThrowableFunction;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.Function;

@WrapMinecraftClass({@VersionName(name="net.minecraft.component.DataComponentType", begin=2005, end=2100), @VersionName(name="net.minecraft.component.ComponentType", begin=2100)})
public interface ComponentKeyV2005 extends WrapperObject
{
    @WrapperCreator
    static ComponentKeyV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentKeyV2005.class, wrapped);
    }
    
    static ComponentKeyV2005 fromId(Identifier id)
    {
        return RegistriesV1300.componentKeyV2005().get(id).castTo(ComponentKeyV2005::create);
    }
    
    static ComponentKeyV2005 fromId(String id)
    {
        return fromId(Identifier.newInstance(id));
    }
    
    default <T extends WrapperObject> Specialized<T> specialized(Function<Object, T> wrapperCreator)
    {
        return new Specialized<>(this, wrapperCreator);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getCodec"))
    CodecV1600 getCodec();
    
    class Specialized<T extends WrapperObject>
    {
        protected ComponentKeyV2005 key;
        protected Function<Object, T> wrapperCreator;
        
        public Specialized(ComponentKeyV2005 key, Function<Object, T> wrapperCreator)
        {
            this.key = key;
            this.wrapperCreator = wrapperCreator;
        }
        
        public T get(ComponentMapV2005 map)
        {
            return map.get(this.key, this.wrapperCreator);
        }
        
        public T set(ComponentMapDefaultedV2005 map, T value)
        {
            return map.set(this.key, value).castTo(this.wrapperCreator);
        }
        
        public Result<Option<T>, String> decode(NbtCompound nbt)
        {
            return this.key.getCodec().parse(NbtOpsV1300.withRegistriesV1903(), nbt.getWrapped()).toResult().mapValue(ThrowableFunction.<Object, T, RuntimeException>optionMap(this.wrapperCreator::apply));
        }
        public Result<Option<NbtCompound>, String> encode(T value)
        {
            return this.key.getCodec().encodeStart(NbtOpsV1300.withRegistriesV1903(), value.getWrapped()).toResult().mapValue(ThrowableFunction.<Object, NbtCompound, RuntimeException>optionMap(NbtCompound::create));
        }
        public T copy(T value)
        {
            Result<Option<NbtCompound>, String> encode = this.encode(value);
            for(String err: encode.getError())
                throw new RuntimeException(err);
            Result<Option<T>, String> decode = this.decode(encode.getValue().unwrap());
            for(String err: decode.getError())
                throw new RuntimeException(err);
            return decode.getValue().unwrap();
        }
    }
}
