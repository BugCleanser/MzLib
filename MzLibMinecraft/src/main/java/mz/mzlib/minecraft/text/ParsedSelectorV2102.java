package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.serialization.DataResultV1600;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.Result;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=2102)
@WrapMinecraftClass(@VersionName(name="net.minecraft.text.ParsedSelector"))
public interface ParsedSelectorV2102 extends WrapperObject
{
    WrapperFactory<ParsedSelectorV2102> FACTORY = WrapperFactory.of(ParsedSelectorV2102.class);
    
    @WrapMinecraftMethod(@VersionName(name="comp_3067"))
    String getUnparsed();
    
    static Result<Option<ParsedSelectorV2102>, String> parse(String selector)
    {
        return new DataResultV1600.Wrapper<>(FACTORY.getStatic().staticParse0(selector), FACTORY).toResult();
    }
    @WrapMinecraftMethod(@VersionName(name="parse"))
    DataResultV1600<?> staticParse0(String selector);
}
