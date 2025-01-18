package mz.mzlib.minecraft.network.packet;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.GenericFutureListener;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.minecraft.network.ServerCommonNetworkHandlerV2002;
import mz.mzlib.minecraft.network.ServerPlayNetworkHandler;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.ThreadLocalGrowingHashMap;
import mz.mzlib.util.WeakRefMap;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.Wrapper_void;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ModulePacketListener extends MzModule
{
    public static ModulePacketListener instance = new ModulePacketListener();
    
    public ThreadLocalGrowingHashMap<Channel, Set<Object>> handledPackets = new ThreadLocalGrowingHashMap<>();
    public boolean handle(Channel channel, EntityPlayer player, Object msg, Consumer<Object> rehandler)
    {
        Set<Object> set = this.handledPackets.get(channel);
        if(set==null)
        {
            this.handledPackets.threadLocal.remove();
            return true;
        }
        if(!set.add(msg))
            return true;
        if(MinecraftPlatform.instance.getVersion()>=1904)
            if(WrapperObject.create(msg).isInstanceOf(PacketBundleV1904::create))
            {
                for(Packet p: PacketBundleV1904.create(msg).getPackets())
                    rehandler.accept(p.getWrapped());
                return false;
            }
        
        List<PacketListener<?>> sortedListeners = PacketListenerRegistrar.instance.sortedListeners.get(msg.getClass());
        if(sortedListeners==null)
            return true;
        PacketEvent event = new PacketEvent(player);
        try
        {
            for(PacketListener<?> listener: sortedListeners)
            {
                try
                {
                    listener.call0(event, msg);
                }
                catch(Throwable e)
                {
                    e.printStackTrace(System.err);
                }
            }
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
        if(event.syncTasks!=null && Thread.currentThread()==MinecraftServer.instance.getThread())
        {
            event.syncTasks.run();
            event.syncTasks = null;
        }
        if(event.syncTasks==null)
            return true;
        MinecraftServer.instance.schedule(()->
        {
            try
            {
                event.syncTasks.run();
                if(!event.isCancelled())
                    rehandler.accept(msg);
            }
            catch(Throwable e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        });
        return false;
    }
    
    @Override
    public void onLoad()
    {
        this.register(PacketListenerRegistrar.instance);
        this.register(NothingServerNetworkIoChannelInitializer.class);
        this.register(NothingClientConnection.class);
        this.register(NothingServerPlayNetworkHandler.class);
        if(MinecraftPlatform.instance.getVersion()>=2002)
            this.register(NothingServerCommonNetworkHandlerV2002.class);
        for(EntityPlayer player: MinecraftServer.instance.getPlayers())
        {
            this.initChannel(player.getNetworkHandler().getConnection().getChannel());
        }
    }
    @Override
    public void onUnload()
    {
        for(EntityPlayer player: MinecraftServer.instance.getPlayers())
        {
            this.restoreChannel(player.getNetworkHandler().getConnection().getChannel());
        }
    }
    public void initChannel(Channel channel)
    {
        if(!channel.isOpen() || channel.pipeline().get(PacketListenerChannelHandler.class)!=null)
            return;
        this.handledPackets.put(channel, Collections.newSetFromMap(new WeakRefMap<>(new ConcurrentHashMap<>())));
        channel.pipeline().addBefore("packet_handler", null, new PacketListenerChannelHandler(ClientConnection.create(channel.pipeline().get(RuntimeUtil.<Class<? extends ChannelHandler>>cast(ClientConnection.create(null).staticGetWrappedClass())))));
    }
    public void restoreChannel(Channel channel)
    {
        if(channel.pipeline().get(PacketListenerChannelHandler.class)==null)
            return;
        channel.pipeline().remove(PacketListenerChannelHandler.class);
        this.handledPackets.remove(channel);
    }
    
    @WrapSameClass(ClientConnection.class)
    public interface NothingClientConnection extends ClientConnection, Nothing
    {
        static void channelRead0BeginLocate(NothingInjectLocating locating)
        {
            locating.allAfter(i->AsmUtil.isVisitingWrapped(locating.insns[i], ClientConnection.class, "staticHandlePacket", Packet.class, PacketHandler.class));
            assert !locating.locations.isEmpty();
        }
        
        @NothingInject(wrapperMethodName="channelRead0", wrapperMethodParams={ChannelHandlerContext.class, Packet.class}, locateMethod="channelRead0BeginLocate", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void channelRead0Begin(@LocalVar(2) Packet packet)
        {
            if(ModulePacketListener.instance.handle(this.getChannel(), this.getPlayer(), packet.getWrapped(), msg->this.channelRead0(null, Packet.create(msg))))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
        
        @VersionRange(begin=1901)
        @NothingInject(wrapperMethodName="sendPacketImmediatelyV1901", wrapperMethodParams={Packet.class, PacketCallbacksV1901.class, boolean.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void sendPacketImmediatelyBeginV1901(@LocalVar(1) Packet packet, @LocalVar(2) PacketCallbacksV1901 callbacksV1901, @LocalVar(3) boolean flush)
        {
            if(Thread.currentThread()==MinecraftServer.instance.getThread() && !packet.isBundle())
                packet.setWrappedFrom(Packet.copy(packet));
            if(ModulePacketListener.instance.handle(this.getChannel(), this.getPlayer(), packet.getWrapped(), msg->this.sendPacketImmediatelyV1901(Packet.create(msg), callbacksV1901, flush)))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
    }
    
    @WrapSameClass(ServerPlayNetworkHandler.class)
    public interface NothingServerPlayNetworkHandler extends ServerPlayNetworkHandler, Nothing
    {
        @VersionRange(end=1400)
        @NothingInject(wrapperMethodName="sendPacketV_1400", wrapperMethodParams={Packet.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void sendPacketBeginV_1400(@LocalVar(1) Packet packet)
        {
            if(Thread.currentThread()==MinecraftServer.instance.getThread() && !packet.isBundle())
                packet = Packet.copy(packet);
            if(ModulePacketListener.instance.handle(this.getConnection().getChannel(), this.getPlayer(), packet.getWrapped(), msg->this.sendPacketV_2002(Packet.create(msg))))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
        
        @VersionRange(begin=1400, end=1901)
        @NothingInject(wrapperMethodName="sendPacketV1400_1901", wrapperMethodParams={Packet.class, GenericFutureListener.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void sendPacketBeginV1400_1901(@LocalVar(1) Packet packet, @LocalVar(2) GenericFutureListener<?> callbacks)
        {
            if(callbacks!=null)
                return Nothing.notReturn();
            return this.sendPacketBeginV_1400(packet);
        }
        
        @VersionRange(begin=1901, end=2002)
        @NothingInject(wrapperMethodName="sendPacketV1901_2002", wrapperMethodParams={Packet.class, PacketCallbacksV1901.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void sendPacketBeginV1901_2002(@LocalVar(1) Packet packet, @LocalVar(2) PacketCallbacksV1901 callbacks)
        {
            if(callbacks.isPresent())
                return Nothing.notReturn();
            return this.sendPacketBeginV_1400(packet);
        }
    }
    
    @VersionRange(begin=2002)
    @WrapSameClass(ServerCommonNetworkHandlerV2002.class)
    public interface NothingServerCommonNetworkHandlerV2002 extends ServerCommonNetworkHandlerV2002, Nothing
    {
//        @NothingInject(wrapperMethodName="sendPacket", wrapperMethodParams={Packet.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
//        default Wrapper_void sendPacketBegin(@LocalVar(1) Packet packet)
//        {
//            if(Thread.currentThread()==MinecraftServer.instance.getThread() && !packet.isBundle())
//                packet = Packet.copy(packet);
//            if(ModulePacketListener.instance.handle(this.getConnection().getChannel(), this.getPlayer(), packet.getWrapped(), msg->this.sendPacket(Packet.create(msg))))
//                return Nothing.notReturn();
//            else
//                return Wrapper_void.create(null);
//        }
    }
    
    @WrapMinecraftClass({@VersionName(name="net.minecraft.server.ServerNetworkIo$4", end=1300), @VersionName(name="net.minecraft.server.ServerNetworkIo$1", begin=1300)})
    public interface NothingServerNetworkIoChannelInitializer extends WrapperObject, Nothing
    {
        @WrapMethod("initChannel")
        void initChannel(Channel channel);
        
        static void initChannelEndLocate(NothingInjectLocating locating)
        {
            locating.allAfter(AsmUtil.insnReturn(void.class).getOpcode());
        }
        
        @NothingInject(wrapperMethodName="initChannel", wrapperMethodParams={Channel.class}, locateMethod="initChannelEndLocate", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void initChannelEnd(@LocalVar(1) Channel channel)
        {
            ModulePacketListener.instance.initChannel(channel);
            return Nothing.notReturn();
        }
    }
}
