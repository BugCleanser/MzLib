package mz.mzlib.minecraft.world;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapArrayClass;
import mz.mzlib.util.wrapper.WrapperArray;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.server.world.ServerWorld"))
public interface WorldServer extends WrapperObject, World
{
    WrapperFactory<WorldServer> FACTORY = WrapperFactory.of(WorldServer.class);


    @WrapArrayClass(WorldServer.class)
    interface Array extends WrapperArray<WorldServer>
    {
        WrapperFactory<Array> FACTORY = WrapperFactory.of(Array.class);

        static Array newInstance(int size)
        {
            return (Array) FACTORY.getStatic().static$newInstance(size);
        }
    }
}
