package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrappedfastutil.WfIntList;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;

import java.util.Iterator;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayOutEntityDestroy",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy",minVer=17)})
public interface NmsPacketPlayOutEntityDestroy extends NmsPacket
{
	default Iterator<Integer> getIds()
	{
		if(BukkitWrapper.version>=18)
			return ((it.unimi.dsi.fastutil.ints.IntList)getIdsV17().getRaw()).iterator();
		else if(BukkitWrapper.v17)
			return ((org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.ints.IntList)getIdsV17().getRaw()).iterator();
		else
		{
			int[] r=getIdsV_17();
			return new Iterator<Integer>()
			{
				int next=0;
				@Override
				public boolean hasNext()
				{
					return next<r.length;
				}
				@Override
				public Integer next()
				{
					return r[next++];
				}
			};
		}
	}
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",maxVer=17))
	int[] getIdsV_17();
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",minVer=17))
	WfIntList getIdsV17();
}
