package mz.mzlib.mappings;

public class SpigotMappings
{
    public String accessTransform;
    public String classMappings;
    public String memberMappings;

    public SpigotMappings(String accessTransform, String classMappings, String memberMappings)
    {
        this.accessTransform = accessTransform;
        this.classMappings = classMappings;
        this.memberMappings = memberMappings;
    }

    @Override
    public String toString()
    {
        return "accessTransform: " + accessTransform + ", classMappings: " + classMappings + ", memberMappings: " + memberMappings;
    }
}