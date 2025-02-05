package mz.mzlib.minecraft.vanilla;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.permission.PermissionHelp;
import mz.mzlib.module.MzModule;

import java.util.HashSet;
import java.util.Set;

public abstract class PermissionRegistry implements PermissionHelp
{
    public Set<Permission> permissions = new HashSet<>();
    public Set<String> defaultNonOp = new HashSet<>();
    public Set<String> defaultOp = new HashSet<>();
    
    @Override
    public boolean check(EntityPlayer player, String permission)
    {
        return (player.isOp() ? this.defaultOp : this.defaultNonOp).contains(permission);
    }
    
    @Override
    public void register(MzModule module, Permission object)
    {
        this.permissions.add(object);
        if(object.defaultNonOp)
            this.defaultNonOp.add(object.id);
        if(object.defaultOp)
            this.defaultOp.add(object.id);
    }
    
    @Override
    public void unregister(MzModule module, Permission object)
    {
        this.permissions.remove(object);
        this.defaultNonOp.remove(object.id);
        this.defaultOp.remove(object.id);
    }
}
