package mz.lib.minecraft.bukkit.wrappedobc;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrappednms.NmsICommandListener;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedMethod;

@WrappedBukkitClass(@VersionName("obc.command.CraftBlockCommandSender"))
public interface ObcBlockCommandSender extends WrappedBukkitObject
{
	@WrappedMethod(value="getTileEntity")
	NmsICommandListener getTileEntity();
}
