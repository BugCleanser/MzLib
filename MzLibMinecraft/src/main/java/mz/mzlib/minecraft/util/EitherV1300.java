package mz.mzlib.minecraft.util;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.*;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@VersionRange(begin=1300)
@WrapClassForName("com.mojang.datafixers.util.Either")
public interface EitherV1300<F, S> extends WrapperObject
{
    @WrapperCreator
    static EitherV1300<?, ?> create(Object wrapped)
    {
        return WrapperObject.create(EitherV1300.class, wrapped);
    }
    
    static <F, S> EitherV1300<F, S> first(F value)
    {
        return create(null).staticFirst(value);
    }
    @WrapMethod("left")
    <F1, S1> EitherV1300<F1, S1> staticFirst(F1 value);
    
    static <F, S> EitherV1300<F, S> second(S value)
    {
        return create(null).staticSecond(value);
    }
    @WrapMethod("right")
    <F1, S1> EitherV1300<F1, S1> staticSecond(S1 value);
    
    @WrapMethod("left")
    Optional<F> getFirst();
    @WrapMethod("right")
    Optional<S> getSecond();
    
    default boolean isFirst()
    {
        return this.getFirst().isPresent();
    }
    
    default EitherV1300<F, S> ifFirst(Consumer<F> action)
    {
        this.getFirst().ifPresent(action);
        return this;
    }
    default EitherV1300<F, S> ifSecond(Consumer<S> action)
    {
        this.getSecond().ifPresent(action);
        return this;
    }
    
    default <F1, S1> EitherV1300<F1, S1> map(Function<F, F1> first, Function<S, S1> second)
    {
        if(this.isFirst())
            return EitherV1300.first(first.apply(this.getFirst().orElse(null)));
        else
            return EitherV1300.second(second.apply(this.getSecond().orElse(null)));
    }
    
    default <T> T merge(Function<F, T> first, Function<S, T> second)
    {
        if(this.isFirst())
            return first.apply(this.getFirst().orElse(null));
        else
            return second.apply(this.getSecond().orElse(null));
    }
}
