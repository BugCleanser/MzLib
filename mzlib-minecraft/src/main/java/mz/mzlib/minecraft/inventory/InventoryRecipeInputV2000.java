package mz.mzlib.minecraft.inventory;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;

@VersionRange(begin = 2000)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.inventory.RecipeInputInventory"))
public interface InventoryRecipeInputV2000 extends Inventory
{
    WrapperFactory<InventoryRecipeInputV2000> FACTORY = WrapperFactory.of(InventoryRecipeInputV2000.class);

    @WrapMinecraftMethod(@VersionName(name = "getWidth"))
    int getWidth();
    @WrapMinecraftMethod(@VersionName(name = "getHeight"))
    int getHeight();
}
