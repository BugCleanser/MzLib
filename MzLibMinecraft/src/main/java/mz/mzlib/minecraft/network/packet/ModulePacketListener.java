package mz.mzlib.minecraft.network.packet;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.GenericFutureListener;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.minecraft.network.ServerPlayNetworkHandler;
import mz.mzlib.minecraft.network.packet.s2c.PacketBundleS2cV1904;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.TaskList;
import mz.mzlib.util.ThreadLocalGrowingHashMap;
import mz.mzlib.util.WeakRefMap;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.Wrapper_void;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ModulePacketListener extends MzModule
{
    public static ModulePacketListener instance = new ModulePacketListener();
    
    public ThreadLocalGrowingHashMap<Channel, Set<Object>> handledPackets = new ThreadLocalGrowingHashMap<>();
    public boolean handle(Channel channel, EntityPlayer player, Packet packet, Consumer<Packet> rehandler, boolean isSending)
    {
        return handle(channel, player, packet, rehandler, MinecraftServer.instance::schedule, isSending);
    }
    public boolean handle(Channel channel, EntityPlayer player, final Packet packet, Consumer<Packet> rehandler, Consumer<Runnable> syncer, boolean isSending)
    {
        Set<Object> set = this.handledPackets.get(channel);
        if(set==null || !set.add(packet.getWrapped()))
            return true;
        try
        {
            if(packet.isBundle())
            {
                List<Packet> newPackets = new ArrayList<>();
                TaskList synced = new TaskList();
                Iterable<Packet> packets = packet.castTo(PacketBundleV1904::create).getPackets();
                packet.setWrappedFrom(packet.isInstanceOf(PacketBundleS2cV1904::create) ? PacketBundleS2cV1904.newInstance(newPackets) : RuntimeUtil.valueThrow(new UnsupportedOperationException()));
                for(Iterator<Packet> i = packets.iterator(); i.hasNext(); )
                {
                    Packet p = i.next();
                    if(this.handle(channel, player, p, newPackets::add, synced::schedule, isSending))
                        newPackets.add(p);
                    if(!synced.tasks.isEmpty())
                    {
                        syncer.accept(()->
                        {
                            synced.run();
                            while(i.hasNext())
                            {
                                Packet p1 = i.next();
                                if(this.handle(channel, player, p1, newPackets::add, Runnable::run, isSending))
                                    newPackets.add(p1);
                            }
                            rehandler.accept(packet);
                        });
                        return false;
                    }
                }
                return true;
            }
            if(isSending)
                packet.setWrappedFrom(packet.copy(channel.alloc().buffer(4096)));
            
            List<PacketListener<?>> sortedListeners = PacketListenerRegistrar.instance.sortedListeners.get(packet.getWrapped().getClass());
            if(sortedListeners==null)
                return true;
            PacketEvent event = new PacketEvent(player);
            for(PacketListener<?> listener: sortedListeners)
            {
                try
                {
                    listener.call0(event, packet);
                }
                catch(Throwable e)
                {
                    e.printStackTrace(System.err);
                }
            }
            if(event.syncTasks!=null && Thread.currentThread()==MinecraftServer.instance.getThread())
            {
                event.syncTasks.run();
                event.syncTasks = null;
            }
            if(event.syncTasks==null)
                return true;
            syncer.accept(()->
            {
                event.syncTasks.run();
                if(!event.isCancelled())
                    rehandler.accept(packet);
            });
            return false;
        }
        catch(Throwable e)
        {
            e.printStackTrace(System.err);
            return true;
        }
        finally
        {
            set.add(packet.getWrapped());
        }
    }
    
    @Override
    public void onLoad()
    {
        this.register(PacketListenerRegistrar.instance);
        this.register(NothingServerNetworkIoChannelInitializer.class);
        this.register(NothingClientConnection.class);
        this.register(NothingServerPlayNetworkHandler.class);
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
    public void initChannel(Channel channel) // FIXME
    {
        if(channel.pipeline().get(PacketListenerChannelHandler.class)!=null)
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
            if(ModulePacketListener.instance.handle(this.getChannel(), this.getPlayer(), packet, msg->this.channelRead0(null, msg), false))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
        
        // TODO V_1400
        
        @VersionRange(begin=1400, end=1901)
        @NothingInject(wrapperMethodName="sendPacketImmediatelyV1400_1901", wrapperMethodParams={Packet.class, GenericFutureListener.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void sendPacketImmediatelyBeginV1400_1901(@LocalVar(1) Packet packet, @LocalVar(2) GenericFutureListener<?> callbacksV1901)
        {
            if(ModulePacketListener.instance.handle(this.getChannel(), this.getPlayer(), packet, p->this.sendPacketImmediatelyV1400_1901(p, callbacksV1901), true))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
        
        @VersionRange(begin=1901, end=2002)
        @NothingInject(wrapperMethodName="sendPacketImmediatelyV1901_2002", wrapperMethodParams={Packet.class, PacketCallbacksV1901.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void sendPacketImmediatelyBeginV1901_2002(@LocalVar(1) Packet packet, @LocalVar(2) PacketCallbacksV1901 callbacksV1901)
        {
            if(ModulePacketListener.instance.handle(this.getChannel(), this.getPlayer(), packet, p->this.sendPacketImmediatelyV1901_2002(p, callbacksV1901), true))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
        
        @VersionRange(begin=2002)
        @NothingInject(wrapperMethodName="sendPacketImmediatelyV2002", wrapperMethodParams={Packet.class, PacketCallbacksV1901.class, boolean.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void sendPacketImmediatelyBeginV2002(@LocalVar(1) Packet packet, @LocalVar(2) PacketCallbacksV1901 callbacksV1901, @LocalVar(3) boolean flush)
        {
            if(ModulePacketListener.instance.handle(this.getChannel(), this.getPlayer(), packet, p->this.sendPacketImmediatelyV2002(p, callbacksV1901, flush), true))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
    }
    
    @WrapSameClass(ServerPlayNetworkHandler.class)
    public interface NothingServerPlayNetworkHandler extends ServerPlayNetworkHandler, Nothing
    {
        // TODO
        @VersionRange(end=1400)
        @NothingInject(wrapperMethodName="sendPacketV_1400", wrapperMethodParams={Packet.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void sendPacketBeginV_1400(@LocalVar(1) Packet packet)
        {
            if(ModulePacketListener.instance.handle(this.getConnection().getChannel(), this.getPlayer(), packet, this::sendPacketV_2002, true))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
    }
    
    @WrapMinecraftClass({@VersionName(name="net.minecraft.server.ServerNetworkIo$4", end=1300), @VersionName(name="net.minecraft.server.ServerNetworkIo$1", begin=1300)})
    public interface NothingServerNetworkIoChannelInitializer extends WrapperObject, Nothing
    {
        @WrapMethod("initChannel")
        void initChannel(Channel channel);
        
        static void initChannelEndLocate(NothingInjectLocating locating)
        {
            locating.allAfter(AsmUtil.insnReturn(void.class).getOpcode());
            assert !locating.locations.isEmpty();
        }
        
        @NothingInject(wrapperMethodName="initChannel", wrapperMethodParams={Channel.class}, locateMethod="initChannelEndLocate", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void initChannelEnd(@LocalVar(1) Channel channel)
        {
            ModulePacketListener.instance.initChannel(channel);
            return Nothing.notReturn();
        }
    }
}
