package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;

@VersionalWrappedClass({@VersionalName(value="nms.RecipeCooking",minVer=13,maxVer=17),@VersionalName(value="net.minecraft.world.item.crafting.RecipeCooking",minVer=17)})
public interface NmsRecipeCookingV13 extends NmsIRecipe
{
	@VersionalWrappedFieldAccessor({@VersionalName("key"),@VersionalName(minVer=17, value="b")})
	NmsMinecraftKey getKey();
	@VersionalWrappedFieldAccessor({@VersionalName("group"),@VersionalName(minVer=17, value="c")})
	String getGroup();
}
