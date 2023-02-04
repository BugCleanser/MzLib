package mz.lib.minecraft.bukkit.wrappedobc;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrappednms.NmsEntity;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedMethod;

@WrappedBukkitClass(@VersionName("obc.entity.CraftEntity"))
public interface ObcEntity extends WrappedBukkitObject
{
	@WrappedMethod("getHandle")
	NmsEntity getHandle();
}
