package mz.mzlib.util.nothing;

import mz.mzlib.util.Box;
import mz.mzlib.util.wrapper.WrapperObject;
import org.jetbrains.annotations.Nullable;

public interface Nothing
{
    static <T extends @Nullable Object> Box<T> proceed()
    {
        //noinspection DataFlowIssue
        return null;
    }
    
    @Deprecated
    static <T extends WrapperObject> @Nullable T notReturn()
    {
        return null;
    }

    @Deprecated
    static boolean isReturn(@Nullable WrapperObject result)
    {
        return result != null;
    }
    
    /**
     * @see NothingInjectLocating#followingReturn()
     */
    @Deprecated
    default void locateAllReturn(NothingInjectLocating locating)
    {
        locating.followingReturn();
    }
}
