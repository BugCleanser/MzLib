package mz.lib.minecraft.item;

import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import mz.lib.minecraft.bukkitlegacy.module.IRegistrar;
import mz.lib.minecraft.bukkitlegacy.module.RegistrarRegistrar;
import mz.lib.minecraft.bukkit.nms.NmsRecipeItemStack;
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
