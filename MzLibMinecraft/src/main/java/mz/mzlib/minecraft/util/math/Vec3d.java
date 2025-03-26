package mz.mzlib.minecraft.util.math;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.math.Quaternion;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.util.math.Vec3d"))
public interface Vec3d extends WrapperObject
{
    WrapperFactory<Vec3d> FACTORY = WrapperFactory.find(Vec3d.class);
    @Deprecated
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
    
    @Deprecated
    @WrapMinecraftFieldAccessor(@VersionName(name = "x"))
    void setX(double value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "y"))
    double getY();

    @Deprecated
    @WrapMinecraftFieldAccessor(@VersionName(name = "y"))
    void setY(double value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name = "z"))
    double getZ();
    
    @Deprecated
    @WrapMinecraftFieldAccessor(@VersionName(name = "z"))
    void setZ(double value);
    
    default Vec3d negate()
    {
        return newInstance(-this.getX(), -this.getY(), -this.getZ());
    }
    
    default Vec3d scale(double factor)
    {
        return newInstance(this.getX() * factor, this.getY() * factor, this.getZ() * factor);
    }
    
    default double normSquared()
    {
        return this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ();
    }
    
    default double norm()
    {
        return Math.sqrt(this.normSquared());
    }
    
    default Vec3d normalize()
    {
        return this.scale(1./this.norm());
    }
    
    default Vec3d add(Vec3d other)
    {
        return newInstance(this.getX() + other.getX(), this.getY() + other.getY(), this.getZ() + other.getZ());
    }
    
    default double dot(Vec3d other)
    {
        return this.getX() * other.getX() + this.getY() * other.getY() + this.getZ() * other.getZ();
    }
    
    default Vec3d cross(Vec3d other)
    {
        return newInstance(this.getY() * other.getZ() - this.getZ() * other.getY(), this.getZ() * other.getX() - this.getX() * other.getZ(), this.getX() * other.getY() - this.getY() * other.getX());
    }
    
    default Quaternion toQuaternion()
    {
        return new Quaternion(0, this.getX(), this.getY(), this.getZ());
    }
    
    static Vec3d fromQuaternion(Quaternion q)
    {
        return newInstance(q.b, q.c, q.d);
    }
    
    default Vec3d rotateNormSquared(Quaternion rotation)
    {
        return fromQuaternion(rotation.multiply(toQuaternion()).multiply(rotation.conjugate()));
    }
    
    default Vec3d rotate(Quaternion rotation)
    {
        return this.rotateNormSquared(rotation).scale(1./rotation.normSquared());
    }
}
