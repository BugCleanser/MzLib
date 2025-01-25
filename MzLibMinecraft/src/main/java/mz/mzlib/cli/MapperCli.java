package mz.mzlib.cli;

import mz.mzlib.minecraft.mappings.*;
import mz.mzlib.util.Ref;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.StrongRef;
import org.apache.commons.cli.CommandLine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapperCli
{
    public static CliVersion version = new CliVersion("1.14.4");
    //	public static int getVersion()
//	{
//		return version.version;
//	}
    public static MappingsPipe getY2PMapping(CliVersion version)
    {
        File folder = new File("./mappings");
        Ref<Mappings> yarnLegacy = new StrongRef<>(null);
        Ref<Mappings> yarn = new StrongRef<>(null);
        Ref<Mappings> yarnIntermediary = new StrongRef<>(null);
        Ref<Mappings> platform = new StrongRef<>(null);
        try
        {
            {
                YarnMappings y = new YarnMappingFetcher(version.getVersionString(), folder).fetch();
                if(y.legacy!=null)
                    yarnLegacy.set(Mappings.parseYarnLegacy(y.legacy));
                else
                {
                    yarn.set(Mappings.parseYarn(y.zip));
                    yarnIntermediary.set(Mappings.parseYarnIntermediary(y.intermediary));
                }
            }
            if(version.isPaper() && version.version>=2005)
                platform.set(Mappings.parseMojang(new MojangMappingsFetcher(version.getVersionString(), folder).fetch()));
            else
                platform.set(Mappings.parseSpigot(new SpigotMinecraftMappingsFetcher(version.getVersionString(), folder).fetch()));
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
        
        List<IMappings> result = new ArrayList<>();
        if(yarnLegacy.get()!=null)
            result.add(yarnLegacy.get().reverse());
        else
        {
            result.add(yarn.get().reverse());
            if(version.version>=1403)
                result.add(yarnIntermediary.get().reverse());
        }
        result.add(platform.get().reverse());
        return new MappingsPipe(result);
    }
    
    
    public static void cli(CommandLine cmd, CliVersion version)
    {
        IMappings mappingsP2Y, mappingsY2P;
        File folder = new File("./mappings");
        Ref<Mappings> yarnLegacy = new StrongRef<>(null);
        Ref<Mappings> yarn = new StrongRef<>(null);
        Ref<Mappings> yarnIntermediary = new StrongRef<>(null);
        Ref<Mappings> platform = new StrongRef<>(null);
        try
        {
            {
                YarnMappings y = new YarnMappingFetcher(version.getVersionString(), folder).fetch();
                if(y.legacy!=null)
                    yarnLegacy.set(Mappings.parseYarnLegacy(y.legacy));
                else
                {
                    yarn.set(Mappings.parseYarn(y.zip));
                    yarnIntermediary.set(Mappings.parseYarnIntermediary(y.intermediary));
                }
            }
            if(version.isPaper() && version.version>=2005)
                platform.set(Mappings.parseMojang(new MojangMappingsFetcher(version.getVersionString(), folder).fetch()));
            else
                platform.set(Mappings.parseSpigot(new SpigotMinecraftMappingsFetcher(version.getVersionString(), folder).fetch()));
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
        
        List<IMappings> result = new ArrayList<>();
        result.add(platform.get());
        if(yarnLegacy.get()!=null)
            result.add(yarnLegacy.get());
        else
        {
            if(version.version>=1403)
                result.add(yarnIntermediary.get());
            result.add(yarn.get());
        }
        mappingsP2Y = new MappingsPipe(result);
        
        result = new ArrayList<>();
        if(yarnLegacy.get()!=null)
            result.add(yarnLegacy.get().reverse());
        else
        {
            result.add(yarn.get().reverse());
            if(version.version>=1403)
                result.add(yarnIntermediary.get().reverse());
        }
        result.add(platform.get().reverse());
        mappingsY2P = new MappingsPipe(result);
        System.out.println(mappingsY2P.mapMethod("net.minecraft.network.ClientConnection", new MappingMethod("sendImmediately", new String[]{"Lnet/minecraft/network/Packet;", "Lio/netty/util/concurrent/GenericFutureListener;"})));
        System.out.println(mappingsP2Y.mapMethod("net.minecraft.network.ClientConnection", new MappingMethod("sendImmediately", new String[]{"Lnet.minecraft.network.Packet;", "Lio.netty.util.concurrent.GenericFutureListener;"})));
        System.out.println("Input a Class Name:");
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext())
        {
            String input = scanner.next();
            if("exit".equalsIgnoreCase(input))
            {
                break;
            }
            System.out.println(mappingsP2Y.mapClass(input));
            System.out.println(mappingsY2P.mapClass(input));
        }
        scanner.close();
    }
    
    public static void cli(CommandLine cmd)
    {
        cli(cmd, version);
    }
}
