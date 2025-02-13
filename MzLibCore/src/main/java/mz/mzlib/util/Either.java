package mz.mzlib.util;

public abstract class Either<F, S>
{
    public abstract boolean isFirst();
    public abstract boolean isSecond();
    public abstract Option<F> getFirst();
    public abstract Option<S> getSecond();
    
    public abstract Either<S, F> invert();
    
    public abstract <E extends Throwable> Either<F, S> mapFirst(ThrowableFunction<? super F, ? extends F, E> action) throws E;
    public abstract <E extends Throwable> Either<F, S> mapSecond(ThrowableFunction<? super S, ? extends S, E> action) throws E;
    public abstract <T, E extends Throwable> T map(ThrowableFunction<? super F, ? extends T, E> actionFirst, ThrowableFunction<? super S, ? extends T, E> actionSecond) throws E;
    
    public static <F, S> Either<F, S> first(F value)
    {
        return new First<>(value);
    }
    public static <F, S> Either<F, S> second(S value)
    {
        return new Second<>(value);
    }
    
    public static <F, S> Either<F, S> fromNullable(F first, S second)
    {
        assert Boolean.logicalXor(first != null, second != null);
        if (first != null)
            return first(first);
        else
            return second(second);
    }
}

class First<F, S> extends Either<F, S>
{
    protected F value;
    public First(F value)
    {
        this.value = value;
    }
    public boolean isFirst()
    {
        return true;
    }
    public boolean isSecond()
    {
        return false;
    }
    public Option<F> getFirst()
    {
        return Option.some(this.value);
    }
    public Option<S> getSecond()
    {
        return Option.none();
    }
    @Override
    public Either<S, F> invert()
    {
        return second(this.value);
    }
    @Override
    public <E extends Throwable> Either<F, S> mapFirst(ThrowableFunction<? super F, ? extends F, E> action) throws E
    {
        return first(action.applyOrThrow(this.value));
    }
    @Override
    public <E extends Throwable> Either<F, S> mapSecond(ThrowableFunction<? super S, ? extends S, E> action) throws E
    {
        return this;
    }
    @Override
    public <T, E extends Throwable> T map(ThrowableFunction<? super F, ? extends T, E> actionFirst, ThrowableFunction<? super S, ? extends T, E> actionSecond) throws E
    {
        return actionFirst.applyOrThrow(this.value);
    }
}

class Second<F, S> extends Either<F, S>
{
    protected S value;
    public Second(S value)
    {
        this.value = value;
    }
    public boolean isFirst()
    {
        return false;
    }
    public boolean isSecond()
    {
        return true;
    }
    public Option<F> getFirst()
    {
        return Option.none();
    }
    public Option<S> getSecond()
    {
        return Option.some(value);
    }
    @Override
    public Either<S, F> invert()
    {
        return new First<>(value);
    }
    @Override
    public <E extends Throwable> Either<F, S> mapFirst(ThrowableFunction<? super F, ? extends F, E> action) throws E
    {
        return this;
    }
    @Override
    public <E extends Throwable> Either<F, S> mapSecond(ThrowableFunction<? super S, ? extends S, E> action) throws E
    {
        return second(action.applyOrThrow(this.value));
    }
    @Override
    public <T, E extends Throwable> T map(ThrowableFunction<? super F, ? extends T, E> actionFirst, ThrowableFunction<? super S, ? extends T, E> actionSecond) throws E
    {
        return actionSecond.applyOrThrow(this.value);
    }
}
