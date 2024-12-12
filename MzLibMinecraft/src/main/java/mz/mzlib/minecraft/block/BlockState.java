package mz.mzlib.minecraft.block;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.block.BlockState"))
public interface BlockState extends WrapperObject
{
    @WrapperCreator
    static BlockState create(Object wrapped)
    {
        return WrapperObject.create(BlockState.class, wrapped);
    }
}