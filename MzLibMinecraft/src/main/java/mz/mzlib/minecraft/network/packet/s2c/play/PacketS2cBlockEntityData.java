package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.block.entity.BlockEntityTypeV1300;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.util.math.BlockPos;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.*;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket"))
public interface PacketS2cBlockEntityData extends Packet
{
    WrapperFactory<PacketS2cBlockEntityData> FACTORY = WrapperFactory.find(PacketS2cBlockEntityData.class);
    @Deprecated
    @WrapperCreator
    static PacketS2cBlockEntityData create(Object wrapped)
    {
        return WrapperObject.create(PacketS2cBlockEntityData.class, wrapped);
    }
    
    static PacketS2cBlockEntityData newInstance(BlockPos pos, BlockEntityTypeV1300 type, NbtCompound nbt)
    {
        return create(null).staticNewInstance(pos, type, nbt);
    }
    
    PacketS2cBlockEntityData staticNewInstance(BlockPos pos, BlockEntityTypeV1300 type, NbtCompound data);
    
    @VersionRange(end=1800)
    @WrapConstructor
    PacketS2cBlockEntityData staticNewInstanceV_1800(BlockPos pos, int typeId, NbtCompound data);
    
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=1800)
    default PacketS2cBlockEntityData staticNewInstanceV_1800(BlockPos pos, BlockEntityTypeV1300 type, NbtCompound data)
    {
        // TODO: cast type to id
        throw new UnsupportedOperationException();
    }
    
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=1800)
    @WrapConstructor
    PacketS2cBlockEntityData staticNewInstanceV1800(BlockPos pos, BlockEntityTypeV1300 type, NbtCompound data);
}
