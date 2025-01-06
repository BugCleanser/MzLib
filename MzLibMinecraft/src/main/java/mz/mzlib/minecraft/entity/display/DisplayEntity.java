package mz.mzlib.minecraft.entity.display;

import mz.mzlib.minecraft.entity.EntityType;
import mz.mzlib.minecraft.entity.data.EntityDataType;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntitySpawn;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.util.RuntimeUtil;
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
    
    public Map<EntityDataType, Object> dataMap = new HashMap<>();
    
    public synchronized void putData0(EntityDataType type, Object value)
    {
        this.dataMap.put(type, value);
    }
    public synchronized void putData(EntityDataType type, WrapperObject value)
    {
        this.putData0(type, value.getWrapped());
    }
    
    public synchronized @Nullable Object getData0(EntityDataType type)
    {
        return this.dataMap.get(type);
    }
    public synchronized <T extends WrapperObject> T getData(EntityDataType type, Function<Object, T> wrapperCreator)
    {
        return wrapperCreator.apply(this.getData0(type));
    }
    
    public Map<Class<?>, ?> tags = new HashMap<>();
    public <T> void putTag(Class<T> type, T value)
    {
        this.tags.put(type, RuntimeUtil.cast(value));
    }
    public boolean hasTag(Class<?> type)
    {
        return this.tags.containsKey(type);
    }
    public <T> T getTag(Class<T> type)
    {
        return RuntimeUtil.cast(this.tags.get(type));
    }
    public void removeTag(Class<?> type)
    {
        this.tags.remove(type);
    }
}
