package mz.mzlib.minecraft.bukkit.inventory;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.minecraft.bukkit.entity.BukkitEntityUtil;
import mz.mzlib.minecraft.bukkit.entity.BukkitHumanEntity;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@BukkitEnabled
@WrapCraftbukkitClass(@VersionName(name="OBC.inventory.CraftInventoryView"))
public interface CraftInventoryView extends WrapperObject, BukkitInventoryView
{
    WrapperFactory<CraftInventoryView> FACTORY = WrapperFactory.find(CraftInventoryView.class);
    @Deprecated
    @WrapperCreator
    static CraftInventoryView create(Object wrapped)
    {
        return WrapperObject.create(CraftInventoryView.class, wrapped);
    }
    
    @WrapConstructor
    CraftInventoryView staticNewInstance(BukkitHumanEntity player, BukkitInventory inventory, Window window);
    static CraftInventoryView newInstance(BukkitHumanEntity player, BukkitInventory inventory, Window window)
    {
        return create(null).staticNewInstance(player, inventory, window);
    }
    
    static CraftInventoryView newInstance(AbstractEntityPlayer player, Inventory inventory, Window window)
    {
        BukkitInventory bukkitInventory=CraftInventory.newInstance(inventory);
        // TODO
        return newInstance(BukkitHumanEntity.create(BukkitEntityUtil.toBukkit(player)), bukkitInventory, window);
    }
}
