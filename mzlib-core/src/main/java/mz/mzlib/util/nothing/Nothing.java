package mz.mzlib.util.nothing;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Arrays;

public interface Nothing
{
    static <T extends WrapperObject> T notReturn()
    {
        return null;
    }

    static boolean isReturn(WrapperObject result)
    {
        return result!=null;
    }

    default void locateAllReturn(NothingInjectLocating locating)
    {
        locating.allLater(
            i -> Arrays.asList(
                Opcodes.IRETURN, Opcodes.LRETURN, Opcodes.FRETURN, Opcodes.DRETURN, Opcodes.ARETURN,
                Opcodes.RETURN
            ).contains(locating.insns[i].getOpcode()));
        if(locating.locations.isEmpty())
            throw new IllegalStateException();
    }
}
