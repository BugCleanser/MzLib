package mz.mzlib.minecraft.network.packet;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.GenericFutureListener;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.minecraft.network.ServerPlayNetworkHandler;
import mz.mzlib.minecraft.network.packet.s2c.PacketBundleS2cV1904;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.TaskList;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.basic.Wrapper_void;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ModulePacketListener extends MzModule
{
    public static ModulePacketListener instance = new ModulePacketListener();
    
    public boolean handle(Channel channel, EntityPlayer player, Packet packet, Consumer<Packet> rehandler)
    {
        return handle(channel, player, packet, rehandler, MinecraftServer.instance::schedule);
    }
    public boolean handle(Channel channel, EntityPlayer player, final Packet packet, Consumer<Packet> rehandler, Consumer<Runnable> syncer)
    {
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
                    if(this.handle(channel, player, p, newPackets::add, synced::schedule))
                        newPackets.add(p);
                    if(!synced.tasks.isEmpty())
                    {
                        syncer.accept(()->
                        {
                            synced.run();
                            while(i.hasNext())
                            {
                                Packet p1 = i.next();
                                if(this.handle(channel, player, p1, newPackets::add, Runnable::run))
                                    newPackets.add(p1);
                            }
                            rehandler.accept(packet);
                        });
                        return false;
                    }
                }
                return true;
            }
            
            List<PacketListener<?>> sortedListeners = PacketListenerRegistrar.instance.sortedListeners.get(packet.getWrapped().getClass());
            if(sortedListeners==null)
                return true;
            PacketEvent event = new PacketEvent(channel, player, packet);
            for(PacketListener<?> listener: sortedListeners)
            {
                try
                {
                    listener.call(event);
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
    }
    
    @Override
    public void onLoad()
    {
        this.register(PacketListenerRegistrar.instance);
        this.register(NothingClientConnection.class);
        this.register(NothingServerPlayNetworkHandler.class);
    }
    
    @WrapSameClass(ClientConnection.class)
    public interface NothingClientConnection extends ClientConnection, Nothing
    {
        ThreadLocal<Boolean> rehandling = new ThreadLocal<>();
        
        static void channelRead0BeginLocate(NothingInjectLocating locating)
        {
            locating.allAfter(i->AsmUtil.isVisitingWrapped(locating.insns[i], ClientConnection.class, "staticHandlePacket", Packet.class, PacketHandler.class));
            assert !locating.locations.isEmpty();
        }
        
        @NothingInject(wrapperMethodName="channelRead0", wrapperMethodParams={ChannelHandlerContext.class, Packet.class}, locateMethod="channelRead0BeginLocate", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void channelRead0Begin(@LocalVar(2) Packet packet)
        {
            if(rehandling.get()==Boolean.TRUE)
            {
                rehandling.set(false);
                return Nothing.notReturn();
            }
            if(ModulePacketListener.instance.handle(this.getChannel(), this.getPlayer(), packet, msg->
            {
                rehandling.set(true);
                this.channelRead0(null, msg);
            }))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
        
        // TODO V_1400
        
        @VersionRange(begin=1400, end=1901)
        @NothingInject(wrapperMethodName="sendPacketImmediatelyV1400_1901", wrapperMethodParams={Packet.class, GenericFutureListener.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void sendPacketImmediatelyBeginV1400_1901(@LocalVar(1) Packet packet, @LocalVar(2) GenericFutureListener<?> callbacksV1901)
        {
            if(rehandling.get()==Boolean.TRUE)
            {
                rehandling.set(false);
                return Nothing.notReturn();
            }
            if(ModulePacketListener.instance.handle(this.getChannel(), this.getPlayer(), packet, p->
            {
                rehandling.set(true);
                this.sendPacketImmediatelyV1400_1901(p, callbacksV1901);
            }))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
        
        @VersionRange(begin=1901, end=2002)
        @NothingInject(wrapperMethodName="sendPacketImmediatelyV1901_2002", wrapperMethodParams={Packet.class, PacketCallbacksV1901.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void sendPacketImmediatelyBeginV1901_2002(@LocalVar(1) Packet packet, @LocalVar(2) PacketCallbacksV1901 callbacksV1901)
        {
            if(rehandling.get()==Boolean.TRUE)
            {
                rehandling.set(false);
                return Nothing.notReturn();
            }
            if(ModulePacketListener.instance.handle(this.getChannel(), this.getPlayer(), packet, p->
            {
                rehandling.set(true);
                this.sendPacketImmediatelyV1901_2002(p, callbacksV1901);
            }))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
        
        @VersionRange(begin=2002)
        @NothingInject(wrapperMethodName="sendPacketImmediatelyV2002", wrapperMethodParams={Packet.class, PacketCallbacksV1901.class, boolean.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void sendPacketImmediatelyBeginV2002(@LocalVar(1) Packet packet, @LocalVar(2) PacketCallbacksV1901 callbacksV1901, @LocalVar(3) boolean flush)
        {
            if(rehandling.get()==Boolean.TRUE)
            {
                rehandling.set(false);
                return Nothing.notReturn();
            }
            if(ModulePacketListener.instance.handle(this.getChannel(), this.getPlayer(), packet, p->
            {
                rehandling.set(true);
                this.sendPacketImmediatelyV2002(p, callbacksV1901, flush);
            }))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
    }
    
    @WrapSameClass(ServerPlayNetworkHandler.class)
    public interface NothingServerPlayNetworkHandler extends ServerPlayNetworkHandler, Nothing
    {
        ThreadLocal<Boolean> rehandling = new ThreadLocal<>();
        @VersionRange(end=1400)
        @NothingInject(wrapperMethodName="sendPacketV_1400", wrapperMethodParams={Packet.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void sendPacketBeginV_1400(@LocalVar(1) Packet packet)
        {
            if(rehandling.get()==Boolean.TRUE)
            {
                rehandling.set(false);
                return Nothing.notReturn();
            }
            if(ModulePacketListener.instance.handle(this.getConnection().getChannel(), this.getPlayer(), packet, p->
            {
                rehandling.set(true);
                this.sendPacketV_1400(p);
            }))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
    }
}
