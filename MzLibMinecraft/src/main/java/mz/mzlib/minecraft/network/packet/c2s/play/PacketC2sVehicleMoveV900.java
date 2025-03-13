package mz.mzlib.minecraft.network.packet.c2s.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=900)
@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket"))
public interface PacketC2sVehicleMoveV900 extends Packet
{
    WrapperFactory<PacketC2sVehicleMoveV900> FACTORY = WrapperFactory.find(PacketC2sVehicleMoveV900.class);
    @Deprecated
    @WrapperCreator
    static PacketC2sVehicleMoveV900 create(Object wrapped)
    {
        return WrapperObject.create(PacketC2sVehicleMoveV900.class, wrapped);
    }
    
    @VersionRange(end=2104)
    @WrapMinecraftFieldAccessor(@VersionName(name="x"))
    double getXV_2104();
    
    @VersionRange(end=2104)
    @WrapMinecraftFieldAccessor(@VersionName(name="x"))
    void setXV_2104(double value);
    
    @VersionRange(end=2104)
    @WrapMinecraftFieldAccessor(@VersionName(name="y"))
    double getYV_2104();
    
    @VersionRange(end=2104)
    @WrapMinecraftFieldAccessor(@VersionName(name="y"))
    void setYV_2104(double value);
    
    @VersionRange(end=2104)
    @WrapMinecraftFieldAccessor(@VersionName(name="z"))
    double getZV_2104();
    
    @VersionRange(end=2104)
    @WrapMinecraftFieldAccessor(@VersionName(name="z"))
    void setZV_2104(double value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="yaw"))
    float getYaw();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="yaw"))
    void setYaw(float value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="pitch"))
    float getPitch();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="pitch"))
    void setPitch(float value);
    
    default Vec3d getLocationV_2104()
    {
        return Vec3d.newInstance(this.getXV_2104(), this.getYV_2104(), this.getZV_2104());
    }
    
    default void setLocationV_2104(Vec3d value)
    {
        this.setXV_2104(value.getX());
        this.setYV_2104(value.getY());
        this.setZV_2104(value.getZ());
    }
}
