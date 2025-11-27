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

    TypedMap<TypedMapClass.Key<?>> getDelegate();

    class Key<T> implements TypedMap.Key<T, Key<?>>
    {
        Class<T> type;
        public Key(Class<T> type)
        {
            this.type = type;
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
        @CompoundOverride(parent=Wrapper.class, method="getDelegate")
        @WrapFieldAccessor("delegate")
        WrapperTypedMap getDelegate();
        @WrapConstructor
        WrapperImpl static$of();
    }
}
