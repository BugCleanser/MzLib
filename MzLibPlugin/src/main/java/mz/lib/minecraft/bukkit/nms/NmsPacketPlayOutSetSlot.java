package mz.lib.minecraft.bukkit.nms;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.lib.TypeUtil;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;
import mz.lib.wrapper.WrappedObject;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayOutSetSlot",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayOutSetSlot",minVer=17)})
public interface NmsPacketPlayOutSetSlot extends NmsPacket
{
	static NmsPacketPlayOutSetSlot newInstance()
	{
		try
		{
			return WrappedObject.wrap(NmsPacketPlayOutSetSlot.class,Root.getUnsafe().allocateInstance(WrappedObject.getRawClass(NmsPacketPlayOutSetSlot.class)));
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="a"),@VersionalName(minVer=17, value="c")})
	int getWindowId();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="a"),@VersionalName(minVer=17, value="c")})
	NmsPacketPlayOutSetSlot setWindowId(int id);
	@VersionalWrappedFieldAccessor(@VersionalName(value="d",minVer=17))
	int getRevisionV17();
	@VersionalWrappedFieldAccessor(@VersionalName(value="d",minVer=17))
	NmsPacketPlayOutSetSlot setRevisionV17(int revision);
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="b"),@VersionalName(minVer=17, value="e")})
	int getSlot();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="b"),@VersionalName(minVer=17, value="e")})
	NmsPacketPlayOutSetSlot setSlot(int slot);
	
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="c"),@VersionalName(minVer=17, value="f")})
	NmsItemStack getItem();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="c"),@VersionalName(minVer=17, value="f")})
	NmsPacketPlayOutSetSlot setItem(NmsItemStack item);
}
