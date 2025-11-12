package mz.mzlib.minecraft.data;

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
        return of(key, checker, getter, setter, reviserGetter, R::get);
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
}
