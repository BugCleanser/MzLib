package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.ItemEntity"))
public interface EntityItem extends WrapperObject, Entity
{
    @WrapperCreator
    static EntityItem create(Object wrapped)
    {
        return WrapperObject.create(EntityItem.class, wrapped);
    }
}
