package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedObject;
import mz.lib.wrapper.*;
import org.bukkit.map.MapCursor;

@VersionalWrappedClass({@VersionalName(value="nms.MapIcon",maxVer=17),@VersionalName(value="net.minecraft.world.level.saveddata.maps.MapIcon",minVer=17)})
public interface NmsMapIcon extends VersionalWrappedObject
{
	@VersionalWrappedClass({@VersionalName(value="nms.MapIcon$Type",maxVer=17),@VersionalName(value="net.minecraft.world.level.saveddata.maps.MapIcon$Type",minVer=17)})
	static interface NmsMapIconType extends VersionalWrappedObject
	{
		Enum<?> getRaw();
	}
	
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsMapIconType getType();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsMapIcon setType(NmsMapIconType type);
	default NmsMapIcon setType(MapCursor.Type type)
	{
		return setType(WrappedObject.wrap(NmsMapIconType.class,WrappedObject.getRawClass(NmsMapIconType.class).getEnumConstants()[type.ordinal()]));
	}
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	byte getX();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsMapIcon setX(byte x);
	@VersionalWrappedFieldAccessor(@VersionalName("@1"))
	byte getY();
	@VersionalWrappedFieldAccessor(@VersionalName("@1"))
	NmsMapIcon setY(byte y);
	@VersionalWrappedFieldAccessor(@VersionalName("@2"))
	byte getRotation();
	@VersionalWrappedFieldAccessor(@VersionalName("@2"))
	NmsMapIcon setRotation(byte rotation);
	@VersionalWrappedFieldAccessor(@VersionalName(value="@0",minVer=13))
	NmsIChatBaseComponent getNameV13();
	@VersionalWrappedFieldAccessor(@VersionalName(value="@0",minVer=13))
	NmsMapIcon setNameV13(NmsIChatBaseComponent name);
	
	static NmsMapIcon newInstance()
	{
		return WrappedObject.allocInstance(NmsMapIcon.class);
	}
}
