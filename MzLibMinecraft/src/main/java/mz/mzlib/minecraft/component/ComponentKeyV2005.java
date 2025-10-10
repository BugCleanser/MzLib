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
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.component.DataComponentType", begin=2005, end=2100), @VersionName(name="net.minecraft.component.ComponentType", begin=2100)})
public interface ComponentKeyV2005<T> extends WrapperObject
{
    WrapperFactory<ComponentKeyV2005<?>> FACTORY = RuntimeUtil.cast(WrapperFactory.of(ComponentKeyV2005.class));
    
    static ComponentKeyV2005<?> fromId(Identifier id)
    {
        return RegistriesV1300.componentKeyV2005().get(id).as(FACTORY);
    }
    static ComponentKeyV2005<?> fromId(String id)
    {
        return fromId(Identifier.newInstance(id));
    }
    
    static <T extends WrapperObject> Wrapper<T> fromId(Identifier id, WrapperFactory<T> type)
    {
        return new Wrapper<>(fromId(id), type);
    }
    static <T extends WrapperObject> Wrapper<T> fromId(String id, WrapperFactory<T> type)
    {
        return new Wrapper<>(fromId(id), type);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getCodec"))
    CodecV1600<T> getCodec();
    
    class Wrapper<T extends WrapperObject>
    {
        ComponentKeyV2005<?> base;
        WrapperFactory<T> type;
        
        public Wrapper(ComponentKeyV2005<?> base, WrapperFactory<T> type)
        {
            this.base = base;
            this.type = type;
        }
        
        public CodecV1600.Wrapper<T> getCodec()
        {
            return new CodecV1600.Wrapper<>(this.base.getCodec(), this.type);
        }
        
        public Option<T> get(ComponentMapV2005 map)
        {
            return map.get(this.base, this.type);
        }
        
        public Option<T> put(ComponentMapDefaultedV2005 map, T value)
        {
            return Option.fromWrapper(map.put(this.base, value)).map(t -> t.castTo(this.type));
        }
        
        public Option<T> remove(ComponentMapDefaultedV2005 map)
        {
            return Option.fromWrapper(map.remove(this.base)).map(t -> t.castTo(this.type));
        }
        
        public Result<Option<T>, String> decode(NbtCompound nbt)
        {
            return this.getCodec().parse(NbtOpsV1300.withRegistriesV1903(), nbt).toResult();
        }
        public Result<Option<NbtCompound>, String> encode(T value)
        {
            return this.getCodec().encodeStart(NbtOpsV1300.withRegistriesV1903(), value).toResult();
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
