package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.*;

@VersionalWrappedClass(@VersionalName(value="net.minecraft.world.level.saveddata.maps.WorldMap$b",minVer=17))
public interface WorldMapDataV17 extends VersionalWrappedObject
{
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	int getX();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	WorldMapDataV17 setX(int x);
	
	@VersionalWrappedFieldAccessor(@VersionalName("@1"))
	int getZ();
	@VersionalWrappedFieldAccessor(@VersionalName("@1"))
	WorldMapDataV17 setZ(int z);
	
	@VersionalWrappedFieldAccessor(@VersionalName("@2"))
	int getColumns();
	@VersionalWrappedFieldAccessor(@VersionalName("@2"))
	WorldMapDataV17 setColumns(int columns);
	
	@VersionalWrappedFieldAccessor(@VersionalName("@3"))
	int getRows();
	@VersionalWrappedFieldAccessor(@VersionalName("@3"))
	WorldMapDataV17 setRows(int rows);
	
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	byte[] getData();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	WorldMapDataV17 setData(byte[] data);
}
