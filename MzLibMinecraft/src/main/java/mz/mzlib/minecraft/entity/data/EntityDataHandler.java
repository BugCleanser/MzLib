package mz.mzlib.minecraft.entity.data;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="byte", remap=false, end=900), @VersionName(name="net.minecraft.entity.data.TrackedDataHandler", begin=900)})
public interface EntityDataHandler extends WrapperObject
{
    @WrapperCreator
    static EntityDataHandler create(Object wrapped)
    {
        return WrapperObject.create(EntityDataHandler.class, wrapped);
    }
}
