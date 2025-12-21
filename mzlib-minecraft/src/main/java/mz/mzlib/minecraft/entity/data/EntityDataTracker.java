package mz.mzlib.minecraft.entity.data;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntityData;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.*;

import java.util.function.Function;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.data.DataTracker"))
public interface EntityDataTracker extends WrapperObject
{
    WrapperFactory<EntityDataTracker> FACTORY = WrapperFactory.of(EntityDataTracker.class);
    @Deprecated
    @WrapperCreator
    static EntityDataTracker create(Object wrapped)
    {
        return WrapperObject.create(EntityDataTracker.class, wrapped);
    }

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
    interface Entry extends WrapperObject, PacketS2cEntityData.Entry
    {
        WrapperFactory<Entry> FACTORY = WrapperFactory.of(Entry.class);
        @Deprecated
        @WrapperCreator
        static Entry create(Object wrapped)
        {
            return WrapperObject.create(Entry.class, wrapped);
        }

        @VersionRange(end = 900)
        @WrapMinecraftFieldAccessor(@VersionName(name = "valueType"))
        int getTypeIdV_900();

        @VersionRange(end = 900)
        @WrapMinecraftFieldAccessor(@VersionName(name = "field_3424"))
        int getIndexV_900();

        static Entry newInstance(EntityDataKey type, WrapperObject value)
        {
            return newInstance0(type, value.getWrapped());
        }

        static Entry newInstance0(EntityDataKey type, Object value)
        {
            return Entry.FACTORY.getStatic().static$newInstance0(type, value);
        }

        Entry static$newInstance0(EntityDataKey type, Object value);

        @VersionRange(end = 900)
        @WrapConstructor
        Entry static$newInstance0V_900(int typeId, int index, Object value);

        @SpecificImpl("static$newInstance0")
        @VersionRange(end = 900)
        default Entry static$newInstance0V_900(EntityDataKey type, Object value)
        {
            return this.static$newInstance0V_900(type.getTypeIdV_900(), type.getIndexV_900(), value);
        }

        @SpecificImpl("static$newInstance0")
        @VersionRange(begin = 900)
        @WrapConstructor
        Entry static$newInstance0V900(EntityDataKey type, Object value);

        @Override
        EntityDataKey getKey();

        @SpecificImpl("getKey")
        @VersionRange(end = 900)
        default EntityDataKey getKeyV_900()
        {
            return EntityDataKey.newInstanceV_900(this.getIndexV_900(), (byte) this.getTypeIdV_900());
        }

        @SpecificImpl("getKey")
        @VersionRange(begin = 900)
        @WrapMinecraftMethod({
            @VersionName(name = "method_12758", end = 1400),
            @VersionName(name = "getData", begin = 1400)
        })
        EntityDataKey getKeyV900();

        @Override
        @WrapMinecraftMethod({ @VersionName(name = "getValue", end = 1400), @VersionName(name = "get", begin = 1400) })
        Object getValue();

        @Override
        @WrapMinecraftMethod({ @VersionName(name = "setValue", end = 1400), @VersionName(name = "set", begin = 1400) })
        void setValue(Object value);

        @VersionRange(begin = 1903)
        @WrapMinecraftMethod(@VersionName(name = "toSerialized"))
        EntityDataV1903 toDataV1903();

        @Override
        default String toString0()
        {
            return PacketS2cEntityData.Entry.super.toString0();
        }
    }

    @VersionRange(begin = 1903)
    @WrapMinecraftInnerClass(outer = EntityDataTracker.class, name = @VersionName(name = "SerializedEntry"))
    interface EntityDataV1903 extends WrapperObject, PacketS2cEntityData.Entry
    {
        WrapperFactory<EntityDataV1903> FACTORY = WrapperFactory.of(EntityDataV1903.class);
        @Deprecated
        @WrapperCreator
        static EntityDataV1903 create(Object wrapped)
        {
            return WrapperObject.create(EntityDataV1903.class, wrapped);
        }

        @WrapMinecraftFieldAccessor(@VersionName(name = "comp_1115"))
        int getIndex();

        @WrapMinecraftFieldAccessor(@VersionName(name = "handler"))
        EntityDataHandler getHandler();

        @Override
        default EntityDataKey getKey()
        {
            return EntityDataKey.newInstance(this.getIndex(), this.getHandler());
        }

        @Override
        @WrapMinecraftFieldAccessor(@VersionName(name = "comp_1117"))
        Object getValue();

        @Override
        @WrapMinecraftFieldAccessor(@VersionName(name = "comp_1117"))
        void setValue(Object value);

        default <T extends WrapperObject> T getValue(WrapperFactory<T> factory)
        {
            return factory.create(this.getValue());
        }
        @Deprecated
        default <T extends WrapperObject> T getValue(Function<Object, T> creator)
        {
            return creator.apply(this.getValue());
        }

        @Override
        default String toString0()
        {
            return PacketS2cEntityData.Entry.super.toString0();
        }
    }
}
