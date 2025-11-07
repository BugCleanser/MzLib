package mz.mzlib.util.math;

public class RingDivision<T> extends RingUnital<T>
{
    public static final RingDivision<Float> FLOAT = new RingDivision<>(
        Group.FLOAT_ADDITION, Group.FLOAT_MULTIPLICATION);
    public static final RingDivision<Double> DOUBLE = new RingDivision<>(
        Group.DOUBLE_ADDITION, Group.DOUBLE_MULTIPLICATION);

    public RingDivision(Group<T> addition, Group<T> multiplication)
    {
        super(addition, multiplication);
    }

    @Override
    public Group<T> getMultiplication()
    {
        return (Group<T>) super.getMultiplication();
    }

    public T inverse(T element)
    {
        return this.getMultiplication().inverse(element);
    }

    public T divide(T dividend, T divisor)
    {
        return this.multiply(dividend, this.inverse(divisor));
    }
}
