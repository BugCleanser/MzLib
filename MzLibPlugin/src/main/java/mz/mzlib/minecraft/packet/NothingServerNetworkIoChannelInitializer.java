package mz.mzlib.minecraft.packet;

import io.netty.channel.Channel;
import mz.mzlib.asm.Opcodes;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.basic.Wrapper_void;
import mz.mzlib.util.nothing.*;

@WrapMinecraftClass(
        {
            @VersionName(name="net.minecraft.server.ServerNetworkIo$4",end=1300),
            @VersionName(name="net.minecraft.server.ServerNetworkIo$1",begin=1300)
        })
public interface NothingServerNetworkIoChannelInitializer extends WrapperObject, Nothing
{
    @WrapMethod("initChannel")
    void initChannel(Channel channel);

    @NothingInject(wrapperMethod = "initChannel", locatingSteps = @LocatingStep(type = LocatingStepType.AFTER_ALL, arg = Opcodes.RETURN), type = NothingInjectType.INSERT_BEFORE)
    default Wrapper_void injectionInitChannel(@LocalVar(1) Channel channel)
    {
        PacketListenerModule.handlePipeline(channel.pipeline());
        return Nothing.notReturn();
    }
}
