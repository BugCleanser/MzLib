package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapSameClass(TextSelector.class)
@VersionRange(end=1900)
public interface TextSelectorV_1900 extends WrapperObject, TextSelector, AbstractTextV_1900
{
    @WrapperCreator
    static TextSelectorV_1900 create(Object wrapped)
    {
        return WrapperObject.create(TextSelectorV_1900.class, wrapped);
    }
}
