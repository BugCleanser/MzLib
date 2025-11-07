package mz.mzlib.minecraft.world;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.world.World"))
public interface AbstractWorld extends WrapperObject
{
    WrapperFactory<AbstractWorld> FACTORY = WrapperFactory.of(AbstractWorld.class);
    @Deprecated
    @WrapperCreator
    static AbstractWorld create(Object wrapped)
    {
        return WrapperObject.create(AbstractWorld.class, wrapped);
    }
}
