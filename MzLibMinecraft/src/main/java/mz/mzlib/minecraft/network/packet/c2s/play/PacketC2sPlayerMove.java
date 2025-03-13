package mz.mzlib.minecraft.network.packet.c2s.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket"))
public interface PacketC2sPlayerMove extends Packet
{
    WrapperFactory<PacketC2sPlayerMove> FACTORY = WrapperFactory.find(PacketC2sPlayerMove.class);
    @Deprecated
    @WrapperCreator
    static PacketC2sPlayerMove create(Object wrapped)
    {
        return WrapperObject.create(PacketC2sPlayerMove.class, wrapped);
    }
    
    @WrapMinecraftInnerClass(outer=PacketC2sPlayerMove.class, name={@VersionName(name="PositionOnly", end=1700), @VersionName(name="PositionAndOnGround", begin=1700)})
    interface LocationAndOnGround extends PacketC2sPlayerMove
    {
        WrapperFactory<LocationAndOnGround> FACTORY = WrapperFactory.find(LocationAndOnGround.class);
        @Deprecated
        @WrapperCreator
        static LocationAndOnGround create(Object wrapped)
        {
            return WrapperObject.create(LocationAndOnGround.class, wrapped);
        }
    }
    
    @WrapMinecraftInnerClass(outer=PacketC2sPlayerMove.class, name={@VersionName(name="LookOnly", end=1700), @VersionName(name="LookAndOnGround", begin=1700)})
    interface LookAndOnGround extends PacketC2sPlayerMove
    {
        WrapperFactory<LookAndOnGround> FACTORY = WrapperFactory.find(LookAndOnGround.class);
        @Deprecated
        @WrapperCreator
        static LookAndOnGround create(Object wrapped)
        {
            return WrapperObject.create(LookAndOnGround.class, wrapped);
        }
    }
    
    @WrapMinecraftInnerClass(outer=PacketC2sPlayerMove.class, name={@VersionName(name="Both", end=1700), @VersionName(name="Full", begin=1700)})
    interface Full extends PacketC2sPlayerMove
    {
        WrapperFactory<Full> FACTORY = WrapperFactory.find(Full.class);
        @Deprecated
        @WrapperCreator
        static Full create(Object wrapped)
        {
            return WrapperObject.create(Full.class, wrapped);
        }
    }
    
    @WrapMinecraftInnerClass(outer=PacketC2sPlayerMove.class, name={@VersionName(name="OnGroundOnly", begin=1700)})
    interface OnGroundOnlyV1700 extends PacketC2sPlayerMove
    {
        WrapperFactory<OnGroundOnlyV1700> FACTORY = WrapperFactory.find(OnGroundOnlyV1700.class);
        @Deprecated
        @WrapperCreator
        static OnGroundOnlyV1700 create(Object wrapped)
        {
            return WrapperObject.create(OnGroundOnlyV1700.class, wrapped);
        }
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
    
    @WrapMinecraftFieldAccessor(@VersionName(name="onGround"))
    boolean isOnGround();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="onGround"))
    void setOnGround(boolean value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="changePosition"))
    boolean isLocationChanged();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="changePosition"))
    void setLocationChanged(boolean value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="changeLook"))
    boolean isLookChanged();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="changeLook"))
    void setLookChanged(boolean value);
    
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
