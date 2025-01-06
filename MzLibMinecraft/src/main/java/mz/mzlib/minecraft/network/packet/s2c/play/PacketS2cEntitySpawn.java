package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.EntityType;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.UUID;

// TODO
@WrapMinecraftClass(
        {
                @VersionName(name="net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket", end=1500),
                @VersionName(name="net.minecraft.client.network.packet.EntitySpawnS2CPacket", begin=1500, end=1502),
                @VersionName(name="net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket", begin=1502)
        })
public interface PacketS2cEntitySpawn extends Packet
{
    @WrapperCreator
    static PacketS2cEntitySpawn create(Object wrapped)
    {
        return WrapperObject.create(PacketS2cEntitySpawn.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="entityId"))
    int getEntityId();
    @WrapMinecraftFieldAccessor(@VersionName(name="uuid"))
    UUID getUuid();
    @WrapMinecraftFieldAccessor(@VersionName(name="entityType"))
    EntityType getType();
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
    @WrapMinecraftFieldAccessor(@VersionName(name="headYaw"))
    byte getScaledHeadYaw();
    
    default Vec3d getPosition()
    {
        return Vec3d.newInstance(this.getX(), this.getY(), this.getZ());
    }
}
