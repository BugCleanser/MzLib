package mz.mzlib.minecraft.permission;

public class Permission
{
    public String id;
    public boolean commonDefault;
    public boolean opDefault;
    
    public Permission(String id, boolean commonDefault, boolean opDefault)
    {
	    this.id=id;
	    this.commonDefault=commonDefault;
	    this.opDefault=opDefault;
    }
    public Permission(String id)
    {
        this(id, false, true);
    }
}
