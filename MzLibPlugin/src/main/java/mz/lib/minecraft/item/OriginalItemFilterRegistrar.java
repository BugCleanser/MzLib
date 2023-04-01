package mz.lib.minecraft.item;

import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.module.MzModule;
import mz.lib.module.IRegistrar;
import mz.lib.module.RegistrarRegistrar;
import mz.lib.minecraft.bukkit.nms.NmsRecipeItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

public class OriginalItemFilterRegistrar extends MzModule implements IRegistrar<OriginalItemFilterRegistrar.OriginalItemFilter>
{
	public static OriginalItemFilterRegistrar instance=new OriginalItemFilterRegistrar();
	@Override
	public void onLoad()
	{
		depend(RegistrarRegistrar.instance);
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
	public boolean register(MzModule module,OriginalItemFilter obj)
	{
		NmsRecipeItemStack.originalItemFilters.add(obj.value);
		return true;
	}
	
	@Override
	public void unregister(MzModule module,OriginalItemFilter obj)
	{
		NmsRecipeItemStack.originalItemFilters.remove(obj.value);
	}
}
