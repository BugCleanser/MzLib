package mz.mzlib.mc.bukkit.delegator;

import mz.mzlib.mc.MinecraftServer;
import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.bukkit.MinecraftServerBukkit;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.util.delegator.DelegatorClassFinder;
import mz.mzlib.util.delegator.DelegatorClassFinderClass;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DelegatorClassFinderClass(DelegatorBukkitClass.Finder.class)
public @interface DelegatorBukkitClass
{
	VersionName[] value();
	
	class Finder extends DelegatorClassFinder
	{
		@Override
		public Class<?> find(ClassLoader classLoader,Annotation annotation) throws ClassNotFoundException
		{
			for(VersionName name:((DelegatorMinecraftClass)annotation).value())
			{
				if(MinecraftServer.getInstance().inVersion(name))
				{
					try
					{
						if(name.name().startsWith("NMS."))
							return Class.forName("net.minecraft.server."+MinecraftServerBukkit.protocolVersion+'.'+name.name().substring("NMS.".length()));
						else if(name.name().startsWith("OBC."))
							return Class.forName("org.bukkit.craftbukkit."+MinecraftServerBukkit.protocolVersion+'.'+name.name().substring("OBC.".length()));
						else
							return Class.forName(name.name());
					}
					catch(ClassNotFoundException ignored)
					{
					}
				}
			}
			throw new ClassNotFoundException("No class found: "+annotation);
		}
	}
}
