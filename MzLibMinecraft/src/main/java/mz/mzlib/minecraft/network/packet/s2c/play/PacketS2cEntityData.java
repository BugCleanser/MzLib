package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.entity.data.EntityDataTracker;
import mz.mzlib.minecraft.entity.data.EntityDataType;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.StrongRef;
import mz.mzlib.util.wrapper.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket"))
public interface PacketS2cEntityData extends Packet
{
    @WrapperCreator
    static PacketS2cEntityData create(Object wrapped)
    {
        return WrapperObject.create(PacketS2cEntityData.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="id"))
    int getEntityId();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="trackedValues"))
    List<?> getDataList0();
    
    static PacketS2cEntityData newInstance(int entityId)
    {
        return create(null).staticNewInstance(entityId);
    }
    
    PacketS2cEntityData staticNewInstance(int entityId);
    
    @VersionRange(end=1903)
    @WrapConstructor
    PacketS2cEntityData staticNewInstanceV_1903(int entityId, EntityDataTracker dataTracker, boolean updateAll);
    
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=1903)
    default PacketS2cEntityData staticNewInstanceV_1903(int entityId)
    {
        return this.staticNewInstanceV_1903(entityId, EntityDataTracker.newInstanceV_1903(Entity.create(null)), true);
    }
    
    @VersionRange(begin=1903)
    @WrapConstructor
    PacketS2cEntityData staticNewInstance0V1903(int entityId, List<?> dataList0);
    
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=1903)
    default PacketS2cEntityData staticNewInstanceV1903(int entityId)
    {
        return this.staticNewInstance0V1903(entityId, new ArrayList<>());
    }
    
    interface Entry
    {
        EntityDataType getType();
        
        Object getValue0();
        
        void setValue0(Object value);
        
        default String toString0()
        {
            return this.getType().getIndex()+": "+getValue0().toString();
        }
    }
    
    static Entry newEntry(EntityDataType type, WrapperObject value)
    {
        return newEntry0(type, value.getWrapped());
    }
    
    static Entry newEntry0(EntityDataType type, Object value)
    {
        return create(null).staticNewEntry0(type, value);
    }
    
    Entry staticNewEntry0(EntityDataType type, Object value);
    
    @SpecificImpl("staticNewEntry0")
    @VersionRange(end=1903)
    default EntityDataTracker.Entry staticNewEntry0V_1903(EntityDataType type, Object value)
    {
        return EntityDataTracker.Entry.newInstance0(type, value);
    }
    
    @SpecificImpl("staticNewEntry0")
    @VersionRange(begin=1903)
    default EntityDataTracker.EntityDataV1903 staticNewEntry0V1903(EntityDataType type, Object value)
    {
        return this.staticNewEntry0V_1903(type, value).getDataV1903();
    }
    
    List<Entry> getDataList();
    
    @SpecificImpl("getDataList")
    @VersionRange(end=1903)
    default List<EntityDataTracker.Entry> getDataListV_1903()
    {
        return new ListWrapper<>(getDataList0(), EntityDataTracker.Entry::create);
    }
    
    @SpecificImpl("getDataList")
    @VersionRange(begin=1903)
    default List<EntityDataTracker.EntityDataV1903> getDataListV1903()
    {
        return new ListWrapper<>(getDataList0(), EntityDataTracker.EntityDataV1903::create);
    }
    
    default void removeData(EntityDataType type)
    {
        List<Entry> list = getDataList();
        for(int i = 0; i<list.size(); i++)
        {
            if(type.equals(list.get(i).getType()))
            {
                list.remove(i);
                break;
            }
        }
    }
    
    default void addData0(EntityDataType type, Object value)
    {
        this.getDataList().add(newEntry0(type, value));
    }
    
    default void addData(EntityDataType type, WrapperObject value)
    {
        this.addData0(type, value.getWrapped());
    }
    
    default void putData0(EntityDataType type, Object value)
    {
        for(Entry entry: this.getDataList())
        {
            if(type.equals(entry.getType()))
            {
                entry.setValue0(value);
                return;
            }
        }
        this.addData0(type, value);
    }
    
    default void putData(EntityDataType type, WrapperObject value)
    {
        this.putData0(type, value.getWrapped());
    }
    
    default void forEachData0(BiConsumer<EntityDataType, Object> action)
    {
        for(Entry entry: this.getDataList())
        {
            action.accept(entry.getType(), entry.getValue0());
        }
    }
    
    default <T extends WrapperObject> void forEachData(Function<Object, T> wrapperCreator, BiConsumer<EntityDataType, T> action)
    {
        this.forEachData0((type, value)->action.accept(type, wrapperCreator.apply(value)));
    }
    
    
    default @Nullable Object getData0(EntityDataType type)
    {
        StrongRef<Object> result = new StrongRef<>(null);
        this.forEachData0((t, value)->
        {
            if(t.equals(type))
                result.set(value);
        });
        return result.get();
    }
    
    default <T extends WrapperObject> T getData(EntityDataType type, Function<Object, T> wrapperCreator)
    {
        return wrapperCreator.apply(this.getData0(type));
    }
    
    // TODO newInstance
}
