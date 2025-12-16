package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventorySimple;
import mz.mzlib.minecraft.ui.window.control.UiWindowRegion;
import mz.mzlib.minecraft.window.WindowType;

import java.awt.*;

public class UiWindowCrafting extends UiWindow
{
    public UiWindowRegion regionInput;
    public UiWindowRegion regionOutput;
    public UiWindowRegion regionPlayer;

    public UiWindowCrafting(Inventory inventory)
    {
        super(WindowType.CRAFTING, inventory);
        this.registerRegion(this.regionInput = UiWindowRegion.rect(new Dimension(3, 3), 1));
        this.registerRegion(this.regionOutput = UiWindowRegion.rect(new Dimension(1, 1), 0));
        this.registerRegion(this.regionPlayer = UiWindowRegion.rect(new Dimension(9, 4), 10));
    }
    public UiWindowCrafting()
    {
        this(InventorySimple.newInstance(WindowType.CRAFTING.upperSize));
    }
}
