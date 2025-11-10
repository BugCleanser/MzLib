package mz.mzlib.minecraft.world;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.server.world.ServerWorld"))
public interface WorldServer extends WrapperObject, World
{
    WrapperFactory<WorldServer> FACTORY = WrapperFactory.of(WorldServer.class);
    @Deprecated
    @WrapperCreator
    static WorldServer create(Object wrapped)
    {
        return WrapperObject.create(WorldServer.class, wrapped);
    }
}
