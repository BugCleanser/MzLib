package mz.mzlib.minecraft.bukkit.inventory;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapCraftbukkitClass(@VersionName(name="OBC.inventory.CraftInventory"))
public interface CraftInventory extends BukkitInventory
{
    @WrapperCreator
    static CraftInventory create(Object wrapped)
    {
        return WrapperObject.create(CraftInventory.class, wrapped);
    }
    
    @WrapConstructor
    CraftInventory staticNewInstance(Window window);
    static CraftInventory newInstance(Window window)
    {
        return create(null).staticNewInstance(window);
    }
}
