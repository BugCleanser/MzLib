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
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.Function;

@WrapMinecraftClass({@VersionName(name="net.minecraft.component.DataComponentType", begin=2005, end=2100), @VersionName(name="net.minecraft.component.ComponentType", begin=2100)})
public interface ComponentKeyV2005 extends WrapperObject
{
    WrapperFactory<ComponentKeyV2005> FACTORY = WrapperFactory.find(ComponentKeyV2005.class);
    @Deprecated
    @WrapperCreator
    static ComponentKeyV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentKeyV2005.class, wrapped);
    }
    
    static ComponentKeyV2005 fromId(Identifier id)
    {
        return RegistriesV1300.componentKeyV2005().get(id).as(FACTORY);
    }
    
    static ComponentKeyV2005 fromId(String id)
    {
        return fromId(Identifier.newInstance(id));
    }
    
    default <T extends WrapperObject> Specialized<T> specialized(WrapperFactory<T> factory)
    {
        return new Specialized<>(this, factory);
    }
    @Deprecated
    default <T extends WrapperObject> Specialized<T> specialized(Function<Object, T> creator)
    {
        return new Specialized<>(this, creator);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getCodec"))
    CodecV1600 getCodec();
    
    class Specialized<T extends WrapperObject>
    {
        protected ComponentKeyV2005 key;
        protected WrapperFactory<T> factory;
        
        public Specialized(ComponentKeyV2005 key, WrapperFactory<T> factory)
        {
            this.key = key;
            this.factory = factory;
        }
        @Deprecated
        public Specialized(ComponentKeyV2005 key, Function<Object, T> creator)
        {
            this(key, new WrapperFactory<>(creator));
        }
        
        public Option<T> get(ComponentMapV2005 map)
        {
            return map.get(this.key, this.factory);
        }
        
        public Option<T> put(ComponentMapDefaultedV2005 map, T value)
        {
            return Option.fromWrapper(map.put(this.key, value)).map(t -> t.castTo(this.factory));
        }
        
        public Option<T> remove(ComponentMapDefaultedV2005 map)
        {
            return Option.fromWrapper(map.remove(this.key)).map(t -> t.castTo(this.factory));
        }
        
        public Result<Option<T>, String> decode(NbtCompound nbt)
        {
            return this.key.getCodec().parse(NbtOpsV1300.withRegistriesV1903(), nbt.getWrapped()).toResult().mapValue(ThrowableFunction.<Object, T, RuntimeException>optionMap(this.factory::create));
        }
        public Result<Option<NbtCompound>, String> encode(T value)
        {
            return this.key.getCodec().encodeStart(NbtOpsV1300.withRegistriesV1903(), value.getWrapped()).toResult().mapValue(ThrowableFunction.<Object, NbtCompound, RuntimeException>optionMap(NbtCompound.FACTORY::create));
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
