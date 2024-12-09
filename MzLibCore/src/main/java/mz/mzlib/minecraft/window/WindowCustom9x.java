package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventoryPlayer;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@Compound
public interface WindowCustom9x extends AbstractWindowCustom, WindowGeneric9x
{
    @WrapperCreator
    static WindowCustom9x create(Object wrapped)
    {
        return WrapperObject.create(WindowCustom9x.class, wrapped);
    }
    
    
    WindowCustom9x staticNewInstance(int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows);
    static WindowCustom9x newInstance(int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows)
    {
        return create(null).staticNewInstance(syncId, inventoryPlayer, inventory, rows);
    }
    
    @WrapConstructor
    @VersionRange(end=1400)
    WindowCustom9x staticNewInstanceV_1400(Inventory inventoryPlayer, Inventory inventory, AbstractEntityPlayer player);
    @SpecificImpl("staticNewInstance")
    @VersionRange(end=1400)
    default WindowCustom9x staticNewInstanceV_1400(int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows)
    {
        return staticNewInstanceV_1400(inventoryPlayer, inventory, inventoryPlayer.getPlayer());
    }
    
    @WrapConstructor
    @VersionRange(begin=1400)
    WindowCustom9x staticNewInstanceV1400(WindowTypeV1400 type, int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows);
    @SpecificImpl("staticNewInstance")
    @VersionRange(begin=1400)
    default WindowCustom9x staticNewInstanceV1400(int syncId, InventoryPlayer inventoryPlayer, Inventory inventory, int rows)
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
        return staticNewInstanceV1400(type, syncId, inventoryPlayer, inventory, rows);
    }
}
