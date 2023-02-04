package mz.lib.minecraft.bukkit.event;

import mz.lib.wrapper.*;
import org.bukkit.event.Event;

@WrappedClass("org.bukkit.event.Event")
public interface WrappedEvent extends WrappedObject
{
    @Override
    Event getRaw();

    @WrappedFieldAccessor("async")
    WrappedEvent setAsync(boolean async);
}
