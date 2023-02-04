package mz.lib.minecraft.bukkit.wrappednms;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.wrapper.WrappedObject;

import java.util.List;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayOutEntityMetadata",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata",minVer=17)})
public interface NmsPacketPlayOutEntityMetadata extends NmsPacket
{
	static NmsPacketPlayOutEntityMetadata newInstance(int entityId,List<Object> dataWatcherItems)
	{
		try
		{
			NmsPacketPlayOutEntityMetadata r=WrappedObject.wrap(NmsPacketPlayOutEntityMetadata.class,Root.getUnsafe().allocateInstance(WrappedObject.getRawClass(NmsPacketPlayOutEntityMetadata.class)));
			r.setEntityId(entityId);
			r.setDataWatcherItems(dataWatcherItems);
			return r;
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	int getEntityId();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsPacketPlayOutEntityMetadata setEntityId(int id);
	
	/**
	 * @return list of DataWatcherItem before 1.19.3, list of DataWatcherSerializedItem later
	 */
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	List<Object> getDataWatcherItems();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsPacketPlayOutEntityMetadata setDataWatcherItems(List<Object> items);
}
