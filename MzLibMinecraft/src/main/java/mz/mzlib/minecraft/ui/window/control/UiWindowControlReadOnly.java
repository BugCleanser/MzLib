package mz.mzlib.minecraft.ui.window.control;

import java.awt.*;

public class UiWindowControlReadOnly extends UiWindowControl
{
    public UiWindowControlReadOnly(Rectangle bounds)
    {
        super(bounds);
    }

    @Override
    public boolean canPlace(Point point)
    {
        return false;
    }

    @Override
    public boolean canTake(Point point)
    {
        return false;
    }
}
