package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventorySimple;
import mz.mzlib.minecraft.ui.window.control.UiWindowRegion;
import mz.mzlib.minecraft.window.WindowType;

import java.awt.*;

public class UiWindowRect extends UiWindow
{
    public UiWindowRegion region;

    public UiWindowRect(int rowsUpper, Inventory inventory)
    {
        super(WindowType.generic9x(rowsUpper), inventory);
        this.registerRegion(this.region = UiWindowRegion.rect(new Dimension(9, rowsUpper + 4), 0));
    }
    public UiWindowRect(int rowsUpper)
    {
        this(rowsUpper, InventorySimple.newInstance(rowsUpper * 9));
    }
}
