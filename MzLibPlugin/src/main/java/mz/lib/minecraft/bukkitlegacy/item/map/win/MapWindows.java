package mz.lib.minecraft.bukkitlegacy.item.map.win;

import mz.lib.minecraft.bukkitlegacy.item.map.MapScreen;
import mz.lib.mzlang.PropAccessor;

public interface MapWindows extends MapControl
{
	@PropAccessor("screen")
	MapScreen getScreen();
	@PropAccessor("screen")
	void setScreen(MapScreen screen);
	
	//	@Override
//	default List<NmsMapIcon> getIcons(Player player)
//	{
//		return Lists.newArrayList(NmsMapIcon.newInstance().setType(MapCursor.Type.RED_MARKER).setX((byte)0).setY((byte)0).setRotation((byte)14));
//	}
}
