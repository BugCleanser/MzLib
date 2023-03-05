package mz.lib.minecraft.bukkitlegacy.recipe;

import mz.lib.minecraft.MinecraftLanguages;
import org.bukkit.entity.HumanEntity;

public enum ShownRecipeType
{
	UNSAVED()
	{
		@Override
		public String getName(HumanEntity player)
		{
			return MinecraftLanguages.get(player,"mzlib.recipesEditor.types.unsavedRecipe");
		}
	},
	FIXED()
	{
		@Override
		public String getName(HumanEntity player)
		{
			return MinecraftLanguages.get(player,"mzlib.recipesEditor.types.fixedRecipe");
		}
	},
	DISABLED()
	{
		@Override
		public String getName(HumanEntity player)
		{
			return MinecraftLanguages.get(player,"mzlib.recipesEditor.types.disabledRecipe");
		}
	};
	
	public abstract String getName(HumanEntity player);
}
