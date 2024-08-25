package mz.mzlib.minecraft.packet.c2s.play;

import mz.mzlib.minecraft.Vector;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftChildClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket", end = 1400),
                @VersionName(name = "net.minecraft.server.network.packet.PlayerMoveC2SPacket", begin = 1400, end = 1502),
                @VersionName(name = "net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket", begin = 1502)
        })
public interface PacketPlayerMoveC2S extends Packet
{
    @WrapperCreator
    static PacketPlayerMoveC2S create(Object wrapped)
    {
        return WrapperObject.create(PacketPlayerMoveC2S.class, wrapped);
    }

    @WrapMinecraftChildClass(wrapperSupper = PacketPlayerMoveC2S.class,name=
            {
                    @VersionName(name = "PositionOnly", end=1700),
                    @VersionName(name = "PositionAndOnGround", begin = 1700)
            })
    interface LocationAndOnGround extends PacketPlayerMoveC2S
    {
    }
    @WrapMinecraftChildClass(wrapperSupper = PacketPlayerMoveC2S.class,name=
            {
                    @VersionName(name = "LookOnly", end=1700),
                    @VersionName(name = "LookAndOnGround", begin = 1700)
            })
    interface LookAndOnGround extends PacketPlayerMoveC2S
    {
    }
    @WrapMinecraftChildClass(wrapperSupper = PacketPlayerMoveC2S.class,name=
            {
                    @VersionName(name = "Both", end=1700),
                    @VersionName(name = "Full", begin = 1700)
            })
    interface Full extends PacketPlayerMoveC2S
    {
    }
    @WrapMinecraftChildClass(wrapperSupper = PacketPlayerMoveC2S.class,name=
            {
                    @VersionName(name = "OnGroundOnly", begin = 1700)
            })
    interface OnGroundOnlyV1700 extends PacketPlayerMoveC2S
    {
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

    @WrapMinecraftFieldAccessor(@VersionName(name = "onGround"))
    boolean isOnGround();

    @WrapMinecraftFieldAccessor(@VersionName(name = "onGround"))
    void setOnGround(boolean value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "changePosition"))
    boolean isLocationChanged();

    @WrapMinecraftFieldAccessor(@VersionName(name = "changePosition"))
    void setLocationChanged(boolean value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "changeLook"))
    boolean isLookChanged();

    @WrapMinecraftFieldAccessor(@VersionName(name = "changeLook"))
    void setLookChanged(boolean value);

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
