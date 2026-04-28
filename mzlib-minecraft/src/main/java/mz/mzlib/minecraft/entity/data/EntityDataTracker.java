package mz.mzlib.minecraft.entity.data;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntityData;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.jetbrains.annotations.ApiStatus;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.data.DataTracker"))
public interface EntityDataTracker extends WrapperObject
{
    WrapperFactory<EntityDataTracker> FACTORY = WrapperFactory.of(EntityDataTracker.class);

    static EntityDataTracker newInstanceV_1903(Entity entity)
    {
        return FACTORY.getStatic().static$newInstanceV_1903(entity);
    }

    @VersionRange(end = 1903)
    @WrapConstructor
    EntityDataTracker static$newInstanceV_1903(Entity entity);

    @WrapMinecraftInnerClass(outer = EntityDataTracker.class, name = {
        @VersionName(name = "DataEntry", end = 1400),
        @VersionName(name = "Entry", begin = 1400)
    })
    interface Entry<T> extends WrapperObject, PacketS2cEntityData.Entry<T>
    {
        WrapperFactory<Entry<?>> FACTORY = WrapperFactory.of(Entry.class);

        @VersionRange(end = 900)
        @WrapMinecraftFieldAccessor(@VersionName(name = "valueType"))
        int getTypeIdV_900();

        @VersionRange(end = 900)
        @WrapMinecraftFieldAccessor(@VersionName(name = "field_3424"))
        int getIndexV_900();

        static <T> Entry<T> of(EntityDataKey<T> type, WrapperObject value)
        {
            return newInstance0(type, RuntimeUtil.cast(value.getWrapped()));
        }

        static <T> Entry<T> newInstance0(EntityDataKey<T> type, T value)
        {
            return FACTORY.getStatic().static$newInstance0(type, value);
        }

        <T1> Entry<T1> static$newInstance0(EntityDataKey<T1> type, T1 value);

        @VersionRange(end = 900)
        @WrapConstructor
        <T1> Entry<T1> static$newInstance0V_900(int typeId, int index, T1 value);

        @SpecificImpl("static$newInstance0")
        @VersionRange(end = 900)
        default <T1> Entry<T1> static$newInstance0V_900(EntityDataKey<T1> type, T1 value)
        {
            return this.static$newInstance0V_900(type.getTypeIdV_900(), type.getIndexV_900(), value);
        }

        @SpecificImpl("static$newInstance0")
        @VersionRange(begin = 900)
        @WrapConstructor
        <T1> Entry<T1> static$newInstance0V900(EntityDataKey<T1> type, T1 value);

        @Override
        EntityDataKey<T> getKey();

        @SpecificImpl("getKey")
        @VersionRange(end = 900)
        default EntityDataKey<T> getKeyV_900()
        {
            return EntityDataKey.newInstanceV_900(this.getIndexV_900(), (byte) this.getTypeIdV_900());
        }

        @SpecificImpl("getKey")
        @VersionRange(begin = 900)
        @WrapMinecraftMethod({
            @VersionName(name = "method_12758", end = 1400),
            @VersionName(name = "getData", begin = 1400)
        })
        EntityDataKey<T> getKeyV900();

        @Override
        @WrapMinecraftMethod({ @VersionName(name = "getValue", end = 1400), @VersionName(name = "get", begin = 1400) })
        T getValue();

        @Override
        @WrapMinecraftMethod({ @VersionName(name = "setValue", end = 1400), @VersionName(name = "set", begin = 1400) })
        void setValue(T value);

        @VersionRange(begin = 1903)
        @WrapMinecraftMethod(@VersionName(name = "toSerialized"))
        EntityDataV1903<T> toDataV1903();

        @Override
        default String toString0()
        {
            return PacketS2cEntityData.Entry.super.toString0();
        }
    }

    @VersionRange(begin = 1903)
    @WrapMinecraftInnerClass(outer = EntityDataTracker.class, name = @VersionName(name = "SerializedEntry"))
    interface EntityDataV1903<T> extends WrapperObject, PacketS2cEntityData.Entry<T>
    {
        WrapperFactory<EntityDataV1903<?>> FACTORY = WrapperFactory.of(EntityDataV1903.class);

        @WrapMinecraftFieldAccessor(@VersionName(name = "comp_1115"))
        int getIndex();

        @WrapMinecraftFieldAccessor(@VersionName(name = "handler"))
        EntityDataHandler<T> getHandler();

        @Override
        default EntityDataKey<T> getKey()
        {
            return EntityDataKey.of(this.getIndex(), this.getHandler());
        }

        @Override
        @WrapMinecraftFieldAccessor(@VersionName(name = "comp_1117"))
        T getValue();

        @Override
        @WrapMinecraftFieldAccessor(@VersionName(name = "comp_1117"))
        void setValue(Object value);

        @ApiStatus.Experimental
        default <T1 extends WrapperObject> T1 getValue(WrapperFactory<T1> factory)
        {
            return factory.create(this.getValue());
        }

        @Override
        default String toString0()
        {
            return PacketS2cEntityData.Entry.super.toString0();
        }
    }
}
