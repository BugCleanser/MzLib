package mz.mzlib.minecraft.network.packet;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.util.wrapper.WrapperObject;

public class PacketListenerChannelHandler extends ChannelDuplexHandler
{
    public ClientConnection clientConnection;
    public PacketListenerChannelHandler(ClientConnection clientConnection)
    {
        this.clientConnection = clientConnection;
    }
    
    public EntityPlayer getPlayer()
    {
        return this.clientConnection.getPlayer();
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        MinecraftServer.instance.schedule(()->ModulePacketListener.instance.restoreChannel(ctx.channel()));
        super.channelInactive(ctx);
    }
    
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
    {
        if(!WrapperObject.create(msg).isInstanceOf(Packet::create))
        {
            ctx.write(msg, promise);
            return;
        }
        Packet packet = Packet.create(msg);
        if(ModulePacketListener.instance.handle(ctx.channel(), this.getPlayer(), packet, p->ctx.channel().write(p.getWrapped()), true))
            ctx.write(packet.getWrapped(), promise);
    }
}
