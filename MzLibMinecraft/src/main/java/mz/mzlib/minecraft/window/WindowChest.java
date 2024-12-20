package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventoryPlayer;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.screen.ChestScreenHandler", end=1400), @VersionName(name="net.minecraft.container.GenericContainer",begin=1400, end=1600), @VersionName(name="net.minecraft.screen.GenericContainerScreenHandler", begin=1600)})
public interface WindowChest extends WrapperObject, Window
{
    @WrapperCreator
    static WindowChest create(Object wrapped)
    {
        return WrapperObject.create(WindowChest.class, wrapped);
    }
    
    WindowChest staticNewInstance(WindowTypeV1400 type, int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows);
    @WrapConstructor
    @VersionRange(end=1400)
    WindowChest staticNewInstanceV_1400(Inventory inventoryPlayer, Inventory inventory, AbstractEntityPlayer player);
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=1400)
    default WindowChest staticNewInstanceV_1400(WindowTypeV1400 type, int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows)
    {
        if(rows*9!=inventory.size())
            throw new IllegalArgumentException();
        return staticNewInstanceV_1400(inventoryPlayer, inventory, inventoryPlayer.getPlayer());
    }
    @WrapConstructor
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=1400)
    WindowChest staticNewInstanceV1400(WindowTypeV1400 type, int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows);
    static WindowChest newInstance(WindowTypeV1400 type, int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows)
    {
        return create(null).staticNewInstance(type, syncId, inventoryPlayer, inventory, rows);
    }
    static WindowChest newInstance(int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows)
    {
        return newInstance(UnionWindowType.generic9x(rows).typeV1400, syncId, inventoryPlayer, inventory, rows);
    }
}
