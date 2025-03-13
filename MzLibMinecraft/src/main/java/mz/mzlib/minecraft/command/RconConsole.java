package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.server.command.Console", end=1400), @VersionName(name="net.minecraft.server.dedicated.ServerCommandOutput", begin=1400, end=1600), @VersionName(name="net.minecraft.server.rcon.RconCommandOutput", begin=1600)})
public interface RconConsole extends WrapperObject, CommandOutput
{
    WrapperFactory<RconConsole> FACTORY = WrapperFactory.find(RconConsole.class);
    @Deprecated
    @WrapperCreator
    static RconConsole create(Object wrapped)
    {
        return WrapperObject.create(RconConsole.class, wrapped);
    }
}
