package mz.lib.minecraft.item.map;

import mz.lib.wrapper.*;
import org.bukkit.map.*;

import java.awt.*;

@WrappedClass("org.bukkit.map.MapPalette")
public interface WrappedMapPalette extends WrappedObject
{
	static Color[] getColors()
	{
		return WrappedObject.getStatic(WrappedMapPalette.class).staticGetColors();
	}
	static double getDistance(Color c1,Color c2)
	{
		return WrappedObject.getStatic(WrappedMapPalette.class).staticGetDistance(c1,c2);
	}
	@SuppressWarnings("deprecation")
	static byte matchColor(Color color)
	{
		return MapPalette.matchColor(color);
	}
	
	@WrappedFieldAccessor("colors")
	Color[] staticGetColors();
	@WrappedMethod("getDistance")
	double staticGetDistance(Color c1,Color c2);
	
	@Override
	MapPalette getRaw();
}
