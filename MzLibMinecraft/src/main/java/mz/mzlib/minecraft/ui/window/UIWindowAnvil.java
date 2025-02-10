package mz.mzlib.minecraft.ui.window;

import mz.mzlib.Priority;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.window.async.EventAsyncWindowAnvilSetName;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.ui.UI;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.minecraft.window.WindowType;
import mz.mzlib.module.MzModule;

public abstract class UIWindowAnvil extends UIWindow
{
    public UIWindowAnvil(Inventory inventory)
    {
        super(WindowType.ANVIL, inventory);
    }
    
    public UIWindowAnvil()
    {
        super(WindowType.ANVIL, 3);
    }
    
    public void onNameChanged(EntityPlayer player, String name)
    {
    }
    
    public static class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(new EventListener<>(EventAsyncWindowAnvilSetName.class, Priority.VERY_LOW, event->event.sync(()->
            {
                if(event.isCancelled())
                    return;
                Window window = event.getPlayer().getCurrentWindow();
                if(!window.isInstanceOf(WindowUIWindow::create))
                    return;
                UI ui = window.castTo(WindowUIWindow::create).getUIWindow();
                if(ui instanceof UIWindowAnvil)
                {
                    event.sync(()->
                    {
                        if(window.isInstanceOf(WindowUIWindow::create))
                        {
                            UI ui1 = window.castTo(WindowUIWindow::create).getUIWindow();
                            if(ui1 instanceof UIWindowAnvil)
                                ((UIWindowAnvil)ui1).onNameChanged(event.getPlayer(), event.getName());
                        }
                    });
                }
            })));
        }
    }
}
