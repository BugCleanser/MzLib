package mz.lib.minecraft.bukkit.wrappednms;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayOutSetSlot",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayOutSetSlot",minVer=17)})
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
	
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="a"),@VersionName(minVer=17, value="c")})
	int getWindowId();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="a"),@VersionName(minVer=17, value="c")})
	NmsPacketPlayOutSetSlot setWindowId(int id);
	@WrappedBukkitFieldAccessor(@VersionName(value="d",minVer=17))
	int getRevisionV17();
	@WrappedBukkitFieldAccessor(@VersionName(value="d",minVer=17))
	NmsPacketPlayOutSetSlot setRevisionV17(int revision);
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="b"),@VersionName(minVer=17, value="e")})
	int getSlot();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="b"),@VersionName(minVer=17, value="e")})
	NmsPacketPlayOutSetSlot setSlot(int slot);
	
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="c"),@VersionName(minVer=17, value="f")})
	NmsItemStack getItem();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="c"),@VersionName(minVer=17, value="f")})
	NmsPacketPlayOutSetSlot setItem(NmsItemStack item);
}
