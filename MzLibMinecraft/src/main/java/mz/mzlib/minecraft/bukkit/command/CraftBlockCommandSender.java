package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;
import org.bukkit.command.BlockCommandSender;

@BukkitEnabled
@WrapCraftbukkitClass(@VersionName(name="OBC.command.CraftBlockCommandSender"))
public interface CraftBlockCommandSender extends WrapperObject
{
    WrapperFactory<CraftBlockCommandSender> FACTORY = WrapperFactory.find(CraftBlockCommandSender.class);
    @Deprecated
    @WrapperCreator
    static CraftBlockCommandSender create(Object wrapped)
    {
        return WrapperObject.create(CraftBlockCommandSender.class, wrapped);
    }
    
    @Override
    BlockCommandSender getWrapped();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="block", begin=1400))
    CommandSource getCommandSourceV1400();
}
