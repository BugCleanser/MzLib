package mz.lib.minecraft.event.entity;

import mz.lib.event.*;
import mz.lib.minecraft.entity.*;
import mz.lib.mzlang.*;

public interface EntityEvent extends Event
{
	@PropAccessor("entity")
	Entity getEntity();
	@PropAccessor("entity")
	void setEntity(Entity entity);
}
