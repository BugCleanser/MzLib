package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public class Unit implements Comparable<Unit>
{
    public static final Unit INSTANCE = new Unit();

    private Unit()
    {
    }

    @Override
    public int hashCode()
    {
        return 0;
    }
    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Unit;
    }

    @Override
    public String toString()
    {
        return "()";
    }

    @Override
    public int compareTo(Unit o)
    {
        return 0;
    }
}
