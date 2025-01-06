package mz.mzlib.demo;

import mz.mzlib.Priority;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.entity.EntityItem;
import mz.mzlib.minecraft.entity.EntityType;
import mz.mzlib.minecraft.event.player.displayentity.EventDisplayEntityDataAsync;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.text.TextColor;
import mz.mzlib.module.MzModule;

import java.util.Optional;

public class ModuleItemName extends MzModule
{
    public static ModuleItemName instance = new ModuleItemName();
    
    @Override
    public void onLoad()
    {
        this.register(new EventListener<>(EventDisplayEntityDataAsync.class, Priority.LOW, event->
        {
            if(!event.getDisplayEntity().type.equals(EntityType.fromId("item")))
                return;
            if(Boolean.TRUE.equals(event.getData0(Entity.dataTypeCustomNameVisible())))
                return;
            ItemStack item=event.getData(EntityItem.dataTypeItem(), ItemStack::create);
            if(!item.isPresent())
                return;
            event.putNewData0(Entity.dataTypeCustomNameVisible(), true);
            event.putNewData0(Entity.dataTypeCustomName(), Optional.of(Text.literal(I18nMinecraft.getTranslation(event.getPlayer(), item.getTranslationKey())).setColor(TextColor.YELLOW).getWrapped()));
        }));
    }
}
