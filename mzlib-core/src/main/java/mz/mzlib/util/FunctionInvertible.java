package mz.mzlib.util;

import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class FunctionInvertible<T extends @Nullable Object, U extends @Nullable Object> implements Invertible<FunctionInvertible<U, T>>, Function<T, U>
{
    protected Function<? super T, ? extends U> forward;
    protected Function<? super U, ? extends T> backward;
    public FunctionInvertible(Function<? super T, ? extends U> forward, Function<? super U, ? extends T> backward)
    {
        this.forward = forward;
        this.backward = backward;
    }

    public static <T extends @Nullable Object, U extends @Nullable Object> FunctionInvertible<T, U> of(
        Function<? super T, ? extends U> forward,
        Function<? super U, ? extends T> backward)
    {
        return new FunctionInvertible<>(forward, backward);
    }

    @Override
    public FunctionInvertible<U, T> inverse()
    {
        return of(this.backward, this.forward);
    }

    @Override
    public U apply(T t)
    {
        return this.forward.apply(t);
    }

    public <V> FunctionInvertible<T, V> thenApply(FunctionInvertible<U, V> after)
    {
        return of(this.andThen(after), after.inverse().andThen(this.inverse()));
    }
    public <V> FunctionInvertible<T, V> thenApply(
        Function<? super U, ? extends V> after,
        Function<? super V, ? extends U> afterInverse)
    {
        return this.thenApply(of(after, afterInverse));
    }

    public static <T, U> FunctionInvertible<T, U> cast()
    {
        return of(RuntimeUtil::cast, RuntimeUtil::cast);
    }

    public <V> FunctionInvertible<T, V> thenCast()
    {
        return this.thenApply(FunctionInvertible.cast());
    }

    public static <T> FunctionInvertible<T, Ref<T>> ref()
    {
        return of(RefStrong::new, Ref::get);
    }

    public static <T> FunctionInvertible<@Nullable T, Option<T>> option()
    {
        return of(Option::fromNullable, Option::toNullable);
    }
    public static <T, R> FunctionInvertible<Option<T>, Option<R>> optionMap(FunctionInvertible<T, R> mapper)
    {
        return of(it -> it.mapNullable(mapper), it -> it.mapNullable(mapper.inverse()));
    }
    public static <T extends WrapperObject> FunctionInvertible<T, Option<T>> wrapperOption(WrapperFactory<T> type)
    {
        return of(Option::fromWrapper, it -> it.unwrapOrGet(() -> type.create(null)));
    }

    public static <T extends @Nullable Object> FunctionInvertible<T, Optional<T>> optional()
    {
        return of(Optional::ofNullable, RuntimeUtil::orNull);
    }
    public static <T> FunctionInvertible<Option<T>, Optional<T>> option2optional()
    {
        return of(Option::toOptional, Option::fromOptional);
    }

    public static <T extends WrapperObject> FunctionInvertible<Object, T> wrapper(WrapperFactory<T> factory)
    {
        return of(factory::create, WrapperObject::getWrapped);
    }
    @Deprecated
    public static <T extends WrapperObject> FunctionInvertible<Object, T> wrapper(Function<Object, T> creator)
    {
        return of(creator, WrapperObject::getWrapped);
    }

    public static <T extends WrapperObject, U extends WrapperObject> FunctionInvertible<T, U> wrapperCast(
        WrapperFactory<T> factory,
        WrapperFactory<U> factory1)
    {
        return wrapper(factory).inverse().thenApply(wrapper(factory1));
    }
    @Deprecated
    public static <T extends WrapperObject, U extends WrapperObject> FunctionInvertible<T, U> wrapperCast(
        Function<Object, T> creator,
        Function<Object, U> creator1)
    {
        return wrapper(creator).inverse().thenApply(wrapper(creator1));
    }

    public static <T> FunctionInvertible<T, T> identity()
    {
        return of(ThrowableFunction.identity(), ThrowableFunction.identity());
    }
}
