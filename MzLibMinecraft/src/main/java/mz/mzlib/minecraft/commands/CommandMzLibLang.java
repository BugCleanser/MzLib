package mz.mzlib.minecraft.commands;

import mz.mzlib.i18n.I18n;
import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.command.ChildCommandRegistration;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.command.argument.ArgumentParser;
import mz.mzlib.minecraft.command.argument.ArgumentParserString;
import mz.mzlib.minecraft.i18n.LangEditor;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UiStack;
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
        this.register(new ChildCommandRegistration(
            MzLibMinecraft.instance.command, //
            this.command = new Command("lang").setPermissionChecker(Command.permissionChecker(this.permission)) //
                .addChild(new Command("loadmc").setHandler(this::lang)) //
                .addChild(new Command("custom").setHandler(this::custom))
        ));
    }

    public void lang(CommandContext context)
    {
        if(context.argsReader.hasNext())
            context.successful = false;
        if(!context.successful)
            return;
        if(context.doExecute)
        {
            if(MinecraftI18n.instance.taskLoading != null)
            {
                context.getSource().sendMessage(
                    MinecraftI18n.resolveText(context.getSource(), "mzlib.commands.mzlib.lang.loadmc.loading"));
                return;
            }
            MinecraftI18n.instance.loadMinecraftLanguages();
            context.getSource()
                .sendMessage(MinecraftI18n.resolveText(context.getSource(), "mzlib.commands.mzlib.lang.loadmc.begin"));
        }
    }

    public void custom(CommandContext context)
    {
        String lang = new ArgumentParserLanguage(
            MinecraftI18n.resolve(context.getSource(), "mzlib.commands.mzlib.lang.custom.arg.language")).handle(
            context);
        CommandContext fork = context.fork();
        if(fork.argsReader.hasNext())
            fork.successful = false;
        if(fork.successful)
        {
            if(fork.doExecute)
            {
                Text check = Command.checkPermissionSenderPlayer(context.getSource());
                if(check != null)
                {
                    fork.getSource().sendMessage(check);
                    return;
                }
                UiStack.get(fork.getSource().getPlayer().unwrap()).start(new LangEditor(lang));
            }
            return;
        }
        String key = new ArgumentParserTranslationKey(
            MinecraftI18n.resolve(context.getSource(), "mzlib.generic.key")).handle(context);
        fork = context.fork();
        if(fork.argsReader.hasNext())
            fork.successful = false;
        if(fork.successful)
        {
            if(fork.doExecute)
            {
                Text check = Command.checkPermissionSenderPlayer(context.getSource());
                if(check != null)
                {
                    fork.getSource().sendMessage(check);
                    return;
                }
                UiStack.get(fork.getSource().getPlayer().unwrap()).start(new LangEditor(lang, key));
            }
            return;
        }
        String operate = new ArgumentParserString(context.getSource(), false, "set", "remove").handle(context);
        if(!context.successful)
            return;
        switch(operate)
        {
            case "set":
                String value = new ArgumentParserTranslationValue(
                    MinecraftI18n.resolve(context.getSource(), "mzlib.generic.value"), lang, key).handle(context);
                if(context.argsReader.hasNext())
                    context.successful = false;
                if(!context.successful)
                    break;
                if(context.doExecute)
                {
                    I18n.custom.map.computeIfAbsent(lang, k -> new ConcurrentHashMap<>()).put(key, value);
                    MinecraftI18n.saveCustomLanguage(lang);
                    context.getSource()
                        .sendMessage(MinecraftI18n.resolveText(context.getSource(), "mzlib.generic.successful"));
                }
                break;
            case "remove":
                if(context.argsReader.hasNext())
                    context.successful = false;
                if(!context.successful)
                    break;
                if(context.doExecute)
                {
                    I18n.custom.map.computeIfAbsent(lang, k -> new ConcurrentHashMap<>()).remove(key);
                    MinecraftI18n.saveCustomLanguage(lang);
                    context.getSource()
                        .sendMessage(MinecraftI18n.resolveText(context.getSource(), "mzlib.generic.successful"));
                }
                break;
            default:
                context.successful = false;
                break;
        }
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
                LangEditor.getTranslationKeyChildNodes(parent).stream().map(n -> parent + "." + n)
                    .filter(s -> s.startsWith(result)).forEach(context.suggestions::add);
            }
            else
                LangEditor.getTranslationKeyChildNodes(null).stream().filter(s -> s.startsWith(result))
                    .forEach(context.suggestions::add);
            return result;
        }
    }

    public static class ArgumentParserTranslationValue extends ArgumentParserString
    {
        public String lang;
        public String key;

        public ArgumentParserTranslationValue(String name, String lang, String key)
        {
            super(name, true, LangEditor.escape(I18n.resolve(lang, key)).replace(" ", "\\u0020"));
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
