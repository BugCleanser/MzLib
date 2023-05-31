package mz.lib.minecraft.message;

import mz.lib.minecraft.*;

public interface MessageColor
{
	static MessageColor fromName(String name)
	{
		return Factory.instance.getMessageColor(name);
	}
}
