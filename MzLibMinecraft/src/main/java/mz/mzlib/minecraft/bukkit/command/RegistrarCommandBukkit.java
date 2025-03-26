package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.bukkit.MinecraftPlatformBukkit;
import mz.mzlib.minecraft.bukkit.MzLibBukkitPlugin;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.CollectionUtil;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperObject;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class RegistrarCommandBukkit implements IRegistrar<Command>
{
    public static RegistrarCommandBukkit instance = new RegistrarCommandBukkit();
    
    public CommandMapBukkit commandMap;
    {
        if(MinecraftPlatformBukkit.instance.isPaper() && MinecraftPlatformBukkit.instance.getVersion()>=2102 && WrapperObject.FACTORY.create(Bukkit.getPluginManager()).isInstanceOf(PluginManagerPaperV2102.FACTORY))
            commandMap = CommandMapBukkit.FACTORY.create(PluginManagerPaperV2102.FACTORY.create(Bukkit.getPluginManager()).getInstanceManager().getCommandMap());
        else
            commandMap = PluginManagerBukkit.FACTORY.create(Bukkit.getPluginManager()).getCommandMap();
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
            PluginCommand pc = CommandBukkit.newInstance(object.name, MzLibBukkitPlugin.instance).getWrapped();
            pc.setAliases(new ArrayList<>(Arrays.asList(object.aliases)));
            commandMap.getWrapped().register(object.namespace, pc);
            pc.setTabCompleter((sender, command, name, args)->object.suggest(BukkitCommandSourceUtil.fromBukkit(sender), name, String.join(" ", args)));
            pc.setExecutor((sender, command, name, args)->
            {
                object.execute(BukkitCommandSourceUtil.fromBukkit(sender), name, args.length==0 ? null : String.join(" ", args));
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
            System.err.println("Failed to unregister the command: "+object.name);
            return;
        }
        pc.setExecutor(null);
        pc.setTabCompleter(null);
        pc.unregister(commandMap.getWrapped());
        for(String name: CollectionUtil.addAll(new ArrayList<>(pc.getAliases()), pc.getName().toLowerCase(Locale.ENGLISH).trim()))
        {
            commandMap.getCommands().remove(name);
            commandMap.getCommands().remove(object.namespace+":"+name);
        }
    }
}
