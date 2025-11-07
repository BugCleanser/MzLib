package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.bukkit.command.BukkitCommandSourceUtil;
import mz.mzlib.minecraft.bukkit.entity.BukkitEntityUtil;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.permission.PermissionHelp;
import mz.mzlib.module.MzModule;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionDefault;

public class PermissionHelpBukkit implements PermissionHelp
{
    public static PermissionHelpBukkit instance = new PermissionHelpBukkit();

    @Override
    public boolean check(CommandSource commandSource, String permission)
    {
        return BukkitCommandSourceUtil.toBukkit(commandSource).hasPermission(permission);
    }

    @Override
    public boolean check(EntityPlayer player, String permission)
    {
        return BukkitEntityUtil.toBukkit(player).hasPermission(permission);
    }

    @Override
    public void register(MzModule module, Permission object)
    {
        Bukkit.getPluginManager().addPermission(new org.bukkit.permissions.Permission(
            object.id, object.defaultNonOp ?
            (object.defaultOp ? PermissionDefault.TRUE : PermissionDefault.NOT_OP) :
            (object.defaultOp ? PermissionDefault.OP : PermissionDefault.FALSE)
        ));
    }
    @Override
    public void unregister(MzModule module, Permission object)
    {
        Bukkit.getPluginManager().removePermission(object.id);
    }
}
