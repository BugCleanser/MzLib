package mz.mzlib.minecraft.entity.data;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.data.TrackedDataHandler"))
public interface EntityDataHandler extends WrapperObject
{
    @WrapperCreator
    static EntityDataHandler create(Object wrapped)
    {
        return WrapperObject.create(EntityDataHandler.class, wrapped);
    }
}
