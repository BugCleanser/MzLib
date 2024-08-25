package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.Vector;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.damage.DamageSource;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.Entity"))
public interface Entity extends WrapperObject
{
    @WrapperCreator
    static Entity create(Object wrapped)
    {
        return WrapperObject.create(Entity.class, wrapped);
    }

    default Vector getPosition()
    {
        if(MinecraftPlatform.instance.getVersion()<1600)
            return Vector.newInstance(this.getXV_1600(), this.getYV_1600(), this.getZV_1600());
        else
            return this.getPositionV1600();
    }

    default void setPosition(Vector value)
    {
        if(MinecraftPlatform.instance.getVersion()<1600)
        {
            this.setXV_1600(value.getX());
            this.setZV_1600(value.getZ());
            this.setYV_1600(value.getY());
        }
        else
            this.setPositionV1600(value);
    }

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

    @WrapMinecraftMethod(@VersionName(name = "getPos", begin = 1600))
    Vector getPositionV1600();

    @WrapMinecraftMethod(@VersionName(name = "setPosition", begin = 1600))
    void setPositionV1600(Vector value);
}
