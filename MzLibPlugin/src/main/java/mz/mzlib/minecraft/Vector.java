package mz.mzlib.minecraft;

import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.util.math.Vec3d"))
public interface Vector extends WrapperObject
{
    @WrapperCreator
    static Vector create(Object wrapped)
    {
        return WrapperObject.create(Vector.class, wrapped);
    }

    @WrapConstructor
    Vector staticNewInstance(double x, double y, double z);
    static Vector newInstance(double x, double y, double z)
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
