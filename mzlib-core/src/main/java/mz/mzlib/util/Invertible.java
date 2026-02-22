package mz.mzlib.util;

import org.jetbrains.annotations.Nullable;

public abstract class Invertible<U extends Invertible<? extends Invertible<U>>>
{
    protected @Nullable U inverse;

    protected abstract U invert();
    public U inverse()
    {
        if(this.inverse != null)
            return this.inverse;
        this.inverse = this.invert();
        this.inverse.inverse = RuntimeUtil.cast(this);
        return this.inverse;
    }
}
