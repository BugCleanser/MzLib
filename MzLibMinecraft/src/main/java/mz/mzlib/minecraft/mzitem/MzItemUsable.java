package mz.mzlib.minecraft.mzitem;

import mz.mzlib.Priority;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.entity.player.Hand;
import mz.mzlib.minecraft.event.player.EventPlayerUseItem;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.wrapper.WrapSameClass;

@WrapSameClass(MzItem.class)
public interface MzItemUsable extends MzItem
{
    default boolean use(EntityPlayer player, Hand hand)
    {
        return true;
    }
    
    class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(new EventListener<>(EventPlayerUseItem.class, Priority.VERY_LOW, event ->
            {
                if(event.isCancelled())
                    return;
                for(MzItem mzItem: RegistrarMzItem.instance.toMzItem(event.getItemStack()))
                {
                    if(!(mzItem instanceof MzItemUsable))
                        return;
                    if(!((MzItemUsable)mzItem).use(event.getPlayer(), event.getHand()))
                        event.setCancelled(true);
                }
            }));
        }
    }
}
