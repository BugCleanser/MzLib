package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

@ApiStatus.NonExtendable
public class Pair<T0 extends @Nullable Object, T1 extends @Nullable Object>
{
    protected T0 first;
    protected T1 second;

    public Pair(T0 first, T1 second)
    {
        this.first = first;
        this.second = second;
    }
    public static <T0 extends @Nullable Object, T1 extends @Nullable Object> Pair<T0, T1> of(T0 first, T1 second)
    {
        return new Pair<>(first, second);
    }

    public T0 getFirst()
    {
        return this.first;
    }
    public T1 getSecond()
    {
        return this.second;
    }

    public Map.Entry<T0, T1> toMapEntry()
    {
        return new AbstractMap.SimpleEntry<>(first, second);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.first, this.second);
    }
    @Override
    public boolean equals(Object o)
    {
        return o instanceof Pair && Objects.equals(first, ((Pair<?, ?>) o).first) &&
            Objects.equals(second, ((Pair<?, ?>) o).second);
    }
    @Override
    public String toString()
    {
        return "("+this.getFirst()+", "+this.getSecond()+")";
    }

    public static <T0 extends @Nullable Object, T1 extends @Nullable Object> Comparator<Pair<T0, T1>> comparing(Comparator<T0> comparatorFirst, Comparator<T1> comparatorSecond)
    {
        return (a, b) ->
        {
            int result = comparatorFirst.compare(a.first, b.first);
            if(result != 0)
                return result;
            return comparatorSecond.compare(a.second, b.second);
        };
    }
    public static <T0 extends @Nullable Object, T1 extends @Nullable Object> Comparator<Pair<T0, T1>> comparingByFirst(Comparator<T0> comparator)
    {
        return Comparator.comparing(Pair::getFirst, comparator);
    }
    public static <T0 extends @Nullable Object, T1 extends @Nullable Object> Comparator<Pair<T0, T1>> comparingBySecond(Comparator<T1> comparator)
    {
        return Comparator.comparing(Pair::getSecond, comparator);
    }
    public static <T0 extends Comparable<? super T0>, T1 extends Comparable<? super T1>> Comparator<Pair<T0, T1>> comparing()
    {
        return comparing(T0::compareTo, T1::compareTo);
    }
    public static <T0 extends Comparable<? super T0>, T1> Comparator<Pair<T0, T1>> comparingByFirst()
    {
        return comparingByFirst(T0::compareTo);
    }
    public static <T0, T1 extends Comparable<? super T1>> Comparator<Pair<T0, T1>> comparingBySecond()
    {
        return comparingBySecond(T1::compareTo);
    }

    @ApiStatus.NonExtendable
    public static class Mut<T0 extends @Nullable Object, T1 extends @Nullable Object> extends Pair<T0, T1>
    {
        public Mut(T0 first, T1 second)
        {
            super(first, second);
        }
        public static <T0 extends @Nullable Object, T1 extends @Nullable Object> Mut<T0, T1> of(T0 first, T1 second)
        {
            return new Mut<>(first, second);
        }

        public void setFirst(T0 value)
        {
            this.first = value;
        }
        public void setSecond(T1 value)
        {
            this.second = value;
        }
    }
}
