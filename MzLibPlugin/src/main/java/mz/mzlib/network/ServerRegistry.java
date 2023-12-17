package mz.mzlib.network;

public class ServerRegistry<T> extends Registry<T>
{
	public Protocol protocol;
	public ServerRegistry(Protocol protocol,String name)
	{
		super(name);
		this.protocol=protocol;
	}
}
