package mz.mzlib.minecraft.packet;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import mz.mzlib.minecraft.MinecraftMainThreadRunner;
import mz.mzlib.minecraft.entity.EntityPlayer;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.minecraft.network.ServerPlayNetworkHandler;
import mz.mzlib.minecraft.network.listener.MinecraftPacketListener;
import mz.mzlib.util.RuntimeUtil;

import java.util.List;
import java.util.function.Consumer;

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
        if(ServerPlayNetworkHandler.create(null).getWrappedClass().isAssignableFrom(listener.getWrappedClass()))
            return ServerPlayNetworkHandler.create(listener.getWrapped()).getPlayer();
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


    public void handle(Object msg, Consumer<Object> firer, Consumer<Object> rehandler)
    {
        if(msg instanceof RehandledPacket)
        {
            firer.accept(((RehandledPacket) msg).packet);
            ((RehandledPacket) msg).event.future.complete(null);
            return;
        }

        List<PacketListener<?>> sortedListeners =  PacketListenerRegistrar.instance.sortedListeners.get(msg.getClass());
        if(sortedListeners==null)
        {
            firer.accept(msg);
            return;
        }
        PacketEvent event=new PacketEvent(PacketListenerChannelHandler.this.getPlayer());
        if (PacketListenerRegistrar.instance.sync.getOrDefault(msg.getClass(),false))
        {
            MinecraftMainThreadRunner.instance.schedule(()->
            {
                try
                {
                    for (PacketListener<?> listener : sortedListeners)
                        listener.handler.accept(event,RuntimeUtil.cast((Packet)listener.packetCreator.getTarget().invokeExact((Object) msg)));
                    if(event.isCancelled())
                        event.future.cancel(false);
                    else
                        rehandler.accept(new RehandledPacket(event,msg));
                }
                catch (Throwable e)
                {
                    throw RuntimeUtil.sneakilyThrow(e);
                }
            });
        }
        else
        {
            try
            {
                for (PacketListener<?> listener : sortedListeners)
                    listener.handler.accept(event,RuntimeUtil.cast((Packet)listener.packetCreator.getTarget().invokeExact((Object) msg)));
            }
            catch (Throwable e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
            if(event.isCancelled())
                event.future.cancel(false);
            else
            {
                firer.accept(msg);
                event.future.complete(null);
            }
        }
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    {
        handle(msg,ctx::fireChannelRead,ctx.channel().pipeline()::fireChannelRead);
    }
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
    {
        handle(msg,m->ctx.write(m,promise),ctx.channel()::write);
    }
}
