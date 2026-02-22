package mz.mzlib.minecraft.bukkit.entity;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperFactory;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapSameClass(Entity.class)
public interface EntityBukkit extends Entity
{
    WrapperFactory<EntityBukkit> FACTORY = WrapperFactory.of(EntityBukkit.class);

    @WrapMethod("getBukkitEntity")
    CraftEntity getBukkitEntity();
}
