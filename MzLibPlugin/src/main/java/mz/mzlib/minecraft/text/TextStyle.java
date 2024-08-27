package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
        {
                // TODO
                @VersionName(name = "net.minecraft.text.Style", end=1400)
        })
public interface TextStyle extends WrapperObject
{
    @WrapperCreator
    static TextStyle create(Object wrapped)
    {
        return WrapperObject.create(TextStyle.class, wrapped);
    }

    @WrapConstructor
    TextStyle staticNewInstance();
    static TextStyle newInstance()
    {
        return create(null).staticNewInstance();
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "bold"))
    Boolean getBold();

    @WrapMinecraftFieldAccessor(@VersionName(name = "bold"))
    void setBold(Boolean value);
}
