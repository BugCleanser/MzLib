package mz.lib.minecraft.bukkitlegacy;

import mz.lib.wrapper.*;
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
