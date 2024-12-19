package mz.mzlib.minecraft.commands;

import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.command.argument.ArgumentParserNbtCompound;
import mz.mzlib.minecraft.command.argument.ArgumentParserPlayer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.module.MzModule;

public class CommandGiveNbt extends MzModule
{
    public static CommandGiveNbt instance=new CommandGiveNbt();
    
    public Permission permission=new Permission("mzlib.command.giveNbt".toLowerCase());
    
    @Override
    public void onLoad()
    {
        this.register(new Command("giveNbt".toLowerCase()).setPermissionChecker(sender->Command.checkPermission(sender, this.permission)).setExecutor(context ->
        {
            EntityPlayer player=new ArgumentParserPlayer().process(context);
            NbtCompound nbt=new ArgumentParserNbtCompound().process(context);
            if(!context.successful)
                return;
            if(!context.doExecute)
                return;
            try
            {
                ItemStack is = ItemStack.decode(nbt);
                player.getInventory().addItemStack(is);
                player.drop(is, true);
            }
            catch(Throwable e)
            {
                context.successful=false;
                context.sender.sendMessage(Text.literal("§4非法物品 "+e.getMessage())); // TODO: i18n
            }
        }));
    }
}
