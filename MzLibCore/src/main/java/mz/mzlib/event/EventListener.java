package mz.mzlib.event;

import java.util.function.Consumer;

public class EventListener<T extends Event>
{
    public Class<T> eventClass;
    public float priority;
    public Consumer<T> handler;

    public EventListener(Class<T> eventClass, float priority, Consumer<T> handler)
    {
        this.eventClass = eventClass;
        this.priority = priority;
        this.handler = handler;
    }
    public EventListener(Class<T> eventClass, Consumer<T> handler)
    {
        this(eventClass, 0.f, handler);
    }
}
