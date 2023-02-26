package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedMethod;
import mz.lib.minecraft.wrapper.VersionalWrappedObject;

@VersionalWrappedClass(@VersionalName(value="net.minecraft.util.RandomSource",minVer=19))
public interface NmsRandomSourceV19 extends VersionalWrappedObject
{
	@VersionalWrappedMethod(@VersionalName("@0"))
	float nextFloat();
}
