package mz.mzlib.minecraft.ui.window.control;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.window.WindowAction;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class UiWindowButton extends UiWindowControlReadOnly
{
    Consumer<WindowAction> actionHandler;

    public UiWindowButton(Point location, Consumer<WindowAction> actionHandler)
    {
        super(new Rectangle(location, new Dimension(1, 1)));
        this.setActionHandler(actionHandler);
    }
    public UiWindowButton(
        Point location,
        Function<EntityPlayer, ItemStack> iconGetter,
        Consumer<WindowAction> actionHandler)
    {
        this(location, actionHandler);
        this.setBackground(iconGetter);
    }

    public void setActionHandler(Consumer<WindowAction> actionHandler)
    {
        this.actionHandler = actionHandler;
    }

    @Override
    public void onAction(WindowAction action, Point point)
    {
        this.actionHandler.accept(action);
    }
}
