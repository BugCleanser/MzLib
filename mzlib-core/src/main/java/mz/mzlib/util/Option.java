package mz.mzlib.util;

import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

@ApiStatus.NonExtendable
public abstract class Option<T extends @Nullable Object> implements Iterable<T>
{
    public static <T extends @Nullable Object> Option<T> some(T value)
    {
        return new Some<>(value);
    }
    public static <T extends @Nullable Object> Option<T> none()
    {
        return RuntimeUtil.cast(None.INSTANCE);
    }

    public static <T> Option<T> fromNullable(@Nullable T value)
    {
        return value != null ? some(RuntimeUtil.cast(value)) : none();
    }
    public abstract @Nullable T toNullable();

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T> Option<T> fromOptional(Optional<T> optional)
    {
        return optional.map(Option::some).orElseGet(Option::none);
    }
    public static <T> Optional<T> toOptional(Option<T> option)
    {
        //noinspection RedundantTypeArguments
        return option.<Optional<T>>map(Optional::of).unwrapOrGet(Optional::empty);
    }

    public static <T extends WrapperObject> Option<T> fromWrapper(T wrapper)
    {
        if(wrapper.isPresent())
            return some(wrapper);
        else
            return none();
    }

    public Either<T, @Nullable Void> toEither()
    {
        return this.mapNullable(Either::<T, Void>first).unwrapOrGet(() -> Either.<T, @Nullable Void>second(null));
    }

    public abstract boolean isSome();
    public abstract boolean isNone();
    public abstract boolean isSome(Object value);

    public T unwrap() throws NoSuchElementException
    {
        return this.unwrap(NoSuchElementException::new);
    }
    public abstract <E extends Throwable> T unwrap(Supplier<? extends E> supplier) throws E;

    public T unwrapOr(T defaultValue)
    {
        return this.unwrapOrGet(ThrowableSupplier.constant(defaultValue));
    }
    public <E extends Throwable> T unwrapOrGet(ThrowableSupplier<? extends T, E> supplier) throws E
    {
        T result = this.toNullable();
        if(result == null)
            result = supplier.getOrThrow();
        return result;
    }
    public T unwrapOrGet(Supplier<? extends T> supplier)
    {
        return this.unwrapOrGet(ThrowableSupplier.ofSupplier(supplier));
    }

    public <U> Option<U> and(Option<U> other)
    {
        if(this.isNone())
            return none();
        else
            return other;
    }
    public Option<T> or(Option<T> other)
    {
        if(this.isSome())
            return this;
        else
            return other;
    }

    public abstract <U extends @Nullable Object> Option<U> flatMap(Function<? super T, ? extends Option<U>> mapper);
    public <U extends @Nullable Object> Option<U> map(Function<? super T, ? extends U> mapper)
    {
        return this.flatMap(mapper.andThen(Option::some));
    }
    public <U> Option<U> mapNullable(Function<? super T, ? extends @Nullable U> mapper)
    {
        return this.flatMap(mapper.andThen(Option::fromNullable));
    }

    public abstract Option<T> filter(Predicate<? super T> predicate);
    public <U> Option<U> filter(Class<U> type)
    {
        return this.filter(type::isInstance).mapNullable(type::cast);
    }
    public <U extends WrapperObject> Option<U> filter(WrapperFactory<U> type)
    {
        return this.filter(WrapperObject.class).filter(type::isInstance).mapNullable(type::cast);
    }

    public Stream<T> stream()
    {
        //noinspection RedundantTypeArguments
        return this.<Stream<T>>map(Stream::of).unwrapOrGet(Stream::empty);
    }

    @Override
    public Iterator<T> iterator()
    {
        //noinspection RedundantTypeArguments
        return this.<Set<T>>map(Collections::singleton).<Iterator<T>>map(Set::iterator).unwrapOrGet(Collections::emptyIterator);
    }

    @Override
    public abstract int hashCode();
    @Override
    public abstract boolean equals(Object obj);
    @Override
    public abstract String toString();

    @ApiStatus.NonExtendable
    public static class Some<T extends @Nullable Object> extends Option<T>
    {
        private final T value;
        private Some(T value)
        {
            this.value = value;
        }

        public T get()
        {
            return this.value;
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
        public boolean isSome(Object value)
        {
            return Objects.equals(this.get(), value);
        }

        @Override
        public T toNullable()
        {
            return this.get();
        }

        @Override
        public <E extends Throwable> T unwrap(Supplier<? extends E> supplier) throws E
        {
            return this.get();
        }
        public <U extends @Nullable Object> Option<U> flatMap(Function<? super T, ? extends Option<U>> mapper)
        {
            return mapper.apply(this.get());
        }
        @Override
        public Option<T> filter(Predicate<? super T> predicate)
        {
            if(predicate.test(this.get()))
                return this;
            else
                return none();
        }

        @Override
        public int hashCode()
        {
            return Objects.hashCode(this.value);
        }
        @Override
        public boolean equals(Object obj)
        {
            if(this == obj)
                return true;
            if(!(obj instanceof Some))
                return false;
            Some<?> that = (Some<?>) obj;
            return Objects.equals(this.get(), that.get());
        }
        @Override
        public String toString()
        {
            return "Some(" + this.get() + ")";
        }
    }

    @ApiStatus.NonExtendable
    public static class None<T extends @Nullable Object> extends Option<T>
    {
        private static final None<?> INSTANCE = new None<>();
        private None()
        {
        }

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
        public boolean isSome(Object value)
        {
            return false;
        }

        @Override
        public @Nullable T toNullable()
        {
            return null;
        }

        @Override
        public <E extends Throwable> T unwrap(Supplier<? extends E> supplier) throws E
        {
            throw supplier.get();
        }
        public <U extends @Nullable Object> Option<U> flatMap(Function<? super T, ? extends Option<U>> mapper)
        {
            return none();
        }
        @Override
        public Option<T> filter(Predicate<? super T> predicate)
        {
            return this;
        }

        @Override
        public int hashCode()
        {
            return 0;
        }

        @Override
        public boolean equals(Object obj)
        {
            if(this == obj)
                return true;
            return obj instanceof None;
        }

        @Override
        public String toString()
        {
            return "None";
        }
    }
}