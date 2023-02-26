package mz.lib.minecraft.bukkitlegacy.recipelegacy;

import mz.lib.wrapper.WrappedClass;
import mz.lib.wrapper.WrappedFieldAccessor;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@WrappedClass("org.bukkit.inventory.ShapedRecipe")
public interface WrappedShapedRecipe extends WrappedObject
{
	@WrappedFieldAccessor("key")
	WrappedShapedRecipe setKey(NamespacedKey key);
	
	@WrappedFieldAccessor("output")
	WrappedShapedRecipe setResult(ItemStack is);
	
	@WrappedFieldAccessor("ingredients")
	Map<Character,ItemStack> getIngredients();
}
