package mz.lib.minecraft.bukkit.gui.inventory;

import mz.lib.minecraft.bukkit.module.IModule;
import mz.lib.minecraft.bukkit.wrappednms.NmsIChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Function;

/**
 * ChestUI
 */
public abstract class ChestUI extends InventoryUI
{
	public ChestUI(IModule module,int size,String title)
	{
		super(module,size);
		this.inv=Bukkit.createInventory(this,size,title);
	}
	public ChestUI(IModule module,int size,Function<Player,NmsIChatBaseComponent> titleGetter)
	{
		this(module,size,"");
		this.titleModifier=(p,c)->c.set(titleGetter.apply(p));
	}
}
