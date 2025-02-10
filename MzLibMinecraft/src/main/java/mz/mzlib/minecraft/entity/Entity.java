package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.damage.DamageSource;
import mz.mzlib.minecraft.entity.data.EntityDataAdapter;
import mz.mzlib.minecraft.entity.data.EntityDataKey;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.minecraft.world.World;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;
import java.util.function.Function;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.Entity"))
public interface Entity extends WrapperObject
{
    @WrapperCreator
    static Entity create(Object wrapped)
    {
        return WrapperObject.create(Entity.class, wrapped);
    }
    
    /**
     * typeV_1300: {@link String}
     * typeV1300: {@link Optional<mz.mzlib.minecraft.text.Text>}
     */
    static EntityDataKey dataKeyCustomName()
    {
        return create(null).staticDataKeyCustomName();
    }
    
    EntityDataKey staticDataKeyCustomName();
    
    @SpecificImpl("staticDataKeyCustomName")
    @VersionRange(end=900)
    default EntityDataKey staticDataKeyCustomNameV_900()
    {
        return EntityDataKey.newInstanceV_900(2, (byte)4);
    }
    
    @SpecificImpl("staticDataKeyCustomName")
    @VersionRange(begin=900)
    @WrapMinecraftFieldAccessor(@VersionName(name="CUSTOM_NAME"))
    EntityDataKey staticDataKeyCustomNameV900();
    
    EntityDataAdapter<Text> DATA_ADAPTER_CUSTOM_NAME = new EntityDataAdapter<>(dataKeyCustomName(), //
            MinecraftPlatform.instance.getVersion()<1300 ? new InvertibleFunction<>(Text::toLiteral, ((Function<Object, String>)RuntimeUtil::cast).andThen(Text::fromLiteral)) : //
                    new InvertibleFunction<>(((Function<Text, Object>)Text::getWrapped).andThen(Optional::ofNullable), ((Function<Object, Optional<?>>)RuntimeUtil::cast).andThen(RuntimeUtil::orNull).andThen(Text::create)));
    
    /**
     * type0: {@link Boolean}
     */
    static EntityDataKey dataKeyCustomNameVisible()
    {
        return create(null).staticDataKeyCustomNameVisible();
    }
    
    EntityDataKey staticDataKeyCustomNameVisible();
    
    @SpecificImpl("staticDataKeyCustomNameVisible")
    @VersionRange(end=900)
    default EntityDataKey staticDataKeyCustomNameVisibleV_900()
    {
        return EntityDataKey.newInstanceV_900(3, (byte)0);
    }
    
    @SpecificImpl("staticDataKeyCustomNameVisible")
    @VersionRange(begin=900)
    @WrapMinecraftFieldAccessor(@VersionName(name="NAME_VISIBLE"))
    EntityDataKey staticDataKeyCustomNameVisibleV900();
    
    EntityDataAdapter<Boolean> DATA_ADAPTER_CUSTOM_NAME_VISIBLE = new EntityDataAdapter<>(dataKeyCustomNameVisible(), //
            MinecraftPlatform.instance.getVersion()<900 ? new InvertibleFunction<>(RuntimeUtil::castBooleanToByte, RuntimeUtil.<Byte>functionCast().andThen(RuntimeUtil::castByteToBoolean)) : //
                    InvertibleFunction.cast());
    
    Vec3d getPosition();
    
    @SpecificImpl("getPosition")
    @VersionRange(end=1600)
    default Vec3d getPositionV_1600()
    {
        return Vec3d.newInstance(this.getXV_1600(), this.getYV_1600(), this.getZV_1600());
    }
    
    @SpecificImpl("getPosition")
    @WrapMinecraftMethod(@VersionName(name="getPos", begin=1600))
    Vec3d getPositionV1600();
    
    void setPosition(Vec3d value);
    
    @SpecificImpl("setPosition")
    @VersionRange(end=1600)
    default void setPositionV_1600(Vec3d value)
    {
        this.setXV_1600(value.getX());
        this.setZV_1600(value.getZ());
        this.setYV_1600(value.getY());
    }
    
    @SpecificImpl("setPosition")
    @WrapMinecraftFieldAccessor(@VersionName(name="pos", begin=1600, end=1700))
    void setPositionV1600_1700(Vec3d value);
    
    @SpecificImpl("setPosition")
    @WrapMinecraftMethod(@VersionName(name="setPosition", begin=1700))
    void setPositionV1700(Vec3d value);
    
    @VersionRange(end=2102)
    @WrapMinecraftMethod(@VersionName(name="damage"))
    boolean damageV_2102(DamageSource source, float amount);
    
    @VersionRange(begin=2102)
    @WrapMinecraftMethod(@VersionName(name="damage"))
    boolean damageV2102(World world, DamageSource source, float amount);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="x", end=1600))
    double getXV_1600();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="x", end=1600))
    void setXV_1600(double value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="y", end=1600))
    double getYV_1600();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="y", end=1600))
    void setYV_1600(double value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="z", end=1600))
    double getZV_1600();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="z", end=1600))
    void setZV_1600(double value);
}
