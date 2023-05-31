package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.RecipeBookSettings",minVer=16,maxVer=17),@VersionName(value="net.minecraft.stats.RecipeBookSettings",minVer=17)})
public interface NmsRecipeBookSettingsV16 extends WrappedBukkitObject
{
	static NmsRecipeBookSettingsV16 newInstance()
	{
		return WrappedObject.getStatic(NmsRecipeBookSettingsV16.class).staticNewInstance();
	}
	@WrappedConstructor
	NmsRecipeBookSettingsV16 staticNewInstance();
}
