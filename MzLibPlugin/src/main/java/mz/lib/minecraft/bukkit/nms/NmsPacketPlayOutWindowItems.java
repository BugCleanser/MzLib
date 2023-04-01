package mz.lib.minecraft.bukkit.nms;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.lib.TypeUtil;
import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkit.obc.ObcItemStack;
import mz.lib.*;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayOutWindowItems",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayOutWindowItems",minVer=17)})
public interface NmsPacketPlayOutWindowItems extends NmsPacket
{
	static NmsPacketPlayOutWindowItems newInstance()
	{
		try
		{
			NmsPacketPlayOutWindowItems r=WrappedObject.wrap(NmsPacketPlayOutWindowItems.class,Root.getUnsafe().allocateInstance(WrappedObject.getRawClass(NmsPacketPlayOutWindowItems.class)));
			r.setItems(new ArrayList<>());
			if(Server.instance.v17)
				r.setCursorV17(ObcItemStack.asNMSCopy(new ItemStack(Material.AIR)));
			return r;
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	
	@VersionalWrappedFieldAccessor(@VersionalName("a"))
	int getWindowId();
	@VersionalWrappedFieldAccessor(@VersionalName("a"))
	NmsPacketPlayOutWindowItems setWindowId(int id);
	
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="b"),@VersionalName(minVer=17, value="c")})
	List<Object> getItems();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=17, value="b"),@VersionalName(minVer=17, value="c")})
	NmsPacketPlayOutWindowItems setItems(List<Object> items);
	
	@VersionalWrappedFieldAccessor(@VersionalName(minVer=17, value="d"))
	NmsItemStack getCursorV17();
	@VersionalWrappedFieldAccessor(@VersionalName(minVer=17, value="d"))
	NmsPacketPlayOutWindowItems setCursorV17(NmsItemStack item);
}
