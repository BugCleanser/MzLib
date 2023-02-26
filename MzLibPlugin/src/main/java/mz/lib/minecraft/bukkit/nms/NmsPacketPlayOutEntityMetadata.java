package mz.lib.minecraft.bukkit.nms;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.lib.TypeUtil;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;
import mz.lib.wrapper.WrappedObject;

import java.util.List;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayOutEntityMetadata",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata",minVer=17)})
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
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	int getEntityId();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsPacketPlayOutEntityMetadata setEntityId(int id);
	
	/**
	 * @return list of DataWatcherItem before 1.19.3, list of DataWatcherSerializedItem later
	 */
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	List<Object> getDataWatcherItems();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsPacketPlayOutEntityMetadata setDataWatcherItems(List<Object> items);
}
