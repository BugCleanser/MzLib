package mz.lib.minecraft.bukkit;

import com.google.common.collect.Lists;
import mz.lib.minecraft.bukkit.entity.EntityViewWatcher;
import mz.lib.minecraft.bukkit.event.SendEntityMetadataEvent;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.wrappednms.NmsEntity;
import mz.lib.minecraft.bukkit.wrappednms.NmsEntityItem;
import mz.lib.minecraft.bukkit.wrappednms.NmsItemStack;
import mz.lib.minecraft.bukkit.wrappednms.NmsPacketPlayOutEntityMetadata;
import mz.lib.minecraft.bukkit.wrappedobc.ObcItemStack;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLocaleChangeEvent;

import java.util.Optional;

public class ShowDropNameModule extends AbsModule
{
	public static ShowDropNameModule instance=new ShowDropNameModule();
	public ShowDropNameModule()
	{
		super(MzLib.instance);
	}
	
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	void onSendEntityMetadata(SendEntityMetadataEvent event)
	{
		if(event.getEntityType()!=EntityType.DROPPED_ITEM)
			return;
		if(MzLib.instance.getConfig().getBoolean("func.showDropName",true))
		{
			NmsItemStack item=event.get(NmsEntityItem.getItemStackType(),NmsItemStack.class);
			if(item.isNull())
				return;
			event.put(NmsEntity.newCustomName(ItemStackBuilder.getDropNameWithNum(ObcItemStack.asCraftMirror(item).getRaw(),event.getPlayer())));
			event.put(NmsEntity.getCustomNameVisibleType(),true);
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	void onPlayerLocaleChange(PlayerLocaleChangeEvent event)
	{
		if(MzLib.instance.getConfig().getBoolean("func.showDropName",true))
			Bukkit.getScheduler().runTask(MzLib.instance,()->
			{
				if(event.getPlayer().isOnline())
					Optional.ofNullable(EntityViewWatcher.all.get(event.getPlayer())).ifPresent(m->m.forEach((k,v)->
					{
						if(v.entityType==EntityType.DROPPED_ITEM)
							ProtocolUtil.sendPacket(event.getPlayer(),NmsPacketPlayOutEntityMetadata.newInstance(k,Lists.newArrayList(NmsEntity.newCustomName(ItemStackBuilder.getDropNameWithNum(ObcItemStack.asCraftMirror(WrappedObject.wrap(NmsItemStack.class,v.data.get(NmsEntityItem.getItemStackType()))).getRaw(),event.getPlayer())).getRaw())));
					}));
			});
	}
}
