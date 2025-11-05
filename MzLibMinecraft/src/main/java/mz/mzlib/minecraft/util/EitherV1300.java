package mz.mzlib.minecraft.util;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.Either;
import mz.mzlib.util.Option;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.*;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@VersionRange(begin=1300)
@WrapClassForName("com.mojang.datafixers.util.Either")
public interface EitherV1300<F, S> extends WrapperObject
{
    WrapperFactory<EitherV1300<?, ?>> FACTORY = RuntimeUtil.cast(WrapperFactory.of(EitherV1300.class));
    
    static <F, S> EitherV1300<F, S> first(F value)
    {
        return FACTORY.getStatic().static$first(value);
    }
    @WrapMethod("left")
    <F1, S1> EitherV1300<F1, S1> static$first(F1 value);
    
    static <F, S> EitherV1300<F, S> second(S value)
    {
        return FACTORY.getStatic().static$second(value);
    }
    @WrapMethod("right")
    <F1, S1> EitherV1300<F1, S1> static$second(S1 value);
    
    @WrapMethod("left")
    Optional<F> getFirst0();
    @WrapMethod("right")
    Optional<S> getSecond0();
    
    default Option<F> getFirst()
    {
        return Option.fromOptional(this.getFirst0());
    }
    default Option<S> getSecond()
    {
        return Option.fromOptional(this.getSecond0());
    }
    
    static <F, S> EitherV1300<F, S> fromEither(Either<F, S> either)
    {
        return either.map(EitherV1300::first, EitherV1300::second);
    }
    default Either<F, S> toEither()
    {
        return this.map(Either::first, Either::second);
    }
    
    default boolean isFirst()
    {
        return this.getFirst().isSome();
    }
    
    default EitherV1300<F, S> ifFirst(Consumer<F> action)
    {
        for(F f: this.getFirst())
        {
            action.accept(f);
        }
        return this;
    }
    default EitherV1300<F, S> ifSecond(Consumer<S> action)
    {
        for(S s: this.getSecond())
        {
            action.accept(s);
        }
        return this;
    }
    
    default <F1> EitherV1300<F1, S> mapFirst(Function<? super F, ? extends F1> action)
    {
        if(this.isFirst())
            return first(action.apply(this.getFirst().unwrap()));
        else
            return second(this.getSecond().unwrap());
    }
    
    default <S1> EitherV1300<F, S1> mapSecond(Function<S, S1> action)
    {
        if(this.isFirst())
            return first(this.getFirst().unwrap());
        else
            return second(action.apply(this.getSecond().unwrap()));
    }
    
    default <T> T map(Function<F, T> first, Function<S, T> second)
    {
        if(this.isFirst())
            return first.apply(this.getFirst().unwrapOr(null));
        else
            return second.apply(this.getSecond().unwrapOr(null));
    }
    
    default <F1 extends WrapperObject, S1 extends WrapperObject> EitherV1300<F1, S1> toWrapper(WrapperFactory<F1> typeFirst, WrapperFactory<S1> typeSecond)
    {
        for(F f: this.getFirst())
        {
            return first(typeFirst.create(f));
        }
        return second(typeSecond.create(this.getSecond().unwrap()));
    }
    static EitherV1300<?, ?> fromWrapper(EitherV1300<? extends WrapperObject, ? extends WrapperObject> wrapper)
    {
        for(WrapperObject f: wrapper.getFirst())
        {
            return first(f.getWrapped());
        }
        return second(wrapper.getSecond().unwrap().getWrapped());
    }
}
