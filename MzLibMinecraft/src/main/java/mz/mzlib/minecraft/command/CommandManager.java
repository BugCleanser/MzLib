package mz.mzlib.minecraft.command;

import com.mojang.brigadier.CommandDispatcher;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.server.command.CommandManager"))
public interface CommandManager extends WrapperObject, Instance
{
    CommandManager instance = RuntimeUtil.nul();
    
    @WrapMinecraftMethod({@VersionName(name="method_17518", end=1400), @VersionName(name="getDispatcher", begin=1400)})
    CommandDispatcher<?> getDispatcher();
    
    @WrapMinecraftMethod({@VersionName(name="method_17532", end=1400), @VersionName(name="sendCommandTree", begin=1400)})
    void sendCommandTree(EntityPlayer player);
    
    default void updateAll()
    {
        for(EntityPlayer player:MinecraftServer.instance.getPlayers())
            this.sendCommandTree(player);
    }
}
