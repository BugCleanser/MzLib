package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

@ApiStatus.NonExtendable
public class Pair<T1 extends @Nullable Object, T2 extends @Nullable Object>
{
    protected T1 first;
    protected T2 second;

    public Pair(T1 first, T2 second)
    {
        this.first = first;
        this.second = second;
    }
    public static <T1 extends @Nullable Object, T2 extends @Nullable Object> Pair<T1, T2> of(T1 first, T2 second)
    {
        return new Pair<>(first, second);
    }

    public T1 getFirst()
    {
        return this.first;
    }
    public T2 getSecond()
    {
        return this.second;
    }

    public Map.Entry<T1, T2> toMapEntry()
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

    public static <T1 extends @Nullable Object, T2 extends @Nullable Object> Comparator<Pair<T1, T2>> comparing(Comparator<T1> comparatorFirst, Comparator<T2> comparatorSecond)
    {
        return (a, b) ->
        {
            int result = comparatorFirst.compare(a.first, b.first);
            if(result != 0)
                return result;
            return comparatorSecond.compare(a.second, b.second);
        };
    }
    public static <T1 extends @Nullable Object, T2 extends @Nullable Object> Comparator<Pair<T1, T2>> comparingByFirst(Comparator<T1> comparator)
    {
        return Comparator.comparing(Pair::getFirst, comparator);
    }
    public static <T1 extends @Nullable Object, T2 extends @Nullable Object> Comparator<Pair<T1, T2>> comparingBySecond(Comparator<T2> comparator)
    {
        return Comparator.comparing(Pair::getSecond, comparator);
    }
    public static <T1 extends Comparable<? super T1>, T2 extends Comparable<? super T2>> Comparator<Pair<T1, T2>> comparing()
    {
        return comparing(T1::compareTo, T2::compareTo);
    }
    public static <T1 extends Comparable<? super T1>, T2> Comparator<Pair<T1, T2>> comparingByFirst()
    {
        return comparingByFirst(T1::compareTo);
    }
    public static <T1, T2 extends Comparable<? super T2>> Comparator<Pair<T1, T2>> comparingBySecond()
    {
        return comparingBySecond(T2::compareTo);
    }

    @ApiStatus.NonExtendable
    public static class Mut<T1 extends @Nullable Object, T2 extends @Nullable Object> extends Pair<T1, T2>
    {
        public Mut(T1 first, T2 second)
        {
            super(first, second);
        }
        public static <T1 extends @Nullable Object, T2 extends @Nullable Object> Mut<T1, T2> of(T1 first, T2 second)
        {
            return new Mut<>(first, second);
        }

        public void setFirst(T1 value)
        {
            this.first = value;
        }
        public void setSecond(T2 value)
        {
            this.second = value;
        }
    }
}
