package mz.mzlib.mappings;

import java.util.zip.ZipInputStream;

public class YarnMappings
{
    public String legacy;
    public ZipInputStream zip;
    public String intermediary;

    public YarnMappings(String legacy)
    {
        this.legacy=legacy;
    }
    public YarnMappings(ZipInputStream zip, String intermediary)
    {
        this.zip = zip;
        this.intermediary = intermediary;
    }
}
