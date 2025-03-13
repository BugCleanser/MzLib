package mz.mzlib.minecraft.bukkit.network.packet;

import io.netty.util.concurrent.GenericFutureListener;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.network.ClientConnectionBukkit;
import mz.mzlib.minecraft.network.packet.ModulePacketListener;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.network.packet.PacketCallbacksV1901;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.nothing.LocalVar;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.basic.Wrapper_void;

public class ModuleBukkitPacketListener extends MzModule
{
    public static ModuleBukkitPacketListener instance = new ModuleBukkitPacketListener();
    
    @Override
    public void onLoad()
    {
        this.register(NothingClientConnection.class);
    }
    
    @WrapSameClass(ClientConnectionBukkit.class)
    public interface NothingClientConnection extends ClientConnectionBukkit, Nothing
    {
        ThreadLocal<Boolean> rehandling = new ThreadLocal<>();
        
        @VersionRange(begin=1701, end=1901)
        @NothingInject(wrapperMethodName="sendPacketImmediatelyV1701_1901", wrapperMethodParams={Packet.class, GenericFutureListener.class, Boolean.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void sendPacketImmediatelyBeginV1701_1901(@LocalVar(1) Packet packet, @LocalVar(2) GenericFutureListener<?> callbacksV1901, @LocalVar(3) Boolean flush)
        {
            if(rehandling.get()==Boolean.TRUE)
                return Nothing.notReturn();
            if(ModulePacketListener.instance.handle(this.getChannel(), this.getPlayer(), packet, p->
            {
                rehandling.set(true);
                this.sendPacketImmediatelyV1701_1901(p, callbacksV1901, flush);
                rehandling.set(false);
            }))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
        
        @VersionRange(begin=1901, end=2002)
        @NothingInject(wrapperMethodName="sendPacketImmediatelyV1901_2002", wrapperMethodParams={Packet.class, PacketCallbacksV1901.class, Boolean.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
        default Wrapper_void sendPacketImmediatelyBeginV1901_2002(@LocalVar(1) Packet packet, @LocalVar(2) PacketCallbacksV1901 callbacksV1901, @LocalVar(3) Boolean flush)
        {
            if(rehandling.get()==Boolean.TRUE)
                return Nothing.notReturn();
            if(ModulePacketListener.instance.handle(this.getChannel(), this.getPlayer(), packet, p->
            {
                rehandling.set(true);
                this.sendPacketImmediatelyV1901_2002(p, callbacksV1901, flush);
                rehandling.set(false);
            }))
                return Nothing.notReturn();
            else
                return Wrapper_void.create(null);
        }
    }
}
