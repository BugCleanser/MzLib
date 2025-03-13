package mz.mzlib.minecraft.bukkit.entity;

import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.util.wrapper.*;

@BukkitEnabled
@WrapSameClass(Entity.class)
public interface EntityBukkit extends Entity
{
    WrapperFactory<EntityBukkit> FACTORY = WrapperFactory.find(EntityBukkit.class);
    @Deprecated
    @WrapperCreator
    static EntityBukkit create(Object wrapped)
    {
        return WrapperObject.create(EntityBukkit.class, wrapped);
    }

    @WrapMethod("getBukkitEntity")
    CraftEntity getBukkitEntity();
}
