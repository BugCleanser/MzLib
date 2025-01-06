package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.data.EntityDataTracker;
import mz.mzlib.minecraft.entity.data.EntityDataType;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.StrongRef;
import mz.mzlib.util.wrapper.ListWrapper;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

// TODO
@WrapMinecraftClass({@VersionName(name="net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket", end=1500), @VersionName(name="net.minecraft.client.network.packet.EntityTrackerUpdateS2CPacket", begin=1500, end=1502), @VersionName(name="net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket", begin=1502)})
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
    
    default List<EntityDataTracker.EntityData> getDataList()
    {
        return new ListWrapper<>(getDataList0(), EntityDataTracker.EntityData::create);
    }
    
    default void removeData(int index)
    {
        List<EntityDataTracker.EntityData> list = getDataList();
        for(int i = 0; i<list.size(); i++)
        {
            if(list.get(i).getIndex()==index)
            {
                list.remove(i);
                break;
            }
        }
    }
    
    default void addData0(EntityDataType type, Object value)
    {
        this.getDataList().add(EntityDataTracker.Entry.newInstance0(type, value).getData());
    }
    
    default void addData(EntityDataType type, WrapperObject value)
    {
        this.addData0(type, value.getWrapped());
    }
    
    default void putData0(EntityDataType type, Object value)
    {
        this.removeData(type.getIndex());
        this.addData0(type, value);
    }
    
    default void putData(EntityDataType type, WrapperObject value)
    {
        this.putData0(type, value.getWrapped());
    }
    
    default void forEachData0(BiConsumer<EntityDataType, Object> action)
    {
        for(EntityDataTracker.EntityData data: this.getDataList())
        {
            action.accept(data.getType(), data.getValue0());
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
