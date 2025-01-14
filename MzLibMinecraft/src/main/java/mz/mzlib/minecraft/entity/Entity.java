package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.damage.DamageSource;
import mz.mzlib.minecraft.entity.data.EntityDataType;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.minecraft.world.World;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Optional;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.Entity"))
public interface Entity extends WrapperObject
{
    @WrapperCreator
    static Entity create(Object wrapped)
    {
        return WrapperObject.create(Entity.class, wrapped);
    }
    
    /**
     * type0V1300: {@link java.util.Optional<mz.mzlib.minecraft.text.Text>}
     */
    static EntityDataType dataTypeCustomName()
    {
        return create(null).staticDataTypeCustomName();
    }
    
    // TODO: versioning
    @WrapMinecraftFieldAccessor(@VersionName(name="CUSTOM_NAME"))
    EntityDataType staticDataTypeCustomName();
    
    // FIXME: V_1300
    static Object newDataValue0CustomName(Text name)
    {
        return Optional.ofNullable(name.getWrapped());
    }
    
    /**
     * type0: {@link Boolean}
     */
    static EntityDataType dataTypeCustomNameVisible()
    {
        return create(null).staticDataTypeCustomNameVisible();
    }
    
    // TODO: versioning
    @WrapMinecraftFieldAccessor(@VersionName(name="NAME_VISIBLE"))
    EntityDataType staticDataTypeCustomNameVisible();
    
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
