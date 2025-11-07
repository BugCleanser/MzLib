package mz.mzlib.minecraft.commands;

import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.command.argument.ArgumentParserNbtCompound;
import mz.mzlib.minecraft.command.argument.ArgumentParserPlayer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Option;
import mz.mzlib.util.Result;

import java.util.Collections;

public class CommandGiveNbt extends MzModule
{
    public static CommandGiveNbt instance = new CommandGiveNbt();

    public Permission permission = new Permission("mzlib.command.givenbt");

    public Command command;

    @Override
    public void onLoad()
    {
        this.register(this.permission);
        this.register(this.command = new Command("givenbt") //
            .setNamespace("mzlib") //
            .setPermissionChecker(Command.permissionChecker(this.permission)) //
            .setHandler(this::handle));
    }

    public void handle(CommandContext context)
    {
        EntityPlayer player;
        NbtCompound nbt;

        CommandContext fork = context.fork();
        player = new ArgumentParserPlayer().handle(fork);
        nbt = new ArgumentParserNbtCompound().handle(fork);
        if(fork.argsReader.hasNext())
            fork.successful = false;
        if(fork.successful)
            context = fork;
        else if(context.getSource().getPlayer().isSome())
        {
            player = context.getSource().getPlayer().unwrap();
            nbt = new ArgumentParserNbtCompound().handle(context);
            if(context.argsReader.hasNext())
                context.successful = false;
            if(!context.getSource().getPlayer().isSome())
            {
                context.successful = false;
                return;
            }
        }
        if(!context.successful || !context.doExecute)
            return;
        Result<Option<ItemStack>, String> decode = ItemStack.decode(nbt);
        for(ItemStack itemStack : decode.getValue())
        {
            player.give(itemStack);
        }
        for(String err : decode.getError())
        {
            context.successful = false;
            context.getSource().sendMessage(MinecraftI18n.resolveText(
                context.getSource(), "mzlib.commands.givenbt.error.illegal_item",
                Collections.singletonMap("error", err)
            ));
        }
    }
}
