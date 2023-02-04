package mz.lib.minecraft.bukkit.wrappednms;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrappedobc.ObcItemStack;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayOutWindowItems",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayOutWindowItems",minVer=17)})
public interface NmsPacketPlayOutWindowItems extends NmsPacket
{
	static NmsPacketPlayOutWindowItems newInstance()
	{
		try
		{
			NmsPacketPlayOutWindowItems r=WrappedObject.wrap(NmsPacketPlayOutWindowItems.class,Root.getUnsafe().allocateInstance(WrappedObject.getRawClass(NmsPacketPlayOutWindowItems.class)));
			r.setItems(new ArrayList<>());
			if(BukkitWrapper.v17)
				r.setCursorV17(ObcItemStack.asNMSCopy(new ItemStack(Material.AIR)));
			return r;
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	
	@WrappedBukkitFieldAccessor(@VersionName("a"))
	int getWindowId();
	@WrappedBukkitFieldAccessor(@VersionName("a"))
	NmsPacketPlayOutWindowItems setWindowId(int id);
	
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="b"),@VersionName(minVer=17, value="c")})
	List<Object> getItems();
	@WrappedBukkitFieldAccessor({@VersionName(maxVer=17, value="b"),@VersionName(minVer=17, value="c")})
	NmsPacketPlayOutWindowItems setItems(List<Object> items);
	
	@WrappedBukkitFieldAccessor(@VersionName(minVer=17, value="d"))
	NmsItemStack getCursorV17();
	@WrappedBukkitFieldAccessor(@VersionName(minVer=17, value="d"))
	NmsPacketPlayOutWindowItems setCursorV17(NmsItemStack item);
}
