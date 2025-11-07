package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.command.Command", end = 1300))
public interface CommandV_1300 extends WrapperObject
{
    @WrapMinecraftMethod(@VersionName(name = "getCommandName"))
    String getName();

    @WrapMinecraftMethod(@VersionName(name = "getAliases"))
    List<String> getAliases();

    @WrapMinecraftMethod(@VersionName(name = "getUsageTranslationKey"))
    String getUsageTranslationKey(CommandSource source);

    boolean canExecute(CommandSource source);
    @SpecificImpl("canExecute")
    @VersionRange(end = 900)
    @WrapMinecraftMethod(@VersionName(name = "isAccessible"))
    boolean canExecuteV_900(CommandSource source);
    @VersionRange(begin = 900)
    @WrapMinecraftMethod(@VersionName(name = "method_3278"))
    boolean canExecuteV900(MinecraftServer server, CommandSource source);
    @SpecificImpl("canExecute")
    @VersionRange(begin = 900)
    default boolean canExecuteV900(CommandSource source)
    {
        return canExecuteV900(MinecraftServer.instance, source);
    }

    void execute(CommandSource source, String[] args);
    @SpecificImpl("execute")
    @VersionRange(end = 900)
    @WrapMinecraftMethod(@VersionName(name = "execute"))
    void executeV_900(CommandSource source, String[] args);
    @VersionRange(begin = 900)
    @WrapMinecraftMethod(@VersionName(name = "method_3279"))
    void executeV900(MinecraftServer server, CommandSource source, String[] args);
    @SpecificImpl("execute")
    @VersionRange(begin = 900)
    default void executeSpecificImplV900(CommandSource source, String[] args)
    {
        this.executeV900(MinecraftServer.instance, source, args);
    }

    // TODO: suggestions
//    List<String> method_10738(MinecraftServer server, GameObject source, String[] strings, BlockPos pos);
}
