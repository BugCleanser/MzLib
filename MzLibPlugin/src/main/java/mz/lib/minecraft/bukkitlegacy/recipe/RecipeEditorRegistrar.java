package mz.lib.minecraft.bukkitlegacy.recipe;

import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.bukkitlegacy.gui.*;
import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import mz.lib.minecraft.bukkitlegacy.module.IRegistrar;
import mz.lib.minecraft.bukkitlegacy.module.RegistrarRegistrar;

import java.util.ArrayList;
import java.util.List;

public class RecipeEditorRegistrar extends AbsModule implements IRegistrar<RecipeEditor<?>>
{
	public static RecipeEditorRegistrar instance=new RecipeEditorRegistrar();
	public RecipeEditorRegistrar()
	{
		super(MzLib.instance,RegistrarRegistrar.instance,ViewList.Module.instance);
	}
	
	public List<RecipeEditor<?>> recipeEditors=new ArrayList<>();
	
	@Override
	public Class<RecipeEditor<?>> getType()
	{
		return TypeUtil.cast(RecipeEditor.class);
	}
	
	@Override
	public boolean register(RecipeEditor<?> obj)
	{
		return recipeEditors.add(obj);
	}
	@Override
	public void unregister(RecipeEditor<?> obj)
	{
		recipeEditors.remove(obj);
	}
	
	@Override
	public void onEnable()
	{
		//reg(NmsAutoRecipe.class);
		
		reg(ShapedRecipeEditor.instance);
	}
}
