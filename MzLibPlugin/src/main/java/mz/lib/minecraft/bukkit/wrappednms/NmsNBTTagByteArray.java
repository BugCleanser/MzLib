package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;

@WrappedBukkitClass({@VersionName(value="nms.NBTTagByteArray",maxVer=17),@VersionName(value="net.minecraft.nbt.NBTTagByteArray",minVer=17)})
public interface NmsNBTTagByteArray extends NmsNBTBase
{
	@WrappedBukkitFieldAccessor(@VersionName({"data","@0"}))
	byte[] getData();
	@WrappedBukkitFieldAccessor(@VersionName({"data","@0"}))
	NmsNBTTagByteArray setData(byte[] data);
}
