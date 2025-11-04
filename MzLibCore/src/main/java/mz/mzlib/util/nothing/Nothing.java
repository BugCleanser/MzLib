package mz.mzlib.util.nothing;

import mz.mzlib.asm.Opcodes;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Arrays;

@WrapSameClass(WrapperObject.class)
public interface Nothing
{
    static <T extends WrapperObject> T notReturn()
    {
        return null;
    }
    
    default void locateAllReturn(NothingInjectLocating locating)
    {
        locating.allLater(i->Arrays.asList(Opcodes.IRETURN, Opcodes.LRETURN, Opcodes.FRETURN, Opcodes.DRETURN, Opcodes.ARETURN, Opcodes.RETURN).contains(locating.insns[i].getOpcode()));
        assert !locating.locations.isEmpty();
    }
}
