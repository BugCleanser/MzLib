package mz.mzlib.minecraft.util.math;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.util.math.Vec3i"))
public interface Vec3i extends WrapperObject
{
    WrapperFactory<Vec3i> FACTORY = WrapperFactory.of(Vec3i.class);
    @Deprecated
    @WrapperCreator
    static Vec3i create(Object wrapped)
    {
        return WrapperObject.create(Vec3i.class, wrapped);
    }

    @WrapConstructor
    BlockPos static$newInstance(int x, int y, int z);
    static BlockPos newInstance(int x, int y, int z)
    {
        return FACTORY.getStatic().static$newInstance(x, y, z);
    }
}
