package mz.mzlib.demo;

import mz.mzlib.Priority;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.event.player.EventPlayerDisplayItemInEntity;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
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
        this.register(new EventListener<>(EventPlayerDisplayItemInEntity.InEntityItem.class, Priority.VERY_VERY_LOW, event->
        {
            if(!event.getDisplayEntity().hasTag(ModuleItemName.class) && Boolean.TRUE.equals(event.getEventDisplayEntityData().getData0(Entity.dataTypeCustomNameVisible())))
                return;
            event.getDisplayEntity().putTag(ModuleItemName.class, null);
            event.eventDisplayEntityData.putNewData0(Entity.dataTypeCustomNameVisible(), true);
            event.eventDisplayEntityData.putNewData0(Entity.dataTypeCustomName(), Optional.of(Text.literal(String.format("%sx%d", I18nMinecraft.getTranslation(event.getPlayer(), event.getItemStack().getTranslationKey()), event.getItemStack().getCount())).setColor(TextColor.YELLOW).getWrapped()));
        }));
    }
}
