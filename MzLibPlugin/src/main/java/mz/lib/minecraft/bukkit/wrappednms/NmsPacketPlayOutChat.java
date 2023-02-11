package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.Optional;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.paper.WrappedComponentPaperV17;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.UUID;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayOutChat",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayOutChat",minVer=17,maxVer=19),@VersionName(value="net.minecraft.network.protocol.game.ClientboundSystemChatPacket",minVer=19)})
public interface NmsPacketPlayOutChat extends NmsPacket
{
	@Optional
	@WrappedBukkitFieldAccessor({@VersionName(value="adventure$message",maxVer=19),@VersionName(value="adventure$content",minVer=19)})
	WrappedComponentPaperV17 getComponentPaperV17();
	@Optional
	@WrappedBukkitFieldAccessor({@VersionName(value="adventure$message",maxVer=19),@VersionName(value="adventure$content",minVer=19)})
	NmsPacketPlayOutChat setComponentPaperV17(WrappedComponentPaperV17 component);
	@WrappedBukkitFieldAccessor(@VersionName(value="components",maxVer=19))
	BaseComponent[] getMd5MsgV_19();
	@WrappedBukkitFieldAccessor(@VersionName(value="components",maxVer=19))
	NmsPacketPlayOutChat setMd5MsgV_19(BaseComponent[] msg);
	@WrappedBukkitFieldAccessor(@VersionName(value="a",maxVer=19))
	NmsIChatBaseComponent getNmsMsgV_19();
	@WrappedBukkitFieldAccessor(@VersionName(value="a",maxVer=19))
	NmsPacketPlayOutChat setNmsMsgV_19(NmsIChatBaseComponent msg);
	@WrappedBukkitFieldAccessor(@VersionName(minVer=16,value="c",maxVer=19))
	UUID getUidV16_19();
	@WrappedBukkitFieldAccessor(@VersionName(minVer=16,value="c",maxVer=19))
	NmsPacketPlayOutChat setUidV16_19(UUID id);
	@WrappedBukkitFieldAccessor({@VersionName(value="content",minVer=19),@VersionName(value="a",minVer=19)})
	String getJsonV19();
	@WrappedBukkitFieldAccessor({@VersionName(value="content",minVer=19),@VersionName(value="a",minVer=19)})
	NmsPacketPlayOutChat setJsonV19(String json);
}
