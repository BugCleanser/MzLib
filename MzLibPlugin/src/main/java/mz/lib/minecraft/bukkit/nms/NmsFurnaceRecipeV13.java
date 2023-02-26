package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;

@VersionalWrappedClass({@VersionalName(value="nms.FurnaceRecipe",minVer=13,maxVer=17),@VersionalName(value="net.minecraft.world.item.crafting.FurnaceRecipe",minVer=17)})
public interface NmsFurnaceRecipeV13 extends NmsRecipeCookingV13
{
}
