package mz.lib.minecraft.bukkit.wrappedobc;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.inventory.Recipe;

import java.util.Iterator;

@WrappedBukkitClass(@VersionName("obc.inventory.RecipeIterator"))
public interface ObcRecipeIterator extends WrappedBukkitObject, Iterator<Recipe>
{
	static ObcRecipeIterator newInstance()
	{
		return WrappedObject.getStatic(ObcRecipeIterator.class).staticNewInstance();
	}
	@WrappedConstructor
	ObcRecipeIterator staticNewInstance();
	@Override
	@WrappedMethod("hasNext")
	boolean hasNext();
	
	@Override
	@WrappedMethod("next")
	Recipe next();
	
	@Override
	@WrappedMethod("remove")
	void remove();
}
