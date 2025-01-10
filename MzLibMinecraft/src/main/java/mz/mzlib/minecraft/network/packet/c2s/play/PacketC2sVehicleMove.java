package mz.mzlib.minecraft.network.packet.c2s.play;

import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket"))
public interface PacketC2sVehicleMove extends Packet
{
    @WrapperCreator
    static PacketC2sVehicleMove create(Object wrapped)
    {
        return WrapperObject.create(PacketC2sVehicleMove.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="x"))
    double getX();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="x"))
    void setX(double value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="y"))
    double getY();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="y"))
    void setY(double value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="z"))
    double getZ();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="z"))
    void setZ(double value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="yaw"))
    float getYaw();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="yaw"))
    void setYaw(float value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="pitch"))
    float getPitch();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="pitch"))
    void setPitch(float value);
    
    default Vec3d getLocation()
    {
        return Vec3d.newInstance(this.getX(), this.getY(), this.getZ());
    }
    
    default void setLocation(Vec3d value)
    {
        this.setX(value.getX());
        this.setY(value.getY());
        this.setZ(value.getZ());
    }
}
