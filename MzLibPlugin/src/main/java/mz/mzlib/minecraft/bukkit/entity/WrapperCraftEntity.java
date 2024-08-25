package mz.mzlib.minecraft.bukkit.entity;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapCraftbukkitClass(@VersionName(name="OBC.entity.CraftEntity"))
public interface WrapperCraftEntity extends WrapperObject
{
    @Override
    org.bukkit.entity.Entity getWrapped();

    @WrapperCreator
    static WrapperCraftEntity create(Object wrapped)
    {
        return WrapperObject.create(WrapperCraftEntity.class, wrapped);
    }
}
