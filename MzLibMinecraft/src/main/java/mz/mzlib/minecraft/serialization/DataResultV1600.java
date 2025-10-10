package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.*;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;
import java.util.function.Consumer;

@VersionRange(begin=1600)
@WrapMinecraftClass(@VersionName(name="com.mojang.serialization.DataResult"))
public interface DataResultV1600 extends WrapperObject
{
    WrapperFactory<DataResultV1600> FACTORY = WrapperFactory.of(DataResultV1600.class);
    @Deprecated
    @WrapperCreator
    static DataResultV1600 create(Object wrapped)
    {
        return WrapperObject.create(DataResultV1600.class, wrapped);
    }
    
    default Option<Object> resultOrPartial(Consumer<String> onError)
    {
        return Option.fromOptional(this.resultOrPartial0(onError));
    }
    @WrapMinecraftMethod(@VersionName(name="resultOrPartial"))
    Optional<Object> resultOrPartial0(Consumer<String> onError);
    
    default Option<String> getErrorMessage() // FIXME
    {
        Ref<String> error = new RefStrong<>(null);
        Option<Object> ignored = this.resultOrPartial(error::set);
        return Option.fromNullable(error.get());
    }
    
    default Result<Option<Object>, String> toResult() // FIXME
    {
        for(String msg: this.getErrorMessage())
            return Result.failure(Option.none(), msg);
        return Result.success(this.resultOrPartial(ThrowableConsumer.nothing()));
    }
    
    default <T extends WrapperObject> Wrapper<T> wrapper(WrapperFactory<T> type)
    {
        return new Wrapper<>(this, type);
    }
    
    class Wrapper<T extends WrapperObject>
    {
        DataResultV1600 base;
        WrapperFactory<T> type;
        public Wrapper(DataResultV1600 base, WrapperFactory<T> type)
        {
            this.base = base;
            this.type = type;
        }
        
        public Result<Option<T>, String> toResult() // FIXME
        {
            for(String msg: this.base.getErrorMessage())
                return Result.failure(Option.none(), msg);
            return Result.success(this.base.resultOrPartial(ThrowableConsumer.nothing()).map(type::create));
        }
    }
}
