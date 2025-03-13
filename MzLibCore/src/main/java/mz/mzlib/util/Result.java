package mz.mzlib.util;

import java.util.Objects;
import java.util.function.Function;

public abstract class Result<V, E>
{
    protected V value;
    
    protected Result(V value)
    {
        this.value = Objects.requireNonNull(value, "value");
    }
    
    public V getValue()
    {
        return this.value;
    }
    public abstract boolean isSuccess();
    
    public abstract boolean isFailure();
    
    public abstract Option<E> getError();
    
    public abstract Either<V, E> toEither();
    
    public abstract Pair<V, Option<E>> toPair();
    
    public abstract <V1> Result<V1, E> mapValue(Function<? super V,? extends V1> action);
    public abstract <E1> Result<V, E1> mapError(Function<? super E,? extends E1> action);
    
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
    
    static class Success<V, E> extends Result<V, E>
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
        public Pair<V, Option<E>> toPair()
        {
            return new Pair<>(this.value, Option.none());
        }
        @Override
        public <V1> Result<V1, E> mapValue(Function<? super V,? extends V1> action)
        {
            return success(action.apply(this.value));
        }
        @Override
        public <E1> Result<V, E1> mapError(Function<? super E, ? extends E1> action)
        {
            return RuntimeUtil.cast(this);
        }
    }
    
    static class Failure<V, E> extends Result<V, E>
    {
        protected E error;
        protected Failure(V value, E error)
        {
            super(value);
            this.error = Objects.requireNonNull(error, "error");
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
            return Option.some(this.error);
        }
        @Override
        public Either<V, E> toEither()
        {
            return Either.second(this.error);
        }
        @Override
        public Pair<V, Option<E>> toPair()
        {
            return new Pair<>(this.value, Option.some(this.error));
        }
        @Override
        public <V1> Result<V1, E> mapValue(Function<? super V,? extends V1> action)
        {
            return failure(action.apply(this.value), this.error);
        }
        @Override
        public <E1> Result<V, E1> mapError(Function<? super E, ? extends E1> action)
        {
            return failure(this.value, action.apply(this.error));
        }
    }
}

