package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;

@WrappedBukkitClass(@VersionName(value="net.minecraft.world.level.saveddata.maps.WorldMap$b",minVer=17))
public interface WorldMapDataV17 extends WrappedBukkitObject
{
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	int getX();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	WorldMapDataV17 setX(int x);
	
	@WrappedBukkitFieldAccessor(@VersionName("@1"))
	int getZ();
	@WrappedBukkitFieldAccessor(@VersionName("@1"))
	WorldMapDataV17 setZ(int z);
	
	@WrappedBukkitFieldAccessor(@VersionName("@2"))
	int getColumns();
	@WrappedBukkitFieldAccessor(@VersionName("@2"))
	WorldMapDataV17 setColumns(int columns);
	
	@WrappedBukkitFieldAccessor(@VersionName("@3"))
	int getRows();
	@WrappedBukkitFieldAccessor(@VersionName("@3"))
	WorldMapDataV17 setRows(int rows);
	
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	byte[] getData();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	WorldMapDataV17 setData(byte[] data);
}
