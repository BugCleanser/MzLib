package mz.mzlib.minecraft.world;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.server.world.ServerWorld"))
public interface World extends WrapperObject, AbstractWorld
{
    @WrapperCreator
    static World create(Object wrapped)
    {
        return WrapperObject.create(World.class, wrapped);
    }
}
