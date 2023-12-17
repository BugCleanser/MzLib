package mz.mzlib.network;

import mz.mzlib.util.RuntimeUtil;
import org.bukkit.util.Consumer;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public abstract class Channel
{
	public Protocol protocol;
	public Map<Class<? extends Packet>,Consumer<? extends Packet>> listeners=new HashMap<>();
	public Channel(Protocol protocol)
	{
		this.protocol=protocol;
	}
	
	public void regAsServer()
	{
		protocol.channels.add(this);
	}
	public void regAsClient()
	{
		this.regAsServer();
	}
	public <T extends Packet> void addListener(Class<? extends T> type,Consumer<T> processor)
	{
		this.listeners.put(type,processor);
	}
	public void unreg()
	{
		protocol.channels.remove(this);
	}
	
	public void receive(byte[] packet)
	{
		try
		{
			ByteBuffer buffer=ByteBuffer.wrap(packet);
			int packetId=buffer.getInt();
			Packet p=this.protocol.packetClasses.get(protocol.getPacketRegistry().get(packetId)).newInstance();
			p.deserialize(buffer,packet.length-Integer.BYTES);
			receive(p);
		}
		catch(Throwable e)
		{
			throw RuntimeUtil.sneakilyThrow(e);
		}
	}
	public void receive(Packet packet)
	{
		this.listeners.get(packet.getClass()).accept(RuntimeUtil.cast(packet));
	}
}
