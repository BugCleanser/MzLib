package mz.lib.minecraft.bukkit.obc;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.wrapper.*;
import org.bukkit.inventory.Recipe;

import java.util.Iterator;

@VersionalWrappedClass(@VersionalName("obc.inventory.RecipeIterator"))
public interface ObcRecipeIterator extends VersionalWrappedObject, Iterator<Recipe>
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
