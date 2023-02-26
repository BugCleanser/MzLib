package mz.lib.minecraft.bukkit.nms;

import mz.lib.Optional;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkit.paper.WrappedComponentPaperV165;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.UUID;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayOutChat",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayOutChat",minVer=17,maxVer=19),@VersionalName(value="net.minecraft.network.protocol.game.ClientboundSystemChatPacket",minVer=19)})
public interface NmsPacketPlayOutChat extends NmsPacket
{
	@Optional
	@VersionalWrappedFieldAccessor({@VersionalName(value="adventure$message",minVer=16.5f,maxVer=19),@VersionalName(value="adventure$content",minVer=19)})
	WrappedComponentPaperV165 getComponentPaperV165();
	@Optional
	@VersionalWrappedFieldAccessor({@VersionalName(value="adventure$message",minVer=16.5f,maxVer=19),@VersionalName(value="adventure$content",minVer=19)})
	NmsPacketPlayOutChat setComponentPaperV165(WrappedComponentPaperV165 component);
	@VersionalWrappedFieldAccessor(@VersionalName(value="components",maxVer=19))
	BaseComponent[] getMd5MsgV_19();
	@VersionalWrappedFieldAccessor(@VersionalName(value="components",maxVer=19))
	NmsPacketPlayOutChat setMd5MsgV_19(BaseComponent[] msg);
	@VersionalWrappedFieldAccessor(@VersionalName(value="a",maxVer=19))
	NmsIChatBaseComponent getNmsMsgV_19();
	@VersionalWrappedFieldAccessor(@VersionalName(value="a",maxVer=19))
	NmsPacketPlayOutChat setNmsMsgV_19(NmsIChatBaseComponent msg);
	@VersionalWrappedFieldAccessor(@VersionalName(minVer=16,value="c",maxVer=19))
	UUID getUidV16_19();
	@VersionalWrappedFieldAccessor(@VersionalName(minVer=16,value="c",maxVer=19))
	NmsPacketPlayOutChat setUidV16_19(UUID id);
	@VersionalWrappedFieldAccessor({@VersionalName(value="content",minVer=19),@VersionalName(value="a",minVer=19)})
	String getJsonV19();
	@VersionalWrappedFieldAccessor({@VersionalName(value="content",minVer=19),@VersionalName(value="a",minVer=19)})
	NmsPacketPlayOutChat setJsonV19(String json);
}
