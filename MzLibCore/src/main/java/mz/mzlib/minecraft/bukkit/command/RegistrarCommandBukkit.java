package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.bukkit.BukkitGameObjectUtil;
import mz.mzlib.minecraft.bukkit.MzLibBukkitPlugin;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.CollectionUtil;
import mz.mzlib.util.RuntimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class RegistrarCommandBukkit implements IRegistrar<Command>
{
    public static RegistrarCommandBukkit instance=new RegistrarCommandBukkit();
    
    public Constructor<PluginCommand> constructorPluginCommand;
    public SimpleCommandMap commandMap;
    public Field fieldKnownCommands;
    public Map<String, Command> knownCommands;
    
    {
        try
        {
            constructorPluginCommand = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructorPluginCommand.setAccessible(true);
            Field cmc = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            cmc.setAccessible(true);
            commandMap = RuntimeUtil.cast(cmc.get(Bukkit.getPluginManager()));
            fieldKnownCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            fieldKnownCommands.setAccessible(true);
            knownCommands = RuntimeUtil.cast(fieldKnownCommands.get(commandMap));
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    
    @Override
    public Class<Command> getType()
    {
        return Command.class;
    }
    
    @Override
    public synchronized void register(MzModule module, Command object)
    {
        try
        {
            PluginCommand pc = constructorPluginCommand.newInstance(object.name, MzLibBukkitPlugin.instance);
            pc.setAliases(new ArrayList<>(Arrays.asList(object.aliases)));
            commandMap.register(object.prefix, pc);
            pc.setTabCompleter((sender, command, name, args)->object.complete(BukkitGameObjectUtil.fromBukkit(sender), name, String.join(" ", args)));
            pc.setExecutor((sender, command, name, args)->
            {
                object.execute(BukkitGameObjectUtil.fromBukkit(sender), name, String.join(" ", args));
                return true;
            });
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    
    @Override
    public synchronized void unregister(MzModule module, Command object)
    {
        PluginCommand pc = Bukkit.getPluginCommand(object.name);
        if(pc==null)
        {
            throw new IllegalStateException("Failed to register the command: "+object.name);
        }
        pc.setExecutor(null);
        pc.setTabCompleter(null);
        pc.unregister(commandMap);
        knownCommands = new HashMap<>(knownCommands);
        for(String name: CollectionUtil.addAll(new ArrayList<>(pc.getAliases()), pc.getName().toLowerCase(Locale.ENGLISH).trim()))
        {
            knownCommands.remove(name);
            knownCommands.remove(object.prefix+":"+name);
        }
        try
        {
            fieldKnownCommands.set(commandMap, knownCommands);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
