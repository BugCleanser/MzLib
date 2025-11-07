package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.entity.data.EntityDataAdapter;
import mz.mzlib.minecraft.entity.data.EntityDataHolder;
import mz.mzlib.minecraft.entity.data.EntityDataKey;
import mz.mzlib.minecraft.entity.data.EntityDataTracker;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.Option;
import mz.mzlib.util.RefStrong;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket"))
public interface PacketS2cEntityData extends Packet, EntityDataHolder
{
    WrapperFactory<PacketS2cEntityData> FACTORY = WrapperFactory.of(PacketS2cEntityData.class);
    @Deprecated
    @WrapperCreator
    static PacketS2cEntityData create(Object wrapped)
    {
        return WrapperObject.create(PacketS2cEntityData.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "id"))
    int getEntityId();

    @WrapMinecraftFieldAccessor(@VersionName(name = "trackedValues"))
    List<Object> getDataList0();

    @WrapMinecraftFieldAccessor(@VersionName(name = "trackedValues"))
    void setDataList0(List<?> value);

    static PacketS2cEntityData newInstance(int entityId)
    {
        return FACTORY.getStatic().static$newInstance(entityId);
    }

    PacketS2cEntityData static$newInstance(int entityId);

    @VersionRange(end = 1903)
    @WrapConstructor
    PacketS2cEntityData static$newInstanceV_1903(int entityId, EntityDataTracker dataTracker, boolean updateAll);

    @SpecificImpl("static$newInstance")
    @VersionRange(end = 1903)
    default PacketS2cEntityData static$newInstanceV_1903(int entityId)
    {
        PacketS2cEntityData result = this.static$newInstanceV_1903(
            entityId, EntityDataTracker.newInstanceV_1903(Entity.FACTORY.getStatic()), true);
        result.setDataList0(new ArrayList<>());
        return result;
    }

    @VersionRange(begin = 1903)
    @WrapConstructor
    PacketS2cEntityData static$newInstance0V1903(int entityId, List<?> dataList0);

    @SpecificImpl("static$newInstance")
    @VersionRange(begin = 1903)
    default PacketS2cEntityData static$newInstanceV1903(int entityId)
    {
        return this.static$newInstance0V1903(entityId, new ArrayList<>());
    }

    interface Entry
    {
        EntityDataKey getKey();

        Object getValue();

        void setValue(Object value);

        default String toString0()
        {
            return this.getKey().getIndex() + ": " + getValue().toString();
        }
    }

    static Entry newEntry(EntityDataKey key, Object value)
    {
        return FACTORY.getStatic().static$newEntry(key, value);
    }

    Entry static$newEntry(EntityDataKey type, Object value);

    @SpecificImpl("static$newEntry")
    @VersionRange(end = 1903)
    default EntityDataTracker.Entry static$newEntryV_1903(EntityDataKey type, Object value)
    {
        return EntityDataTracker.Entry.newInstance0(type, value);
    }

    @SpecificImpl("static$newEntry")
    @VersionRange(begin = 1903)
    default EntityDataTracker.EntityDataV1903 static$newEntryV1903(EntityDataKey type, Object value)
    {
        return this.static$newEntryV_1903(type, value).toDataV1903();
    }

    List<Entry> getDataList();

    @SpecificImpl("getDataList")
    @VersionRange(end = 1903)
    default List<EntityDataTracker.Entry> getDataListV_1903()
    {
        return new ListProxy<>(getDataList0(), InvertibleFunction.wrapper(EntityDataTracker.Entry.FACTORY));
    }

    @SpecificImpl("getDataList")
    @VersionRange(begin = 1903)
    default List<EntityDataTracker.EntityDataV1903> getDataListV1903()
    {
        return new ListProxy<>(getDataList0(), InvertibleFunction.wrapper(EntityDataTracker.EntityDataV1903.FACTORY));
    }

    @Override
    default Option<Object> removeData(EntityDataKey type)
    {
        List<Entry> list = getDataList();
        for(int i = 0; i < list.size(); i++)
        {
            if(type.equals(list.get(i).getKey()))
                return Option.fromNullable(list.remove(i).getValue());
        }
        return Option.none();
    }

    default void addData(EntityDataKey type, Object value)
    {
        this.getDataList().add(newEntry(type, value));
    }
    default <T> void addData(EntityDataAdapter<T> adapter, T value)
    {
        this.addData(adapter.getKey(), adapter.function.apply(value));
    }

    @Override
    default Option<Object> putData(EntityDataKey type, Object value)
    {
        Option<Object> result = this.removeData(type);
        this.addData(type, value);
        return result;
    }

    @Override
    default void forEachData(BiConsumer<EntityDataKey, Object> action)
    {
        for(Entry entry : this.getDataList())
        {
            action.accept(entry.getKey(), entry.getValue());
        }
    }

    @Override
    default Option<Object> getData(EntityDataKey type)
    {
        RefStrong<Object> result = new RefStrong<>(null);
        this.forEachData((t, value) ->
        {
            if(t.equals(type))
                result.set(value);
        });
        return Option.fromNullable(result.get());
    }
}
