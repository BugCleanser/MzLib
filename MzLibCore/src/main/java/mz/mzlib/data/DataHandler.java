package mz.mzlib.data;

import mz.mzlib.module.MzModule;
import mz.mzlib.module.Registrable;
import mz.mzlib.util.Editor;
import mz.mzlib.util.ThrowableBiConsumer;
import mz.mzlib.util.ThrowableSupplier;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DataHandler<H, T> implements Registrable
{
    final DataKey<H, T> key;
    Predicate<H> checker;
    Function<H, T> getter;
    BiConsumer<H, T> setter;
    public DataHandler(DataKey<H, T> key, Predicate<H> checker, Function<H, T> getter, BiConsumer<H, T> setter)
    {
        this.key = key;
        this.checker = checker;
        this.getter = getter;
        this.setter = setter;
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

    public static <H, T> DataHandler<H, T> of(
        DataKey<H, T> key,
        Predicate<H> checker,
        Function<H, T> getter,
        BiConsumer<H, T> setter)
    {
        return new DataHandler<>(key, checker, getter, setter);
    }
    public static <H, T, R> Revisable<H, T, R> of(
        DataKey.Revisable<H, T, R> key,
        Predicate<H> checker,
        Function<H, T> getter,
        BiConsumer<H, T> setter,
        Function<T, R> reviserGetter,
        Function<R, T> reviserApplier)
    {
        return new Revisable<>(key, checker, getter, setter, reviserGetter, reviserApplier);
    }
    public static <H, T, R extends Supplier<? extends T>> Revisable<H, T, R> of(
        DataKey.Revisable<H, T, R> key,
        Predicate<H> checker,
        Function<H, T> getter,
        BiConsumer<H, T> setter,
        Function<T, R> reviserGetter)
    {
        return of(key, checker, getter, setter, reviserGetter, Supplier::get);
    }

    public static class Revisable<H, T, R> extends DataHandler<H, T>
    {
        Function<T, R> reviserGetter;
        Function<R, T> reviserApplier;

        public Revisable(
            DataKey.Revisable<H, T, R> key,
            Predicate<H> checker,
            Function<H, T> getter,
            BiConsumer<H, T> setter,
            Function<T, R> reviserGetter,
            Function<R, T> reviserApplier)
        {
            super(key, checker, getter, setter);
            this.reviserGetter = reviserGetter;
            this.reviserApplier = reviserApplier;
        }

        public Editor<R> revise(H holder)
        {
            return Editor.ofReviser(
                ThrowableSupplier.constant(holder).thenApply(this.getter).thenApply(this.reviserGetter),
                this.reviserApplier,
                ThrowableBiConsumer.ofBiConsumer(this.setter).bindFirst(ThrowableSupplier.constant(holder))
            );
        }
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
                this.key.handler = this.key.handlers.get(this.key.handlers.size() - 1);
            else
                this.key.handler = null;
        }
    }

    public static <H, T> Factory<H, T> factory(DataKey<H, T> key)
    {
        return new Factory<>(key);
    }

    public static class Factory<H, T>
    {
        DataKey<H, T> key;
        Predicate<H> checker;
        Function<H, T> getter;
        BiConsumer<H, T> setter;

        public Factory(DataKey<H, T> key)
        {
            this.key = key;
        }
        public Factory(Factory<H, T> other)
        {
            this.key = other.key;
            this.checker = other.checker;
            this.getter = other.getter;
            this.setter = other.setter;
        }

        public Factory<H, T> checker(Predicate<H> checker)
        {
            Factory<H, T> result = new Factory<>(this);
            result.checker = checker;
            return result;
        }
        public Factory<H, T> checker(Class<? extends H> holderClass)
        {
            return this.checker(holderClass::isInstance);
        }
        public Factory<H, T> getter(Function<H, T> getter)
        {
            Factory<H, T> result = new Factory<>(this);
            result.getter = getter;
            return result;
        }
        public Factory<H, T> setter(BiConsumer<H, T> setter)
        {
            Factory<H, T> result = new Factory<>(this);
            result.setter = setter;
            return result;
        }

        public DataHandler<H, T> build()
        {
            if(this.key == null)
                throw new IllegalStateException("Key is not set");
            if(this.checker == null)
                throw new IllegalStateException("Checker is not set");
            if(this.getter == null)
                throw new IllegalStateException("Getter is not set");
            if(this.setter == null)
                throw new IllegalStateException("Setter is not set");
            return new DataHandler<>(this.key, this.checker, this.getter, this.setter);
        }
        public void register(MzModule module)
        {
            module.register(this.build());
        }

        public <R> Revisable<H, T, R> revisable()
        {
            return new Revisable<>(this);
        }

        public static class Revisable<H, T, R>
        {
            Factory<H, T> base;
            Function<T, R> getter;
            Function<R, T> applier;

            public Revisable(Factory<H, T> base)
            {
                if(!(base.key instanceof DataKey.Revisable))
                    throw new IllegalArgumentException("Key is not revisable");
                this.base = base;
            }
            public Revisable(Revisable<H, T, R> other)
            {
                this.base = new Factory<>(other.base);
                this.getter = other.getter;
                this.applier = other.applier;
            }

            public Revisable<H, T, R> getter(Function<T, R> getter)
            {
                Revisable<H, T, R> result = new Revisable<>(this);
                result.getter = getter;
                return result;
            }
            public Revisable<H, T, R> applier(Function<R, T> applier)
            {
                Revisable<H, T, R> result = new Revisable<>(this);
                result.applier = applier;
                return result;
            }

            public DataHandler<H, T> build()
            {
                if(this.getter == null)
                    throw new IllegalStateException("Getter is not set");
                if(this.applier == null)
                    throw new IllegalStateException("Applier is not set");
                return new DataHandler.Revisable<>(
                    (DataKey.Revisable<H, T, R>) this.base.key,
                    this.base.checker,
                    this.base.getter,
                    this.base.setter,
                    this.getter,
                    this.applier
                );
            }
            public void register(MzModule module)
            {
                module.register(this.build());
            }
        }
    }
}
