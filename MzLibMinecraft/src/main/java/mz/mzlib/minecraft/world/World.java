package mz.mzlib.minecraft.world;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.server.world.ServerWorld"))
public interface World extends WrapperObject, AbstractWorld
{
    WrapperFactory<World> FACTORY = WrapperFactory.find(World.class);
    @Deprecated
    @WrapperCreator
    static World create(Object wrapped)
    {
        return WrapperObject.create(World.class, wrapped);
    }
}
