package mz.lib.minecraft.bukkit.wrappedobc;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrappednms.NmsEnchantment;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedMethod;

@WrappedBukkitClass(@VersionName("obc.enchantments.CraftEnchantment"))
public interface ObcEnchantment extends WrappedBukkitObject
{
	@WrappedMethod("getHandle")
	NmsEnchantment getHandle();
}
