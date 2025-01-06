package mz.mzlib.minecraft.entity.data;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.data.TrackedData"))
public interface EntityDataType extends WrapperObject
{
    @WrapperCreator
    static EntityDataType create(Object wrapped)
    {
        return WrapperObject.create(EntityDataType.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="id"))
    int getIndex();
    
    static EntityDataType newInstance(int index, EntityDataHandler handler)
    {
        return create(null).staticNewInstance(index, handler);
    }
    @WrapConstructor
    EntityDataType staticNewInstance(int index, EntityDataHandler handler);
}
