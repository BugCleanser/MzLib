package mz.mzlib.data;

import mz.mzlib.module.MzModule;
import mz.mzlib.module.Registrable;
import mz.mzlib.util.*;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class DataHandler<H, T, R> implements Registrable
{
    final DataKey<H, T, ? super R> key;
    Predicate<H> checker;
    Function<H, T> getter;
    BiConsumer<H, T> setter;
    Function<T, R> reviserGetter;
    Function<R, T> reviserApplier;
    public DataHandler(DataKey<H, T, ? super R> key, Predicate<H> checker, Function<H, T> getter, BiConsumer<H, T> setter, Function<T, R> reviserGetter, Function<R, T> reviserApplier)
    {
        this.key = key;
        this.checker = checker;
        this.getter = getter;
        this.setter = setter;
        this.reviserGetter = reviserGetter;
        this.reviserApplier = reviserApplier;
    }

    public boolean check(H holder)
    {
        return this.checker.test(holder);
    }
    public T get(H holder)
    {
        return this.getter.apply(holder);
    }
    public void set(H holder, T value)
    {
        this.setter.accept(holder, value);
    }
    public Editor<R> revise(H holder)
    {
        return Editor.ofReviser(
                ThrowableSupplier.constant(holder).thenApply(this.getter).thenApply(this.reviserGetter),
                this.reviserApplier,
                ThrowableBiConsumer.ofBiConsumer(this.setter).bindFirst(ThrowableSupplier.constant(holder))
        );
    }

    public static <H, T, R> DataHandler<H, T, R> of(
        DataKey<H, T, ? super R> key,
        Predicate<H> checker,
        Function<H, T> getter,
        BiConsumer<H, T> setter,
        Function<T, R> reviserGetter,
        Function<R, T> reviserApplier)
    {
        return new DataHandler<>(key, checker, getter, setter, reviserGetter, reviserApplier);
    }

    @Override
    public void onRegister(MzModule module)
    {
        synchronized(this.key)
        {
            this.key.handlers.add(this);
            this.key.handler = this;
        }
    }
    @Override
    public void onUnregister(MzModule module)
    {
        synchronized(this.key)
        {
            this.key.handlers.remove(this);
            if(!this.key.handlers.isEmpty())
                this.key.handler = RuntimeUtil.cast(this.key.handlers.get(this.key.handlers.size() - 1));
            else
                this.key.handler = null;
        }
    }

    public static <H, T, R> Factory<H, T, R> factory(DataKey<H, T, ? super R> key)
    {
        return new Factory<>(key);
    }

    public static class Factory<H, T, R>
    {
        DataKey<H, T, ? super R> key;
        Predicate<H> checker;
        Function<H, T> getter;
        BiConsumer<H, T> setter;
        Function<T, R> reviserGetter;
        Function<R, T> reviserApplier;

        public Factory(DataKey<H, T, ? super R> key)
        {
            this.key = key;
            this.checker = ThrowablePredicate.always();
            this.reviserGetter = x -> RuntimeUtil.valueThrow(new UnsupportedOperationException());
            this.reviserApplier = x -> null;
        }
        public Factory(Factory<H, T, R> other)
        {
            this.key = other.key;
            this.checker = other.checker;
            this.getter = other.getter;
            this.setter = other.setter;
            this.reviserGetter = other.reviserGetter;
            this.reviserApplier = other.reviserApplier;
        }

        public Factory<H, T, R> checker(Predicate<H> checker)
        {
            Factory<H, T, R> result = new Factory<>(this);
            result.checker = checker;
            return result;
        }
        public Factory<H, T, R> getter(Function<H, T> getter)
        {
            Factory<H, T, R> result = new Factory<>(this);
            result.getter = getter;
            return result;
        }
        public Factory<H, T, R> setter(BiConsumer<H, T> setter)
        {
            Factory<H, T, R> result = new Factory<>(this);
            result.setter = setter;
            return result;
        }
        public Factory<H, T, R> reviserGetter(Function<T, R> value)
        {
            Factory<H, T, R> result = new Factory<>(this);
            result.reviserGetter = value;
            return result;
        }
        public Factory<H, T, R> reviserApplier(Function<R, T> value)
        {
            Factory<H, T, R> result = new Factory<>(this);
            result.reviserApplier = value;
            return result;
        }

        public DataHandler<H, T, R> build()
        {
            if(this.key == null)
                throw new IllegalStateException("Key is not set");
            if(this.checker == null)
                throw new IllegalStateException("Checker is not set");
            if(this.getter == null)
                throw new IllegalStateException("Getter is not set");
            if(this.setter == null)
                throw new IllegalStateException("Setter is not set");
            return new DataHandler<>(this.key, this.checker, this.getter, this.setter, this.reviserGetter, this.reviserApplier);
        }
        public void register(MzModule module)
        {
            module.register(this.build());
        }
    }
}
