package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.Vector;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.packet.PacketEvent;
import mz.mzlib.minecraft.packet.PacketListener;
import mz.mzlib.minecraft.packet.c2s.play.PacketPlayerMoveC2S;
import mz.mzlib.minecraft.packet.c2s.play.PacketVehicleMoveC2S;
import mz.mzlib.module.MzModule;

public abstract class EventPlayerMoveAsync extends PlayerEvent
{
    public EventPlayerMoveAsync(EntityPlayer player)
    {
        super(player);
    }

    public abstract boolean isLocationChanged();
    public abstract boolean isLookChanged();
    public abstract boolean isOnGroundChanged();
    public abstract boolean isVehicleLookChanged();
    public abstract Vector getLocation();
    public abstract float getYaw();
    public abstract float getPitch();
    public abstract boolean isOnGround();
    public abstract float getVehicleYaw();
    public abstract float getVehiclePitch();
    public abstract void setLocation(Vector value);
    public abstract void setYaw(float value);
    public abstract void setPitch(float value);
    public abstract void setOnGround(boolean value);
    public abstract void setVehicleYaw(float value);
    public abstract void setVehiclePitch(float value);

    @Override
    public void call()
    {
    }

    public static class EventPlayerMoveAsyncByPacketPlayerMoveC2S extends EventPlayerMoveAsync
    {
        public PacketPlayerMoveC2S packet;
        public EventPlayerMoveAsyncByPacketPlayerMoveC2S(EntityPlayer player, PacketPlayerMoveC2S packet)
        {
            super(player);
            this.packet = packet;
        }

        public PacketPlayerMoveC2S getPacket()
        {
            return this.packet;
        }

        @Override
        public boolean isLocationChanged()
        {
            return this.getPacket().isLocationChanged();
        }

        @Override
        public boolean isLookChanged()
        {
            return this.getPacket().isLookChanged();
        }

        @Override
        public boolean isOnGroundChanged()
        {
            return true;
        }

        @Override
        public Vector getLocation()
        {
            return this.getPacket().getLocation();
        }

        @Override
        public float getYaw()
        {
            return this.getPacket().getYaw();
        }

        @Override
        public boolean isOnGround()
        {
            return this.getPacket().isOnGround();
        }

        @Override
        public float getPitch()
        {
            return this.getPacket().getPitch();
        }

        @Override
        public void setLocation(Vector value)
        {
            this.getPacket().setLocation(value);
        }

        @Override
        public void setYaw(float value)
        {
            this.getPacket().setYaw(value);
        }

        @Override
        public void setPitch(float value)
        {
            this.getPacket().setPitch(value);
        }

        @Override
        public void setOnGround(boolean value)
        {
            this.getPacket().setOnGround(value);
        }

        @Override
        public boolean isVehicleLookChanged()
        {
            return false;
        }

        @Override
        public float getVehiclePitch()
        {
            return 0;
        }

        @Override
        public float getVehicleYaw()
        {
            return 0;
        }

        @Override
        public void setVehicleYaw(float value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setVehiclePitch(float value)
        {
            throw new UnsupportedOperationException();
        }
    }
    public static class EventPlayerMoveByPacketVehicleMoveC2S extends EventPlayerMoveAsync
    {
        public PacketVehicleMoveC2S packet;

        public EventPlayerMoveByPacketVehicleMoveC2S(EntityPlayer player, PacketVehicleMoveC2S packet)
        {
            super(player);
            this.packet = packet;
        }

        public PacketVehicleMoveC2S getPacket()
        {
            return this.packet;
        }

        @Override
        public boolean isLocationChanged()
        {
            return true;
        }

        @Override
        public boolean isLookChanged()
        {
            return false;
        }

        @Override
        public boolean isOnGroundChanged()
        {
            return false;
        }

        @Override
        public boolean isVehicleLookChanged()
        {
            return true;
        }

        @Override
        public boolean isOnGround()
        {
            return false;
        }

        @Override
        public void setOnGround(boolean value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Vector getLocation()
        {
            return this.getPacket().getLocation();
        }

        @Override
        public void setLocation(Vector value)
        {
            this.getPacket().setLocation(value);
        }

        @Override
        public float getVehicleYaw()
        {
            return this.getPacket().getYaw();
        }

        @Override
        public float getVehiclePitch()
        {
            return this.getPacket().getPitch();
        }

        @Override
        public void setVehicleYaw(float value)
        {
            this.getPacket().setYaw(value);
        }

        @Override
        public void setVehiclePitch(float value)
        {
            this.getPacket().setPitch(value);
        }

        @Override
        public float getYaw()
        {
            return 0;
        }

        @Override
        public float getPitch()
        {
            return 0;
        }

        @Override
        public void setYaw(float value)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPitch(float value)
        {
            throw new UnsupportedOperationException();
        }
    }
    public static class Module extends MzModule
    {
        public static Module instance = new Module();

        public void handle(PacketEvent packetEvent,PacketPlayerMoveC2S packet)
        {
            EventPlayerMoveAsync event = new EventPlayerMoveAsyncByPacketPlayerMoveC2S(packetEvent.getPlayer(), packet);
            event.setCancelled(packetEvent.isCancelled());
            event.call();
            packetEvent.setCancelled(event.isCancelled());
            packetEvent.runLater(event::complete);
        }

        public void handle(PacketEvent packetEvent,PacketVehicleMoveC2S packet)
        {
            EventPlayerMoveAsync event = new EventPlayerMoveByPacketVehicleMoveC2S(packetEvent.getPlayer(), packet);
            event.setCancelled(packetEvent.isCancelled());
            event.call();
            packetEvent.setCancelled(event.isCancelled());
            packetEvent.runLater(event::complete);
        }

        @Override
        public void onLoad()
        {
            this.register(EventPlayerMoveAsync.class);
            this.register(new PacketListener<>(PacketPlayerMoveC2S.LocationAndOnGround.class, false, this::handle));
            this.register(new PacketListener<>(PacketPlayerMoveC2S.LookAndOnGround.class, false, this::handle));
            this.register(new PacketListener<>(PacketPlayerMoveC2S.Full.class, false, this::handle));
            if(MinecraftPlatform.instance.getVersion()>=1700)
                this.register(new PacketListener<>(PacketPlayerMoveC2S.OnGroundOnlyV1700.class, false, this::handle));
            this.register(new PacketListener<>(PacketVehicleMoveC2S.class,false,this::handle));
        }
    }
}
