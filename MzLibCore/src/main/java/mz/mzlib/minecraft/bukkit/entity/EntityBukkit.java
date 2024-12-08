package mz.mzlib.minecraft.bukkit.entity;

import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapSameClass(Entity.class)
public interface EntityBukkit extends Entity
{
    @WrapperCreator
    static EntityBukkit create(Object wrapped)
    {
        return WrapperObject.create(EntityBukkit.class, wrapped);
    }

    @WrapMethod("getBukkitEntity")
    CraftEntity getBukkitEntity();
}
