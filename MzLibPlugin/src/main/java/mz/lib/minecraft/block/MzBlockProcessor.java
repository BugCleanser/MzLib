package mz.lib.minecraft.block;

import com.google.common.collect.*;
import mz.lib.minecraft.MzLibMinecraft;
import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.minecraft.bukkitlegacy.*;
import mz.lib.minecraft.item.*;
import mz.lib.module.MzModule;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.world.*;
import org.bukkit.scheduler.*;

import java.util.*;

public class MzBlockProcessor extends MzModule
{
	public static MzBlockProcessor instance=new MzBlockProcessor();
	
	public BiMap<Chunk,Entity> reservoirs=HashBiMap.create();
	public Map<Chunk,Map<Block,MzBlock>> mzBlocks=new HashMap<>();
	
	@Override
	public void onLoad()
	{
		depend(MzItemRegistrar.instance);
		reg(MzBlockReservoirItem.class);

		Bukkit.getScheduler().runTaskTimer(getPlugin(),()->
		{
			Queue<Chunk> chunks=new ArrayDeque<>();
			for(World w:Bukkit.getWorlds())
				Collections.addAll(chunks,w.getLoadedChunks());
			int speed=getPlugin().getConfig().getInt("blocks.maxSaveSpeed",16);
			BukkitTask[] task=new BukkitTask[1];
			task[0]=Bukkit.getScheduler().runTaskTimer(getPlugin(),()->
			{
				for(int i=0;i<speed;i++)
				{
					if(chunks.isEmpty())
					{
						task[0].cancel();
						return;
					}
					save(chunks.poll());
				}
			},0,1);
		},0,getPlugin().getConfig().getInt("blocks.saveInterval",1200));
	}
	
	public void load(Chunk chunk)
	{
		for(Entity e:chunk.getEntities())
		{
			if(e instanceof ArmorStand)
			{
				if(e.getScoreboardTags().contains("MzBlockReservoir"))
				{
					if(reservoirs.containsKey(chunk))
						throw new RuntimeException("???");
					reservoirs.put(chunk,e);
					for(NmsNBTTagCompound data:((MzBlockReservoirItem)MzItem.get(((ArmorStand)e).getHelmet())).getData().values(NmsNBTTagCompound.class))
					{
					
					}
				}
			}
		}
	}
	public void save(Chunk chunk)
	{
	
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onChunkLoad(ChunkLoadEvent event)
	{
		load(event.getChunk());
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onChunkUnload(ChunkUnloadEvent event)
	{
		save(event.getChunk());
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onWorldSave(WorldSaveEvent event)
	{
		for(Chunk c:event.getWorld().getLoadedChunks())
			save(c);
	}
}
