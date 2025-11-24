package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.damage.DamageSource;
import mz.mzlib.minecraft.entity.data.EntityDataAdapter;
import mz.mzlib.minecraft.entity.data.EntityDataKey;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.minecraft.world.WorldServer;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;
import java.util.UUID;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.Entity"))
public interface Entity extends WrapperObject
{
    WrapperFactory<Entity> FACTORY = WrapperFactory.of(Entity.class);
    @Deprecated
    @WrapperCreator
    static Entity create(Object wrapped)
    {
        return WrapperObject.create(Entity.class, wrapped);
    }

    /**
     * typeV_1300: {@link String}
     * typeV1300: {@link Optional<mz.mzlib.minecraft.text.Text>}
     */
    static EntityDataKey<?> dataKeyCustomName()
    {
        return FACTORY.getStatic().static$dataKeyCustomName();
    }

    EntityDataKey<?> static$dataKeyCustomName();

    @SpecificImpl("static$dataKeyCustomName")
    @VersionRange(end = 900)
    default EntityDataKey<String> static$dataKeyCustomNameV_900()
    {
        return EntityDataKey.newInstanceV_900(2, (byte) 4);
    }

    @SpecificImpl("static$dataKeyCustomName")
    @VersionRange(begin = 900)
    @WrapMinecraftFieldAccessor(@VersionName(name = "CUSTOM_NAME"))
    EntityDataKey<?> static$dataKeyCustomNameV900();

    EntityDataAdapter<Text> DATA_ADAPTER_CUSTOM_NAME = new EntityDataAdapter<>(
        dataKeyCustomName(), //
        MinecraftPlatform.instance.getVersion() < 1300 ?
            new FunctionInvertible<>(Text::toLegacy, Text::fromLegacy).thenCast() :
            FunctionInvertible.wrapper(Text.FACTORY).inverse().thenApply(FunctionInvertible.optional()).thenCast()
    );

    /**
     * typeV_900: {@link Byte}
     * typeV900: {@link Boolean}
     */
    static EntityDataKey<?> dataKeyCustomNameVisible()
    {
        return FACTORY.getStatic().static$dataKeyCustomNameVisible();
    }

    EntityDataKey<?> static$dataKeyCustomNameVisible();

    @SpecificImpl("static$dataKeyCustomNameVisible")
    @VersionRange(end = 900)
    default EntityDataKey<Byte> static$dataKeyCustomNameVisibleV_900()
    {
        return EntityDataKey.newInstanceV_900(3, (byte) 0);
    }

    @SpecificImpl("static$dataKeyCustomNameVisible")
    @VersionRange(begin = 900)
    @WrapMinecraftFieldAccessor(@VersionName(name = "NAME_VISIBLE"))
    EntityDataKey<Boolean> static$dataKeyCustomNameVisibleV900();

    EntityDataAdapter<Boolean> DATA_ADAPTER_CUSTOM_NAME_VISIBLE = new EntityDataAdapter<>(
        dataKeyCustomNameVisible(), //
        MinecraftPlatform.instance.getVersion() < 900 ?
            new FunctionInvertible<>(RuntimeUtil::castBooleanToByte, RuntimeUtil::castByteToBoolean).thenCast() :
            //
            FunctionInvertible.cast()
    );

    @WrapMinecraftFieldAccessor({
        @VersionName(name = "playerUuid", end = 1400),
        @VersionName(name = "uuid", begin = 1400)
    })
    UUID getUuid();

    Vec3d getPosition();

    @SpecificImpl("getPosition")
    @VersionRange(end = 1600)
    default Vec3d getPositionV_1600()
    {
        return Vec3d.newInstance(this.getXV_1600(), this.getYV_1600(), this.getZV_1600());
    }

    @SpecificImpl("getPosition")
    @WrapMinecraftFieldAccessor(@VersionName(name = "pos", begin = 1600))
    Vec3d getPositionV1600();

    void setPosition(Vec3d value);

    @SpecificImpl("setPosition")
    @VersionRange(end = 1600)
    default void setPositionV_1600(Vec3d value)
    {
        this.setXV_1600(value.getX());
        this.setZV_1600(value.getZ());
        this.setYV_1600(value.getY());
    }

    @SpecificImpl("setPosition")
    @WrapMinecraftFieldAccessor(@VersionName(name = "pos", begin = 1600, end = 1700))
    void setPositionV1600_1700(Vec3d value);

    @SpecificImpl("setPosition")
    @WrapMinecraftMethod(@VersionName(name = "setPosition", begin = 1700))
    void setPositionV1700(Vec3d value);

    @VersionRange(end = 2102)
    @WrapMinecraftMethod(@VersionName(name = "damage"))
    boolean damageV_2102(DamageSource source, float amount);

    @VersionRange(begin = 2102)
    @WrapMinecraftMethod(@VersionName(name = "damage"))
    boolean damageV2102(WorldServer world, DamageSource source, float amount);

    @WrapMinecraftFieldAccessor(@VersionName(name = "x", end = 1600))
    double getXV_1600();

    @WrapMinecraftFieldAccessor(@VersionName(name = "x", end = 1600))
    void setXV_1600(double value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "y", end = 1600))
    double getYV_1600();

    @WrapMinecraftFieldAccessor(@VersionName(name = "y", end = 1600))
    void setYV_1600(double value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "z", end = 1600))
    double getZV_1600();

    @WrapMinecraftFieldAccessor(@VersionName(name = "z", end = 1600))
    void setZV_1600(double value);
}
