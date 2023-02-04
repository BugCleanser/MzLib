package mz.lib.minecraft.bukkit.recipelegacy;

import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.module.IRegistrar;
import mz.lib.minecraft.bukkit.wrappednms.NmsNBTTagCompound;
import mz.lib.minecraft.bukkit.wrappednms.NmsNBTTagString;
import mz.lib.minecraft.bukkit.wrappednms.NmsNonNullList;
import mz.lib.minecraft.bukkit.wrappednms.NmsRecipeItemStack;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public abstract class RecipeEditor<T extends Recipe> extends AbsModule implements IRegistrar<T>
{
	public RecipeSelector<T> selector=new RecipeSelector<>(this);
	public RecipeEditor(Plugin plugin)
	{
		super(plugin,RecipeEditorRegistrar.instance);
	}
	
	public abstract Class<T> getType();
	public String getTypeName()
	{
		return getType().getSimpleName();
	}
	public abstract String getTypeName(HumanEntity player);
	public File dir=new File(RecipeEditorRegistrar.dir,getTypeName());
	
	public abstract ItemStack getIcon(HumanEntity player);
	public ItemStack getIcon(EditableRecipe<T> recipe,HumanEntity player)
	{
		return new ItemStackBuilder(recipe.data.getResult().clone()).addLore(StringUtil.replaceStrings(LangUtil.getTranslated(player,"mzlib.recipesEditor.kindRecipe.lore"),new MapEntry<>("%\\{type\\}",recipe.type.getName(player)),new MapEntry<>("%\\{id\\}",getKey(recipe.data).toString())).split("\n")).get();
	}
	public abstract T newRecipe();
	public abstract NamespacedKey getKey(T recipe);
	public void setKey(T recipe,NamespacedKey key) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}
	public void register(EditableRecipe<T> recipe)
	{
		try
		{
			if(recipe.type!=EditableRecipe.RecipeType.DISABLED)
				register(recipe.data);
			if(recipe.type!=EditableRecipe.RecipeType.UNSAVED)
			{
				if(!dir.isDirectory())
					dir.mkdirs();
				File file=getFile(getKey(recipe.data));
				if(!file.isFile())
					file.createNewFile();
				try(FileOutputStream fos=new FileOutputStream(file))
				{
					save(new EditableRecipe<>(EditableRecipe.RecipeType.FIXED,recipe.data)).write(new DataOutputStream(fos));
				}
			}
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public void add(EditableRecipe<T> recipe)
	{
		selector.list.add(recipe);
		register(recipe);
	}
	public void unregister(EditableRecipe<T> recipe)
	{
		if(recipe.type!=EditableRecipe.RecipeType.DISABLED)
			unregister(recipe.data);
		if(recipe.type!=EditableRecipe.RecipeType.UNSAVED)
			getFile(getKey(recipe.data)).delete();
	}
	public void remove(NamespacedKey key)
	{
		Iterator<EditableRecipe<T>> i=selector.list.iterator();
		while(i.hasNext())
		{
			EditableRecipe<T> r=i.next();
			if(getKey(r.data).equals(key))
			{
				unregister(r);
				i.remove();
			}
		}
	}
	public void remove(EditableRecipe<T> recipe)
	{
		unregister(recipe);
		selector.list.remove(recipe);
	}
	public static NamespacedKey randKey()
	{
		return new NamespacedKey(MzLib.instance,UUID.randomUUID().toString());
	}
	public abstract NmsNBTTagCompound save(T recipe);
	public abstract List<T> getActives();
	public NmsNBTTagCompound save(EditableRecipe<T> recipe)
	{
		NmsNBTTagCompound r=NmsNBTTagCompound.newInstance();
		r.set("type",NmsNBTTagString.newInstance(recipe.type.name()));
		r.set("data",save(recipe.data));
		return r;
	}
	public abstract T load(NmsNBTTagCompound nbt);
	public abstract T clone(T data);
	public NmsRecipeItemStack clone(NmsRecipeItemStack ri)
	{
		return NmsRecipeItemStack.custom(ri.getChoices(),ri::test);
	}
	public List<NmsRecipeItemStack> clone(NmsNonNullList ris)
	{
		List<NmsRecipeItemStack> r=new ArrayList<>(ris.getRaw().size());
		for(WrappedObject ri:ris)
		{
			r.add(clone(ri.cast(NmsRecipeItemStack.class)));
		}
		return r;
	}
	public EditableRecipe<T> clone(EditableRecipe<T> recipe)
	{
		return new EditableRecipe<>(recipe.type,clone(recipe.data));
	}
	public EditableRecipe<T> loadEditable(NmsNBTTagCompound nbt)
	{
		return new EditableRecipe<>(EditableRecipe.RecipeType.valueOf(nbt.getString("type")),load(nbt.getCompound("data")));
	}
	public File getFile(NamespacedKey key)
	{
		return new File(dir,key.toString().replace(':','=')+".nbt");
	}
	public void loadFiles()
	{
		File[] files=this.dir.listFiles();
		if(files!=null)
			for(File file:files)
			{
				try(DataInputStream dis=new DataInputStream(new FileInputStream(file)))
				{
					this.selector.list.add(loadEditable(NmsNBTTagCompound.read(dis)));
				}
				catch(Throwable e)
				{
					throw TypeUtil.throwException(e);
				}
			}
	}
	@Override
	public void onEnable()
	{
		this.selector.list.clear();
		loadFiles();
		refreshActives();
	}
	
	public abstract void edit(HumanEntity player,EditableRecipe<T> recipe);
	
	public boolean contains(NamespacedKey key)
	{
		for(EditableRecipe<T> recipe: selector.list)
		{
			if(getKey(recipe.data).equals(key))
				return true;
		}
		return false;
	}
	
	public boolean contains(List<T> list,T recipe)
	{
		for(T r:list)
		{
			if(getKey(r).equals(getKey(recipe)))
				return true;
		}
		return false;
	}
	
	public void onSave(EditableRecipe<T> recipe)
	{
	}
	
	public void refreshActives()
	{
		Iterator<EditableRecipe<T>> iterator=selector.list.iterator();
		while(iterator.hasNext())
		{
			if(iterator.next().type==EditableRecipe.RecipeType.UNSAVED)
				iterator.remove();
		}
		List<T> actives=getActives();
		for(EditableRecipe<T> recipe:selector.list)
		{
			if(contains(actives,recipe.data))
			{
				if(recipe.type!=EditableRecipe.RecipeType.UNSAVED)
				{
					this.unregister(recipe.data);
				}
			}
			if(recipe.type==EditableRecipe.RecipeType.FIXED)
			{
				this.register(recipe.data);
			}
		}
		for(T active:actives)
		{
			if(!this.contains(getKey(active)))
				selector.list.add(new EditableRecipe<>(EditableRecipe.RecipeType.UNSAVED,active));
		}
		
		selector.refresh();
	}
}
