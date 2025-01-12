package mz.mzlib.minecraft.world;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.world.World"))
public interface AbstractWorld extends WrapperObject
{
    @WrapperCreator
    static AbstractWorld create(Object wrapped)
    {
        return WrapperObject.create(AbstractWorld.class, wrapped);
    }
}
