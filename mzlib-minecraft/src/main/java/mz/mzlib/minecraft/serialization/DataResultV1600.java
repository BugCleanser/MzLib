package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Box;
import mz.mzlib.util.Functional;
import mz.mzlib.util.Option;
import mz.mzlib.util.Result;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@VersionRange(begin = 1600)
@WrapMinecraftClass(@VersionName(name = "com.mojang.serialization.DataResult"))
public interface DataResultV1600<T> extends WrapperObject
{
    WrapperFactory<DataResultV1600<?>> FACTORY = WrapperFactory.of(DataResultV1600.class);

    @ApiStatus.Experimental
    default @Nullable T resultOrPartial(Consumer<String> onError)
    {
        return this.resultOrPartial0(onError).orElse(null);
    }

    @ApiStatus.Internal
    @WrapMinecraftMethod(@VersionName(name = "resultOrPartial"))
    Optional<T> resultOrPartial0(Consumer<String> onError);

    @ApiStatus.Experimental
    default Option<String> getErrorMessage() // FIXME
    {
        Box.Mut<@Nullable String> error = Box.of(null);
        this.resultOrPartial(error::set);
        return Option.fromNullable(error.get());
    }

    default Result<T, String> toResult()
    {
        for(String msg : this.getErrorMessage())
        {
            return Result.failure(Option.fromNullable(this.resultOrPartial(Functional.nothing1())), msg);
        }
        return Result.success(Objects.requireNonNull(this.resultOrPartial(Functional.nothing1())));
    }

    class Wrapper<T extends WrapperObject>
    {
        DataResultV1600<?> base;
        WrapperFactory<T> type;
        public Wrapper(DataResultV1600<?> base, WrapperFactory<T> type)
        {
            this.base = base;
            this.type = type;
        }

        public Result<T, String> toResult()
        {
            return this.base.toResult().mapValue(type::create);
        }
    }
}


