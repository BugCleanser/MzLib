package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.map.MapCursor;

@WrappedBukkitClass({@VersionName(value="nms.MapIcon",maxVer=17),@VersionName(value="net.minecraft.world.level.saveddata.maps.MapIcon",minVer=17)})
public interface NmsMapIcon extends WrappedBukkitObject
{
	@WrappedBukkitClass({@VersionName(value="nms.MapIcon$Type",maxVer=17),@VersionName(value="net.minecraft.world.level.saveddata.maps.MapIcon$Type",minVer=17)})
	static interface NmsMapIconType extends WrappedBukkitObject
	{
		Enum<?> getRaw();
	}
	
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsMapIconType getType();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsMapIcon setType(NmsMapIconType type);
	default NmsMapIcon setType(MapCursor.Type type)
	{
		return setType(WrappedObject.wrap(NmsMapIconType.class,WrappedObject.getRawClass(NmsMapIconType.class).getEnumConstants()[type.ordinal()]));
	}
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	byte getX();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsMapIcon setX(byte x);
	@WrappedBukkitFieldAccessor(@VersionName("@1"))
	byte getY();
	@WrappedBukkitFieldAccessor(@VersionName("@1"))
	NmsMapIcon setY(byte y);
	@WrappedBukkitFieldAccessor(@VersionName("@2"))
	byte getRotation();
	@WrappedBukkitFieldAccessor(@VersionName("@2"))
	NmsMapIcon setRotation(byte rotation);
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",minVer=13))
	NmsIChatBaseComponent getNameV13();
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",minVer=13))
	NmsMapIcon setNameV13(NmsIChatBaseComponent name);
	
	static NmsMapIcon newInstance()
	{
		return WrappedObject.allocInstance(NmsMapIcon.class);
	}
}
