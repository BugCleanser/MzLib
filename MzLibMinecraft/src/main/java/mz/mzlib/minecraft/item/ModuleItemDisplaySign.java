package mz.mzlib.minecraft.item;

import mz.mzlib.Priority;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.event.player.async.EventAsyncPlayerDisplayItem;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.module.MzModule;

import java.util.List;

public class ModuleItemDisplaySign extends MzModule
{
    public static ModuleItemDisplaySign instance = new ModuleItemDisplaySign();

    @Override
    public void onLoad()
    {
        this.register(new EventListener<>(EventAsyncPlayerDisplayItem.class, Priority.LOWEST, this::handle));
    }

    public void handle(EventAsyncPlayerDisplayItem event)
    {
        for(NbtCompound customData : Item.CUSTOM_DATA.get(event.getItemStack()))
        {
            if(customData.getByte("mz_display").unwrapOr((byte) 0) != 1)
                break;
            for(ItemStack itemStack : event.reviseItemStack())
            {
                for(NbtCompound customData1 : Item.CUSTOM_DATA.revise(itemStack))
                {
                    customData1.put("mz_display", (byte) 2);
                }
                for(List<Text> lore : Item.LORE.revise(itemStack))
                {
                    lore.add(0, MinecraftI18n.resolveText(event.getPlayer(), "mzlib.item_display_sign"));
                }
            }
            return;
        }
        if(event.getItemStack().getWrapped() != event.getOriginal().getWrapped())
        {
            for(ItemStack itemStack : event.reviseItemStack())
            {
                for(NbtCompound customData : Item.CUSTOM_DATA.revise(itemStack))
                {
                    if(customData.getByte("mz_display").unwrapOr((byte) 0) == 0)
                        customData.put("mz_display", (byte) 1);
                }
            }
        }
    }
}
