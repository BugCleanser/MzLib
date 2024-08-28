package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(end=1900)
@WrapSameClass(TextScore.class)
public interface TextScoreV_1900 extends TextScore, AbstractTextV_1900
{
    @WrapperCreator
    static TextScoreV_1900 create(Object wrapped)
    {
        return WrapperObject.create(TextScoreV_1900.class, wrapped);
    }
}
