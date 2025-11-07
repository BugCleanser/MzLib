package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventoryPlayer;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.*;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.screen.ChestScreenHandler", end = 1400),
    @VersionName(name = "net.minecraft.container.GenericContainer", begin = 1400, end = 1600),
    @VersionName(name = "net.minecraft.screen.GenericContainerScreenHandler", begin = 1600)
})
public interface WindowChest extends WrapperObject, Window
{
    WrapperFactory<WindowChest> FACTORY = WrapperFactory.of(WindowChest.class);
    @Deprecated
    @WrapperCreator
    static WindowChest create(Object wrapped)
    {
        return WrapperObject.create(WindowChest.class, wrapped);
    }

    WindowChest static$newInstance(
        WindowType type,
        int syncId,
        InventoryPlayer inventoryPlayer,
        Inventory inventory,
        int rows);
    @WrapConstructor
    @VersionRange(end = 1400)
    WindowChest static$newInstanceV_1400(Inventory inventoryPlayer, Inventory inventory, AbstractEntityPlayer player);
    @SpecificImpl("static$newInstance")
    @VersionRange(end = 1400)
    default WindowChest static$newInstanceV_1400(
        WindowType type,
        int syncId,
        InventoryPlayer inventoryPlayer,
        Inventory inventory,
        int rows)
    {
        if(rows * 9 != inventory.size())
            throw new IllegalArgumentException();
        return static$newInstanceV_1400(inventoryPlayer, inventory, inventoryPlayer.getPlayer());
    }
    @VersionRange(begin = 1400)
    @WrapConstructor
    WindowChest static$newInstanceV1400(
        WindowTypeV1400 type,
        int syncId,
        InventoryPlayer inventoryPlayer,
        Inventory inventory,
        int rows);
    @SpecificImpl("static$newInstance")
    @VersionRange(begin = 1400)
    default WindowChest static$newInstanceV1400(
        WindowType type,
        int syncId,
        InventoryPlayer inventoryPlayer,
        Inventory inventory,
        int rows)
    {
        return this.static$newInstanceV1400(type.typeV1400, syncId, inventoryPlayer, inventory, rows);
    }
    static WindowChest newInstance(
        WindowType type,
        int syncId,
        InventoryPlayer inventoryPlayer,
        Inventory inventory,
        int rows)
    {
        return FACTORY.getStatic().static$newInstance(type, syncId, inventoryPlayer, inventory, rows);
    }
    static WindowChest newInstance(int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows)
    {
        return newInstance(WindowType.generic9x(rows), syncId, inventoryPlayer, inventory, rows);
    }
}
