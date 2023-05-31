package mz.lib.minecraft.bukkit;

import mz.lib.wrapper.WrappedClass;
import mz.lib.wrapper.WrappedFieldAccessor;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

@WrappedClass("org.bukkit.event.inventory.InventoryClickEvent")
public interface WrappedInventoryClickEvent extends WrappedObject
{
	@Override
	InventoryClickEvent getRaw();
	
	@WrappedFieldAccessor("action")
	void setAction(InventoryAction action);
}
