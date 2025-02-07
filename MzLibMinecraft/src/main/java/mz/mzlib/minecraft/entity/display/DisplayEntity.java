package mz.mzlib.minecraft.entity.display;

import mz.mzlib.minecraft.entity.EntityType;
import mz.mzlib.minecraft.entity.data.EntityDataHolder;
import mz.mzlib.minecraft.entity.data.EntityDataKey;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntitySpawn;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.util.RuntimeUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public class DisplayEntity implements EntityDataHolder
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
        this.type = packetSpawn.getEntityType();
        this.position = packetSpawn.getPosition();
    }
    
    public EntityDataHolder dataHolder = EntityDataHolder.of(new HashMap<>());
    public EntityDataHolder unsynced = EntityDataHolder.of(new HashMap<>());
    
    @Override
    public Object getData(EntityDataKey key)
    {
        return this.dataHolder.getData(key);
    }
    @Override
    public Object putData(EntityDataKey key, Object value)
    {
        this.unsynced.putData(key, value);
        return this.dataHolder.putData(key, value);
    }
    @Override
    public Object removeData(EntityDataKey key)
    {
        this.unsynced.removeData(key);
        return this.dataHolder.removeData(key);
    }
    @Override
    public void forEachData(BiConsumer<EntityDataKey, Object> action)
    {
        this.dataHolder.forEachData(action);
    }
    
    public void removeTag(Class<?> type)
    {
        this.tags.remove(type);
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
}
