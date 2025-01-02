package mz.mzlib.minecraft.network.packet;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.ClientConnection;

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
        MinecraftServer.instance.schedule(()->PacketListenerModule.instance.restoreChannel(ctx.channel()));
        super.channelInactive(ctx);
    }
    
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
    {
        if(PacketListenerModule.instance.handle(ctx.channel(), this.getPlayer(), msg, ()->ctx.channel().write(msg)))
            ctx.write(msg, promise);
    }
}
