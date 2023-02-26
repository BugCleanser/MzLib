package mz.lib.minecraft.bukkitlegacy.recipelegacy;

import java.util.List;

import mz.lib.wrapper.WrappedClass;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import mz.lib.wrapper.WrappedFieldAccessor;
import mz.lib.wrapper.WrappedObject;

@WrappedClass("org.bukkit.inventory.ShapelessRecipe")
public interface WrappedShapelessRecipe extends WrappedObject
{
	@WrappedFieldAccessor("key")
	WrappedShapelessRecipe setKey(NamespacedKey key);
	
	@WrappedFieldAccessor("output")
	WrappedShapelessRecipe setResult(ItemStack is);

	@WrappedFieldAccessor("ingredients")
	List<ItemStack> getIngredients();
}
