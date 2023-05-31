package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.wrapper.WrappedObject;

import java.util.List;
import java.util.stream.Collectors;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayOutMap",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayOutMap",minVer=17)})
public interface NmsPacketPlayOutMap extends NmsPacket
{
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	int getId();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsPacketPlayOutMap setId(int id);
	
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	byte getScale();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsPacketPlayOutMap setScale(byte scale);
	
	default NmsPacketPlayOutMap setIcons(List<NmsMapIcon> icons)
	{
		if(BukkitWrapper.v17)
			setIconsV17(icons.stream().map(i->i.getRaw()).collect(Collectors.toList()));
		else
		{
			NmsMapIconArray a=NmsMapIconArray.newInstance(icons.size());
			for(int i=0;i<a.length();i++)
			{
				a.set(i,icons.get(i));
			}
			setIconsV_17(a);
		}
		return this;
	}
	
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",maxVer=17))
	NmsMapIconArray getIconsV_17();
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",maxVer=17))
	NmsPacketPlayOutMap setIconsV_17(NmsMapIconArray icons);
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",minVer=17))
	List getIconsV17();
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",minVer=17))
	NmsPacketPlayOutMap setIconsV17(List icons);
	
	default NmsPacketPlayOutMap initDataV17()
	{
		if(WrappedObject.isNull(getDataV17()))
			setDataV17(WrappedObject.allocInstance(WorldMapDataV17.class));
		return this;
	}
	default int getX()
	{
		if(BukkitWrapper.v17)
			return getDataV17().getX();
		else
			return getXV_17();
	}
	default NmsPacketPlayOutMap setX(int x)
	{
		if(BukkitWrapper.v17)
		{
			initDataV17().getDataV17().setX(x);
			return this;
		}
		else
			return setXV_17(x);
	}
	default int getZ()
	{
		if(BukkitWrapper.v17)
			return getDataV17().getZ();
		else
			return getZV_17();
	}
	default NmsPacketPlayOutMap setZ(int z)
	{
		if(BukkitWrapper.v17)
		{
			initDataV17().getDataV17().setZ(z);
			return this;
		}
		else
			return setZV_17(z);
	}
	default int getColumns()
	{
		if(BukkitWrapper.v17)
			return getDataV17().getColumns();
		else
			return getColumnsV_17();
	}
	default NmsPacketPlayOutMap setColumns(int columns)
	{
		if(BukkitWrapper.v17)
		{
			initDataV17().getDataV17().setColumns(columns);
			return this;
		}
		else
			return setColumnsV_17(columns);
	}
	default int getRows()
	{
		if(BukkitWrapper.v17)
			return getDataV17().getRows();
		else
			return getRowsV_17();
	}
	default NmsPacketPlayOutMap setRows(int rows)
	{
		if(BukkitWrapper.v17)
		{
			initDataV17().getDataV17().setRows(rows);
			return this;
		}
		else
			return setRowsV_17(rows);
	}
	default byte[] getData()
	{
		if(BukkitWrapper.v17)
			return getDataV17().getData();
		else
			return getDataV_17();
	}
	default NmsPacketPlayOutMap setData(byte[] data)
	{
		if(BukkitWrapper.v17)
		{
			initDataV17().getDataV17().setData(data);
			return this;
		}
		else
			return setDataV_17(data);
	}
	
	@WrappedBukkitFieldAccessor(@VersionName(value="@1",maxVer=17))
	int getXV_17();
	@WrappedBukkitFieldAccessor(@VersionName(value="@1",maxVer=17))
	NmsPacketPlayOutMap setXV_17(int x);
	@WrappedBukkitFieldAccessor(@VersionName(value="@2",maxVer=17))
	int getZV_17();
	@WrappedBukkitFieldAccessor(@VersionName(value="@2",maxVer=17))
	NmsPacketPlayOutMap setZV_17(int z);
	@WrappedBukkitFieldAccessor(@VersionName(value="@3",maxVer=17))
	int getColumnsV_17();
	@WrappedBukkitFieldAccessor(@VersionName(value="@3",maxVer=17))
	NmsPacketPlayOutMap setColumnsV_17(int columns);
	@WrappedBukkitFieldAccessor(@VersionName(value="@4",maxVer=17))
	int getRowsV_17();
	@WrappedBukkitFieldAccessor(@VersionName(value="@4",maxVer=17))
	NmsPacketPlayOutMap setRowsV_17(int rows);
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",maxVer=17))
	byte[] getDataV_17();
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",maxVer=17))
	NmsPacketPlayOutMap setDataV_17(byte[] data);
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",minVer=17))
	WorldMapDataV17 getDataV17();
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",minVer=17))
	NmsPacketPlayOutMap setDataV17(WorldMapDataV17 data);
}
