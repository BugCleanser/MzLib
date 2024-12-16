package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@WrapMinecraftClass(@VersionName(name="com.mojang.serialization.DataResult", begin=1600))
public interface DataResultV1600 extends WrapperObject
{
    @WrapperCreator
    static DataResultV1600 create(Object wrapped)
    {
        return WrapperObject.create(DataResultV1600.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="resultOrPartial"))
    Optional<Object> resultOrPartial(Consumer<String> onError);
    
    default <E extends Throwable> Object getOrThrow(Supplier<E> exceptionSupplier) throws E
    {
        return resultOrPartial(m->
        {
            throw new RuntimeException(m);
        }).orElseThrow(exceptionSupplier);
    }
}
