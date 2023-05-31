package mz.lib.minecraft.bukkitlegacy;

import com.google.common.collect.Lists;
import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.minecraft.bukkitlegacy.entity.*;
import mz.lib.minecraft.bukkitlegacy.event.SendEntityMetadataEvent;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import mz.lib.minecraft.bukkit.obc.ObcItemStack;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLocaleChangeEvent;

import java.util.*;

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
