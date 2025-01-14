package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@WrapMinecraftClass(@VersionName(name="net.minecraft.command.Command", end=1300))
public interface CommandV_1300 extends WrapperObject
{
    @WrapMinecraftMethod(@VersionName(name="getCommandName"))
    String getName();

    @WrapMinecraftMethod(@VersionName(name="getAliases"))
    List<String> getAliases();

    @WrapMinecraftMethod(@VersionName(name="getUsageTranslationKey"))
    String getUsageTranslationKey(CommandSource source);

    @WrapMinecraftMethod(@VersionName(name="method_3278"))
    boolean canExecute(MinecraftServer server, CommandSource source);

    @WrapMinecraftMethod(@VersionName(name="method_3279"))
    void execute(MinecraftServer server, CommandSource source, String[] args);

    // TODO
//    List<String> method_10738(MinecraftServer server, GameObject source, String[] strings, BlockPos pos);
}
