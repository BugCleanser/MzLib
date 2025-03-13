package mz.mzlib.minecraft.block.entity;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.block.BlockState;
import mz.mzlib.minecraft.util.math.BlockPos;
import mz.mzlib.minecraft.window.WindowFactory;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.*;

@WrapMinecraftClass(@VersionName(name="net.minecraft.block.entity.ChestBlockEntity"))
public interface BlockEntityChest extends WrapperObject, BlockEntity, WindowFactory
{
    WrapperFactory<BlockEntityChest> FACTORY = WrapperFactory.find(BlockEntityChest.class);
    @Deprecated
    @WrapperCreator
    static BlockEntityChest create(Object wrapped)
    {
        return WrapperObject.create(BlockEntityChest.class, wrapped);
    }
    
    BlockEntityChest staticNewInstance(BlockPos pos, BlockState state);
    @VersionRange(end=1700)
    @WrapConstructor
    BlockEntityChest staticNewInstanceV_1700();
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=1700)
    default BlockEntityChest staticNewInstanceV_1700(BlockPos pos, BlockState state)
    {
        return staticNewInstanceV_1700();
    }
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=1700)
    @WrapConstructor
    BlockEntityChest staticNewInstanceV1700(BlockPos pos, BlockState state);
    static BlockEntityChest newInstance(BlockPos pos, BlockState state)
    {
        return create(null).staticNewInstance(pos, state);
    }
}
