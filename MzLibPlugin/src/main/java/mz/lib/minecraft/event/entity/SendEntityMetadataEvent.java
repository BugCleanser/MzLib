package mz.lib.minecraft.event.entity;

import mz.lib.ClassUtil;
import mz.lib.TypeUtil;
import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.minecraft.bukkitlegacy.*;
import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import mz.lib.minecraft.entity.*;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;

import java.util.*;
import java.util.stream.Collectors;

public class SendEntityMetadataEvent extends Event implements Cancellable
{
	public @Deprecated
	static HandlerList handlers=new HandlerList();
	public int entityId;
	public List<NmsDataWatcherItem> datas;
	public Player player;
	public @Deprecated
	boolean cancelled=false;
	public SendEntityMetadataEvent(Player player,int entityId,List<Object> datas)
	{
		super(!Bukkit.getServer().isPrimaryThread());
		this.player=player;
		this.entityId=entityId;
			this.datas=TypeUtil.cast(datas.stream().map(o->
			{
				if(Server.instance.version>=19.3f)
					if(WrappedObject.getRawClass(NmsDataWatcherSerializedItemV193.class).isAssignableFrom(o.getClass()))
						return WrappedObject.wrap(NmsDataWatcherSerializedItemV193.class,o).toDataWatcherItem();
				return WrappedObject.wrap(NmsDataWatcherItem.class,o);
			}).collect(Collectors.toList()));
	}
	
	public static class Module extends AbsModule
	{
		public static Module instance=new Module();
		public Module()
		{
			super(MzLib.instance,ProtocolUtil.instance);
			reg(new ProtocolUtil.SendListener<>(EventPriority.HIGHEST,NmsPacketPlayOutEntityMetadata.class,(player,packet,cancelled)->
			{
				if(cancelled.get())
					return;
				packet.setRaw(ClassUtil.clone(packet.getRaw()));
				SendEntityMetadataEvent e=new SendEntityMetadataEvent(player,packet.getEntityId(),packet.getDataWatcherItems());
				Bukkit.getPluginManager().callEvent(e);
				if(!e.cancelled)
				{
					packet.setEntityId(e.entityId);
					packet.setDataWatcherItems(e.toNmsDatas());
				}
				else
					cancelled.set(true);
			}));
		}
	}
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
	public List<Object> toNmsDatas()
	{
		if(Server.instance.version<19.3f)
			return datas.stream().map(i->i.getRaw()).collect(Collectors.toList());
		else
			return datas.stream().map(NmsDataWatcherSerializedItemV193::newInstance).map(i->i.getRaw()).collect(Collectors.toList());
	}
	@Override
	public HandlerList getHandlers()
	{
		return getHandlerList();
	}
	@Override
	public boolean isCancelled()
	{
		return cancelled;
	}
	@Override
	public void setCancelled(boolean cancelled)
	{
		this.cancelled=cancelled;
	}
	public Player getPlayer()
	{
		return player;
	}
	public EntityViewWatcher getWatcher()
	{
		return Optional.ofNullable(EntityViewWatcher.all.get(player)).map(s->s.get(entityId)).orElse(null);
	}
	public EntityType getEntityType()
	{
		return Optional.ofNullable(getWatcher()).map(w->w.entityType).orElse(null);
	}
	public void remove(NmsDataWatcherObject type)
	{
		datas.removeIf(nmsDataWatcherItem->type.equals(nmsDataWatcherItem.getType()));
	}
	public void put(NmsDataWatcherItem item)
	{
		put(item.getType(),item.getValue());
	}
	public void put(NmsDataWatcherObject type,Object value)
	{
		remove(type);
		datas.add(NmsDataWatcherItem.newInstance(type,value));
	}
	public Object get(NmsDataWatcherObject type)
	{
		for(NmsDataWatcherItem data:datas)
		{
			if(type.equals(data.getType()))
				return data.getValue();
		}
		return null;
	}
	public <T extends WrappedObject> T get(NmsDataWatcherObject type,Class<T> wrapper)
	{
		return WrappedObject.wrap(wrapper,get(type));
	}
}
