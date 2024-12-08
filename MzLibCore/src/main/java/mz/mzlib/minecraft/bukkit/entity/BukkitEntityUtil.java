package mz.mzlib.minecraft.bukkit.entity;

import mz.mzlib.minecraft.entity.Entity;

public class BukkitEntityUtil
{
    public static org.bukkit.entity.Entity toBukkit(Entity entity)
    {
        return EntityBukkit.create(entity.getWrapped()).getBukkitEntity().getWrapped();
    }
}
