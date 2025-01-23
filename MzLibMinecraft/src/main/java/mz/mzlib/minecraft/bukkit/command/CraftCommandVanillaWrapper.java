package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.BukkitOnly;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;
import org.bukkit.command.CommandSender;

@BukkitOnly
@WrapCraftbukkitClass(@VersionName(name="OBC.command.VanillaCommandWrapper"))
public interface CraftCommandVanillaWrapper extends WrapperObject
{
    @WrapperCreator
    static CraftCommandVanillaWrapper create(Object wrapped)
    {
        return WrapperObject.create(CraftCommandVanillaWrapper.class, wrapped);
    }
    
    static CommandSource toCommandSource(CommandSender object)
    {
        return create(null).staticToCommandSource(object);
    }
    @WrapMethod("getListener")
    CommandSource staticToCommandSource(CommandSender object);
}
