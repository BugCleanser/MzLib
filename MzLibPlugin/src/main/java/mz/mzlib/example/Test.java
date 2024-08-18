package mz.mzlib.example;

import mz.mzlib.mappings.*;
import mz.mzlib.util.Ref;
import mz.mzlib.util.StrongRef;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test
{
	public static void main(String[] args) throws Throwable
	{
		Ref<Mappings> yarnLegacy = new StrongRef<>(null);
		Ref<Mappings> yarn = new StrongRef<>(null);
		Ref<Mappings> yarnIntermediary = new StrongRef<>(null);
		Ref<Mappings> platform = new StrongRef<>(null);
		try
		{
			{
				YarnMappings y = new YarnMappingFetcher("1.12.2", new File("./mappings")).fetch().get();
				if (y.legacy != null)
					yarnLegacy.set(Mappings.parseYarnLegacy(y.legacy));
				else
				{
					yarn.set(Mappings.parseYarn(y.zip));
					yarnIntermediary.set(Mappings.parseYarnIntermediary(y.intermediary));
				}
			}
			platform.set(Mappings.parseSpigot(new SpigotMappingsFetcher("1.12.2", new File("./mappings")).fetch().get()));
		}
		catch (Throwable e)
		{
			throw Util.sneakilyThrow(e);
		}

		List<IMappings> result = new ArrayList<>();
		result.add(platform.get());
		if (yarnLegacy.get() != null)
			result.add(yarnLegacy.get());
		else
		{
			result.add(yarnIntermediary.get());
			result.add(yarn.get());
		}
		IMappings mappingsP2Y = new MappingsPipe(result);

		result = new ArrayList<>();
		if (yarnLegacy.get() != null)
			result.add(yarnLegacy.get().reverse());
		else
		{
			result.add(yarn.get().reverse());
			result.add(yarnIntermediary.get().reverse());
		}
		result.add(platform.get().reverse());
		IMappings mappingsY2P = new MappingsPipe(result);
		System.out.println(platform.get().mapField("EntityPlayer","playerConnection"));
		System.out.println(platform.get().reverse().mapField("oq","a"));
		System.out.println(platform.get().reverse().mapField("oq","b"));
		System.out.println(platform.get().reverse().mapField("oq","bW"));
		while (true)
		{
			String input=new Scanner(System.in).next();
			System.out.println(mappingsP2Y.mapClass(input));
			System.out.println(mappingsY2P.mapClass(input));
		}
	}
}
