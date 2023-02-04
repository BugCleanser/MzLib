package mz.lib.minecraft.bukkit.item.map.win;

import mz.lib.minecraft.bukkit.item.map.MzMapCanvas;
import mz.lib.mzlang.MzObject;
import mz.lib.mzlang.PropAccessor;

import java.util.List;

public interface MapControl extends MzObject
{
	@PropAccessor("parent")
	MapControl getParent();
	@PropAccessor("parent")
	void setParent(MapControl parent);
	
	@PropAccessor("subControls")
	List<MapControl> getSubControls();
	@PropAccessor("subControls")
	void setSubControls(List<MapControl> subControls);
	
	@PropAccessor("canvas")
	MzMapCanvas getCanvas();
	@PropAccessor("canvas")
	void setCanvas(MzMapCanvas canvas);
}
