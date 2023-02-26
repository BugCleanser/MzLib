package mz.lib.event;

import mz.lib.*;
import mz.lib.mzlang.*;

public interface Event extends MzObject
{
	default void call()
	{
		for(Class<? extends Event> s:ClassUtil.getSuperClasses(this.getClass(),Event.class))
			for(IEventListener<?> l:EventListenerRegistrar.instance.registered.get(s))
				l.accept(TypeUtil.cast(this));
	}
}
