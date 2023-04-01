package mz.lib.minecraft.bukkit.obc;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkit.nms.NmsEntity;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.*;

@VersionalWrappedClass(@VersionalName("obc.entity.CraftEntity"))
public interface ObcEntity extends VersionalWrappedObject
{
	@WrappedMethod("getHandle")
	NmsEntity getHandle();
}
