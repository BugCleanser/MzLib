package mz.mzlib.minecraft.bukkit.entity;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.util.wrapper.*;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapSameClass(Entity.class)
public interface EntityBukkit extends Entity
{
    WrapperFactory<EntityBukkit> FACTORY = WrapperFactory.of(EntityBukkit.class);
    @Deprecated
    @WrapperCreator
    static EntityBukkit create(Object wrapped)
    {
        return WrapperObject.create(EntityBukkit.class, wrapped);
    }

    @WrapMethod("getBukkitEntity")
    CraftEntity getBukkitEntity();
}
