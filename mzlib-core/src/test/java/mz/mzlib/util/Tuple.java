package mz.mzlib.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Just a joke
 */
public class Tuple<P extends Tuple<?, ?>, T>
{
    List<Object> values;

    Tuple(List<Object> values)
    {
        this.values = values;
    }

    public int size()
    {
        return this.values.size();
    }
    public Object get(int index)
    {
        return this.values.get(index);
    }
    public T last()
    {
        return RuntimeUtil.cast(this.values.get(this.values.size() - 1));
    }
    public P prefix()
    {
        return RuntimeUtil.cast(new Tuple<>(this.values.subList(0, this.values.size()-1)));
    }

    public static <P extends Tuple<?, ?>, L1> P sub1(Tuple<P, L1> tuple)
    {
        return tuple.prefix();
    }
    public static <P extends Tuple<?, ?>, L1, L2> P sub2(Tuple<Tuple<P, L1>, L2> tuple)
    {
        return sub1(sub1(tuple));
    }
    public static <P extends Tuple<?, ?>, L1, L2, L3, L4> P sub4(Tuple<Tuple<Tuple<Tuple<P, L1>, L2>, L3>, L4> tuple)
    {
        return sub2(sub2(tuple));
    }

    public static Builder<Tuple<?, ?>> builder()
    {
        return new Builder<>();
    }
    public static class Builder<T extends Tuple<?,?>>
    {
        List<Object> values = new ArrayList<>();
        public <A> Builder<Tuple<T, A>> append(A value)
        {
            values.add(value);
            return RuntimeUtil.cast(this);
        }
        public T build()
        {
            return RuntimeUtil.cast(new Tuple<>(Arrays.asList(this.values.toArray())));
        }
    }
}
