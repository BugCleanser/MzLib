package mz.mzlib.minecraft.util.math;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.util.math.BlockPos"))
public interface BlockPos extends WrapperObject, Vec3i
{
    WrapperFactory<BlockPos> FACTORY = WrapperFactory.of(BlockPos.class);
    @Deprecated
    @WrapperCreator
    static BlockPos create(Object wrapped)
    {
        return WrapperObject.create(BlockPos.class, wrapped);
    }
    
    @WrapConstructor
    BlockPos staticNewInstance(int x, int y, int z);
    static BlockPos newInstance(int x, int y, int z)
    {
        return create(null).staticNewInstance(x, y, z);
    }
    
    @WrapConstructor
    BlockPos staticNewInstance(Vec3i vec3i);
    static BlockPos newInstance(Vec3i vec3i)
    {
        return create(null).staticNewInstance(vec3i);
    }
}
