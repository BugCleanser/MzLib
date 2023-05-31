package mz.lib.minecraft.bukkitlegacy.item.map;

import mz.lib.IncrementalIdMap;
import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkitlegacy.*;
import mz.lib.minecraft.bukkitlegacy.entity.*;
import mz.lib.minecraft.bukkitlegacy.event.SendEntityMetadataEvent;
import mz.lib.minecraft.bukkitlegacy.event.ShowInventoryItemEvent;
import mz.lib.minecraft.bukkitlegacy.item.MzItem;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import mz.mzlib.*;
import mz.lib.minecraft.bukkit.nms.NmsDataWatcherItem;
import mz.lib.minecraft.bukkit.nms.NmsEntityItemFrame;
import mz.lib.minecraft.bukkit.nms.NmsItemStack;
import mz.lib.minecraft.bukkit.nms.NmsPacketPlayOutEntityDestroy;
import mz.lib.minecraft.bukkit.obc.ObcItemStack;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MzMapProcessor extends AbsModule
{
	public static MzMapProcessor instance=new MzMapProcessor();
	public MzMapProcessor()
	{
		super(MzLib.instance,ProtocolUtil.instance);
	}
	
	public Map<Player,IncrementalIdMap<MzMap>> views=new ConcurrentHashMap<>();
	public Map<Player,Map<Integer,Integer>> hotbarRefs=new ConcurrentHashMap<>();
	public Map<Player,Map<Integer,Integer>> frameRefs=new ConcurrentHashMap<>();
	public Map<NmsItemStack,Map<Player,Map<Integer,MzMapCanvas>>> buf=new ConcurrentHashMap<>();
	
	@EventHandler(priority=EventPriority.HIGH,ignoreCancelled=true)
	public void onSendEntityMetadata(SendEntityMetadataEvent event)
	{
		l1:
		{
			if(event.getEntityType()==EntityType.ITEM_FRAME)
				break l1;
			if(Server.instance.v17)
				if(event.getEntityType()==EntityTypeUtil.glowItemFrameV17)
					break l1;
			return;
		}
		init(event.player);
		Map<Integer,Integer> fs=frameRefs.get(event.getPlayer());
		if(fs.containsKey(event.entityId))
		{
			Integer id=fs.remove(event.entityId);
			MzMap d=views.get(event.player).remove(id);
			Map<Player,Map<Integer,MzMapCanvas>> ps=buf.get(d.getItemStack().getHandle());
			Map<Integer,MzMapCanvas> is=ps.get(event.player);
			is.remove(id);
			if(is.isEmpty())
			{
				ps.remove(event.player);
				if(ps.isEmpty())
					buf.remove(d.getItemStack().getHandle());
			}
		}
		for(NmsDataWatcherItem i:event.datas)
		{
			if(NmsEntityItemFrame.getItemType().equals(i.getType()))
			{
				MzItem mzitem=MzItem.get(ObcItemStack.asBukkitCopy(i.getValue(NmsItemStack.class)));
				if(mzitem instanceof MzMap)
				{
					mzitem.setItemStack(ObcItemStack.asCraftCopy(mzitem.getItemStack().getRaw()));
					event.datas.remove(i);
					event.datas.add(NmsDataWatcherItem.newInstance(NmsEntityItemFrame.getItemType(),mzitem.getItemStack().getHandle().getRaw()));
					int id=views.get(event.player).alloc((MzMap)mzitem);
					if(!buf.containsKey(mzitem.getItemStack().getHandle()))
						buf.put(mzitem.getItemStack().getHandle(),new ConcurrentHashMap<>());
					Map<Player,Map<Integer,MzMapCanvas>> b=buf.get(mzitem.getItemStack().getHandle());
					if(!b.containsKey(event.player))
						b.put(event.player,new ConcurrentHashMap<>());
					b.get(event.player).put(id,new MzMapCanvas(128,128));
					fs.put(event.entityId,id);
					new ItemStackBuilder(mzitem.getItemStack()).setMapId(id+MzLib.instance.getConfig().getInt("mapOffset",16384));
					((MzMap)mzitem).redraw(event.player,0,0,128,128);
				}
				break;
			}
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
	public void onInventoryClick(InventoryClickEvent event)
	{
		if(event.getWhoClicked() instanceof Player&&event.getWhoClicked().getGameMode()!=GameMode.CREATIVE)
			if(MzItem.get(event.getCurrentItem()) instanceof MzMap||MzItem.get(event.getCursor()) instanceof MzMap)
			{
				Bukkit.getScheduler().runTask(MzLib.instance,()->((Player)event.getWhoClicked()).updateInventory());
			}
	}
	
	@EventHandler(priority=EventPriority.LOW,ignoreCancelled=true)
	public void onShowInventoryItem(ShowInventoryItemEvent event)
	{
		init(event.player);
		if(event.slot>event.player.getOpenInventory().getTopInventory().getSize())
		{
			int slot=event.player.getOpenInventory().convertSlot(event.slot);
			if(slot<9||slot==40)
			{
				Integer lastId=hotbarRefs.get(event.player).remove(slot);
				if(lastId!=null)
				{
					MzMap d=views.get(event.player).remove(lastId);
					Map<Player,Map<Integer,MzMapCanvas>> ps=buf.get(d.getItemStack().getHandle());
					Map<Integer,MzMapCanvas> is=ps.get(event.player);
					is.remove(lastId);
					if(is.isEmpty())
					{
						ps.remove(event.player);
						if(ps.isEmpty())
							buf.remove(d.getItemStack().getHandle());
					}
				}
				MzItem mzitem=MzItem.get(event.itemStack);
				if(mzitem instanceof MzMap)
				{
					int id=views.get(event.player).alloc((MzMap)mzitem);
					if(!buf.containsKey(mzitem.getItemStack().getHandle()))
						buf.put(mzitem.getItemStack().getHandle(),new ConcurrentHashMap<>());
					Map<Player,Map<Integer,MzMapCanvas>> b=buf.get(mzitem.getItemStack().getHandle());
					if(!b.containsKey(event.player))
						b.put(event.player,new ConcurrentHashMap<>());
					b.get(event.player).put(id,new MzMapCanvas(128,128));
					hotbarRefs.get(event.player).put(slot,id);
					event.item.setMapId(id+MzLib.instance.getConfig().getInt("mapOffset",16384));
					((MzMap)mzitem).redraw(event.player,0,0,128,128);
				}
			}
		}
	}
	
	@Override
	public void onEnable()
	{
		reg(new ProtocolUtil.SendListener<>(EventPriority.HIGHEST,NmsPacketPlayOutEntityDestroy.class,(pl,pa,c)->
		{
			init(pl);
			Map<Integer,Integer> fs=frameRefs.get(pl);
			for(Iterator<Integer> it=pa.getIds();it.hasNext();)
			{
				int i=it.next();
				if(fs.containsKey(i))
				{
					Integer id=fs.remove(i);
					MzMap d=views.get(pl).remove(id);
					Map<Player,Map<Integer,MzMapCanvas>> ps=buf.get(d.getItemStack().getHandle());
					Map<Integer,MzMapCanvas> is=ps.get(pl);
					is.remove(id);
					if(is.isEmpty())
					{
						ps.remove(pl);
						if(ps.isEmpty())
							buf.remove(d.getItemStack().getHandle());
					}
				}
			}
		}));
	}
	public void init(Player player)
	{
		if(!views.containsKey(player))
			views.put(player,new IncrementalIdMap<>());
		if(!hotbarRefs.containsKey(player))
			hotbarRefs.put(player,new ConcurrentHashMap<>());
		if(!frameRefs.containsKey(player))
			frameRefs.put(player,new ConcurrentHashMap<>());
	}
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		views.remove(event.getPlayer());
		hotbarRefs.remove(event.getPlayer());
		frameRefs.remove(event.getPlayer());
		for(Iterator<Map.Entry<NmsItemStack,Map<Player,Map<Integer,MzMapCanvas>>>> it=buf.entrySet().iterator();it.hasNext();)
		{
			Map.Entry<NmsItemStack,Map<Player,Map<Integer,MzMapCanvas>>> i=it.next();
			i.getValue().remove(event.getPlayer());
			if(i.getValue().isEmpty())
				it.remove();
		}
	}
}
