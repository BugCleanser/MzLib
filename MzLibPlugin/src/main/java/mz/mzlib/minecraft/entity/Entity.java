package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapperCreator;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.Entity"))
public interface Entity extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static Entity create(Object wrapped)
    {
        return WrapperObject.create(Entity.class, wrapped);
    }
}
