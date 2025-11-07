package mz.mzlib.minecraft.mappings;

public class MappingsPackage extends MappingsClasses<MappingsPackage>
{
    protected String from;
    protected String to;

    public MappingsPackage(String from, String to)
    {
        this.from = from;
        this.to = to;
    }

    @Override
    public String mapClass0(String cn)
    {
        if(cn.startsWith(getPrefix(this.from)) && cn.indexOf('.', getPrefix(this.from).length() + 1) == -1)
            return getPrefix(this.to) + cn.substring(getPrefix(this.from).length());
        return null;
    }

    public static String getPrefix(String packageName)
    {
        if(packageName == null)
            return "";
        else
            return packageName + ".";
    }

    @Override
    protected MappingsPackage invert()
    {
        return new MappingsPackage(this.to, this.from);
    }
}
