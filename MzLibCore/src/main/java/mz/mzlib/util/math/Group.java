package mz.mzlib.util.math;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Group<T> extends Monoid<T>
{
    Group<Boolean> BOOLEAN_XOR = build(false, (a, b)->a^b, Function.identity());
    Group<Byte> BYTE_ADDITION = build((byte)0, (a, b)->(byte)(a+b), b -> (byte)-b);
    Group<Short> SHORT_ADDITION = build((short)0, (a, b)->(short)(a+b), s -> (short)-s);
    Group<Integer> INT_ADDITION = build(0, Integer::sum, i -> -i);
    Group<Long> LONG_ADDITION = build(0L, Long::sum, l -> -l);
    Group<Float> FLOAT_ADDITION = build(0.f, Float::sum, f -> -f);
    Group<Float> FLOAT_MULTIPLICATION = build(1.f, (a, b)->a*b, f -> 1.f/f);
    Group<Double> DOUBLE_ADDITION = build(0., Double::sum, d -> -d);
    Group<Double> DOUBLE_MULTIPLICATION = build(1., (a, b)->a*b, d -> 1.f/d);
    
    T inverse(T element);
    
    static <T> Group<T> build(T identity, BiFunction<T, T, T> action, Function<T, T> inverse)
    {
        return new Group<T>()
        {
            @Override
            public T identity()
            {
                return identity;
            }
            
            @Override
            public T apply(T left, T right)
            {
                return action.apply(left, right);
            }
            
            @Override
            public T inverse(T element)
            {
                return inverse.apply(element);
            }
        };
    }
}
