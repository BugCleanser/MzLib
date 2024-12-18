package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.GameObject;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Command
{
    public String prefix="minecraft";
    public String name;
    public String[] aliases;
    public List<Command> children;
    public Function<GameObject, Text> permissionChecker;
    public Consumer<CommandContext> executor;
    
    public Command(String name, String ...aliases)
    {
        this.name = name;
        this.aliases = aliases;
    }
    
    public Command addChild(Command child)
    {
        this.children.add(child);
        return this;
    }
    
    public List<String> suggest(GameObject sender, String command, String args)
    {
        Text permissionCheckInfo=this.permissionChecker!=null?this.permissionChecker.apply(sender):null;
        if(permissionCheckInfo!=null)
            return CollectionUtil.newArrayList(permissionCheckInfo.toPlain());
        String[] argv2 = args.split("\\s+", 2);
        if(argv2.length>1)
            for(Command i:this.children)
            {
                if(CollectionUtil.addAll(CollectionUtil.newArrayList(i.aliases), i.name).contains(argv2[0]))
                    return i.suggest(sender, command+' '+argv2[0], argv2[1]);
            }
        List<String> result=new ArrayList<>();
        if(this.executor!=null)
        {
            CommandContext context = new CommandContext(command, args, false);
            this.executor.accept(context);
            result.addAll(context.suggestions);
        }
        if(argv2.length==1)
        {
            for(Command i:this.children)
            {
                if(i.name.startsWith(argv2[0]))
                    result.add(i.name);
            }
            for(Command i:this.children)
                for(String j:i.aliases)
                {
                    if(j.startsWith(argv2[0]))
                        result.add(j);
                }
        }
        return result;
    }
    public void execute(GameObject sender, String command, String args)
    {
        Text permissionCheckInfo=this.permissionChecker!=null?this.permissionChecker.apply(sender):null;
        if(permissionCheckInfo!=null)
        {
            sender.sendMessage(permissionCheckInfo);
            return;
        }
        String[] argv2 = args.split("\\s+", 2);
        for(Command i:this.children)
        {
            if(CollectionUtil.addAll(CollectionUtil.newArrayList(i.aliases), i.name).contains(argv2[0]))
            {
                i.execute(sender, command+' '+argv2[0], argv2.length>1?argv2[1]:null);
                return;
            }
        }
        CommandContext context = new CommandContext(command, args, true);
        if(this.executor!=null)
            this.executor.accept(context);
        if(this.executor==null || !context.successful)
        {
            // TODO: i18n
            sender.sendMessage(Text.literal("/"+command+" <"+String.join(" | ", children.stream().map(c->c.name).collect(Collectors.toSet()))+"> ..."));
        }
    }
}
