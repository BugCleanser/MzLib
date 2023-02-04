package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;

@WrappedBukkitClass({@VersionName(value="nms.RecipeCooking",minVer=13,maxVer=17),@VersionName(value="net.minecraft.world.item.crafting.RecipeCooking",minVer=17)})
public interface NmsRecipeCookingV13 extends NmsIRecipe
{
	@WrappedBukkitFieldAccessor({@VersionName("key"),@VersionName(minVer=17, value="b")})
	NmsMinecraftKey getKey();
	@WrappedBukkitFieldAccessor({@VersionName("group"),@VersionName(minVer=17, value="c")})
	String getGroup();
}
