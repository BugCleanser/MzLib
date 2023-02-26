package mz.lib.minecraft.bukkitlegacy.recipe;

import mz.lib.minecraft.bukkitlegacy.LangUtil;
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
