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
    WrapperFactory<BlockEntityChest> FACTORY = WrapperFactory.of(BlockEntityChest.class);
    @Deprecated
    @WrapperCreator
    static BlockEntityChest create(Object wrapped)
    {
        return WrapperObject.create(BlockEntityChest.class, wrapped);
    }
    
    BlockEntityChest static$newInstance(BlockPos pos, BlockState state);
    @VersionRange(end=1700)
    @WrapConstructor
    BlockEntityChest static$newInstanceV_1700();
    @SpecificImpl("static$newInstance")
    @VersionRange(end=1700)
    default BlockEntityChest static$newInstanceV_1700(BlockPos pos, BlockState state)
    {
        return static$newInstanceV_1700();
    }
    @SpecificImpl("static$newInstance")
    @VersionRange(begin=1700)
    @WrapConstructor
    BlockEntityChest static$newInstanceV1700(BlockPos pos, BlockState state);
    static BlockEntityChest newInstance(BlockPos pos, BlockState state)
    {
        return FACTORY.getStatic().static$newInstance(pos, state);
    }
}
