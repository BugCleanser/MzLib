package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.util.EitherV1300;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.Either;
import mz.mzlib.util.Option;
import mz.mzlib.util.Result;
import mz.mzlib.util.wrapper.*;

import java.util.function.Function;

@VersionRange(begin=1900)
@WrapMinecraftClass(@VersionName(name="net.minecraft.text.ScoreTextContent"))
public interface TextContentScoreV1900 extends WrapperObject, TextContentV1900
{
    WrapperFactory<TextContentScoreV1900> FACTORY = WrapperFactory.of(TextContentScoreV1900.class);
    
    static TextContentScoreV1900 newInstance(String name, String objective)
    {
        return FACTORY.getStatic().static$newInstance(name, objective);
    }
    TextContentScoreV1900 static$newInstance(String name, String objective);
    @SpecificImpl("static$newInstance")
    @VersionRange(end=2102)
    @WrapConstructor
    TextContentScoreV1900 static$newInstanceV_2102(String name, String objective);
    @SpecificImpl("static$newInstance")
    @VersionRange(begin=2102)
    default TextContentScoreV1900 static$newInstanceV2102(String name, String objective)
    {
        Result<Option<ParsedSelectorV2102>, String> parse = ParsedSelectorV2102.parse(name);
        return newInstanceV2102(parse.isSuccess() ? Either.first(parse.getValue().unwrap()) : Either.second(name), objective);
    }
    @VersionRange(begin=2102)
    static TextContentScoreV1900 newInstanceV2102(Either<ParsedSelectorV2102, String> name, String objective)
    {
        return FACTORY.getStatic().static$newInstance0V2102(EitherV1300.fromEither(name.mapFirst(ParsedSelectorV2102::getWrapped)), objective);
    }
    @VersionRange(begin=2102)
    @WrapConstructor
    TextContentScoreV1900 static$newInstance0V2102(EitherV1300<?, String> name, String objective);
    
    String getName();
    @SpecificImpl("getName")
    @VersionRange(end=2102)
    @WrapMinecraftFieldAccessor(@VersionName(name="name"))
    String getNameV_2102();
    @SpecificImpl("getName")
    @VersionRange(begin=2102)
    default String getName1V2102()
    {
        return this.getNameV2102().map(ParsedSelectorV2102::getUnparsed, Function.identity());
    }
    @VersionRange(begin=2102)
    default Either<ParsedSelectorV2102, String> getNameV2102()
    {
        return this.getName0V2102().toEither().mapFirst(ParsedSelectorV2102.FACTORY::create);
    }
    @VersionRange(begin=2102)
    @WrapMinecraftFieldAccessor(@VersionName(name="name"))
    EitherV1300<?, String> getName0V2102();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="objective"))
    String getObjective();
}
