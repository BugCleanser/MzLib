package mz.lib.minecraft.bukkit.recipelegacy;

import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.gui.ViewList;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.module.IRegistrar;
import mz.lib.minecraft.bukkit.module.RegistrarRegistrar;

import java.io.File;

public class RecipeEditorRegistrar extends AbsModule implements IRegistrar<RecipeEditor>
{
	public static RecipeEditorRegistrar instance=new RecipeEditorRegistrar();
	public RecipeEditorRegistrar()
	{
		super(MzLib.instance,RegistrarRegistrar.instance,ViewList.Module.instance);
	}
	public static File dir=new File(MzLib.instance.getDataFolder(),"recipes");
	
	@Override
	public Class<RecipeEditor> getType()
	{
		return RecipeEditor.class;
	}
	@Override
	public boolean register(RecipeEditor obj)
	{
		RecipesSelector.instance.list.add(obj);
		return true;
	}
	@Override
	public void unregister(RecipeEditor obj)
	{
		RecipesSelector.instance.list.remove(obj);
	}
	
	@Override
	public void onEnable()
	{
		ShapedRecipeEditor.instance.load();
	}
}
