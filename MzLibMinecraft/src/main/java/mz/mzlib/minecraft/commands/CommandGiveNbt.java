package mz.mzlib.minecraft.commands;

import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.command.argument.ArgumentParserNbtCompound;
import mz.mzlib.minecraft.command.argument.ArgumentParserPlayer;
import mz.mzlib.minecraft.entity.EntityItem;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.module.MzModule;

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
        this.register(this.command = new Command("givenbt").setNamespace("mzlib").setPermissionChecker(Command.permissionChecker(this.permission)).setHandler(context->
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
            else
            {
                nbt = new ArgumentParserNbtCompound().handle(context);
                if(context.argsReader.hasNext())
                    context.successful = false;
                if(!context.getSource().getPlayer().isPresent())
                {
                    context.successful = false;
                    return;
                }
                player = context.getSource().getPlayer();
            }
            if(!context.successful || !context.doExecute)
                return;
            try
            {
                ItemStack is = ItemStack.decode(nbt);
                player.getInventory().addItemStack(is);
                if(!ItemStack.isEmpty(is))
                {
                    EntityItem ignored = player.drop(is, true);
                }
            }
            catch(Throwable e)
            {
                context.successful = false;
                context.getSource().sendMessage(Text.literal(I18nMinecraft.getTranslationWithArgs(context.getSource(), "mzlib.commands.givenbt.error.illegal_item", Collections.singletonMap("error", e.getMessage()))));
            }
        }));
    }
}
