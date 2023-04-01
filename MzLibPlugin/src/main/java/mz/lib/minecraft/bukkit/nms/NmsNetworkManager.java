package mz.lib.minecraft.bukkit.nms;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.GenericFutureListener;
import mz.lib.minecraft.bukkitlegacy.ProtocolUtil;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.nothing.VersionalNothingInject;
import mz.lib.minecraft.nothing.VersionalNothing;
import mz.lib.minecraft.wrapper.*;
import mz.lib.nothing.LocalVar;
import mz.lib.nothing.Nothing;
import mz.lib.nothing.NothingLocation;
import mz.lib.wrapper.WrappedObject;
import mz.lib.wrapper.*;
import org.bukkit.entity.Player;

import java.util.Optional;

@VersionalWrappedClass({@VersionalName(value="nms.NetworkManager",maxVer=17),@VersionalName(value="net.minecraft.network.NetworkManager",minVer=17)})
public interface NmsNetworkManager extends VersionalWrappedObject, VersionalNothing
{
	static void receivePacketV13(NmsPacket packet,NmsPacketListener listener)
	{
		WrappedObject.getStatic(NmsNetworkManager.class).staticReceivePacketV13(packet,listener);
	}
	@VersionalNothingInject(name=@VersionalName(maxVer=13, value="a"),args={ChannelHandlerContext.class,NmsPacket.class},location=NothingLocation.FRONT)
	default Optional<Void> beforeReceivePacketV_13(@LocalVar(2) NmsPacket packet)
	{
		return beforeReceivePacketV13(packet,this.getPacketListener());
	}
	@VersionalNothingInject(name=@VersionalName(minVer=13, value="a"), args={NmsPacket.class,NmsPacketListener.class}, location=NothingLocation.FRONT)
	static Optional<Void> beforeReceivePacketV13(@LocalVar(0) NmsPacket packet,@LocalVar(1) NmsPacketListener listener)
	{
		if(listener.is(NmsPlayerConnection.class))
		{
			if(ProtocolUtil.onPacketReceive((Player) listener.cast(NmsPlayerConnection.class).getPlayer().getBukkitEntity(),packet))
				return Nothing.doReturn(null);
		}
		return Nothing.doContinue();
	}
	@VersionalWrappedFieldAccessor({@VersionalName("packetListener"),@VersionalName(maxVer=13, value="m"),@VersionalName(minVer=17,maxVer=18.2f, value="m"),@VersionalName(value="o",minVer=18.2f,maxVer=19),@VersionalName(value="o",minVer=19)})
	NmsPacketListener getPacketListener();
	@VersionalWrappedMethod(@VersionalName(minVer=13, value="a"))
	void staticReceivePacketV13(NmsPacket packet,NmsPacketListener listener);
	
	@VersionalWrappedMethod({@VersionalName("sendPacket"),@VersionalName(minVer=18, value="a")})
	void sendPacket(NmsPacket packet);
	
	@VersionalNothingInject(name=@VersionalName(maxVer=13, value="sendPacket"),args={NmsPacket.class},location=NothingLocation.FRONT)
	@VersionalNothingInject(name=@VersionalName(maxVer=13, value="a"),args={NmsPacket.class,GenericFutureListener[].class}, location=NothingLocation.FRONT)
	@VersionalNothingInject(name={@VersionalName(minVer=13, value="sendPacket", maxVer=19.1f),@VersionalName(minVer=18, value="a", maxVer=19.1f)}, args={NmsPacket.class,GenericFutureListener.class}, location=NothingLocation.FRONT)
	@VersionalNothingInject(name=@VersionalName(value={"sendPacket","a"}, minVer=19.1f), args={NmsPacket.class,NmsPacketSendListener.class}, location=NothingLocation.FRONT)
	default Optional<Void> sendPacketBefore(@LocalVar(1) NmsPacket packet)
	{
		if(this.getPacketListener().is(NmsPlayerConnection.class))
		{
			if(ProtocolUtil.onPacketSend((Player) this.getPacketListener().cast(NmsPlayerConnection.class).getPlayer().getBukkitEntity(),packet))
				return Nothing.doReturn(null);
		}
		return Nothing.doContinue();
	}
}
