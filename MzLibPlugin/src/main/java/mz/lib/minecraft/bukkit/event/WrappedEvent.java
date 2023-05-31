package mz.lib.minecraft.bukkit.event;

import mz.lib.wrapper.WrappedClass;
import mz.lib.wrapper.WrappedFieldAccessor;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.event.Event;

@WrappedClass("org.bukkit.event.Event")
public interface WrappedEvent extends WrappedObject
{
    @Override
    Event getRaw();

    @WrappedFieldAccessor("async")
    WrappedEvent setAsync(boolean async);
}
