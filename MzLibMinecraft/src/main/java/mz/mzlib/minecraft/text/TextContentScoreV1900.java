package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapSameClass(TextScore.class)
@VersionRange(begin=1900)
public interface TextContentScoreV1900 extends WrapperObject, TextScore, TextContentV1900
{
    WrapperFactory<TextContentScoreV1900> FACTORY = WrapperFactory.find(TextContentScoreV1900.class);
    @Deprecated
    @WrapperCreator
    static TextContentScoreV1900 create(Object wrapped)
    {
        return WrapperObject.create(TextContentScoreV1900.class, wrapped);
    }
}
