package mz.mzlib.minecraft.packet.c2s.play;

import mz.mzlib.minecraft.Vector;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket", end = 1400),
                @VersionName(name = "net.minecraft.server.network.packet.VehicleMoveC2SPacket", begin = 1400, end = 1502),
                @VersionName(name = "net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket", begin = 1502)
        })
public interface PacketVehicleMoveC2S extends Packet
{
    @WrapperCreator
    static PacketVehicleMoveC2S create(Object wrapped)
    {
        return WrapperObject.create(PacketVehicleMoveC2S.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "x"))
    double getX();

    @WrapMinecraftFieldAccessor(@VersionName(name = "x"))
    void setX(double value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "y"))
    double getY();

    @WrapMinecraftFieldAccessor(@VersionName(name = "y"))
    void setY(double value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "z"))
    double getZ();

    @WrapMinecraftFieldAccessor(@VersionName(name = "z"))
    void setZ(double value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "yaw"))
    float getYaw();

    @WrapMinecraftFieldAccessor(@VersionName(name = "yaw"))
    void setYaw(float value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "pitch"))
    float getPitch();

    @WrapMinecraftFieldAccessor(@VersionName(name = "pitch"))
    void setPitch(float value);

    default Vector getLocation()
    {
        return Vector.newInstance(this.getX(), this.getY(), this.getZ());
    }
    default void setLocation(Vector value)
    {
        this.setX(value.getX());
        this.setY(value.getY());
        this.setZ(value.getZ());
    }
}
