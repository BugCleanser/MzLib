package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapSameClass(TextScore.class)
@VersionRange(end=1900)
public interface TextScoreV_1900 extends TextScore, AbstractTextV_1900
{
    WrapperFactory<TextScoreV_1900> FACTORY = WrapperFactory.of(TextScoreV_1900.class);
    @Deprecated
    @WrapperCreator
    static TextScoreV_1900 create(Object wrapped)
    {
        return WrapperObject.create(TextScoreV_1900.class, wrapped);
    }
}
