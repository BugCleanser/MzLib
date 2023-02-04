package mz.lib.minecraft.bukkit.wrappedobc;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrappednms.NmsItem;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Material;

@WrappedBukkitClass(@VersionName("obc.util.CraftMagicNumbers"))
public interface ObcMagicNumbers extends WrappedBukkitObject
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
