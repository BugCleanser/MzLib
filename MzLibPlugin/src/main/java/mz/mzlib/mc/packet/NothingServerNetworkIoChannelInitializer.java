package mz.mzlib.mc.packet;

import io.netty.channel.Channel;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorMethod;
import mz.mzlib.util.delegator.basic.Delegator_void;
import mz.mzlib.util.nothing.*;

@DelegatorMinecraftClass(
        {
            @VersionName(name="net.minecraft.server.ServerNetworkIo$4",end=1300),
            @VersionName(name="net.minecraft.server.ServerNetworkIo$1",begin=1300)
        })
public interface NothingServerNetworkIoChannelInitializer extends Delegator, Nothing
{
    @DelegatorMethod("initChannel")
    void initChannel(Channel channel);

    @NothingInject(delegatorMethod = "initChannel", locatingSteps = @LocatingStep(type = LocatingStepType.AFTER_ALL, arg = Opcodes.RETURN), type = NothingInjectType.INSERT_BEFORE)
    default Delegator_void injectionInitChannel(@LocalVar(1) Channel channel)
    {
        PacketListenerModule.handlePipeline(channel.pipeline());
        return Nothing.notReturn();
    }
}
