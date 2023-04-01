package mz.lib.minecraft.bukkit.obc;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkit.nms.NmsWorldServer;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.*;

@VersionalWrappedClass(@VersionalName("obc.CraftWorld"))
public interface ObcWorld extends VersionalWrappedObject
{
	@WrappedMethod("getHandle")
	NmsWorldServer getHandle();
}
