package mz.lib.event;

public interface IEventListener<T extends Event>
{
	Class<T> getType();
	void accept(T event);
}
