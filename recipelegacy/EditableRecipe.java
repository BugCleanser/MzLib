package mz.lib.minecraft.bukkit.recipelegacy;

import mz.lib.minecraft.bukkit.LangUtil;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Recipe;

public class EditableRecipe<T extends Recipe>
{
	public static enum RecipeType
	{
		UNSAVED()
		{
			@Override
			public String getName(HumanEntity player)
			{
				return LangUtil.getTranslated(player,"mzlib.recipesEditor.types.unsavedRecipe");
			}
		},
		FIXED()
		{
			@Override
			public String getName(HumanEntity player)
			{
				return LangUtil.getTranslated(player,"mzlib.recipesEditor.types.fixedRecipe");
			}
		},
		DISABLED()
		{
			@Override
			public String getName(HumanEntity player)
			{
				return LangUtil.getTranslated(player,"mzlib.recipesEditor.types.disabledRecipe");
			}
		};
		
		public abstract String getName(HumanEntity player);
	}
	
	public RecipeType type;
	public T data;
	
	public EditableRecipe(RecipeType type,T data)
	{
		this.type=type;
		this.data=data;
	}
}
