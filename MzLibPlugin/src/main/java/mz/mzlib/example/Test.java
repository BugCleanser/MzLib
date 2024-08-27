package mz.mzlib.example;

import mz.mzlib.minecraft.mappings.*;
import mz.mzlib.util.Ref;
import mz.mzlib.util.StrongRef;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test
{
	public static String getVersionString()
	{
		return "1.19.4";
	}
	public static boolean isPaper()
	{
		return true;
	}
	public static int version;
	static
	{
		String[] versions = getVersionString().split("\\.", -1);
		version = Integer.parseInt(versions[1]) * 100 + (versions.length > 2 ? Integer.parseInt(versions[2]) : 0);
	}
	public static int getVersion()
	{
		return version;
	}
	public static void main(String[] args) throws Throwable
	{
		IMappings mappingsP2Y,mappingsY2P;
		File folder = new File("./mappings");
		Ref<Mappings> yarnLegacy=new StrongRef<>(null);
		Ref<Mappings> yarn=new StrongRef<>(null);
		Ref<Mappings> yarnIntermediary=new StrongRef<>(null);
		Ref<Mappings> platform=new StrongRef<>(null);
		try
		{
			{
				YarnMappings y = new YarnMappingFetcher(getVersionString(), folder).fetch().get();
				if (y.legacy != null)
					yarnLegacy.set(Mappings.parseYarnLegacy(y.legacy));
				else
				{
					yarn.set(Mappings.parseYarn(y.zip));
					yarnIntermediary.set(Mappings.parseYarnIntermediary(y.intermediary));
				}
			}
			if(isPaper()&&getVersion()>=2005)
				platform.set(Mappings.parseMojang(new MojangMappingsFetcher(getVersionString(), folder).fetch().get()));
			else
				platform.set(Mappings.parseSpigot(new SpigotMappingsFetcher(getVersionString(), folder).fetch().get()));
		}
		catch (Throwable e)
		{
			throw Util.sneakilyThrow(e);
		}

		List<IMappings> result = new ArrayList<>();
		result.add(platform.get());
		if(yarnLegacy.get()!=null)
			result.add(yarnLegacy.get());
		else
		{
			if (getVersion() >= 1403)
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
			if (getVersion() >= 1403)
				result.add(yarnIntermediary.get().reverse());
		}
		result.add(platform.get().reverse());
		mappingsY2P = new MappingsPipe(result);
		System.out.println(mappingsY2P.mapMethod("net.minecraft.server.MinecraftServer", new MappingMethod("tick", new String[]{"Ljava/util/function/BooleanSupplier;"})));
		while (true)
		{
			String input=new Scanner(System.in).next();
			System.out.println(mappingsP2Y.mapClass(input));
			System.out.println(mappingsY2P.mapClass(input));
		}
	}
}
