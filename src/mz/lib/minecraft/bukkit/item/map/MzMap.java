package mz.lib.minecraft.bukkit.item.map;

import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.ProtocolUtil;
import mz.lib.minecraft.bukkit.item.MzItem;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.wrappednms.NmsMapIcon;
import mz.lib.minecraft.bukkit.wrappednms.NmsPacketPlayOutMap;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MzMap extends MzItem
{
	void onRedraw(Player player,MzMapCanvas canvas,int x,int y,int width,int height);
	default List<NmsMapIcon> getIcons(Player player)
	{
		return null;
	}
	default void redraw(int x,int y,int width,int height)
	{
		for(Player p:MzMapProcessor.instance.buf.get(getItemStack().getHandle()).keySet())
		{
			redraw(p,x,y,width,height);
		}
	}
	default void redraw(Player player,int x,int y,int width,int height)
	{
		Map<Player,Map<Integer,MzMapCanvas>> b=MzMapProcessor.instance.buf.get(getItemStack().getHandle());
		if(b!=null&&b.containsKey(player))
			for(Map.Entry<Integer,MzMapCanvas> e:b.get(player).entrySet())
			{
				onRedraw(player,e.getValue(),x,y,width,height);
				NmsPacketPlayOutMap packet=WrappedObject.allocInstance(NmsPacketPlayOutMap.class);
				packet.setId(e.getKey()+MzLib.instance.getConfig().getInt("mapOffset",16384));
				packet.setScale((byte)0);
				List<NmsMapIcon> bis=getIcons(player);
				if(bis==null)
					packet.setIcons(new ArrayList<>());
				else
					packet.setIcons(bis);
				packet.setX(x);
				packet.setZ(y);
				packet.setColumns(width);
				packet.setRows(height);
				packet.setData(e.getValue().data);
				ProtocolUtil.sendPacket(player,packet);
			}
	}
	
	@Override
	default void onSet(Player player)
	{
		new ItemStackBuilder(getItemStack()).removeMapId();
	}
	
	@Override
	default String getRawIdV_13()
	{
		return "minecraft:filled_map";
	}
}
