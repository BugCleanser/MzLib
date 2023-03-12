package mz.lib.minecraft.mzlibcommand.debug;

import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.command.AbsLastCommandProcessor;
import mz.lib.minecraft.command.CommandHandler;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;

public class DebugFieldsSubcommand extends AbsLastCommandProcessor
{
    public static DebugFieldsSubcommand instance=new DebugFieldsSubcommand();
    public DebugFieldsSubcommand()
    {
        super(false,null,"fields");
    }

    @CommandHandler
    public void execute(CommandSender sender,String className)
    {
        try
        {
            for(Field f:Class.forName(className,false, MzLib.class.getClassLoader()).getDeclaredFields())
            {
                sender.sendMessage("§a"+f.toString());
            }
        }
        catch(ClassNotFoundException e)
        {
            sender.sendMessage("§4ClassNotFoundException");
        }
    }
}
