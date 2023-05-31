package mz.lib.minecraft.bukkit.wrappednms;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.GenericFutureListener;
import mz.lib.minecraft.bukkit.ProtocolUtil;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.nothing.NothingBukkit;
import mz.lib.minecraft.bukkit.nothing.NothingBukkitInject;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.nothing.LocalVar;
import mz.lib.nothing.Nothing;
import mz.lib.nothing.NothingLocation;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.entity.Player;

import java.util.Optional;

@WrappedBukkitClass({@VersionName(value="nms.NetworkManager",maxVer=17),@VersionName(value="net.minecraft.network.NetworkManager",minVer=17)})
public interface NmsNetworkManager extends WrappedBukkitObject, NothingBukkit
{
	static void receivePacketV13(NmsPacket packet,NmsPacketListener listener)
	{
		WrappedObject.getStatic(NmsNetworkManager.class).staticReceivePacketV13(packet,listener);
	}
	@NothingBukkitInject(name=@VersionName(maxVer=13, value="a"),args={ChannelHandlerContext.class,NmsPacket.class},location=NothingLocation.FRONT)
	default Optional<Void> beforeReceivePacketV_13(@LocalVar(2) NmsPacket packet)
	{
		return beforeReceivePacketV13(packet,this.getPacketListener());
	}
	@NothingBukkitInject(name=@VersionName(minVer=13, value="a"), args={NmsPacket.class,NmsPacketListener.class}, location=NothingLocation.FRONT)
	static Optional<Void> beforeReceivePacketV13(@LocalVar(0) NmsPacket packet,@LocalVar(1) NmsPacketListener listener)
	{
		if(listener.is(NmsPlayerConnection.class))
		{
			if(ProtocolUtil.onPacketReceive((Player) listener.cast(NmsPlayerConnection.class).getPlayer().getBukkitEntity(),packet))
				return Nothing.doReturn(null);
		}
		return Nothing.doContinue();
	}
	@WrappedBukkitFieldAccessor({@VersionName("packetListener"),@VersionName(maxVer=13, value="m"),@VersionName(minVer=17,maxVer=18.2f, value="m"),@VersionName(value="o",minVer=18.2f,maxVer=19),@VersionName(value="o",minVer=19)})
	NmsPacketListener getPacketListener();
	@WrappedBukkitMethod(@VersionName(minVer=13, value="a"))
	void staticReceivePacketV13(NmsPacket packet,NmsPacketListener listener);
	
	@WrappedBukkitMethod({@VersionName("sendPacket"),@VersionName(minVer=18, value="a")})
	void sendPacket(NmsPacket packet);
	
	@NothingBukkitInject(name=@VersionName(maxVer=13, value="sendPacket"),args={NmsPacket.class},location=NothingLocation.FRONT)
	@NothingBukkitInject(name=@VersionName(maxVer=13, value="a"),args={NmsPacket.class,GenericFutureListener[].class}, location=NothingLocation.FRONT)
	@NothingBukkitInject(name={@VersionName(minVer=13, value="sendPacket", maxVer=19.1f),@VersionName(minVer=18, value="a", maxVer=19.1f)}, args={NmsPacket.class,GenericFutureListener.class}, location=NothingLocation.FRONT)
	@NothingBukkitInject(name=@VersionName(value={"sendPacket","a"}, minVer=19.1f), args={NmsPacket.class,NmsPacketSendListener.class}, location=NothingLocation.FRONT)
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
