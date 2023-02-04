package mz.lib.minecraft.bukkit.recipe;

import mz.lib.minecraft.bukkit.LangUtil;
import org.bukkit.entity.HumanEntity;

public enum ShownRecipeType
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
