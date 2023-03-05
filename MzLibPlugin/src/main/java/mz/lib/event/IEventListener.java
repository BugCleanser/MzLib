package mz.lib.event;

public interface IEventListener<T extends Event> extends Comparable<IEventListener<T>>
{
	Class<T> getType();
	default float getOrder()
	{
		return 5;
	}
	void accept(T event);
	@Override
	default int compareTo(IEventListener<T> o)
	{
		return Float.compare(this.getOrder(),o.getOrder());
	}
}
