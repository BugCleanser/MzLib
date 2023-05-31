package mz.lib.minecraft.bukkitlegacy.message;

import mz.lib.Optional;
import mz.lib.wrapper.WrappedClass;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;
import net.md_5.bungee.api.ChatColor;

@Optional
@WrappedClass("net.md_5.bungee.api.ChatColor")
public interface WrappedChatColor extends WrappedObject
{
	@Override
	ChatColor getRaw();
	
	@Optional
	@WrappedMethod(value="of")
	ChatColor staticOfV16(String string);
	static ChatColor ofV16(String string)
	{
		return WrappedObject.getStatic(WrappedChatColor.class).staticOfV16(string);
	}
}
