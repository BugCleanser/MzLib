package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.RecipeBookSettings",minVer=16,maxVer=17),@VersionalName(value="net.minecraft.stats.RecipeBookSettings",minVer=17)})
public interface NmsRecipeBookSettingsV16 extends VersionalWrappedObject
{
	static NmsRecipeBookSettingsV16 newInstance()
	{
		return WrappedObject.getStatic(NmsRecipeBookSettingsV16.class).staticNewInstance();
	}
	@WrappedConstructor
	NmsRecipeBookSettingsV16 staticNewInstance();
}
