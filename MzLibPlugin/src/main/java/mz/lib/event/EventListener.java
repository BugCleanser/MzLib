package mz.lib.event;

import java.util.function.*;

public class EventListener<T extends Event> implements IEventListener<T>
{
	public Class<T> type;
	public float order;
	public Consumer<T> acceptor;
	
	public EventListener(Class<T> type,float order,Consumer<T> acceptor)
	{
		this.type=type;
		this.order=order;
		this.acceptor=acceptor;
	}
	public EventListener(Class<T> type,Consumer<T> acceptor)
	{
		this(type,5,acceptor);
	}
	
	public Class<T> getType()
	{
		return type;
	}
	@Override
	public float getOrder()
	{
		return order;
	}
	@Override
	public void accept(T event)
	{
		acceptor.accept(event);
	}
}
