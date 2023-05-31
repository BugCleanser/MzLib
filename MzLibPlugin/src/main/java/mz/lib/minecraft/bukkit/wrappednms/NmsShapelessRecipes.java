package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;

@WrappedBukkitClass({@VersionName(value="nms.ShapelessRecipes",maxVer=17),@VersionName(value="net.minecraft.world.item.crafting.ShapelessRecipes",minVer=17)})
public interface NmsShapelessRecipes extends WrappedBukkitObject
{
}
