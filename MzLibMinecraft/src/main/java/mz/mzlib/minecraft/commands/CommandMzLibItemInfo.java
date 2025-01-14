package mz.mzlib.minecraft.commands;

import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.command.ChildCommandRegistration;
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
        this.register(new ChildCommandRegistration(MzLibMinecraft.instance.command, this.command = new Command("iteminfo").setPermissionCheckers(Command::checkPermissionSenderPlayer, Command.permissionChecker(this.permission)).setHandler(context->
        {
            if(context.argsReader.hasNext())
                context.successful = false;
            if(!context.successful)
                return;
            if(context.doExecute)
            {
                context.getSource().sendMessage(Text.literal(context.getSource().getPlayer().getHandItemStack().encode().toString()));
            }
        })));
    }
    
    @Override
    public void onUnload()
    {
        MzLibMinecraft.instance.command.removeChild(this.command);
    }
}
