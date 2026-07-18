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
import mz.mzlib.util.Box;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.Impl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket"))
public interface PacketS2cEntityData extends Packet, EntityDataHolder
{
    WrapperFactory<PacketS2cEntityData> FACTORY = WrapperFactory.of(PacketS2cEntityData.class);
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

    @Impl("static$newInstance")
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

    @Impl("static$newInstance")
    @VersionRange(begin = 1903)
    default PacketS2cEntityData static$newInstanceV1903(int entityId)
    {
        return this.static$newInstance0V1903(entityId, new ArrayList<>());
    }

    interface Entry<T>
    {
        EntityDataKey<T> getKey();

        T getValue();

        void setValue(T value);

        default String toString0()
        {
            return this.getKey().getIndex() + ": " + this.getValue();
        }
    }

    static <T> Entry<T> newEntry(EntityDataKey<T> key, T value)
    {
        return FACTORY.getStatic().static$newEntry(key, value);
    }

    <T> Entry<T> static$newEntry(EntityDataKey<T> type, T value);

    @Impl("static$newEntry")
    @VersionRange(end = 1903)
    default <T> EntityDataTracker.Entry<T> static$newEntryV_1903(EntityDataKey<T> type, T value)
    {
        return EntityDataTracker.Entry.newInstance0(type, value);
    }

    @Impl("static$newEntry")
    @VersionRange(begin = 1903)
    default <T> EntityDataTracker.EntityDataV1903<T> static$newEntryV1903(EntityDataKey<T> type, T value)
    {
        return this.static$newEntryV_1903(type, value).toDataV1903();
    }

    List<Entry<?>> getDataList();

    @Impl("getDataList")
    @VersionRange(end = 1903)
    default List<EntityDataTracker.Entry<?>> getDataListV_1903()
    {
        return new ListProxy<>(getDataList0(), FunctionInvertible.wrapper(EntityDataTracker.Entry.FACTORY));
    }

    @Impl("getDataList")
    @VersionRange(begin = 1903)
    default List<EntityDataTracker.EntityDataV1903<?>> getDataListV1903()
    {
        return new ListProxy<>(getDataList0(), FunctionInvertible.wrapper(EntityDataTracker.EntityDataV1903.FACTORY));
    }

    @Override
    default <T> T removeData(EntityDataKey<T> type)
    {
        List<Entry<?>> list = this.getDataList();
        for(int i = 0; i < list.size(); i++)
        {
            if(type.equals(list.get(i).getKey()))
                return RuntimeUtil.cast(list.remove(i).getValue());
        }
        return null;
    }

    default <T> void addData(EntityDataKey<T> type, T value)
    {
        this.getDataList().add(newEntry(type, value));
    }
    default <T> void addData(EntityDataAdapter<T> adapter, T value)
    {
        this.addData(adapter.getKey(), RuntimeUtil.cast(adapter.getFunction().apply(value)));
    }

    @Override
    default <T> @Nullable T putData(EntityDataKey<T> type, T value)
    {
        T result = this.removeData(type);
        this.addData(type, value);
        return result;
    }

    @Override
    default void forEachData(BiConsumer<EntityDataKey<?>, Object> action)
    {
        for(Entry<?> entry : this.getDataList())
        {
            action.accept(entry.getKey(), entry.getValue());
        }
    }

    @Override
    default <T> @Nullable T getData(EntityDataKey<T> type)
    {
        Box.Mut<@Nullable T> result = Box.Mut.of(null);
        this.forEachData((t, value) ->
        {
            if(t.equals(type))
                result.set(RuntimeUtil.cast(value));
        });
        return result.get();
    }
}

