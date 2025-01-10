package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.EntityType;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.UUID;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket"))
public interface PacketS2cEntitySpawn extends Packet
{
    @WrapperCreator
    static PacketS2cEntitySpawn create(Object wrapped)
    {
        return WrapperObject.create(PacketS2cEntitySpawn.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="id", end=2100), @VersionName(name="entityId", begin=2100)})
    int getEntityId();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="uuid"))
    UUID getUuid();
    
    @WrapMinecraftFieldAccessor({@VersionName(name="entityTypeId", end=1903), @VersionName(name="entityType", begin=1903)})
    EntityType getEntityType();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="x"))
    double getX();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="y"))
    double getY();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="z"))
    double getZ();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="yaw"))
    byte getScaledYaw();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="pitch"))
    byte getScaledPitch();
    
    @VersionRange(begin=1900)
    @WrapMinecraftFieldAccessor(@VersionName(name="headYaw"))
    byte getScaledHeadYawV1900();
    
    default Vec3d getPosition()
    {
        return Vec3d.newInstance(this.getX(), this.getY(), this.getZ());
    }
}
