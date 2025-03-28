package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.Result;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;

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
    
    @WrapMinecraftMethod(@VersionName(name="resultOrPartial"))
    Optional<Object> resultOrPartial0();
    default Option<Object> resultOrPartial()
    {
        return Option.fromOptional(this.resultOrPartial0());
    }
    
    @WrapMinecraftMethod(@VersionName(name="error"))
    Optional<Object> toError0();
    default Option<Error> toError()
    {
        return Option.fromOptional(this.toError0()).map(Error.FACTORY::create);
    }
    default Option<String> getErrorMessage()
    {
        return toError().map(Error::getMessage);
    }
    
    default Result<Option<Object>, String> toResult()
    {
        for(String msg: this.getErrorMessage())
            return Result.failure(Option.none(), msg);
        return Result.success(this.resultOrPartial());
    }
    
    @WrapMinecraftInnerClass(outer=DataResultV1600.class, name=@VersionName(name="Error"))
    interface Error extends DataResultV1600
    {
        WrapperFactory<Error> FACTORY = WrapperFactory.find(Error.class);
        @Deprecated
        @WrapperCreator
        static Error create(Object wrapped)
        {
            return WrapperObject.create(Error.class, wrapped);
        }
        
        @WrapMinecraftMethod(@VersionName(name="message"))
        String getMessage();
    }
}
