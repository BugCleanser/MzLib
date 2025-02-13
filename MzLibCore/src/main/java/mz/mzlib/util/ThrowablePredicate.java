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
    
    static <T, E extends Throwable> ThrowablePredicate<T, E> of(ThrowablePredicate<T, E> predicate)
    {
        return predicate;
    }
    
    static <T, E extends Throwable> ThrowablePredicate<T, E> of(Predicate<T> predicate)
    {
        return predicate::test;
    }
    
    static <T, E extends Throwable> ThrowablePredicate<T, E> of(ThrowableFunction<T, Boolean, E> function)
    {
        return function::applyOrThrow;
    }
    
    @Override
    default ThrowablePredicate<T, E> negate()
    {
        return of(Predicate.super.negate());
    }
    
    @Override
    default ThrowablePredicate<T, E> and(Predicate<? super T> other)
    {
        return of(Predicate.super.and(other));
    }
    
    @Override
    default ThrowablePredicate<T, E> or(Predicate<? super T> other)
    {
        return of(Predicate.super.or(other));
    }
}
