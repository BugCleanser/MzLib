package mz.mzlib.cli;

import mz.mzlib.minecraft.mappings.MappingsPipe;

import java.util.*;

public class CliSorter extends Sorter<String, CliSorter.VersionBucket,CliVersion>{
    public static String[] versions = new String[]{"1.12.2","1.14.3","1.20.5","1.15","1.15.1","1.15.2","1.16.1","1.16.3","1.16.4","1.17.1","1.18","1.18.2","1.16.5","1.19.2","1.19.4","1.20.1","1.20.4","1.21.1"};

    public static Map<String,VersionBucket> getCliMap(String mappedClass) throws InterruptedException {
        CliSorter sorter = new CliSorter();
        for(String version:versions) {
            System.out.println(version);
            CliVersion cliv = new CliVersion(version);
            MappingsPipe mappingsPipe = MapperCli.getY2PMapping(cliv);
            String mappingClass = mappingsPipe.mapClass(mappedClass);
            sorter.addMap(mappingClass,cliv);
        }
        return sorter.map;
    }

    @Override
    public VersionBucket newSet() {
        return new VersionBucket();
    }

    public static class VersionBucket extends HashSet<CliVersion>{

    }
}
