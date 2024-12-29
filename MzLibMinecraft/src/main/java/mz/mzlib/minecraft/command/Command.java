package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.permission.PermissionHelp;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.util.CollectionUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Command
{
    public String namespace = "minecraft";
    public String name;
    public String[] aliases;
    public Function<CommandSender, Text> permissionChecker;
    public Consumer<CommandContext> handler;
    
    public Command(String name, String... aliases)
    {
        this.name = name;
        this.aliases = aliases;
    }
    
    public Command setNamespace(String value)
    {
        this.namespace = value;
        return this;
    }
    
    public List<Command> children = new ArrayList<>();
    
    public Command addChild(Command child)
    {
        this.children.add(child);
        return this;
    }
    
    public void removeChild(Command child)
    {
        this.children.remove(child);
    }
    
    public Command setPermissionChecker(Function<CommandSender, Text> value)
    {
        this.permissionChecker = value;
        return this;
    }
    
    @SafeVarargs
    public final Command setPermissionCheckers(Function<CommandSender, Text>... value)
    {
        return this.setPermissionChecker(sender->
        {
            for(Function<CommandSender, Text> i: value)
            {
                Text result = i.apply(sender);
                if(result!=null)
                    return result;
            }
            return null;
        });
    }
    
    public static Text checkPermissionAnd(Text... permissionCheckInfos)
    {
        for(Text i: permissionCheckInfos)
            if(i!=null)
                return i;
        return null;
    }
    
    public static Text checkPermissionSenderPlayer(CommandSender sender)
    {
        if(!sender.isInstanceOf(EntityPlayer::create))
            return Text.literal(I18nMinecraft.getTranslation(sender, "mzlib.command.permission.not_player"));
        return null;
    }
    
    public static Text checkPermission(CommandSender sender, Permission permission)
    {
        if(PermissionHelp.instance.check(sender, permission))
            return null;
        return Text.literal(String.format(I18nMinecraft.getTranslation(sender, "mzlib.command.permission.lack"), permission.id));
    }
    
    public static Function<CommandSender, Text> permissionChecker(Permission permission)
    {
        return sender->checkPermission(sender, permission);
    }
    
    public Command setHandler(Consumer<CommandContext> value)
    {
        this.handler = value;
        return this;
    }
    
    public List<String> suggest(CommandSender sender, String command, String args)
    {
        Text permissionCheckInfo = this.permissionChecker!=null ? this.permissionChecker.apply(sender) : null;
        if(permissionCheckInfo!=null)
            return CollectionUtil.newArrayList(permissionCheckInfo.toLiteral());
        String[] argv2 = args.split("\\s+", 2);
        if(argv2.length>1)
            for(Command i: this.children)
            {
                if(CollectionUtil.addAll(CollectionUtil.newArrayList(i.aliases), i.name).contains(argv2[0]))
                    return i.suggest(sender, command+' '+argv2[0], argv2[1]);
            }
        List<String> result = new ArrayList<>();
        if(this.handler!=null)
        {
            CommandContext context = new CommandContext(sender, command, " "+args, false);
            this.handler.accept(context);
            result.addAll(context.getAllEffectiveSuggestions());
            result.addAll(context.getAllArgErrors().stream().map(Text::toLiteral).collect(Collectors.toList()));
        }
        if(argv2.length==1)
        {
            for(Command i: this.children)
            {
                if(i.name.startsWith(argv2[0]))
                    result.add(i.name);
            }
            for(Command i: this.children)
                for(String j: i.aliases)
                {
                    if(j.startsWith(argv2[0]))
                        result.add(j);
                }
        }
        return result;
    }
    public void execute(CommandSender sender, String command, @Nullable String args)
    {
        Text permissionCheckInfo = this.permissionChecker!=null ? this.permissionChecker.apply(sender) : null;
        if(permissionCheckInfo!=null)
        {
            sender.sendMessage(permissionCheckInfo);
            return;
        }
        if(args!=null)
        {
            String[] argv2 = args.split("\\s+", 2);
            for(Command i: this.children)
            {
                if(CollectionUtil.addAll(CollectionUtil.newArrayList(i.aliases), i.name).contains(argv2[0]))
                {
                    i.execute(sender, command+' '+argv2[0], argv2.length>1 ? argv2[1] : null);
                    return;
                }
            }
        }
        CommandContext context = new CommandContext(sender, command, args!=null ? " "+args : "", true);
        if(this.handler!=null)
        {
            this.handler.accept(context);
        }
        if(this.handler==null || !context.isAnySuccessful())
        {
            for(Text e: context.getAllArgErrors())
                sender.sendMessage(e);
            if(!this.children.isEmpty())
                sender.sendMessage(Text.literal(String.format(I18nMinecraft.getTranslation(sender, "mzlib.command.usage.subcommands"), command, this.children.stream().map(i->String.format(I18nMinecraft.getTranslation(sender, "mzlib.command.usage.subcommands.subcommand"), i.name)).collect(Collectors.joining(I18nMinecraft.getTranslation(sender, "mzlib.command.usage.subcommands.subcommand.delimiter"))))));
            if(this.handler!=null)
                for(List<String> argNames: context.getAllArgNames())
                {
                    sender.sendMessage(Text.literal(String.format(I18nMinecraft.getTranslation(sender, "mzlib.command.usage"), command, argNames.stream().map(i->String.format(I18nMinecraft.getTranslation(sender, "mzlib.command.usage.arg"), i)).collect(Collectors.joining(I18nMinecraft.getTranslation(sender, "mzlib.command.usage.arg.delimiter"))))));
                }
        }
    }
}
