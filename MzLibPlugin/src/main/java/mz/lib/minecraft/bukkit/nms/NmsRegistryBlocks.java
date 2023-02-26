package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.RegistryBlocks",maxVer=17),@VersionalName(value="net.minecraft.core.RegistryBlocks",minVer=17)})
public interface NmsRegistryBlocks extends NmsRegistryMaterials
{
	@VersionalWrappedMethod({@VersionalName(minVer=13,value="getKey"),@VersionalName(minVer=18, value="b")})
	NmsMinecraftKey getKeyV13(WrappedObject value);
	
	@VersionalWrappedMethod({@VersionalName("get"),@VersionalName(value="a",minVer=18)})
	WrappedObject get(NmsMinecraftKey key);
	default <T extends WrappedObject> T get(NmsMinecraftKey key,Class<T> wrapper)
	{
		return get(key).cast(wrapper);
	}
}
