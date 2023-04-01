package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedObject;
import mz.lib.wrapper.*;

import java.util.Map;

@VersionalWrappedClass(@VersionalName(value="nms.RegistrySimple",maxVer=13))
public interface NmsRegistrySimpleV_13 extends VersionalWrappedObject
{
	@VersionalWrappedFieldAccessor(@VersionalName("c"))
	Map<Object,Object> getMap();
	
	@VersionalWrappedMethod(@VersionalName("get"))
	WrappedObject get(WrappedObject key);
}
