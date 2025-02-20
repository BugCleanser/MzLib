package mz.mzlib.util.math;

public class RingUnital<T> extends Ring<T>
{
    public static final RingUnital<Boolean> BOOLEAN = new RingUnital<>(Group.BOOLEAN_XOR, Monoid.BOOLEAN_AND);
    public static final RingUnital<Byte> BYTE = new RingUnital<>(Group.BYTE_ADDITION, Monoid.BYTE_MULTIPLICATION);
    public static final RingUnital<Short> SHORT = new RingUnital<>(Group.SHORT_ADDITION, Monoid.SHORT_MULTIPLICATION);
    public static final RingUnital<Integer> INT = new RingUnital<>(Group.INT_ADDITION, Monoid.INT_MULTIPLICATION);
    public static final RingUnital<Long> LONG = new RingUnital<>(Group.LONG_ADDITION, Monoid.LONG_MULTIPLICATION);
    
    public RingUnital(Group<T> addition, Monoid<T> multiplication)
    {
        super(addition, multiplication);
    }
    
    @Override
    public Monoid<T> getMultiplication()
    {
        return (Monoid<T>)super.getMultiplication();
    }
    
    public T unity()
    {
        return this.getMultiplication().identity();
    }
}
