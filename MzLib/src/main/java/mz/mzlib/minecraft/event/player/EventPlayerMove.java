package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sPlayerMove;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sVehicleMove;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.module.MzModule;

import java.util.concurrent.CompletableFuture;

public abstract class EventPlayerMove extends EventPlayer
{
    public PacketEvent packetEvent;
    public EventPlayerMove(EntityPlayer player, PacketEvent packetEvent)
    {
        super(player);
        this.packetEvent = packetEvent;
    }
    
    @Override
    public boolean isCancelled()
    {
        return this.packetEvent.isCancelled();
    }
    @Override
    public void setCancelled(boolean cancelled)
    {
        this.packetEvent.setCancelled(cancelled);
    }
    
    public CompletableFuture<Void> sync()
    {
        return this.packetEvent.sync();
    }
    
    public abstract boolean isLocationChanged();
    
    public abstract boolean isLookChanged();
    
    public abstract boolean isOnGroundChanged();
    
    public abstract boolean isVehicleLookChanged();
    
    public abstract Vec3d getLocation();
    
    public abstract float getYaw();
    
    public abstract float getPitch();
    
    public abstract boolean isOnGround();
    
    public abstract float getVehicleYaw();
    
    public abstract float getVehiclePitch();
    
    public abstract void setLocation(Vec3d value);
    
    public abstract void setYaw(float value);
    
    public abstract void setPitch(float value);
    
    public abstract void setOnGround(boolean value);
    
    public abstract void setVehicleYaw(float value);
    
    public abstract void setVehiclePitch(float value);
    
    @Override
    public void call()
    {
    }
    
    public static class EventPlayerMoveAsyncByPacketC2sPlayerMove extends EventPlayerMove
    {
        public PacketC2sPlayerMove packet;
        public EventPlayerMoveAsyncByPacketC2sPlayerMove(EntityPlayer player, PacketEvent packetEvent, PacketC2sPlayerMove packet)
        {
            super(player, packetEvent);
            this.packet = packet;
        }
        
        public PacketC2sPlayerMove getPacket()
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
        public Vec3d getLocation()
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
        public void setLocation(Vec3d value)
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
    
    public static class EventPlayerMoveByPacketC2sVehicleMove extends EventPlayerMove
    {
        public PacketC2sVehicleMove packet;
        
        public EventPlayerMoveByPacketC2sVehicleMove(EntityPlayer player, PacketEvent packetEvent, PacketC2sVehicleMove packet)
        {
            super(player, packetEvent);
            this.packet = packet;
        }
        
        public PacketC2sVehicleMove getPacket()
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
        public Vec3d getLocation()
        {
            return this.getPacket().getLocation();
        }
        
        @Override
        public void setLocation(Vec3d value)
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
        
        public void handle(PacketEvent packetEvent, PacketC2sPlayerMove packet)
        {
            EventPlayerMove event = new EventPlayerMoveAsyncByPacketC2sPlayerMove(packetEvent.getPlayer(), packetEvent, packet);
            event.setCancelled(packetEvent.isCancelled());
            event.call();
            packetEvent.runLater(event::complete);
        }
        
        public void handle(PacketEvent packetEvent, PacketC2sVehicleMove packet)
        {
            EventPlayerMove event = new EventPlayerMoveByPacketC2sVehicleMove(packetEvent.getPlayer(), packetEvent, packet);
            event.setCancelled(packetEvent.isCancelled());
            event.call();
            packetEvent.runLater(event::complete);
        }
        
        @Override
        public void onLoad()
        {
            this.register(EventPlayerMove.class);
            this.register(new PacketListener<>(PacketC2sPlayerMove.LocationAndOnGround.class, this::handle));
            this.register(new PacketListener<>(PacketC2sPlayerMove.LookAndOnGround.class, this::handle));
            this.register(new PacketListener<>(PacketC2sPlayerMove.Full.class, this::handle));
            if(MinecraftPlatform.instance.getVersion()>=1700)
                this.register(new PacketListener<>(PacketC2sPlayerMove.OnGroundOnlyV1700.class, this::handle));
            this.register(new PacketListener<>(PacketC2sVehicleMove.class, this::handle));
        }
    }
}
