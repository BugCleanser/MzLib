package mz.lib.minecraft.event.entity.player;

import mz.lib.minecraft.entity.*;
import mz.lib.minecraft.event.entity.*;

public interface PlayerEvent extends EntityEvent
{
	@Override
	PlayerEntity getEntity();
}
