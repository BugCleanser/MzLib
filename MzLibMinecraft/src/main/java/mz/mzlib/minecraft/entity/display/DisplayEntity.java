package mz.mzlib.minecraft.entity.display;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.entity.EntityType;
import mz.mzlib.minecraft.entity.data.EntityDataHolder;
import mz.mzlib.minecraft.entity.data.EntityDataKey;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntitySpawn;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.util.Option;
import mz.mzlib.util.RuntimeUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;

public class DisplayEntity implements EntityDataHolder
{
    public EntityPlayer player;
    public int id;
    public UUID uuidV900;
    public EntityType type;
    public Vec3d position;
    
    public DisplayEntity(EntityPlayer player, PacketS2cEntitySpawn packetSpawn)
    {
        this.player = player;
        this.id = packetSpawn.getEntityId();
        if(MinecraftPlatform.instance.getVersion() >= 900)
            this.uuidV900 = packetSpawn.getUuidV900();
        this.type = packetSpawn.getEntityType();
        this.position = packetSpawn.getPosition();
    }
    
    public EntityDataHolder dataHolder = EntityDataHolder.of(new HashMap<>());
    public EntityDataHolder unsynced = EntityDataHolder.of(new HashMap<>());
    
    @Override
    public Option<Object> getData(EntityDataKey key)
    {
        return this.dataHolder.getData(key);
    }
    @Override
    public Option<Object> putData(EntityDataKey key, Object value)
    {
        this.unsynced.putData(key, value);
        return this.dataHolder.putData(key, value);
    }
    @Override
    public Option<Object> removeData(EntityDataKey key)
    {
        this.unsynced.removeData(key);
        return this.dataHolder.removeData(key);
    }
    @Override
    public void forEachData(BiConsumer<EntityDataKey, Object> action)
    {
        this.dataHolder.forEachData(action);
    }
    
    public Map<Identifier, ?> tags = new HashMap<>();
    public <T> Option<T> removeTag(Identifier key)
    {
        return Option.fromNullable(RuntimeUtil.cast(this.tags.remove(key)));
    }
    public <T> Option<T> putTag(Identifier key, T value)
    {
        Objects.requireNonNull(value, "value");
        return Option.fromNullable((RuntimeUtil.cast(this.tags.put(key, RuntimeUtil.cast(value)))));
    }
    public boolean hasTag(Identifier key)
    {
        return this.tags.containsKey(key);
    }
    public <T> Option<T> getTag(Identifier key)
    {
        return Option.fromNullable(RuntimeUtil.cast(this.tags.get(key)));
    }
}
