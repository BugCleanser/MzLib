package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;

@VersionalWrappedClass({@VersionalName(value="nms.RegistryID",maxVer=17),@VersionalName(value="net.minecraft.util.RegistryID",minVer=17)})
public interface NmsRegistryID extends VersionalWrappedObject
{
	@VersionalWrappedFieldAccessor(@VersionalName(maxVer=13, value="b"))
	Object[] getBV_13();
	@VersionalWrappedFieldAccessor(@VersionalName(maxVer=13, value="c"))
	int[] getCV_13();
	@VersionalWrappedFieldAccessor(@VersionalName(maxVer=13, value="d"))
	Object[] getDV_13();
	
	@VersionalWrappedMethod(@VersionalName(value="getId",maxVer=13))
	int getIdV_13(Object o);
	
	@VersionalWrappedMethod(@VersionalName(value="e",minVer=13))
	int eV13(int i);
	@VersionalWrappedMethod(@VersionalName(value="d",minVer=13))
	int dV13(Object o);
}
