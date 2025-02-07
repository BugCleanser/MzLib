package mz.mzlib.minecraft.entity.data;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.data.TrackedData"))
public interface EntityDataKey extends WrapperObject
{
    @WrapperCreator
    static EntityDataKey create(Object wrapped)
    {
        return WrapperObject.create(EntityDataKey.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="field_13816", end=1400), @VersionName(name="id", begin=1400)})
    int getIndex();
    
    static EntityDataKey newInstance(int index, EntityDataHandler handler)
    {
        return create(null).staticNewInstance(index, handler);
    }
    @WrapConstructor
    EntityDataKey staticNewInstance(int index, EntityDataHandler handler);
}
