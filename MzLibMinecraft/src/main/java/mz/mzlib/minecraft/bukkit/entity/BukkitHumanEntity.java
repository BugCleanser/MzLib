package mz.mzlib.minecraft.bukkit.entity;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapClassForName("org.bukkit.entity.HumanEntity")
public interface BukkitHumanEntity extends WrapperObject
{
    WrapperFactory<BukkitHumanEntity> FACTORY = WrapperFactory.of(BukkitHumanEntity.class);
    @Deprecated
    @WrapperCreator
    static BukkitHumanEntity create(Object wrapped)
    {
        return WrapperObject.create(BukkitHumanEntity.class, wrapped);
    }
}
