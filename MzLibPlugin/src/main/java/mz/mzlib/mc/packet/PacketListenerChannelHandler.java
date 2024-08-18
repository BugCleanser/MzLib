package mz.mzlib.mc.packet;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import mz.mzlib.mc.MinecraftMainThreadRunner;
import mz.mzlib.mc.entity.EntityPlayer;
import mz.mzlib.mc.network.ClientConnection;
import mz.mzlib.mc.network.ServerPlayNetworkHandler;
import mz.mzlib.mc.network.listener.MinecraftPacketListener;
import mz.mzlib.util.RuntimeUtil;

import java.util.List;

public class PacketListenerChannelHandler extends ChannelDuplexHandler
{
    public ClientConnection clientConnection;
    public PacketListenerChannelHandler(ClientConnection clientConnection)
    {
        this.clientConnection=clientConnection;
    }

    public EntityPlayer getPlayer()
    {
        MinecraftPacketListener listener = this.clientConnection.getPacketListener();
        if(ServerPlayNetworkHandler.create(null).getDelegateClass().isAssignableFrom(listener.getDelegateClass()))
            return ServerPlayNetworkHandler.create(listener.getDelegate()).getPlayer();
        return null;
    }

    public static class RehandledPacket
    {
        public PacketEvent event;
        public Object packet;
        public RehandledPacket(PacketEvent event,Object packet)
        {
            this.event=event;
            this.packet = packet;
        }
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    {
        List<PacketListener<?>> sortedSyncListeners = msg instanceof RehandledPacket ? null : PacketListenerRegistrar.instance.sortedSyncListeners.get(msg.getClass());
        if (sortedSyncListeners != null && !sortedSyncListeners.isEmpty())
        {
            PacketEvent event=new PacketEvent(PacketListenerChannelHandler.this.getPlayer());
            MinecraftMainThreadRunner.instance.schedule(()->
            {
                try
                {
                    for (PacketListener<?> listener : sortedSyncListeners)
                        listener.handler.accept(event,RuntimeUtil.cast((Packet)listener.packetCreator.getTarget().invokeExact((Object) msg)));
                    ctx.channel().pipeline().fireChannelRead(new RehandledPacket(event,msg));
                }
                catch (Throwable e)
                {
                    throw RuntimeUtil.sneakilyThrow(e);
                }
            });
        }
        else
        {
            PacketEvent event;
            Object packet;
            if(msg instanceof RehandledPacket)
            {
                event = ((RehandledPacket) msg).event;
                packet = ((RehandledPacket) msg).packet;
            }
            else
            {
                event=new PacketEvent(PacketListenerChannelHandler.this.getPlayer());
                packet=msg;
            }
            List<PacketListener<?>> sortedAsyncListeners = PacketListenerRegistrar.instance.sortedAsyncListeners.get(msg.getClass());
            if(sortedAsyncListeners!=null)
            {
                try
                {
                    for (PacketListener<?> listener : sortedAsyncListeners)
                        listener.handler.accept(event,RuntimeUtil.cast((Packet)listener.packetCreator.getTarget().invokeExact((Object) packet)));
                }
                catch (Throwable e)
                {
                    throw RuntimeUtil.sneakilyThrow(e);
                }
            }
            if(event.isCancelled())
                event.future.cancel(false);
            else
            {
                ctx.fireChannelRead(msg);
                event.future.complete(null);
            }
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        super.write(ctx, msg, promise);
        //TODO
    }
}
