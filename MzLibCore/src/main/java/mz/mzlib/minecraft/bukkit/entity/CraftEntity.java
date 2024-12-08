package mz.mzlib.minecraft.bukkit.entity;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapCraftbukkitClass(@VersionName(name="OBC.entity.CraftEntity"))
public interface CraftEntity extends WrapperObject
{
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
