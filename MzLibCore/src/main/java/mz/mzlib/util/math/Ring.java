package mz.mzlib.util.math;

public class Ring<T>
{
    protected Group<T> addition;
    protected Semigroup<T> multiplication;

    public Ring(Group<T> addition, Semigroup<T> multiplication)
    {
        this.addition = addition;
        this.multiplication = multiplication;
    }

    public Group<T> getAddition()
    {
        return this.addition;
    }
    public Semigroup<T> getMultiplication()
    {
        return this.multiplication;
    }

    public T zero()
    {
        return this.getAddition().identity();
    }

    public T add(T a, T b)
    {
        return this.getAddition().apply(a, b);
    }
    public T multiply(T a, T b)
    {
        return this.getMultiplication().apply(a, b);
    }

    public T negate(T a)
    {
        return this.getAddition().inverse(a);
    }
    public T subtract(T a, T b)
    {
        return this.add(a, this.negate(b));
    }
}
