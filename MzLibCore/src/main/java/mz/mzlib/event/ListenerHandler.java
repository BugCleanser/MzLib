package mz.mzlib.event;

import mz.mzlib.util.RuntimeUtil;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ListenerHandler
{
    public static Map<Class<? extends Event>,ListenerHandler> handlers=new ConcurrentHashMap<>();
    public static CallSite getCallSite(MethodHandles.Lookup caller,
                                       String invokedName,
                                       MethodType invokedType,
                                       Class<? extends Event> eventClass) throws NoSuchMethodException, IllegalAccessException
    {
        return new ConstantCallSite(caller.findVirtual(ListenerHandler.class,"call",MethodType.methodType(void.class,Event.class)).bindTo(handlers.get(eventClass)).asType(invokedType));
    }

    public List<EventListener<?>> sortedListeners=new ArrayList<>();
    public void call(Event event)
    {
        for(EventListener<?> listener:this.sortedListeners)
        {
            try
            {
                listener.handler.accept(RuntimeUtil.cast(event));
            }
            catch(Throwable e)
            {
                e.printStackTrace(System.err);
            }
        }
    }

    public Set<EventListener<?>> listeners=new HashSet<>();
    public synchronized void update()
    {
        this.sortedListeners=listeners.stream().sorted((a,b)->Float.compare(b.priority,a.priority)).collect(Collectors.toList());
    }
    public synchronized void addListener(EventListener<?> listener)
    {
        this.listeners.add(listener);
        this.update();
    }
    public synchronized void removeListener(EventListener<?> listener)
    {
        this.listeners.remove(listener);
        this.update();
    }
}
