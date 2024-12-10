package mz.mzlib.minecraft.text;

import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Arrays;
import java.util.Objects;

public interface TextTranslatableCommon
{
    /**
     * 得到原始的参数，可空
     * 可能包含未包装的Text或其它对象
     */
    Object[] getArgs0();
    default Text[] getArgs()
    {
        Object[] args0 = this.getArgs0();
        if(args0==null)
            return null;
        return Arrays.stream(args0).map(a -> WrapperObject.create(a).isInstanceOf(Text::create) ? Text.create(a) : Text.literal(Objects.toString(a)) ).toArray(Text[]::new);
    }
}
