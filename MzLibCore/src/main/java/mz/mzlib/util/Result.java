package mz.mzlib.util;

public abstract class Result<V, E>
{
    protected V value;
    
    protected Result(V value)
    {
        this.value = value;
    }
    
    public V getValue()
    {
        return this.value;
    }
    public abstract boolean isSuccess();
    
    public abstract boolean isFailure();
    
    public abstract Option<E> getError();
    
    public abstract Either<V, E> toEither();
    
    public abstract <V1, T extends Throwable> Result<V1, E> mapValue(ThrowableFunction<? super V,? extends V1, T> action) throws T;
    public abstract <E1, T extends Throwable> Result<V, E1> mapError(ThrowableFunction<? super E,? extends E1, T> action) throws T;
    
    public static <V, E> Result<V, E> success(V value)
    {
        return new Success<>(value);
    }
    public static <V, E> Result<V, E> failure(V value, E error)
    {
        return new Failure<>(value, error);
    }
    public static <V, E> Result<V, E> of(V value, Option<E> error)
    {
        for(E e: error)
            return failure(value, e);
        return success(value);
    }
}

class Success<V, E> extends Result<V, E>
{
    protected Success(V value)
    {
        super(value);
    }
    @Override
    public boolean isSuccess()
    {
        return true;
    }
    @Override
    public boolean isFailure()
    {
        return false;
    }
    @Override
    public Option<E> getError()
    {
        return Option.none();
    }
    @Override
    public Either<V, E> toEither()
    {
        return Either.first(this.value);
    }
    @Override
    public <V1, T extends Throwable> Result<V1, E> mapValue(ThrowableFunction<? super V,? extends V1, T> action) throws T
    {
        return success(action.applyOrThrow(this.value));
    }
    @Override
    public <E1, T extends Throwable> Result<V, E1> mapError(ThrowableFunction<? super E, ? extends E1, T> action)
    {
        return RuntimeUtil.cast(this);
    }
}

class Failure<V, E> extends Result<V, E>
{
    protected E error;
    protected Failure(V value, E error)
    {
        super(value);
        this.error = error;
    }
    @Override
    public boolean isSuccess()
    {
        return false;
    }
    @Override
    public boolean isFailure()
    {
        return true;
    }
    @Override
    public Option<E> getError()
    {
        return Option.some(error);
    }
    @Override
    public Either<V, E> toEither()
    {
        return Either.second(error);
    }
    @Override
    public <V1, T extends Throwable> Result<V1, E> mapValue(ThrowableFunction<? super V,? extends V1, T> action) throws T
    {
        return failure(action.apply(value), error);
    }
    @Override
    public <E1, T extends Throwable> Result<V, E1> mapError(ThrowableFunction<? super E, ? extends E1, T> action)
    {
        return failure(value, action.apply(error));
    }
}