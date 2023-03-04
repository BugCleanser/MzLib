package mz.lib.minecraft.bukkit.nms;

import mz.lib.*;
import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.EnumChatFormat",maxVer=17),@VersionalName(value="net.minecraft.EnumChatFormat",minVer=17)})
public interface NmsEnumChatFormat extends VersionalWrappedObject
{
	static NmsEnumChatFormat fromName(String name)
	{
		return WrappedObject.wrap(NmsEnumChatFormat.class,Enum.valueOf(TypeUtil.cast(WrappedObject.getRawClass(NmsEnumChatFormat.class)),name));
	}
}
