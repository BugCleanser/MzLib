package mz.lib.minecraft.block;

import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.mzlang.*;
import org.bukkit.block.*;

public interface MzBlock extends MzObject
{
	@PropAccessor("block")
	Block getBlock();
	@PropAccessor("block")
	void setBlock(Block block);
	
	@CallEach
	default void load(NmsNBTTagCompound nbt)
	{
	}
	@CallEach
	default void save(NmsNBTTagCompound nbt)
	{
	}
	@CallEach
	default void add()
	{
	}
	@CallEach
	default void remove()
	{
	}
}
