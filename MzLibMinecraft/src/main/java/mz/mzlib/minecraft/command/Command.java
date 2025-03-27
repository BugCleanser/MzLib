package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.permission.PermissionHelp;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.util.CollectionUtil;
import mz.mzlib.util.MapBuilder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Command
{
    public String namespace = "minecraft";
    public String name;
    public String[] aliases;
    public Function<CommandSource, Text> permissionChecker;
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
    
    public Command setPermissionChecker(Function<CommandSource, Text> value)
    {
        this.permissionChecker = value;
        return this;
    }
    
    @SafeVarargs
    public final Command setPermissionCheckers(Function<CommandSource, Text>... value)
    {
        return this.setPermissionChecker(source->
        {
            for(Function<CommandSource, Text> i: value)
            {
                Text result = i.apply(source);
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
    
    public static Text checkPermissionSenderPlayer(CommandSource source)
    {
        if(!source.getPlayer().isSome())
            return MinecraftI18n.resolveText(source, "mzlib.command.permission.not_player");
        return null;
    }
    
    public static Text checkPermission(CommandSource source, Permission permission)
    {
        if(PermissionHelp.instance.check(source, permission))
            return null;
        return MinecraftI18n.resolveText(source, "mzlib.command.permission.lack", Collections.singletonMap("permission", permission.id));
    }
    
    public static Function<CommandSource, Text> permissionChecker(Permission permission)
    {
        return source->checkPermission(source, permission);
    }
    
    public Command setHandler(Consumer<CommandContext> value)
    {
        this.handler = value;
        return this;
    }
    
    public List<String> suggest(CommandSource source, String command, String args)
    {
        Text permissionCheckInfo = this.permissionChecker!=null ? this.permissionChecker.apply(source) : null;
        if(permissionCheckInfo!=null)
            return CollectionUtil.newArrayList(permissionCheckInfo.toLiteral());
        String[] argv2 = args.split("\\s+", 2);
        if(argv2.length>1)
            for(Command i: this.children)
            {
                if(CollectionUtil.addAll(CollectionUtil.newArrayList(i.aliases), i.name).contains(argv2[0]))
                    return i.suggest(source, command+' '+argv2[0], argv2[1]);
            }
        List<String> result = new ArrayList<>();
        if(this.handler!=null)
        {
            CommandContext context = new CommandContext(source, command, " "+args, false);
            this.handler.accept(context);
            result.addAll(context.getAllSuggestions());
//            result.addAll(context.getAllArgErrors().stream().map(Text::toLiteral).collect(Collectors.toList()));
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
    public void execute(CommandSource source, String command, @Nullable String args)
    {
        try
        {
            Text permissionCheckInfo = this.permissionChecker!=null ? this.permissionChecker.apply(source) : null;
            if(permissionCheckInfo!=null)
            {
                source.sendMessage(permissionCheckInfo);
                return;
            }
            if(args!=null)
            {
                String[] argv2 = args.split("\\s+", 2);
                for(Command i: this.children)
                {
                    if(CollectionUtil.addAll(CollectionUtil.newArrayList(i.aliases), i.name).contains(argv2[0]))
                    {
                        i.execute(source, command+' '+argv2[0], argv2.length>1 ? argv2[1] : null);
                        return;
                    }
                }
            }
            CommandContext context = new CommandContext(source, command, args!=null ? " "+args : "", true);
            if(this.handler!=null)
            {
                this.handler.accept(context);
            }
            if(this.handler==null || !context.isAnySuccessful())
            {
                for(Text e: context.getAllArgErrors())
                    source.sendMessage(e);
                if(!this.children.isEmpty())
                    source.sendMessage(MinecraftI18n.resolveText(source, "mzlib.command.usage.subcommands", MapBuilder.hashMap().put("command", command).put("subcommands", this.children.stream().map(c->c.name).toArray()).get()));
                if(this.handler!=null)
                    for(List<String> argNames: context.getAllArgNames())
                    {
                        source.sendMessage(MinecraftI18n.resolveText(source, "mzlib.command.usage", MapBuilder.hashMap().put("command", command).put("args", argNames).get()));
                    }
            }
        }
        catch(Throwable e)
        {
            e.printStackTrace(System.err);
        }
    }
}
