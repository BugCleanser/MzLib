package mz.mzlib.minecraft.bukkit.command;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.bukkit.wrapper.WrapCraftbukkitClass;
import mz.mzlib.minecraft.command.CommandSourceV1400;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;
import org.bukkit.command.BlockCommandSender;

@WrapCraftbukkitClass(@VersionName(name="OBC.command.CraftBlockCommandSender"))
public interface CraftBlockCommandSender extends WrapperObject
{
    @WrapperCreator
    static CraftBlockCommandSender create(Object wrapped)
    {
        return WrapperObject.create(CraftBlockCommandSender.class, wrapped);
    }
    
    @Override
    BlockCommandSender getWrapped();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="block", begin=1400))
    CommandSourceV1400 getCommandSourceV1400();
}
