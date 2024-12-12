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
public interface WindowGeneric9x extends WrapperObject, Window
{
    @WrapperCreator
    static WindowGeneric9x create(Object wrapped)
    {
        return WrapperObject.create(WindowGeneric9x.class, wrapped);
    }
    
    String windowIdV_1400="minecraft:chest";
    
    WindowGeneric9x staticNewInstance(WindowTypeV1400 type, int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows);
    @WrapConstructor
    @VersionRange(end=1400)
    WindowGeneric9x staticNewInstanceV_1400(Inventory inventoryPlayer, Inventory inventory, AbstractEntityPlayer player);
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=1400)
    default WindowGeneric9x staticNewInstanceV_1400(WindowTypeV1400 type, int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows)
    {
        if(rows*9!=inventory.size())
            throw new IllegalArgumentException();
        return staticNewInstanceV_1400(inventoryPlayer, inventory, inventoryPlayer.getPlayer());
    }
    @WrapConstructor
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=1400)
    WindowGeneric9x staticNewInstanceV1400(WindowTypeV1400 type, int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows);
    static WindowGeneric9x newInstance(WindowTypeV1400 type, int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows)
    {
        return create(null).staticNewInstance(type, syncId, inventoryPlayer, inventory, rows);
    }
    static WindowGeneric9x newInstance(int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows)
    {
        WindowTypeV1400 type;
        switch(rows)
        {
            case 1:
                type = WindowTypeV1400.generic_9x1();
                break;
            case 2:
                type = WindowTypeV1400.generic_9x2();
                break;
            case 3:
                type = WindowTypeV1400.generic_9x3();
                break;
            case 4:
                type = WindowTypeV1400.generic_9x4();
                break;
            case 5:
                type = WindowTypeV1400.generic_9x5();
                break;
            case 6:
                type = WindowTypeV1400.generic_9x6();
                break;
            default:
                throw new IllegalArgumentException("Invalid rows count: "+rows);
        }
        return newInstance(type, syncId, inventoryPlayer, inventory, rows);
    }
}
