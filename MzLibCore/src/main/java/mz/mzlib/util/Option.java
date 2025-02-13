package mz.mzlib.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public abstract class Option<T> implements Iterable<T>
{
    public static <T> Option<T> some(T value)
    {
        return new Some<>(value);
    }
    public static <T> Option<T> none()
    {
        return RuntimeUtil.cast(None.instance);
    }
    
    public static <T> Option<T> fromNullable(T value)
    {
        if(value==null)
            return none();
        else
            return some(value);
    }
    
    public abstract T toNullable();
    
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T> Option<T> fromOptional(Optional<T> optional)
    {
        return fromNullable(optional.orElse(null));
    }
    
    public abstract Optional<T> toOptional();
    
    public abstract boolean isSome();
    
    public abstract boolean isNone();
    
    public abstract T unwrap();
    
    public abstract T unwrapOr(T defaultValue);
    
    public abstract <U> Option<U> and(Option<U> other);
    
    public abstract Option<T> or(Option<T> other);
    
    public abstract <U, E extends Throwable> Option<U> map(ThrowableFunction<T, U, E> mapper) throws E;
    
    static class Some<T> extends Option<T>
    {
        T value;
        public Some(T value)
        {
            this.value = Objects.requireNonNull(value);
        }
        
        @Override
        public boolean isSome()
        {
            return true;
        }
        @Override
        public boolean isNone()
        {
            return false;
        }
        @Override
        public T toNullable()
        {
            return this.unwrap();
        }
        
        @Override
        public Optional<T> toOptional()
        {
            return Optional.of(this.unwrap());
        }
        
        
        @Override
        public T unwrap()
        {
            return this.value;
        }
        public T unwrapOr(T defaultValue)
        {
            return this.unwrap();
        }
        
        @Override
        public <U> Option<U> and(Option<U> other)
        {
            return other;
        }
        
        @Override
        public Option<T> or(Option<T> other)
        {
            return this;
        }
        
        @Override
        public <U, E extends Throwable> Option<U> map(ThrowableFunction<T, U, E> mapper) throws E
        {
            return fromNullable(mapper.applyOrThrow(this.value));
        }
        
        @Override
        public Iterator<T> iterator()
        {
            return new Itr();
        }
        
        class Itr implements Iterator<T>
        {
            boolean hasNext = true;
            @Override
            public boolean hasNext()
            {
                return this.hasNext;
            }
            @Override
            public T next()
            {
                if(!this.hasNext())
                    throw new NoSuchElementException();
                this.hasNext = false;
                return Some.this.value;
            }
        }
    }
    
    static class None<T> extends Option<T>
    {
        static None<?> instance = new None<>();
        
        @Override
        public boolean isSome()
        {
            return false;
        }
        @Override
        public boolean isNone()
        {
            return true;
        }
        @Override
        public T toNullable()
        {
            return null;
        }
        @Override
        public Optional<T> toOptional()
        {
            return Optional.empty();
        }
        
        @Override
        public T unwrap()
        {
            throw new NoSuchElementException();
        }
        
        @Override
        public T unwrapOr(T defaultValue)
        {
            return defaultValue;
        }
        
        @Override
        public <U> Option<U> and(Option<U> other)
        {
            return none();
        }
        
        @Override
        public Option<T> or(Option<T> other)
        {
            return other;
        }
        
        @Override
        public <U, E extends Throwable> Option<U> map(ThrowableFunction<T, U, E> mapper) throws E
        {
            return none();
        }
        
        @Override
        public Iterator<T> iterator()
        {
            return new Itr();
        }
        
        class Itr implements Iterator<T>
        {
            @Override
            public boolean hasNext()
            {
                return false;
            }
            @Override
            public T next()
            {
                throw new NoSuchElementException();
            }
        }
    }
}
