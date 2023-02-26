package mz.lib.minecraft.bukkit.obc;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkit.nms.NmsEnchantment;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedMethod;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass(@VersionalName("obc.enchantments.CraftEnchantment"))
public interface ObcEnchantment extends VersionalWrappedObject
{
	@WrappedMethod("getHandle")
	NmsEnchantment getHandle();
}
