package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

@ApiStatus.NonExtendable
public abstract class Result<V extends @Nullable Object, E extends @Nullable Object>
{
    public abstract boolean isSuccess();
    public abstract boolean isFailure();

    public abstract boolean isSuccess(V value);
    public abstract boolean isFailure(E error);

    public abstract Option<V> getPossibleValue();
    public abstract Option<E> getPossibleError();

    public abstract @Nullable V getValue();
    public abstract @Nullable E getError();

    public abstract Either<V, E> toEither();
    public Pair<@Nullable V, @Nullable E> toPair()
    {
        return Pair.of(this.getValue(), this.getError());
    }

    public abstract <V1 extends @Nullable Object> Result<V1, E> mapValue(Function<? super V, ? extends V1> action);
    public abstract <E1 extends @Nullable Object> Result<V, E1> mapError(Function<? super E, ? extends E1> action);

    public <T extends Throwable> V getOrThrow(Function<? super E, ? extends T> exceptionSupplier) throws T
    {
        for(E error : Option.fromNullable(this.getError()))
        {
            throw exceptionSupplier.apply(error);
        }
        return this.getValue();
    }

    public static <V extends @Nullable Object, E extends @Nullable Object> Result<V, E> success(V value)
    {
        return new Success<>(value);
    }
    public static <V extends @Nullable Object, E extends @Nullable Object> Result<V, E> failure(Option<V> value, E error)
    {
        return new Failure<>(value, error);
    }
    public static <V extends @Nullable Object, E extends @Nullable Object> Result<V, E> failure(E error)
    {
        return failure(Option.none(), error);
    }
    public static <V extends @Nullable Object, E extends @Nullable Object> Result<V, E> of(Option<V> value, Option<E> error)
    {
        if(value.isNone() && error.isNone())
            throw new IllegalArgumentException();
        for(E e : error)
        {
            return failure(value, e);
        }
        return success(value.unwrap());
    }

    @ApiStatus.NonExtendable
    public static class Success<V extends @Nullable Object, E extends @Nullable Object> extends Result<V, E>
    {
        protected V value;
        public Success(V value)
        {
            this.value = value;
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
        public boolean isSuccess(V value)
        {
            return Objects.equals(this.value, value);
        }
        @Override
        public boolean isFailure(E error)
        {
            return false;
        }

        @Override
        public Option<V> getPossibleValue()
        {
            return Option.some(this.value);
        }
        @Override
        public Option<E> getPossibleError()
        {
            return Option.none();
        }

        @Override
        public V getValue()
        {
            return this.value;
        }
        @Override
        public @Nullable E getError()
        {
            return null;
        }

        @Override
        public Either<V, E> toEither()
        {
            return Either.first(this.value);
        }

        @Override
        public <V1 extends @Nullable Object> Result<V1, E> mapValue(Function<? super V, ? extends V1> action)
        {
            return success(action.apply(this.value));
        }
        @Override
        public <E1> Result<V, E1> mapError(Function<? super E, ? extends E1> action)
        {
            return RuntimeUtil.cast(this);
        }

        @Override
        public String toString()
        {
            return "Success(" + this.getValue() + ")";
        }
    }

    @ApiStatus.NonExtendable
    public static class Failure<V extends @Nullable Object, E extends @Nullable Object> extends Result<V, E>
    {
        protected Option<V> value;
        protected E error;
        protected Failure(Option<V> value, E error)
        {
            this.value = value;
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
        public boolean isSuccess(V value)
        {
            return false;
        }
        @Override
        public boolean isFailure(E error)
        {
            return Objects.equals(this.error, error);
        }

        @Override
        public Option<V> getPossibleValue()
        {
            return this.value;
        }
        @Override
        public Option<E> getPossibleError()
        {
            return Option.some(this.error);
        }

        @Override
        public @Nullable V getValue()
        {
            return this.getPossibleValue().toNullable();
        }
        @Override
        public E getError()
        {
            return this.error;
        }

        @Override
        public Either<V, E> toEither()
        {
            return Either.second(this.error);
        }

        @Override
        public <V1 extends @Nullable Object> Result<V1, E> mapValue(Function<? super V, ? extends V1> action)
        {
            for(V v : this.getPossibleValue())
            {
                return failure(Option.some(action.apply(v)), this.error);
            }
            return RuntimeUtil.cast(this);
        }
        @Override
        public <E1> Result<V, E1> mapError(Function<? super E, ? extends E1> action)
        {
            return failure(this.value, action.apply(this.error));
        }

        @Override
        public String toString()
        {
            return "Failure(" + this.getValue() + "," + this.getError() + " )";
        }
    }
}

