package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2109)
@WrapSameClass(Text.class)
public interface TextObjectV2109 extends WrapperObject, Text
{
    WrapperFactory<TextObjectV2109> FACTORY = WrapperFactory.of(TextObjectV2109.class);
    
    // TODO
}
