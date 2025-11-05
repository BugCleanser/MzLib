package mz.mzlib.minecraft.commands;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.command.ChildCommandRegistration;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.command.argument.ArgumentParserIdentifier;
import mz.mzlib.minecraft.command.argument.ArgumentParserNbtCompound;
import mz.mzlib.minecraft.command.argument.ArgumentParserPlayer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.mzitem.RegistrarMzItem;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.module.MzModule;

public class CommandMzLibGive extends MzModule
{
    public static CommandMzLibGive instance = new CommandMzLibGive();
    
    public Permission permission = new Permission("mzlib.command.mzlib.give");
    
    public Command command;
    
    @Override
    public void onLoad()
    {
        this.register(this.permission);
        this.register(new ChildCommandRegistration(MzLibMinecraft.instance.command, this.command = new Command("give").setPermissionCheckers(Command::checkPermissionSenderPlayer, Command.permissionChecker(this.permission)).setHandler(this::handle)));
    }
    
    public void handle(CommandContext context)
    {
        EntityPlayer player;
        Identifier id;
        NbtCompound data;
        
        ArgumentParserIdentifier idParser = new ArgumentParserIdentifier(RegistrarMzItem.instance.factories.keySet());
        
        CommandContext fork = context.fork();
        player = new ArgumentParserPlayer().handle(fork);
        id = idParser.handle(fork);
        
        CommandContext fork1 = fork.fork();
        data = new ArgumentParserNbtCompound().handle(fork1);
        if(fork1.successful)
            fork = fork1;
        else
            data = NbtCompound.newInstance();
        
        if(fork.argsReader.hasNext())
            fork.successful = false;
        if(fork.successful)
            context = fork;
        else if(context.getSource().getPlayer().isSome())
        {
            player = context.getSource().getPlayer().unwrap();
            id = idParser.handle(context);
            fork = context.fork();
            data = new ArgumentParserNbtCompound().handle(fork);
            if(fork.successful)
                context = fork;
            else
                data = NbtCompound.newInstance();
        }
        
        if(!context.successful || !context.doExecute)
            return;
        
        try
        {
            player.give(RegistrarMzItem.instance.newMzItem(id, data));
        }
        catch(IllegalArgumentException e)
        {
            // TODO error
        }
    }
}
