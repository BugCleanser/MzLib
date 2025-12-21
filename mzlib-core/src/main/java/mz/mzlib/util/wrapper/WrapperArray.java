package mz.mzlib.util.wrapper;

import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.proxy.ListProxy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

@WrapArrayClass(WrapperObject.class)
public interface WrapperArray<T extends WrapperObject> extends WrapperObject
{
    @Override
    Object[] getWrapped();

    WrapperFactory<T> static$getElementFactory();

    default WrapperArray<T> static$newInstance(int length)
    {
        return RuntimeUtil.cast(this.static$create(
            Array.newInstance(this.static$getElementFactory().getStatic().static$getWrappedClass(), length)));
    }

    default T get(int index)
    {
        return this.static$getElementFactory().create(this.getWrapped()[index]);
    }

    default void set(int index, T value)
    {
        this.getWrapped()[index] = value.getWrapped();
    }

    default int length()
    {
        return this.getWrapped().length;
    }

    default List<T> asList()
    {
        return new ListProxy<>(
            Arrays.asList(this.getWrapped()),
            FunctionInvertible.wrapper(this.static$getElementFactory())
        );
    }

    static <T extends WrapperObject, A extends WrapperArray<T>> Collector<T, List<T>, A> collector(WrapperFactory<A> factory)
    {
        return Collector.of(
            ArrayList::new, List::add, (left, right) ->
            {
                left.addAll(right);
                return left;
            }, list ->
            {
                A result = RuntimeUtil.cast(factory.getStatic().static$newInstance(list.size()));
                Collections.copy(result.asList(), list);
                return result;
            }
        );
    }
}
