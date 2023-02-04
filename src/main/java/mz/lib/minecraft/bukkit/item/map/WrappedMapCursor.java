package mz.lib.minecraft.bukkit.item.map;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.map.MapCursor;

@WrappedBukkitClass(@VersionName("org.bukkit.map.MapCursor"))
public interface WrappedMapCursor extends WrappedObject
{
	@Override
	MapCursor getRaw();
	
	@WrappedBukkitMethod(@VersionName(value="getCaption",minVer=13))
	String getCaptionV13();
	@WrappedBukkitMethod(@VersionName(value="setCaption",minVer=13))
	void setCaptionV13(String caption);
}
