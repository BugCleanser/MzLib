package mz.mzlib.minecraft.bukkit.entity;

import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@BukkitEnabled
@WrapClassForName("org.bukkit.entity.HumanEntity")
public interface BukkitHumanEntity extends WrapperObject
{
    @WrapperCreator
    static BukkitHumanEntity create(Object wrapped)
    {
        return WrapperObject.create(BukkitHumanEntity.class, wrapped);
    }
}
