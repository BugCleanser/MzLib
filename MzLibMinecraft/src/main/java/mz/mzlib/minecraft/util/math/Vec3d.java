package mz.mzlib.minecraft.util.math;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.util.math.Vec3d"))
public interface Vec3d extends WrapperObject
{
    @WrapperCreator
    static Vec3d create(Object wrapped)
    {
        return WrapperObject.create(Vec3d.class, wrapped);
    }

    @WrapConstructor
    Vec3d staticNewInstance(double x, double y, double z);
    static Vec3d newInstance(double x, double y, double z)
    {
        return create(null).staticNewInstance(x, y, z);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "x"))
    double getX();

    @WrapMinecraftFieldAccessor(@VersionName(name = "x"))
    void setX(double value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "y"))
    double getY();

    @WrapMinecraftFieldAccessor(@VersionName(name = "y"))
    void setY(double value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "z"))
    double getZ();

    @WrapMinecraftFieldAccessor(@VersionName(name = "z"))
    void setZ(double value);
}
