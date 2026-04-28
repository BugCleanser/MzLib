package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@ApiStatus.NonExtendable
public abstract class Either<F extends @Nullable Object, S extends @Nullable Object> implements Invertible<Either<S, F>>
{
    public abstract boolean isFirst();
    public abstract boolean isSecond();
    public abstract Option<F> getFirst();
    public abstract Option<S> getSecond();

    @Override
    public abstract Either<S, F> inverse();

    public abstract <F1> Either<F1, S> mapFirst(Function<? super F, ? extends F1> action);
    public abstract <S1> Either<F, S1> mapSecond(Function<? super S, ? extends S1> action);
    public abstract <T> T map(
        Function<? super F, ? extends T> actionFirst,
        Function<? super S, ? extends T> actionSecond);

    public static <F extends @Nullable Object, S extends @Nullable Object> Either<F, S> first(F value)
    {
        return new First<>(value);
    }
    public static <F extends @Nullable Object, S extends @Nullable Object> Either<F, S> second(S value)
    {
        return new Second<>(value);
    }

    public static <F extends @Nullable Object, S extends @Nullable Object> Either<F, S> fromNullable(F first, S second)
    {
        if((first != null) == (second != null))
            throw new IllegalArgumentException(Pair.of(first, second).toString());
        if(first != null)
            return first(first);
        else
            return second(second);
    }

    @ApiStatus.NonExtendable
    public static class First<F extends @Nullable Object, S extends @Nullable Object> extends Either<F, S>
    {
        protected F value;
        public First(F value)
        {
            this.value = value;
        }
        public F get()
        {
            return this.value;
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
            return Option.some(this.get());
        }
        public Option<S> getSecond()
        {
            return Option.none();
        }

        @Override
        public Either<S, F> inverse()
        {
            return second(this.get());
        }
        @Override
        public <F1> Either<F1, S> mapFirst(Function<? super F, ? extends F1> action)
        {
            return first(action.apply(this.get()));
        }
        @Override
        public <S1> Either<F, S1> mapSecond(Function<? super S, ? extends S1> action)
        {
            return first(this.value);
        }
        @Override
        public <T> T map(Function<? super F, ? extends T> actionFirst, Function<? super S, ? extends T> actionSecond)
        {
            return actionFirst.apply(this.get());
        }
    }

    @ApiStatus.NonExtendable
    public static class Second<F extends @Nullable Object, S extends @Nullable Object> extends Either<F, S>
    {
        protected S value;
        public Second(S value)
        {
            this.value = value;
        }
        public S get()
        {
            return this.value;
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
            return Option.some(this.get());
        }

        @Override
        public Either<S, F> inverse()
        {
            return first(this.get());
        }
        @Override
        public <F1> Either<F1, S> mapFirst(Function<? super F, ? extends F1> action)
        {
            return second(this.value);
        }
        @Override
        public <S1> Either<F, S1> mapSecond(Function<? super S, ? extends S1> action)
        {
            return second(action.apply(this.get()));
        }
        @Override
        public <T> T map(Function<? super F, ? extends T> actionFirst, Function<? super S, ? extends T> actionSecond)
        {
            return actionSecond.apply(this.get());
        }
    }
}

