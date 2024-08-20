package mz.mzlib.minecraft.packet;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.entity.EntityPlayer;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;

public class PacketListenerModule extends MzModule
{
    public static PacketListenerModule instance=new PacketListenerModule();

    @Override
    public void onLoad()
    {
        this.register(PacketListenerRegistrar.instance);
        this.register(NothingServerNetworkIoChannelInitializer.class);
        for(Object p0:MinecraftServer.instance.getPlayerManager().getPlayerList0())
        {
            handlePipeline(EntityPlayer.create(p0).getNetworkHandler().getConnection().getChannel().pipeline());
        }
    }
    @Override
    public void onUnload()
    {
        for(Object p0:MinecraftServer.instance.getPlayerManager().getPlayerList0())
        {
            restorePipeline(EntityPlayer.create(p0).getNetworkHandler().getConnection().getChannel().pipeline());
        }
    }
    public static void handlePipeline(ChannelPipeline pipeline)
    {
        pipeline.addBefore("packet_handler",null,new PacketListenerChannelHandler(ClientConnection.create(pipeline.get(RuntimeUtil.<Class<? extends ChannelHandler>>cast(ClientConnection.create(null).getWrappedClass())))));
    }
    public static void restorePipeline(ChannelPipeline pipeline)
    {
        pipeline.remove(PacketListenerChannelHandler.class);
    }
}
