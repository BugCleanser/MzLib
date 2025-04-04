package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.text.ScoreText", end=1400),
                @VersionName(name = "net.minecraft.network.chat.ScoreComponent", begin=1400, end=1403),
                @VersionName(name = "net.minecraft.text.ScoreText", begin=1403, end=1900),
                @VersionName(name = "net.minecraft.text.ScoreTextContent", begin=1900)
        })
public interface TextScore extends WrapperObject
{
    WrapperFactory<TextScore> FACTORY = WrapperFactory.find(TextScore.class);
    @Deprecated
    @WrapperCreator
    static TextScore create(Object wrapped)
    {
        return WrapperObject.create(TextScore.class, wrapped);
    }
    
    @VersionRange(end=2102)
    @WrapConstructor
    TextScore staticNewInstanceV_2102(String name, String objective);
    static TextScore newInstanceV_2102(String name, String objective)
    {
        return create(null).staticNewInstanceV_2102(name, objective);
    }
    static TextScore newInstanceV_1600(String name, String objective, String value)
    {
        TextScore result = newInstanceV_2102(name, objective);
        result.setValueV_1600(value);
        return result;
    }

    @WrapMinecraftFieldAccessor(@VersionName(name="name"))
    String getName();
    @WrapMinecraftFieldAccessor(@VersionName(name="name"))
    void setName(String value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "objective"))
    String getObjective();
    @WrapMinecraftFieldAccessor(@VersionName(name="objective"))
    void setObjective(String value);

    @WrapMinecraftFieldAccessor(
            {
                    @VersionName(name = "score", end = 1400),
                    @VersionName(name = "text", begin = 1400, end = 1403),
                    @VersionName(name = "score", begin = 1403, end=1600)
            })
    String getValueV_1600();
    @WrapMinecraftFieldAccessor(
            {
                    @VersionName(name = "score", end = 1400),
                    @VersionName(name = "text", begin = 1400, end = 1403),
                    @VersionName(name = "score", begin = 1403, end=1600)
            })
    void setValueV_1600(String value);
}
