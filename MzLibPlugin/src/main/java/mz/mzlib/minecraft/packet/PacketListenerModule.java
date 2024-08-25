package mz.mzlib.minecraft.packet;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.Wrapper_void;

public class PacketListenerModule extends MzModule
{
    public static PacketListenerModule instance=new PacketListenerModule();

    @Override
    public void onLoad()
    {
        this.register(PacketListenerRegistrar.instance);
        this.register(NothingServerNetworkIoChannelInitializer.class);
        for(EntityPlayer player:MinecraftServer.instance.getPlayerManager().getPlayerList())
        {
            handlePipeline(player.getNetworkHandler().getConnection().getChannel().pipeline());
        }
    }
    @Override
    public void onUnload()
    {
        for(EntityPlayer player:MinecraftServer.instance.getPlayerManager().getPlayerList())
        {
            restorePipeline(player.getNetworkHandler().getConnection().getChannel().pipeline());
        }
    }
    public static void handlePipeline(ChannelPipeline pipeline)
    {
        pipeline.addBefore("packet_handler",null,new PacketListenerChannelHandler(ClientConnection.create(pipeline.get(RuntimeUtil.<Class<? extends ChannelHandler>>cast(ClientConnection.create(null).staticGetWrappedClass())))));
    }
    public static void restorePipeline(ChannelPipeline pipeline)
    {
        pipeline.remove(PacketListenerChannelHandler.class);
    }

    @WrapMinecraftClass(
            {
                    @VersionName(name="net.minecraft.server.ServerNetworkIo$4",end=1300),
                    @VersionName(name="net.minecraft.server.ServerNetworkIo$1",begin=1300)
            })
    public interface NothingServerNetworkIoChannelInitializer extends WrapperObject, Nothing
    {
        @WrapMethod("initChannel")
        void initChannel(Channel channel);

        static void injectionInitChannelLocate(NothingInjectLocating locating)
        {
            locating.allAfter(AsmUtil.insnReturn(void.class).getOpcode());
        }
        @NothingInject(wrapperMethod = "initChannel", locateMethod = "injectionInitChannelLocate", type = NothingInjectType.INSERT_BEFORE)
        default Wrapper_void injectionInitChannel(@LocalVar(1) Channel channel)
        {
            PacketListenerModule.handlePipeline(channel.pipeline());
            return Nothing.notReturn();
        }
    }
}
