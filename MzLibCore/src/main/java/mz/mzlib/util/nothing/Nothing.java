package mz.mzlib.util.nothing;

import mz.mzlib.util.wrapper.WrapperObject;

public interface Nothing
{
    static <T extends WrapperObject> T notReturn()
    {
        return null;
    }
}
