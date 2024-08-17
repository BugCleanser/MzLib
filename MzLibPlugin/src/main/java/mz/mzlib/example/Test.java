package mz.mzlib.example;

import mz.mzlib.mappings.Mappings;
import mz.mzlib.mappings.SpigotMappingsFetcher;
import mz.mzlib.mappings.YarnMappingFetcher;
import mz.mzlib.util.Ref;
import mz.mzlib.util.StrongRef;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class Test
{
	public static void main(String[] args) throws Throwable
	{
		File folder = new File("./mappings");
		Ref<Mappings> yarnLegacy = new StrongRef<>(null);
		Ref<Mappings> yarn = new StrongRef<>(null);
		Ref<Mappings> yarnIntermediary = new StrongRef<>(null);
		Ref<Mappings> spigot = new StrongRef<>(null);
		CompletableFuture<Void> taskYarn = new YarnMappingFetcher("1.15.2", folder).fetch().thenAccept(y ->
		{
			if (y.legacy != null)
				yarnLegacy.set(Mappings.parseYarnLegacy(y.legacy));
			else
			{
				yarn.set(Mappings.parseYarn(y.zip));
				yarnIntermediary.set(Mappings.parseYarnIntermediary(y.intermediary));
			}
		});
		CompletableFuture<Void> taskSpigot = new SpigotMappingsFetcher("1.15.2", folder).fetch().thenAccept(s -> spigot.set(Mappings.parseSpigot(s)));
		taskYarn.join();
		taskSpigot.join();
		System.out.println("test!");
	}
}
