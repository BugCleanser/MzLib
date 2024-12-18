package mz.mzlib.minecraft.command.argument;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.entity.player.EntityPlayer;

public class ArgumentParserPlayer extends ArgumentParser<EntityPlayer>
{
    public ArgumentParserPlayer(String name)
    {
        super(name);
    }
    public ArgumentParserPlayer()
    {
        this("player");
    }
    
    @Override
    public EntityPlayer parse(CommandContext context)
    {
        String name = new ArgumentParserString(this.name, false, MinecraftServer.instance.getPlayers().stream().map(EntityPlayer::getName).toArray(String[]::new)).parse(context);
        if(name==null)
            return null;
        EntityPlayer result = MinecraftServer.instance.getPlayerManager().getPlayer(name);
        if(result.isPresent())
            return result;
        return null;
    }
}
