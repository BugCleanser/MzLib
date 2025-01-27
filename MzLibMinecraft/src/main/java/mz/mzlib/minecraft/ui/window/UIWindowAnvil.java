package mz.mzlib.minecraft.ui.window;

import mz.mzlib.Priority;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.window.EventWindowAnvilSetName;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.ui.UI;
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
            this.register(new EventListener<>(EventWindowAnvilSetName.class, Priority.VERY_LOW, event->
            {
                if(event.isCancelled())
                    return;
                MinecraftServer.instance.execute(()->
                {
                    if(event.getPlayer().getCurrentWindow().isInstanceOf(WindowUIWindow::create))
                    {
                        UI ui = event.getPlayer().getCurrentWindow().castTo(WindowUIWindow::create).getUIWindow();
                        if(ui instanceof UIWindowAnvil)
                            ((UIWindowAnvil)ui).onNameChanged(event.getPlayer(), event.getName());
                    }
                });
            }));
        }
    }
}
