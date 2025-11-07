package mz.mzlib.example;

import mz.mzlib.MzLib;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.mappings.*;

import java.io.File;
import java.util.*;

public class TestMappings
{
    @SuppressWarnings("ConstantValue")
    public static void main(String[] args) throws Throwable
    {
        MzLib.instance.load();
        MzLib.instance.register(new MinecraftPlatform()
        {
            @Override
            public String getVersionString()
            {
                return "1.9.4";
            }
            @Override
            public Set<String> getTags()
            {
                return Collections.emptySet();
            }
            @Override
            public int getVersion()
            {
                return MinecraftPlatform.parseVersion(this.getVersionString());
            }
            @Override
            public String getLanguage(EntityPlayer player)
            {
                return "";
            }
            @Override
            public File getMzLibJar()
            {
                return new File("./out/MzLibMinecraft-10.0.1-beta-dev12.jar");
            }
            @Override
            public File getMzLibDataFolder()
            {
                return null;
            }
            @Override
            public Mappings<?> getMappings()
            {
                return null;
            }
        });
        String protocolVersion = "v1_9_R2";
        boolean isPaper = true;

        File folder = new File("mappings");
        List<Mappings<?>> result = new ArrayList<>();
        if(isPaper && MinecraftPlatform.instance.getVersion() >= 2005)
            result.add(
                new MinecraftMappingsFetcherMojang().fetch(MinecraftPlatform.instance.getVersionString(), folder));
        else
            result.add(new MinecraftMappingsFetcherSpigot().fetch(
                MinecraftPlatform.instance.getVersionString(),
                protocolVersion, folder
            ));
        if(MinecraftPlatform.instance.getVersion() < 1400)
        {
            // = new MinecraftMappingsFetcherLegacyYarnIntermediary().fetch(versionString, folder);
            result.add(
                new MinecraftMappingsFetcherLegacyYarn().fetch(MinecraftPlatform.instance.getVersionString(), folder));
        }
        else
        {
            result.add(
                new MinecraftMappingsFetcherYarnIntermediary().fetch(
                    MinecraftPlatform.instance.getVersionString(),
                    folder
                ));
            result.add(new MinecraftMappingsFetcherYarn().fetch(MinecraftPlatform.instance.getVersionString(), folder));
        }
        MappingsPipe mappings = new MappingsPipe(result);
        Scanner scanner = new Scanner(System.in);
        //noinspection InfiniteLoopStatement
        while(true)
        {
            String input = scanner.nextLine();
            System.out.println(mappings.mapClass(input));
            System.out.println(mappings.inverse().mapClass(input));
        }
    }
}
