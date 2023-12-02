package mz.mzlib.network;

public abstract class Channel
{
	public Protocol protocol;
	public Channel(Protocol protocol)
	{
		this.protocol=protocol;
	}
	
	public void regAsClient()
	{
		protocol.channels.add(this);
	}
	public void regAsServer()
	{
		this.regAsClient();
		//TODO: sync the registries
	}
	public void unreg()
	{
		protocol.channels.remove(this);
	}
}
