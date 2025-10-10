package mz.mzlib.minecraft.block.entity;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.block.entity.BlockEntity"))
public interface BlockEntity extends WrapperObject
{
    WrapperFactory<BlockEntity> FACTORY = WrapperFactory.of(BlockEntity.class);
    @Deprecated
    @WrapperCreator
    static BlockEntity create(Object wrapped)
    {
        return WrapperObject.create(BlockEntity.class, wrapped);
    }
}
