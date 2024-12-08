package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.GameObject;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.util.CollectionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command
{
    public String prefix="minecraft";
    public String name;
    public String[] aliases;
    public Map<String, Command> children;
    public List<CommandExecutor> executor;
    
    public Command(String name, String[] aliases, Map<String, Command> children, List<CommandExecutor> executor)
    {
        this.name = name;
        this.aliases = aliases;
        this.children = children;
        this.executor = executor;
    }
    
    public Command(CommandBuilder builder)
    {
        this.name = builder.name;
        this.aliases = builder.aliases.toArray(new String[0]);
        this.children = new HashMap<>(builder.children);
        this.executor = new ArrayList<>(builder.executor);
    }
    
    public List<String> complete(GameObject sender, String command, String args)
    {
        // TODO
        return CollectionUtil.newArrayList("开发版未支持命令补全");
    }
    public void execute(GameObject sender, String command, String args)
    {
        String[] argv2 = args.split("\\s+", 2);
        Command subcommand = children.get(argv2[0]);
        if(subcommand!=null)
        {
            subcommand.execute(sender, command+' '+argv2[0], argv2.length>1?argv2[1]:"");
            return;
        }
        List<Text> hints = new ArrayList<>();
        for(CommandExecutor e: executor)
        {
            Text hint = e.execute(sender, command, args);
            if(hint==null)
                return;
            hints.add(hint);
        }
        // TODO
        sender.sendMessage(Text.literal("/"+command+" <"+String.join("|", children.keySet())+">"));
        for(Text hint: hints)
        {
            sender.sendMessage(hint);
        }
    }
}
