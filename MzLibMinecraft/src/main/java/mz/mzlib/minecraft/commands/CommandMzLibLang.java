package mz.mzlib.minecraft.commands;

import mz.mzlib.i18n.I18n;
import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.command.ChildCommandRegistration;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.command.argument.ArgumentParser;
import mz.mzlib.minecraft.command.argument.ArgumentParserString;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
import mz.mzlib.minecraft.i18n.LangEditor;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.module.MzModule;

import java.util.concurrent.ConcurrentHashMap;

public class CommandMzLibLang extends MzModule
{
    public static CommandMzLibLang instance = new CommandMzLibLang();
    
    public Permission permission = new Permission("mzlib.command.mzlib.lang");
    
    public Command command;
    
    @Override
    public void onLoad()
    {
        this.register(this.permission);
        this.register(new ChildCommandRegistration(MzLibMinecraft.instance.command, this.command = new Command("lang").setPermissionChecker(Command.permissionChecker(this.permission)).addChild(new Command("loadmc").setHandler(context->
        {
            if(context.argsReader.hasNext())
                context.successful = false;
            if(!context.successful)
                return;
            if(context.doExecute)
            {
                if(I18nMinecraft.instance.taskLoading!=null)
                {
                    context.getSource().sendMessage(Text.literal(I18nMinecraft.getTranslation(context.getSource(), "mzlib.commands.mzlib.lang.loadmc.loading")));
                    return;
                }
                I18nMinecraft.instance.loadMinecraftLanguages();
                context.getSource().sendMessage(Text.literal(I18nMinecraft.getTranslation(context.getSource(), "mzlib.commands.mzlib.lang.loadmc.begin")));
            }
        })).addChild(new Command("custom").setPermissionChecker(Command::checkPermissionSenderPlayer).setHandler(context->
        {
            String lang = new ArgumentParserLanguage(I18nMinecraft.getTranslation(context.getSource(), "mzlib.commands.mzlib.lang.custom.arg.language")).handle(context);
            CommandContext fork = context.fork();
            if(fork.argsReader.hasNext())
                fork.successful = false;
            if(fork.successful)
            {
                if(fork.doExecute)
                    UIStack.get(fork.getSource().getPlayer()).start(new LangEditor(lang));
                return;
            }
            String key = new ArgumentParserTranslationKey(I18nMinecraft.getTranslation(context.getSource(), "mzlib.generic.key")).handle(context);
            fork = context.fork();
            if(fork.argsReader.hasNext())
                fork.successful = false;
            if(fork.successful)
            {
                if(fork.doExecute)
                    UIStack.get(fork.getSource().getPlayer()).start(new LangEditor(lang, key));
                return;
            }
            String operate = new ArgumentParserString(context.getSource(), false, "set", "remove").handle(context);
            if(!context.successful)
                return;
            switch(operate)
            {
                case "set":
                    String value = new ArgumentParserTranslationValue(I18nMinecraft.getTranslation(context.getSource(), "mzlib.generic.value"), lang, key).handle(context);
                    if(context.argsReader.hasNext())
                        context.successful = false;
                    if(!context.successful)
                        break;
                    if(context.doExecute)
                    {
                        I18n.custom.map.computeIfAbsent(lang, k->new ConcurrentHashMap<>()).put(key, value);
                        I18nMinecraft.saveCustomLanguage(lang);
                        context.getSource().sendMessage(Text.literal(I18nMinecraft.getTranslation(context.getSource(), "mzlib.generic.successful")));
                    }
                    break;
                case "remove":
                    if(context.argsReader.hasNext())
                        context.successful = false;
                    if(!context.successful)
                        break;
                    if(context.doExecute)
                    {
                        I18n.custom.map.computeIfAbsent(lang, k->new ConcurrentHashMap<>()).remove(key);
                        I18nMinecraft.saveCustomLanguage(lang);
                        context.getSource().sendMessage(Text.literal(I18nMinecraft.getTranslation(context.getSource(), "mzlib.generic.successful")));
                    }
                    break;
                default:
                    context.successful = false;
                    break;
            }
        }))));
    }
    
    public static class ArgumentParserLanguage extends ArgumentParserString
    {
        public ArgumentParserLanguage(String name)
        {
            super(name, false, LangEditor.getLanguages().toArray(new String[0]));
        }
    }
    
    public static class ArgumentParserTranslationKey extends ArgumentParser<String>
    {
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
