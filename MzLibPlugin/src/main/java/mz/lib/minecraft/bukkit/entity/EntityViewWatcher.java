package mz.lib.minecraft.bukkit.entity;

import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.ProtocolUtil;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.wrappednms.*;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EntityViewWatcher
{
	public static Map<Player,Map<Integer,EntityViewWatcher>> all=new ConcurrentHashMap<>();
	
	public EntityType entityType;
	public UUID uuid;
	public Map<NmsDataWatcherObject,Object> data;
	public EntityViewWatcher(EntityType entityType)
	{
		this(entityType,null);
	}
	public EntityViewWatcher(EntityType entityType,UUID uuid)
	{
		this.entityType=entityType;
		this.uuid=uuid;
		this.data=new ConcurrentHashMap<>();
	}
	
	public static class Module extends AbsModule
	{
		public static Module instance=new Module();
		public Module()
		{
			super(MzLib.instance,ProtocolUtil.instance);
			reg(new ProtocolUtil.SendListener<>(EventPriority.MONITOR,NmsPacketPlayOutSpawnEntity.class,(pl,pa,c)->all.computeIfAbsent(pl,p->new ConcurrentHashMap<>()).put(pa.getEntityId(),new EntityViewWatcher(pa.getEntityType()))));
			reg(new ProtocolUtil.SendListener<>(EventPriority.MONITOR,NmsPacketPlayOutSpawnEntityExperienceOrb.class,(pl,pa,c)->all.computeIfAbsent(pl,p->new ConcurrentHashMap<>()).put(pa.getEntityId(),new EntityViewWatcher(EntityType.EXPERIENCE_ORB))));
			reg(new ProtocolUtil.SendListener<>(EventPriority.MONITOR,NmsPacketPlayOutNamedEntitySpawn.class,(pl,pa,c)->all.computeIfAbsent(pl,p->new ConcurrentHashMap<>()).put(pa.getEntityId(),new EntityViewWatcher(EntityType.PLAYER))));
			if(BukkitWrapper.version<19)
			{
				reg(new ProtocolUtil.SendListener<>(EventPriority.MONITOR,NmsPacketPlayOutSpawnEntityLivingV_19.class,(pl,pa,c)->all.computeIfAbsent(pl,p->new ConcurrentHashMap<>()).put(pa.getEntityId(),new EntityViewWatcher(pa.getEntityType()))));
				reg(new ProtocolUtil.SendListener<>(EventPriority.MONITOR,NmsPacketPlayOutSpawnEntityPaintingV_19.class,(pl,pa,c)->all.computeIfAbsent(pl,p->new ConcurrentHashMap<>()).put(pa.getEntityId(),new EntityViewWatcher(EntityType.PAINTING))));
			}
			reg(new ProtocolUtil.SendListener<>(EventPriority.MONITOR,NmsPacketPlayOutEntityMetadata.class,(pl,pa,c)->
			{
				Map<Integer,EntityViewWatcher> epws=all.get(pl);
				if(epws==null)
					return;
				EntityViewWatcher epw=epws.get(pa.getEntityId());
				if(epw==null)
					return;
				for(Object o:pa.getDataWatcherItems())
				{
					if(!WrappedObject.getRawClass(NmsDataWatcherItem.class).isAssignableFrom(o.getClass()))
						o=WrappedObject.wrap(NmsDataWatcherSerializedItemV193.class,o).toDataWatcherItem().getRaw();
					NmsDataWatcherItem dwi=WrappedObject.wrap(NmsDataWatcherItem.class,o);
					epw.data.put(dwi.getType(),dwi.getValue());
				}
			}));
			reg(new ProtocolUtil.SendListener<>(EventPriority.MONITOR,NmsPacketPlayOutEntityDestroy.class,(pl,pa,c)->
			{
				Map<Integer,EntityViewWatcher> epws=all.get(pl);
				if(epws!=null)
					for(Iterator<Integer> it=pa.getIds();it.hasNext();)
					{
						int id=it.next();
						epws.remove(id);
					}
			}));
		}
	}
}
