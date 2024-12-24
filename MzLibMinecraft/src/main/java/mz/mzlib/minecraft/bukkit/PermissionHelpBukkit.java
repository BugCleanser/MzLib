package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.bukkit.command.BukkitCommandSenderUtil;
import mz.mzlib.minecraft.command.CommandSender;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.permission.PermissionHelp;
import mz.mzlib.module.MzModule;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionDefault;

public class PermissionHelpBukkit implements PermissionHelp
{
    public static PermissionHelpBukkit instance=new PermissionHelpBukkit();
    
    @Override
    public boolean check(CommandSender object, String permission)
    {
        return BukkitCommandSenderUtil.toBukkit(object).hasPermission(permission);
    }
    
    @Override
    public void register(MzModule module, Permission object)
    {
        Bukkit.getPluginManager().addPermission(new org.bukkit.permissions.Permission(object.id, object.commonDefault?(object.opDefault?PermissionDefault.TRUE:PermissionDefault.NOT_OP):(object.opDefault?PermissionDefault.OP:PermissionDefault.FALSE)));
    }
    @Override
    public void unregister(MzModule module, Permission object)
    {
        Bukkit.getPluginManager().removePermission(object.id);
    }
}
