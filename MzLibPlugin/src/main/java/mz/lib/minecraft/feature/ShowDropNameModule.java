package mz.lib.minecraft.feature;

import com.google.common.collect.Lists;
import mz.lib.MzLib;
import mz.lib.minecraft.bukkit.nms.NmsEntity;
import mz.lib.minecraft.bukkit.nms.NmsEntityItem;
import mz.lib.minecraft.bukkit.nms.NmsItemStack;
import mz.lib.minecraft.bukkit.nms.NmsPacketPlayOutEntityMetadata;
import mz.lib.minecraft.bukkit.obc.ObcItemStack;
import mz.lib.minecraft.bukkitlegacy.ProtocolUtil;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.entity.EntityViewWatcher;
import mz.lib.minecraft.event.entity.SendEntityMetadataEvent;
import mz.lib.minecraft.event.entity.player.PlayerLocaleChangeEvent;
import mz.lib.module.*;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventPriority;

import java.util.*;

public class ShowDropNameModule extends MzModule
{
	public static ShowDropNameModule instance=new ShowDropNameModule();
	
	@EventHandler(order=10)
	void onSendEntityMetadata(SendEntityMetadataEvent event)
	{
		if(event.isCancelled())
			return;
		if(event.getEntityType()!= EntityType.DROPPED_ITEM)
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
	
	@EventHandler(priority= EventPriority.MONITOR, ignoreCancelled=true)
	void onPlayerLocaleChange(PlayerLocaleChangeEvent event)
	{
		if(MzLib.instance.getConfig().getBoolean("func.showDropName",true))
			Bukkit.getScheduler().runTask(MzLib.instance,()->
			{
				if(event.getPlayer().isOnline())
					Optional.ofNullable(EntityViewWatcher.all.get(event.getPlayer())).ifPresent(m->m.forEach((k, v)->
					{
						if(v.entityType==EntityType.DROPPED_ITEM)
							ProtocolUtil.sendPacket(event.getPlayer(), NmsPacketPlayOutEntityMetadata.newInstance(k,Lists.newArrayList(NmsEntity.newCustomName(ItemStackBuilder.getDropNameWithNum(ObcItemStack.asCraftMirror(WrappedObject.wrap(NmsItemStack.class,v.data.get(NmsEntityItem.getItemStackType()))).getRaw(),event.getPlayer())).getRaw())));
					}));
			});
	}
}
