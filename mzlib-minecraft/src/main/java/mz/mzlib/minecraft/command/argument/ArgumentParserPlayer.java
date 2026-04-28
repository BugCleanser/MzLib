package mz.mzlib.minecraft.command.argument;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Collections;

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
    public @UnknownNullability EntityPlayer parse(CommandContext context)
    {
        String name = new ArgumentParserString(
            this.name, false,
            MinecraftServer.instance.getPlayers().stream().map(EntityPlayer::getName).toArray(String[]::new)
        ).parse(context);
        for(EntityPlayer result: MinecraftServer.instance.getPlayerManager().getPlayer(name))
            return result;
        context.addArgError(MinecraftI18n.resolveText(
            context.source, "mzlib.command.arg.player.not_found",
            Collections.singletonMap("name", name)
        ));
        return null;
    }
}
