package mz.lib.minecraft.bukkit.obc;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkit.nms.NmsICommandListener;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedMethod;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass(@VersionalName("obc.command.CraftBlockCommandSender"))
public interface ObcBlockCommandSender extends VersionalWrappedObject
{
	@WrappedMethod(value="getTileEntity")
	NmsICommandListener getTileEntity();
}
