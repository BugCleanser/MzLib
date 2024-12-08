package mz.mzlib.minecraft.block.entity;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.block.entity.BlockEntity"))
public interface BlockEntity extends WrapperObject
{
    @WrapperCreator
    static BlockEntity create(Object wrapped)
    {
        return WrapperObject.create(BlockEntity.class, wrapped);
    }
    
    
}
