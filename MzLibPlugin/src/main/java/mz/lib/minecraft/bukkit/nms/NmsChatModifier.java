package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.message.*;
import mz.lib.minecraft.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.ChatModifier",maxVer=17),@VersionalName(value="net.minecraft.network.chat.ChatModifier",minVer=17)})
public interface NmsChatModifier extends VersionalWrappedObject
{
	@VersionalWrappedFieldAccessor(@VersionalName(value="@0",maxVer=16))
	NmsEnumChatFormat getColorV_16();
	@VersionalWrappedFieldAccessor(@VersionalName(value="@0",maxVer=16))
	void setColorV_16(NmsEnumChatFormat color);
	@VersionalWrappedFieldAccessor(@VersionalName(value="@0",minVer=16))
	NmsChatHexColorV16 getColorV16();
	@VersionalWrappedFieldAccessor(@VersionalName(value="@0",minVer=16))
	void setColorV16(NmsChatHexColorV16 color);
}
