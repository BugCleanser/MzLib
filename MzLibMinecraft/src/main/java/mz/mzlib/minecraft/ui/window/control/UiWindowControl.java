package mz.mzlib.minecraft.ui.window.control;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.ui.window.UiAbstractWindow;
import mz.mzlib.minecraft.window.WindowAction;
import mz.mzlib.util.Option;

import java.awt.*;
import java.util.LinkedList;
import java.util.function.Function;

public class UiWindowControl
{
    Rectangle bounds;
    public UiWindowControl(Rectangle bounds)
    {
        this.bounds = bounds;
    }

    public Rectangle getBounds()
    {
        return this.bounds.getBounds();
    }
    public Point getLocation()
    {
        return this.bounds.getLocation();
    }
    public Dimension getSize()
    {
        return this.bounds.getSize();
    }
    public void setBounds(Rectangle value)
    {
        for(UiWindowControl parent : this.getParent())
        {
            parent.invalidate(this.getBounds());
        }
        this.bounds = value;
        this.validate();
        this.invalidate();
    }
    public void setLocation(Point point)
    {
        this.setBounds(new Rectangle(point, this.getSize()));
    }
    public void setSize(Dimension size)
    {
        this.setBounds(new Rectangle(this.getLocation(), size));
    }

    Option<UiWindowControl> parent = Option.none();

    public Option<UiWindowControl> getParent()
    {
        return this.parent;
    }
    public Option<? extends UiAbstractWindow> getUi()
    {
        for(UiWindowControl parent : this.getParent())
        {
            return parent.getUi();
        }
        return Option.none();
    }

    LinkedList<UiWindowControl> children = new LinkedList<>();
    public void removeChild(UiWindowControl child)
    {
        if(!child.getParent().isSome(this) || !this.children.remove(child))
            throw new IllegalStateException("Not child");
        child.parent = Option.none();
    }
    public void addChild(UiWindowControl child)
    {
        for(UiWindowControl parent : child.getParent())
        {
            parent.removeChild(child);
        }
        child.parent = Option.some(this);
        this.children.add(child);
    }
    public Iterable<UiWindowControl> getChildren()
    {
        return this.children;
    }

    boolean visible = true;
    public boolean isVisible()
    {
        return this.visible;
    }
    public void setVisible(boolean value)
    {
        if(this.visible == value)
            return;
        this.visible = value;
        if(value)
            this.invalidate();
        else
            for(UiWindowControl parent : this.getParent())
            {
                parent.invalidate(this.getBounds());
            }
    }
    boolean enabled = true;
    public boolean isEnabled()
    {
        return this.enabled;
    }
    public void setEnabled(boolean value)
    {
        this.enabled = value;
    }

    Option<Rectangle> invalidRect = Option.none();
    public void invalidate(Rectangle rect)
    {
        for(UiAbstractWindow ui : this.getUi())
        {
            ui.markDirty();
        }
        for(Rectangle last : this.invalidRect)
        {
            this.invalidRect = Option.some(last.union(rect));
            return;
        }
        this.invalidRect = Option.some(rect);
    }
    public void invalidate()
    {
        this.invalidate(new Rectangle(this.getSize()));
    }
    public void validate()
    {
        this.invalidRect = Option.none();
    }

    public void onAction(WindowAction action, Point point)
    {
    }

    public boolean canPlace(Point point)
    {
        return true;
    }
    public boolean canTake(Point point)
    {
        return true;
    }

    Option<Function<EntityPlayer, ItemStack>> background = Option.none();

    public void setBackground(Function<EntityPlayer, ItemStack> background)
    {
        this.background = Option.some(background);
    }
    public void removeBackground()
    {
        this.background = Option.none();
    }

    public Option<ItemStack> getIcon(EntityPlayer player, Point point)
    {
        return this.background.map(f -> f.apply(player));
    }
}
