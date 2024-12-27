package mz.mzlib.minecraft.commands;

import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.command.argument.ArgumentParserNbtCompound;
import mz.mzlib.minecraft.command.argument.ArgumentParserPlayer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.module.MzModule;

public class CommandGiveNbt extends MzModule
{
    public static CommandGiveNbt instance=new CommandGiveNbt();
    
    public Permission permission=new Permission("mzlib.command.giveNbt".toLowerCase());
    
    public Command command;
    
    @Override
    public void onLoad()
    {
        this.register(this.command=new Command("giveNbt".toLowerCase()).setNamespace("mzlib").setPermissionChecker(Command.permissionChecker(this.permission)).setHandler(context ->
        {
            EntityPlayer player=new ArgumentParserPlayer().handle(context);
            NbtCompound nbt=new ArgumentParserNbtCompound().handle(context);
            if(!context.successful)
                return;
            if(!context.doExecute)
                return;
            try
            {
                ItemStack is = ItemStack.decode(nbt);
                player.getInventory().addItemStack(is);
                if(!is.isEmpty())
                    player.drop(is, true);
            }
            catch(Throwable e)
            {
                context.successful=false;
                context.sender.sendMessage(Text.literal(String.format(I18nMinecraft.getTranslation(context.sender, "mzlib.commands.givenbt.error.illegal_item"), e.getMessage())));
            }
        }));
    }
}
