package mz.mzlib.network;

import mz.mzlib.util.RuntimeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class Protocol
{
	public Registry<String> registryRegistry;
	public Map<String,Registry<?>> registries=new ConcurrentHashMap<>();
	public Map<String,Class<? extends Packet>> packetClasses=new ConcurrentHashMap<>();
	public Set<Channel> channels=ConcurrentHashMap.newKeySet();
	
	@SuppressWarnings("DeprecatedIsStillUsed")
	@Deprecated
	public Protocol()
	{
	}
	
	@Deprecated
	public void init(Function<String,Registry<?>> registryAllocator)
	{
		this.registryRegistry=RuntimeUtil.cast(registryAllocator.apply("registry"));
		this.registries.put("registry",this.registryRegistry);
		this.registries.put("packet",registryAllocator.apply("packet"));
		this.registryRegistry.add(0,this.registryRegistry.name);
		this.registryRegistry.add(1,"packet");
	}
	public static Protocol newServerProtocol()
	{
		Protocol ret=new Protocol();
		ret.init(name->new ServerRegistry<>(ret,name));
		return ret;
	}
	
	public void regPacketClass(Class<? extends Packet> packetClass) throws Throwable
	{
		try(FileOutputStream fos=new FileOutputStream(new File("")))
		{
			packetClasses.put(packetClass.newInstance().getId(),packetClass);
		}
		catch(Throwable e)
		{
			throw e;
		}
	}
	
	public Registry<String> getPacketRegistry()
	{
		return RuntimeUtil.cast(registries.get("packet"));
	}
}
