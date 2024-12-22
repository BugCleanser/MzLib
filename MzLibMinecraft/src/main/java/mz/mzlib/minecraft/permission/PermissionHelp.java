package mz.mzlib.minecraft.permission;

import mz.mzlib.minecraft.CommandSender;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.util.Instance;
import mz.mzlib.util.RuntimeUtil;

public interface PermissionHelp extends Instance, IRegistrar<Permission>
{
    PermissionHelp instance = RuntimeUtil.nul();
    
    boolean check(CommandSender object, String permission);
    default boolean check(CommandSender object, Permission permission)
    {
        return this.check(object, permission.id);
    }
    
    @Override
    default Class<Permission> getType()
    {
        return Permission.class;
    }
}
