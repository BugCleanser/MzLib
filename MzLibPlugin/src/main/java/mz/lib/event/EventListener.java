package mz.lib.event;

import java.util.function.*;

public class EventListener<T extends Event> implements IEventListener<T>
{
	public Class<T> type;
	public Consumer<T> acceptor;
	
	public EventListener(Class<T> type,Consumer<T> acceptor)
	{
		this.type=type;
		this.acceptor=acceptor;
	}
	
	public Class<T> getType()
	{
		return type;
	}
	@Override
	public void accept(T event)
	{
		acceptor.accept(event);
	}
}
