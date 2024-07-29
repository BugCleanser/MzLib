package mz.mzlib.mc.bukkit.delegator;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.bukkit.MinecraftServerBukkit;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorClassFinder;
import mz.mzlib.util.delegator.DelegatorClassFinderClass;
import org.bukkit.Bukkit;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DelegatorClassFinderClass(DelegatorBukkitClass.Finder.class)
public @interface DelegatorBukkitClass
{
	VersionName[] value();
	
	class Finder extends DelegatorClassFinder
	{
		public static String protocolVersion;
		static
		{
			protocolVersion=Bukkit.getServer().getClass().getPackage().getName().substring("org.bukkit.craftbukkit".length());
			if(protocolVersion.isEmpty())
				protocolVersion=null;
			else
				protocolVersion=protocolVersion.substring(".".length());
		}
		@Override
		public Class<?> find(ClassLoader classLoader,Annotation annotation) throws ClassNotFoundException
		{
			for(VersionName name:((DelegatorBukkitClass)annotation).value())
			{
				if(Delegator.createStatic(MinecraftServerBukkit.class).inVersion(name))
				{
					try
					{
						if(name.name().startsWith("NMS."))
							return Class.forName("net.minecraft.server."+protocolVersion+'.'+name.name().substring("NMS.".length()));
						else if(name.name().startsWith("OBC."))
							return Class.forName("org.bukkit.craftbukkit."+(protocolVersion!=null?protocolVersion+'.':"")+name.name().substring("OBC.".length()));
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
