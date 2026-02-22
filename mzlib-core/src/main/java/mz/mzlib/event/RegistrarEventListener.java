package mz.mzlib.event;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;

public class RegistrarEventListener implements IRegistrar<EventListener<?>>
{
    public static RegistrarEventListener instance = new RegistrarEventListener();

    public Class<? super EventListener<?>> getType()
    {
        return EventListener.class;
    }

    public void register(MzModule module, EventListener<?> object)
    {
        ListenerHandler.handlers.get(object.eventClass).addListener(object);
        for(Class<? extends Event> clazz: RegistrarEventClass.instance.subEvents.get(object.eventClass))
        {
            ListenerHandler.handlers.get(clazz).addListener(object);
        }
    }

    public void unregister(MzModule module, EventListener<?> object)
    {
        ListenerHandler.handlers.get(object.eventClass).removeListener(object);
        for(Class<? extends Event> clazz: RegistrarEventClass.instance.subEvents.get(object.eventClass))
        {
            ListenerHandler.handlers.get(clazz).removeListener(object);
        }
    }
}
