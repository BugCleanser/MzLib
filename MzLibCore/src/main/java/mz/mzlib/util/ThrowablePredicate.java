package mz.mzlib.util;

import java.util.function.Predicate;

public interface ThrowablePredicate<T, E extends Throwable> extends Predicate<T>, ThrowableFunction<T, Boolean, E>
{
    boolean testOrThrow(T arg) throws E;

    @Override
    default boolean test(T t)
    {
        try
        {
            return testOrThrow(t);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    @Override
    default Boolean applyOrThrow(T arg) throws E
    {
        return testOrThrow(arg);
    }

    static <T> ThrowablePredicate<T, RuntimeException> ofPredicate(Predicate<? super T> predicate)
    {
        return predicate::test;
    }
    @Deprecated
    static <T> ThrowablePredicate<T, RuntimeException> of(Predicate<? super T> predicate)
    {
        return ofPredicate(predicate);
    }

    static <T, E extends Throwable> ThrowablePredicate<T, E> of(ThrowablePredicate<? super T, E> predicate)
    {
        return predicate::testOrThrow;
    }

    static <T, E extends Throwable> ThrowablePredicate<T, E> of(ThrowableFunction<? super T, Boolean, E> function)
    {
        return function::applyOrThrow;
    }

    @Override
    default ThrowablePredicate<T, E> negate()
    {
        return ofPredicate(Predicate.super.negate())::testOrThrow;
    }

    @Override
    default ThrowablePredicate<T, E> and(Predicate<? super T> other)
    {
        return ofPredicate(Predicate.super.and(other))::testOrThrow;
    }

    @Override
    default ThrowablePredicate<T, E> or(Predicate<? super T> other)
    {
        return ofPredicate(Predicate.super.or(other))::testOrThrow;
    }
}
