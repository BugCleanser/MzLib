package mz.mzlib.minecraft.permission;

import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;

public interface PermissionHelp extends Instance, IRegistrar<Permission>
{
    PermissionHelp instance = RuntimeUtil.nul();
    
    boolean check(CommandSource object, String permission);
    default boolean check(CommandSource object, Permission permission)
    {
        return this.check(object, permission.id);
    }
    
    boolean check(EntityPlayer player, String permission);
    
    default boolean check(EntityPlayer player, Permission permission)
    {
        return this.check(player, permission.id);
    }
    
    @Override
    default Class<Permission> getType()
    {
        return Permission.class;
    }
}
