package mz.mzlib.minecraft.bukkit.entity;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapCraftbukkitClass(@VersionName(name = "OBC.entity.CraftEntity"))
public interface CraftEntity extends WrapperObject
{
    WrapperFactory<CraftEntity> FACTORY = WrapperFactory.of(CraftEntity.class);
    @Deprecated
    @WrapperCreator
    static CraftEntity create(Object wrapped)
    {
        return WrapperObject.create(CraftEntity.class, wrapped);
    }

    @Override
    org.bukkit.entity.Entity getWrapped();

    @WrapMethod("getHandle")
    Entity getHandle();
}
