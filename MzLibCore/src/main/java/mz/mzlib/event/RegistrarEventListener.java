package mz.mzlib.event;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;

public class RegistrarEventListener implements IRegistrar<EventListener<?>>
{
    public static RegistrarEventListener instance = new RegistrarEventListener();

    public Class<EventListener<?>> getType()
    {
        return RuntimeUtil.cast(EventListener.class);
    }

    public void register(MzModule module, EventListener<?> object)
    {
        ListenerHandler.handlers.get(object.eventClass).addListener(object);
    }

    public void unregister(MzModule module, EventListener<?> object)
    {
        ListenerHandler.handlers.get(object.eventClass).removeListener(object);
    }
}
