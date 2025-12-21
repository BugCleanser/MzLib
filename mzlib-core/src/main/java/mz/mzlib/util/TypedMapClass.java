package mz.mzlib.util;

import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.compound.DelegateField;
import mz.mzlib.util.wrapper.*;

import java.util.Objects;

public interface TypedMapClass extends TypedMap<TypedMapClass.Key<?>>
{
    static TypedMapClass of(TypedMap<TypedMapClass.Key<?>> delegate)
    {
        return WrapperImpl.of(WrapperTypedMap.FACTORY.create(delegate)).getWrapped();
    }
    static TypedMapClass of()
    {
        return of(TypedMap.of());
    }

    default <T> Option<T> get(Class<T> key)
    {
        return this.getDelegate().get(new Key<>(key));
    }
    default <T> Option<T> put(Class<T> key, T value)
    {
        return this.getDelegate().put(new Key<>(key), value);
    }
    default <T> Option<T> remove(Class<T> key)
    {
        return this.getDelegate().remove(new Key<>(key));
    }
    default <T> T getOr(Class<T> key, T defaultValue)
    {
        return this.getOr(new Key<T>(key), defaultValue);
    }
    default boolean containsKey(Class<?> key)
    {
        return TypedMap.super.containsKey(new Key<>(key));
    }
    default void putAll(TypedMapClass m)
    {
        this.putAll(m.getDelegate());
    }
    default <T> Option<T> putIfAbstract(Class<T> key, T value)
    {
        return this.putIfAbstract(new Key<>(key), value);
    }
    default <T> boolean remove(Class<T> key, T value)
    {
        return this.remove(new Key<>(key), value);
    }
    default <T> boolean replace(Class<T> key, T oldValue, T newValue)
    {
        return this.replace(new Key<>(key), oldValue, newValue);
    }
    default <T> Option<T> replace(Class<T> key, T value)
    {
        return this.replace(new Key<>(key), value);
    }
    default <T, E extends Throwable> T computeIfAbsent(
        Class<T> key,
        ThrowableFunction<? super Class<T>, ? extends T, E> action) throws E
    {
        return this.computeIfAbsent(new Key<>(key), k -> action.applyOrThrow(k.getType()));
    }
    default <T, E extends Throwable> T computeIfAbsent(
        Class<T> key,
        ThrowableSupplier<? extends T, E> action) throws E
    {
        return this.computeIfAbsent(new Key<>(key), action);
    }
    default <T, E extends Throwable> Option<T> computeIfPresent(
        Class<T> key,
        ThrowableBiFunction<? super Class<T>, ? super T, ? extends Option<? extends T>, E> action) throws E
    {
        return this.computeIfPresent(new Key<>(key), (k, v) -> action.applyOrThrow(k.getType(), v));
    }
    default <T, E extends Throwable> Option<T> computeIfPresent(
        Class<T> key,
        ThrowableFunction<? super T, ? extends Option<? extends T>, E> action) throws E
    {
        return this.computeIfPresent(new Key<>(key), action);
    }
    default <T, E extends Throwable> Option<T> compute(
        Class<T> key,
        ThrowableBiFunction<? super Class<T>, ? super T, ? extends Option<? extends T>, E> action) throws E
    {
        return this.compute(new Key<>(key), (k, v) -> action.applyOrThrow(k.getType(), v));
    }
    default <T, E extends Throwable> Option<T> compute(
        Class<T> key,
        ThrowableFunction<? super T, ? extends Option<? extends T>, E> action) throws E
    {
        return this.compute(new Key<>(key), action);
    }

    TypedMap<TypedMapClass.Key<?>> getDelegate();

    class Key<T> implements TypedMap.Key<T, Key<?>>
    {
        Class<T> type;
        public Key(Class<T> type)
        {
            this.type = type;
        }
        public Class<T> getType()
        {
            return this.type;
        }
        @Override
        public int hashCode()
        {
            return this.type.hashCode();
        }
        @Override
        public boolean equals(Object obj)
        {
            if(!(obj instanceof Key))
                return false;
            return Objects.equals(this.type, ((Key<?>) obj).type);
        }
    }
    @WrapClass(TypedMap.class)
    interface WrapperTypedMap extends WrapperObject
    {
        WrapperFactory<WrapperTypedMap> FACTORY = WrapperFactory.of(WrapperTypedMap.class);
    }
    @WrapClass(TypedMapClass.class)
    interface Wrapper extends WrapperTypedMap
    {
        @Override
        TypedMapClass getWrapped();

        /**
         * @see TypedMapClass#getDelegate()
         */
        @WrapMethod("getDelegate")
        WrapperTypedMap getDelegate();
    }
    @Compound
    interface WrapperImpl extends Wrapper
    {
        WrapperFactory<WrapperImpl> FACTORY = WrapperFactory.of(WrapperImpl.class);
        static WrapperImpl of(WrapperTypedMap delegate)
        {
            WrapperImpl result = FACTORY.getStatic().static$of();
            result.setDelegate(delegate);
            return result;
        }
        @DelegateField
        void setDelegate(WrapperTypedMap value);
        @CompoundOverride(parent = Wrapper.class, method = "getDelegate")
        @WrapFieldAccessor("delegate")
        WrapperTypedMap getDelegate();
        @WrapConstructor
        WrapperImpl static$of();
    }
}
