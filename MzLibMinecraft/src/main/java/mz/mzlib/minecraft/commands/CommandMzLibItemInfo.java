package mz.mzlib.minecraft.commands;

import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.module.MzModule;

public class CommandMzLibItemInfo extends MzModule
{
    public static CommandMzLibItemInfo instance = new CommandMzLibItemInfo();
    
    public Permission permission = new Permission("mzlib.command.mzlib.iteminfo");
    
    public Command command;
    
    @Override
    public void onLoad()
    {
        MzLibMinecraft.instance.command.addChild(this.command = new Command("iteminfo").setPermissionChecker(Command::checkPermissionSenderPlayer).setPermissionChecker(Command.permissionChecker(this.permission)).setHandler(context->
        {
            if(context.argsReader.hasNext())
                context.successful = false;
            if(!context.successful)
                return;
            if(context.doExecute)
            {
                context.sender.sendMessage(Text.literal(context.sender.castTo(EntityPlayer::create).getHandItemStack().encode().toString()));
            }
        }));
    }
    
    @Override
    public void onUnload()
    {
        MzLibMinecraft.instance.command.removeChild(this.command);
    }
}
