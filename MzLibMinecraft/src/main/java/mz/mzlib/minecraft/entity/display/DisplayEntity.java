package mz.mzlib.minecraft.entity.display;

import mz.mzlib.minecraft.entity.EntityType;
import mz.mzlib.minecraft.entity.data.EntityDataType;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntitySpawn;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.util.wrapper.WrapperObject;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class DisplayEntity
{
    public EntityPlayer player;
    public int id;
    public UUID uuid;
    public EntityType type;
    public Vec3d position;
    
    public DisplayEntity(EntityPlayer player, PacketS2cEntitySpawn packetSpawn)
    {
        this.player = player;
        this.id = packetSpawn.getEntityId();
        this.uuid = packetSpawn.getUuid();
        this.type = packetSpawn.getType();
        this.position = packetSpawn.getPosition();
    }
    
    public Map<EntityDataType, Object> syncedDataMap = new HashMap<>();
    public Map<EntityDataType, Object> unsyncedDataMap = new HashMap<>();
    
    public synchronized void putSyncedData0(EntityDataType type, Object value)
    {
        this.syncedDataMap.put(type, value);
    }
    public synchronized void putSyncedData(EntityDataType type, WrapperObject value)
    {
        this.putSyncedData0(type, value.getWrapped());
    }
    
    public synchronized void putUnsyncedData0(EntityDataType type, Object value)
    {
        // TODO sync
        this.unsyncedDataMap.put(type, value);
    }
    public synchronized void putUnsyncedData(EntityDataType type, WrapperObject value)
    {
        this.putUnsyncedData0(type, value.getWrapped());
    }
    
    public synchronized @Nullable Object getSyncedData0(EntityDataType type)
    {
        return this.syncedDataMap.get(type);
    }
    public synchronized <T extends WrapperObject> T getSyncedData(EntityDataType type, Function<Object, T> wrapperCreator)
    {
        return wrapperCreator.apply(this.getSyncedData0(type));
    }
    
    public synchronized @Nullable Object getData0(EntityDataType type)
    {
        Object result = this.unsyncedDataMap.get(type);
        if(result!=null)
            return result;
        return this.getSyncedData0(type);
    }
    
    public synchronized <T extends WrapperObject> T getData(EntityDataType type, Function<Object, T> wrapperCreator)
    {
        return wrapperCreator.apply(this.getData0(type));
    }
}
