package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.world.CommandBlockExecutor"))
public interface CommandBlockExecutor extends WrapperObject, CommandOutput
{
    WrapperFactory<CommandBlockExecutor> FACTORY = WrapperFactory.of(CommandBlockExecutor.class);
}
