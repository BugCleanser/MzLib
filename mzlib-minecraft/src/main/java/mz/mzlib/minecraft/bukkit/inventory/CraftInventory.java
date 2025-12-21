package mz.mzlib.minecraft.bukkit.inventory;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapCraftbukkitClass(@VersionName(name = "OBC.inventory.CraftInventory"))
public interface CraftInventory extends BukkitInventory
{
    WrapperFactory<CraftInventory> FACTORY = WrapperFactory.of(CraftInventory.class);
    @Deprecated
    @WrapperCreator
    static CraftInventory create(Object wrapped)
    {
        return WrapperObject.create(CraftInventory.class, wrapped);
    }

    @WrapConstructor
    CraftInventory static$newInstance(Inventory inventory);
    static CraftInventory newInstance(Inventory inventory)
    {
        return FACTORY.getStatic().static$newInstance(inventory);
    }
}
