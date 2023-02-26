package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkit.fastutil.WfIntList;
import mz.lib.wrapper.*;
import mz.mzlib.*;
import mz.mzlib.wrapper.*;

import java.util.Iterator;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayOutEntityDestroy",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy",minVer=17)})
public interface NmsPacketPlayOutEntityDestroy extends NmsPacket
{
	static NmsPacketPlayOutEntityDestroy newInstance(int ...ids)
	{
		return WrappedObject.getStatic(NmsPacketPlayOutEntityDestroy.class).staticNewInstance(ids);
	}
	@VersionalWrappedConstructor
	NmsPacketPlayOutEntityDestroy staticNewInstance(int ...ids);
	default Iterator<Integer> getIds()
	{
		if(Server.instance.version>=18)
			return ((it.unimi.dsi.fastutil.ints.IntList)getIdsV17().getRaw()).iterator();
		else if(Server.instance.v17)
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
	@VersionalWrappedFieldAccessor(@VersionalName(value="@0",maxVer=17))
	int[] getIdsV_17();
	@VersionalWrappedFieldAccessor(@VersionalName(value="@0",minVer=17))
	WfIntList getIdsV17();
}
