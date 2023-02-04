package mz.lib.minecraft.bukkit.wrappedobc;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrappednms.NmsWorldServer;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedMethod;

@WrappedBukkitClass(@VersionName("obc.CraftWorld"))
public interface ObcWorld extends WrappedBukkitObject
{
	@WrappedMethod("getHandle")
	NmsWorldServer getHandle();
}
