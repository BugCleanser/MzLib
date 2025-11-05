package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.Either;
import mz.mzlib.util.wrapper.*;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.text.ScoreText", end=1900),
                @VersionName(name = "net.minecraft.text.Text", begin=1900)
        })
public interface TextScore extends WrapperObject, Text
{
    WrapperFactory<TextScore> FACTORY = WrapperFactory.of(TextScore.class);
    
    static TextScore newInstance(String name, String objective)
    {
        return FACTORY.getStatic().static$newInstance(name, objective);
    }
    TextScore static$newInstance(String name, String objective);
    @SpecificImpl("static$newInstance")
    @VersionRange(end=1900)
    @WrapConstructor
    TextScore static$newInstanceV_1900(String name, String objective);
    @SpecificImpl("static$newInstance")
    @VersionRange(begin=1900)
    default TextScore static$newInstanceV1900(String name, String objective)
    {
        return TextMutableV1600.newInstanceV1900(TextContentScoreV1900.newInstance(name, objective)).as(FACTORY);
    }
    
    static TextScore newInstanceV2102(Either<ParsedSelectorV2102, String> name, String objective)
    {
        return TextMutableV1600.newInstanceV1900(TextContentScoreV1900.newInstanceV2102(name, objective)).as(FACTORY);
    }
    
    String getName();
    @SpecificImpl("getName")
    @VersionRange(end=1900)
    @WrapMinecraftFieldAccessor(@VersionName(name="name"))
    String getNameV_1900();
    @SpecificImpl("getName")
    @VersionRange(begin=1900)
    default String getNameV1900()
    {
        return this.getContentV1900().as(TextContentScoreV1900.FACTORY).getName();
    }
    
    default Either<ParsedSelectorV2102, String> getNameV2102()
    {
        return this.getContentV1900().as(TextContentScoreV1900.FACTORY).getNameV2102();
    }
    
    String getObjective();
    @SpecificImpl("getObjective")
    @VersionRange(end=1900)
    @WrapMinecraftFieldAccessor(@VersionName(name="objective"))
    String getObjectiveV_1900();
    @SpecificImpl("getObjective")
    @VersionRange(begin=1900)
    default String getObjectiveV1900()
    {
        return this.getContentV1900().as(TextContentScoreV1900.FACTORY).getObjective();
    }

    @VersionRange(end=1600)
    @WrapMinecraftFieldAccessor(@VersionName(name="score"))
    String getValueV_1600();
    @VersionRange(end=1600)
    @WrapMinecraftFieldAccessor(@VersionName(name="score"))
    void setValueV_1600(String value);
}
