package mz.mzlib.minecraft.ui.window;

import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.player.async.EventPlayerWindowAnvilSetNameAsync;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.ui.UI;
import mz.mzlib.minecraft.window.UnionWindowType;
import mz.mzlib.module.MzModule;

public abstract class UIWindowAnvil extends UIWindow
{
    public UIWindowAnvil(Inventory inventory)
    {
        super(UnionWindowType.ANVIL, inventory);
    }
    
    public UIWindowAnvil()
    {
        super(UnionWindowType.ANVIL, 3);
    }
    
    public void onTextChange(EntityPlayer player, String text)
    {
    }
    
    public static class Module extends MzModule
    {
        public static Module instance=new Module();
        
        @Override
        public void onLoad()
        {
            this.register(new EventListener<>(EventPlayerWindowAnvilSetNameAsync.class, event->event.whenComplete(()->
            {
                if(event.isCancelled())
                    return;
                MinecraftServer.instance.execute(()->
                {
                    if(event.getPlayer().getCurrentWindow().isInstanceOf(WindowUIWindow::create))
                    {
                        UI ui = event.getPlayer().getCurrentWindow().castTo(WindowUIWindow::create).getUIWindow();
                        if(ui instanceof UIWindowAnvil)
                            ((UIWindowAnvil)ui).onTextChange(event.getPlayer(), event.getName());
                    }
                });
            })));
        }
    }
}
