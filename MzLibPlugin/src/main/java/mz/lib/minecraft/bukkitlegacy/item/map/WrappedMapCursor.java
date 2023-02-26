package mz.lib.minecraft.bukkitlegacy.item.map;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedMethod;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.map.MapCursor;

@VersionalWrappedClass(@VersionalName("org.bukkit.map.MapCursor"))
public interface WrappedMapCursor extends WrappedObject
{
	@Override
	MapCursor getRaw();
	
	@VersionalWrappedMethod(@VersionalName(value="getCaption",minVer=13))
	String getCaptionV13();
	@VersionalWrappedMethod(@VersionalName(value="setCaption",minVer=13))
	void setCaptionV13(String caption);
}
