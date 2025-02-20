package mz.mzlib.util.math;

import java.util.function.BiFunction;

public interface Monoid<T> extends Semigroup<T>
{
    Monoid<Boolean> BOOLEAN_AND = build(true, (a, b)->a && b);
    Monoid<Boolean> BOOLEAN_OR = build(false, (a, b)->a || b);
    Monoid<Byte> BYTE_MULTIPLICATION = build((byte)0, (a, b)->(byte)(a*b));
    Monoid<Short> SHORT_MULTIPLICATION = build((short)0, (a, b)->(short)(a*b));
    Monoid<Integer> INT_MULTIPLICATION = build(0, (a, b)->a*b);
    Monoid<Long> LONG_MULTIPLICATION = build(0L, (a, b)->a*b);
    
    T identity();
    
    static <T> Monoid<T> build(T identity, BiFunction<T, T, T> operation)
    {
        return new Monoid<T>()
        {
            @Override
            public T identity()
            {
                return identity;
            }
            @Override
            public T apply(T left, T right)
            {
                return operation.apply(left, right);
            }
        };
    }
}
