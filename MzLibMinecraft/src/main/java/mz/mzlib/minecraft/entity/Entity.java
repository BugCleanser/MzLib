package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.command.CommandSender;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.damage.DamageSource;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.Entity"))
public interface Entity extends WrapperObject, CommandSender
{
    @WrapperCreator
    static Entity create(Object wrapped)
    {
        return WrapperObject.create(Entity.class, wrapped);
    }

    Vec3d getPosition();
    @SpecificImpl("getPosition")
    @VersionRange(end=1600)
    default Vec3d getPositionV_1600()
    {
        return Vec3d.newInstance(this.getXV_1600(), this.getYV_1600(), this.getZV_1600());
    }
    @SpecificImpl("getPosition")
    @WrapMinecraftMethod(@VersionName(name = "getPos", begin = 1600))
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
    @WrapMinecraftMethod(@VersionName(name = "setPosition", begin = 1600))
    void setPositionV1600(Vec3d value);

    @WrapMinecraftMethod(@VersionName(name = "damage"))
    boolean damage(DamageSource source, float amount);

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
