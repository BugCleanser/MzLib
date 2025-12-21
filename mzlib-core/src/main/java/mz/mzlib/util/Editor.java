package mz.mzlib.util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Editor<T> implements AutoCompletable<T, Object>
{
    AutoCompletable<T, Object> delegate;

    Editor(AutoCompletable<T, Object> delegate)
    {
        this.delegate = delegate;
    }

    public <T1> Editor<T1> map(FunctionInvertible<T, T1> action)
    {
        return of(AutoCompletable.of(
            () ->
            {
                Pair<T, Object> data = this.start();
                return Pair.of(action.apply(data.first), data.second);
            }, (e, d) -> this.complete(action.inverse().apply(e), d)
        ));
    }

    public <T1, D1> Editor<T1> then(Function<? super T, ? extends AutoCompletable<T1, D1>> action)
    {
        return of(AutoCompletable.of(
            () ->
            {
                Pair<T, Object> data = this.start();
                AutoCompletable<T1, D1> c = action.apply(data.getFirst());
                Pair<T1, D1> data1 = c.start();
                return Pair.of(data1.getFirst(), new ProxyData<>(data, c, data1.getSecond()));
            }, (e, d) ->
            {
                d.next.complete(e, d.data1);
                this.complete(d.data0.getFirst(), d.data0.getSecond());
            }
        ));
    }
    static class ProxyData<T, T1, D1>
    {
        Pair<T, Object> data0;
        AutoCompletable<T1, D1> next;
        D1 data1;
        ProxyData(Pair<T, Object> data0, AutoCompletable<T1, D1> then, D1 data1)
        {
            this.data0 = data0;
            this.next = then;
            this.data1 = data1;
        }
    }

    @Override
    public Pair<T, Object> start()
    {
        return this.delegate.start();
    }
    @Override
    public void complete(T element, Object data)
    {
        this.delegate.complete(element, data);
    }

    public static <T> Editor<T> of(AutoCompletable<T, ?> delegate)
    {
        return new Editor<>(RuntimeUtil.cast(delegate));
    }
    public static <T> Editor<T> of(Supplier<? extends T> getter, Consumer<? super T> setter)
    {
        return of(AutoCompletable.of(getter, setter));
    }

    public static <T, R> Editor<R> ofReviser(Supplier<R> reviserGetter, Function<R, T> reviserApplier, Consumer<? super T> setter)
    {
        return Editor.of(reviserGetter, ThrowableFunction.ofFunction(reviserApplier).thenAccept(setter));
    }
    public static <T, R extends Supplier<T>> Editor<R> ofReviser(Supplier<R> reviserGetter, Consumer<? super T> setter)
    {
        return ofReviser(reviserGetter, Supplier::get, setter);
    }

    public static <T> Editor<T> ofClone(
        Supplier<? extends T> getter,
        Function<? super T, ? extends T> cloner,
        Consumer<? super T> setter)
    {
        return of(ThrowableSupplier.ofSupplier(getter).thenApply(cloner), setter);
    }
    public static <T, H> Editor<T> of(
        H holder,
        Function<? super H, ? extends T> getter,
        BiConsumer<? super H, ? super T> setter)
    {
        return of(
            ThrowableSupplier.constant(holder).thenApply(getter),
            ThrowableBiConsumer.ofBiConsumer(setter).bindFirst(ThrowableSupplier.constant(holder))
        );
    }

    public static <T> Editor<Ref<T>> ofRef(Supplier<? extends T> getter, Consumer<? super T> setter)
    {
        return Editor.<T>of(getter, setter).map(FunctionInvertible.ref());
    }
    public static <T, H> Editor<Ref<T>> ofRef(H holder, Function<H, T> getter, BiConsumer<H, T> setter)
    {
        return Editor.of(holder, getter, setter).map(FunctionInvertible.ref());
    }
}
