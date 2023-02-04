package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;

@WrappedBukkitClass({@VersionName(value="nms.FurnaceRecipe",minVer=13,maxVer=17),@VersionName(value="net.minecraft.world.item.crafting.FurnaceRecipe",minVer=17)})
public interface NmsFurnaceRecipeV13 extends NmsRecipeCookingV13
{
}
