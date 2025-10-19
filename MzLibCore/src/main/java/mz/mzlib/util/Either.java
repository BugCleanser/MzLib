package mz.mzlib.util;

import java.util.function.Function;

public abstract class Either<F, S>
{
    public abstract boolean isFirst();
    public abstract boolean isSecond();
    public abstract Option<F> getFirst();
    public abstract Option<S> getSecond();
    
    public abstract Either<S, F> invert();
    
    public abstract <F1> Either<F1, S> mapFirst(Function<? super F, ? extends F1> action);
    public abstract <S1> Either<F, S1> mapSecond(Function<? super S, ? extends S1> action);
    public abstract <T> T map(Function<? super F, ? extends T> actionFirst, Function<? super S, ? extends T> actionSecond);
    
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
    
    static class First<F, S> extends Either<F, S>
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
        public <F1> Either<F1, S> mapFirst(Function<? super F, ? extends F1> action)
        {
            return first(action.apply(this.value));
        }
        @Override
        public <S1> Either<F, S1> mapSecond(Function<? super S, ? extends S1> action)
        {
            return first(this.value);
        }
        @Override
        public <T> T map(Function<? super F, ? extends T> actionFirst, Function<? super S, ? extends T> actionSecond)
        {
            return actionFirst.apply(this.value);
        }
    }
    
    static class Second<F, S> extends Either<F, S>
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
        public <F1> Either<F1, S> mapFirst(Function<? super F, ? extends F1> action)
        {
            return second(this.value);
        }
        @Override
        public <S1> Either<F, S1> mapSecond(Function<? super S, ? extends S1> action)
        {
            return second(action.apply(this.value));
        }
        @Override
        public <T> T map(Function<? super F, ? extends T> actionFirst, Function<? super S, ? extends T> actionSecond)
        {
            return actionSecond.apply(this.value);
        }
    }
}

