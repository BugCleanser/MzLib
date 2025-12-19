package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.SleepTicks;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.Ui;
import mz.mzlib.minecraft.ui.window.control.UiWindowControl;
import mz.mzlib.minecraft.ui.window.control.UiWindowRegion;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Option;
import mz.mzlib.util.async.AsyncFunction;
import mz.mzlib.util.async.AsyncFunctionRunner;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public abstract class UiAbstractWindow implements Ui
{
    Function<EntityPlayer, Text> titleGetter = player -> Text.literal("Untitled");
    public void setTitleGetter(Function<EntityPlayer, Text> value)
    {
        this.titleGetter = value;
    }
    public Text getTitle(EntityPlayer player)
    {
        return this.titleGetter.apply(player);
    }

    List<UiWindowRegion> regions = new ArrayList<>();
    public void addRegion(UiWindowRegion region)
    {
        this.regions.add(region);
        region.init(this);
    }

    static class ControlHit
    {
        UiWindowControl control;
        Point point;
        boolean enabled;
        public ControlHit(UiWindowControl control, Point point, boolean enabled)
        {
            this.control = control;
            this.point = point;
            this.enabled = enabled;
        }
    }
    Option<ControlHit> slot2point(int slotIndex)
    {
        for(UiWindowRegion region : this.regions)
        {
            if(!region.isVisible())
                continue;
            for(Point point : region.getPoint(slotIndex))
            {
                return Option.some(point2control(region, point, true));
            }
        }
        return Option.none();
    }
    static ControlHit point2control(UiWindowControl base, Point point, boolean enabled)
    {
        enabled &= base.isEnabled();
        for(UiWindowControl child : base.getChildren())
        {
            if(!child.isVisible())
                continue;
            Rectangle bounds = child.getBounds();
            if(bounds.contains(point))
                return point2control(child, new Point(point.x - bounds.x, point.y - bounds.y), enabled);
        }
        return new ControlHit(base, point, enabled);
    }

    static Set<UiAbstractWindow> uisDirty = ConcurrentHashMap.newKeySet();

    public void markDirty()
    {
        uisDirty.add(this);
    }

    void validate()
    {
        for(UiWindowRegion region : this.regions)
        {
            validateAll(region);
        }
    }
    static void validateAll(UiWindowControl control)
    {
        control.validate();
        for(UiWindowControl child : control.getChildren())
        {
            validateAll(child);
        }
    }

    @Override
    public void reopen()
    {
        if(uisDirty.remove(this))
            this.validate();
        Ui.super.reopen();
    }

    public void update(Set<Integer> indexes)
    {
        this.reopen();
    }
    Set<Integer> getInvalidIndexes()
    {
        Set<Integer> result = new HashSet<>();
        for(UiWindowRegion region : this.regions)
        {
            for(Rectangle rect : getInvalidRect(region))
            {
                for(int i = 0; i < rect.width; i++)
                {
                    for(int j = 0; j < rect.height; j++)
                    {
                        result.add(region.getIndex(new Point(rect.x + i, rect.y + j)));
                    }
                }
            }
        }
        return result;
    }
    static Option<Rectangle> getInvalidRect(UiWindowControl control)
    {
        Option<Rectangle> result = control.getInvalidRect();
        for(UiWindowControl child : control.getChildren())
        {
            Option<Rectangle> c = getInvalidRect(child);
            for(Rectangle o : c)
            {
                Point loc = child.getLocation();
                c = Option.some(new Rectangle(loc.x + o.x, loc.y + o.y, o.width, o.height));
                for(Rectangle r : result)
                {
                    result = Option.some(r.union(o));
                }
                if(result.isNone())
                    result = c;
            }
        }
        return result;
    }

    public static class Module extends MzModule
    {
        public static Module instance = new Module();

        @Override
        public void onLoad()
        {
            AsyncFunctionRunner runner = MinecraftServer.instance.registrable();
            this.register(runner);
            new AsyncFunction<Void>()
            {
                @Override
                protected Void template()
                {
                    for(;;)
                    {
                        for(UiAbstractWindow ui : uisDirty.toArray(new UiAbstractWindow[0]))
                        {
                            Set<Integer> indexes = ui.getInvalidIndexes();
                            ui.validate();
                            uisDirty.remove(ui);
                            ui.update(indexes);
                        }
                        await(new SleepTicks(1L));
                    }
                }
                @Override
                public void run()
                {
                }
            }.start(runner);

            this.register(UiWindow.Module.instance);
        }
    }
}
