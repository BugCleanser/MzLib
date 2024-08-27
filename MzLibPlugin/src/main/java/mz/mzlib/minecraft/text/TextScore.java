package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.text.ScoreText"))
public interface TextScore extends WrapperObject, AbstractText
{
    @WrapperCreator
    static TextScore create(Object wrapped)
    {
        return WrapperObject.create(TextScore.class, wrapped);
    }

    @WrapConstructor
    TextScore staticNewInstance(String name, String objective);
    static TextScore newInstance(String name, String objective)
    {
        return create(null).staticNewInstance(name, objective);
    }
    static TextScore newInstance(String name, String objective, String value)
    {
        TextScore result = newInstance(name, objective);
        result.setValue(value);
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

    @WrapMinecraftFieldAccessor(@VersionName(name="score"))
    String getValue();
    @WrapMinecraftMethod(@VersionName(name="setScore"))
    void setValue(String value);
}
