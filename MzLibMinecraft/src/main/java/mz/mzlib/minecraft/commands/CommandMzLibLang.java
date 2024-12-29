package mz.mzlib.minecraft.commands;

import mz.mzlib.i18n.I18n;
import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.command.argument.ArgumentParser;
import mz.mzlib.minecraft.command.argument.ArgumentParserString;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
import mz.mzlib.minecraft.i18n.LangEditor;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.module.MzModule;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
            String lang = new ArgumentParserLang().handle(context);
            CommandContext fork = context.fork();
            if(fork.argsReader.hasNext())
                fork.successful = false;
            if(fork.successful)
            {
                if(fork.doExecute)
                    UIStack.get(fork.sender.castTo(EntityPlayer::create)).start(new LangEditor(lang));
                return;
            }
            String key = new ArgumentParserTranslationKey().handle(context);
            String operate = new ArgumentParserString("operate", false, "set", "remove").handle(context);
            if(!context.successful)
                return;
            switch(operate)
            {
                case "set":
                    String value = new ArgumentParserTranslationValue(lang, key).handle(context);
                    if(context.argsReader.hasNext())
                        context.successful = false;
                    if(!context.successful)
                        return;
                    if(context.doExecute)
                    {
                        I18n.custom.map.computeIfAbsent(lang, k->new ConcurrentHashMap<>()).put(key, value);
                        I18nMinecraft.saveCustomLanguage(lang);
                        context.sender.sendMessage(Text.literal(I18nMinecraft.getTranslation(context.sender, "mzlib.command.successful")));
                    }
                    break;
                case "remove":
                    if(context.argsReader.hasNext())
                        context.successful = false;
                    if(!context.successful)
                        return;
                    if(context.doExecute)
                    {
                        I18n.custom.map.computeIfAbsent(lang, k->new ConcurrentHashMap<>()).remove(key);
                        I18nMinecraft.saveCustomLanguage(lang);
                        context.sender.sendMessage(Text.literal(I18nMinecraft.getTranslation(context.sender, "mzlib.command.successful")));
                    }
                    break;
                default:
                    context.successful = false;
                    break;
            }
        })));
    }
    
    @Override
    public void onUnload()
    {
        MzLibMinecraft.instance.command.removeChild(this.command);
    }
    
    public static class ArgumentParserLang extends ArgumentParserString
    {
        public ArgumentParserLang()
        {
            this("language");
        }
        public ArgumentParserLang(String name)
        {
            super(name, false, LangEditor.getLanguages().toArray(new String[0]));
        }
    }
    
    public static class ArgumentParserTranslationKey extends ArgumentParser<String>
    {
        public ArgumentParserTranslationKey()
        {
            this("key");
        }
        public ArgumentParserTranslationKey(String name)
        {
            super(name);
        }
        
        @Override
        public String parse(CommandContext context)
        {
            String result = context.argsReader.readString();
            if(result.contains("."))
            {
                String parent = result.substring(0, result.lastIndexOf('.'));
                LangEditor.getTranslationKeyChildNodes(parent).stream().map(n->parent+"."+n).filter(s->s.startsWith(result)).forEach(context.suggestions::add);
            }
            else
            {
                LangEditor.getTranslationKeyChildNodes(null).stream().filter(s->s.startsWith(result)).forEach(context.suggestions::add);
            }
            return result;
        }
    }
    
    public static class ArgumentParserTranslationValue extends ArgumentParserString
    {
        public String lang;
        public String key;
        
        public ArgumentParserTranslationValue(String lang, String key)
        {
            this("value", lang, key);
        }
        public ArgumentParserTranslationValue(String name, String lang, String key)
        {
            super(name, true, LangEditor.escape(I18n.getTranslation(lang, key)).replace(" ", "\\u0020"));
            this.lang = lang;
            this.key = key;
        }
        
        @Override
        public String parse(CommandContext context)
        {
            return LangEditor.unescape(super.parse(context));
        }
    }
}
