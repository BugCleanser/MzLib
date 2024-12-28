package mz.mzlib.minecraft.commands;

import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.command.argument.ArgumentParserString;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
import mz.mzlib.minecraft.i18n.LangEditor;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.module.MzModule;

public class CommandMzLibLang extends MzModule
{
    public static CommandMzLibLang instance = new CommandMzLibLang();
    
    public Permission permission = new Permission("mzlib.command.mzlib.lang");
    
    public Command command;
    
    @Override
    public void onLoad()
    {
        this.register(this.permission);
        MzLibMinecraft.instance.command.addChild(this.command = new Command("lang").setPermissionChecker(Command.permissionChecker(this.permission)).addChild(new Command("loadmc").setHandler(context->
        {
            if(context.argsReader.hasNext())
                context.successful = false;
            if(!context.successful || !context.doExecute)
                return;
            if(I18nMinecraft.instance.taskLoading!=null)
            {
                context.sender.sendMessage(Text.literal(I18nMinecraft.getTranslation(context.sender, "mzlib.commands.mzlib.lang.loadmc.loading")));
                return;
            }
            I18nMinecraft.instance.loadMinecraftLanguages();
            context.sender.sendMessage(Text.literal(I18nMinecraft.getTranslation(context.sender, "mzlib.commands.mzlib.lang.loadmc.begin")));
        })).addChild(new Command("custom").setPermissionChecker(Command::checkPermissionSenderPlayer).setHandler(context->
        {
            String lang = new ArgumentParserString("lang", false).handle(context);
            if(context.argsReader.hasNext())
                context.successful = false;
            if(!context.successful || !context.doExecute)
                return;
            UIStack.get(context.sender.castTo(EntityPlayer::create)).start(new LangEditor(lang));
        })));
    }
    
    @Override
    public void onUnload()
    {
        MzLibMinecraft.instance.command.removeChild(this.command);
    }
}
