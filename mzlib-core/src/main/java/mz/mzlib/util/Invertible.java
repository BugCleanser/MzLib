package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public interface Invertible<U extends Invertible<? extends Invertible<U>>>
{
    U inverse();

    @ApiStatus.Experimental
    abstract class Abstract<U extends Abstract<? extends Invertible<U>>> implements Invertible<U>
    {
        @ApiStatus.Internal
        protected @Nullable U inverse;

        @ApiStatus.OverrideOnly
        protected abstract U invert();
        @Override
        public U inverse()
        {
            if(this.inverse != null)
                return this.inverse;
            this.inverse = this.invert();
            this.inverse.inverse = RuntimeUtil.cast(this);
            return this.inverse;
        }
    }
}
