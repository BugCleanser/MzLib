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
    WrapperFactory<DataResultV1600> FACTORY = WrapperFactory.find(DataResultV1600.class);
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
    
    default Option<String> getErrorMessage()
    {
        Ref<String> error = new RefStrong<>(null);
        Option<Object> ignored = this.resultOrPartial(error::set);
        return Option.fromNullable(error.get());
    }
    
    default Result<Option<Object>, String> toResult()
    {
        for(String msg: this.getErrorMessage())
            return Result.failure(Option.none(), msg);
        return Result.success(this.resultOrPartial(ThrowableConsumer.nothing()));
    }
}
