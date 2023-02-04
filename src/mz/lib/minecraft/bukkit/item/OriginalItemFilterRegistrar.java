package mz.lib.minecraft.bukkit.item;

import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.module.IRegistrar;
import mz.lib.minecraft.bukkit.module.RegistrarRegistrar;
import mz.lib.minecraft.bukkit.wrappednms.NmsRecipeItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

public class OriginalItemFilterRegistrar extends AbsModule implements IRegistrar<OriginalItemFilterRegistrar.OriginalItemFilter>
{
	public static OriginalItemFilterRegistrar instance=new OriginalItemFilterRegistrar();
	public OriginalItemFilterRegistrar()
	{
		super(MzLib.instance,RegistrarRegistrar.instance);
	}
	
	public static class OriginalItemFilter
	{
		Predicate<ItemStack> value;
		public OriginalItemFilter(Predicate<ItemStack> value)
		{
			this.value=value;
		}
	}
	
	@Override
	public Class<OriginalItemFilter> getType()
	{
		return OriginalItemFilter.class;
	}
	
	@Override
	public boolean register(OriginalItemFilter obj)
	{
		NmsRecipeItemStack.originalItemFilters.add(obj.value);
		return true;
	}
	
	@Override
	public void unregister(OriginalItemFilter obj)
	{
		NmsRecipeItemStack.originalItemFilters.remove(obj.value);
	}
}
