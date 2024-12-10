package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapSameClass(TextSelector.class)
@VersionRange(begin=1900)
public interface TextContentSelectorV1900 extends WrapperObject, TextSelector, TextContentV1900
{
    @WrapperCreator
    static TextContentSelectorV1900 create(Object wrapped)
    {
        return WrapperObject.create(TextContentSelectorV1900.class, wrapped);
    }
}
