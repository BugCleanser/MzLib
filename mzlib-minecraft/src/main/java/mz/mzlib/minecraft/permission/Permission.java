package mz.mzlib.minecraft.permission;

public class Permission
{
    public String id;
    public boolean defaultNonOp;
    public boolean defaultOp;

    public Permission(String id, boolean defaultNonOp, boolean defaultOp)
    {
        this.id = id;
        this.defaultNonOp = defaultNonOp;
        this.defaultOp = defaultOp;
    }
    public Permission(String id)
    {
        this(id, false, true);
    }

    @Override
    public int hashCode()
    {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Permission))
            return false;
        Permission permission = (Permission) obj;
        return this.id.equals(permission.id);
    }
}
