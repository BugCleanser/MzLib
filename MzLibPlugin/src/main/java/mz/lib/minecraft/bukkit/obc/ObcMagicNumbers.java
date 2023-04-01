package mz.lib.minecraft.bukkit.obc;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkit.nms.NmsItem;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;
import mz.lib.wrapper.*;
import org.bukkit.Material;

@VersionalWrappedClass(@VersionalName("obc.util.CraftMagicNumbers"))
public interface ObcMagicNumbers extends VersionalWrappedObject
{
	@WrappedMethod("getItem")
	NmsItem staticGetItem(Material m);
	static NmsItem getItem(Material m)
	{
		return WrappedObject.getStatic(ObcMagicNumbers.class).staticGetItem(m);
	}
	
	@WrappedMethod("getMaterial")
	Material staticGetMaterial(NmsItem item);
	static  Material getMaterial(NmsItem item)
	{
		return WrappedObject.getStatic(ObcMagicNumbers.class).staticGetMaterial(item);
	}
}
