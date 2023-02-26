package mz.lib.minecraft.bukkitlegacy.recipelegacy;

import com.google.common.collect.Lists;
import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.minecraft.bukkitlegacy.LangUtil;
import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.bukkitlegacy.gui.ViewList;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkitlegacy.wrappedobc.ObcInventory;
import mz.lib.minecraft.bukkitlegacy.wrappedobc.ObcItemStack;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class ShapedRecipeEditor extends RecipeEditor<NmsShapedRecipes>
{
	public static ShapedRecipeEditor instance=new ShapedRecipeEditor();
	public ShapedRecipeEditor()
	{
		super(MzLib.instance);
	}
	
	@Override
	public Class<NmsShapedRecipes> getType()
	{
		return NmsShapedRecipes.class;
	}
	
	@Override
	public String getTypeName()
	{
		return "ShapedRecipe";
	}
	@Override
	public String getTypeName(HumanEntity player)
	{
		return LangUtil.getTranslated(player,"mzlib.recipesEditor.types.shapedRecipe");
	}
	
	@Override
	public ItemStack getIcon(HumanEntity player)
	{
		return ItemStackBuilder.craftingTable().setName(StringUtil.replaceStrings(LangUtil.getTranslated(player,"mzlib.recipesEditor.kindRecipe.title"),new MapEntry<>("%\\{type\\}",getTypeName(player)))).get();
	}
	
	@Override
	public NmsShapedRecipes newRecipe()
	{
		return NmsShapedRecipes.newInstance(randKey(),"",1,1,Lists.newArrayList(NmsRecipeItemStack.newInstance(Material.STICK)),new ItemStack(Material.STICK));
	}
	
	@Override
	public NamespacedKey getKey(NmsShapedRecipes recipe)
	{
		return recipe.getKey();
	}
	
	@Override
	public void setKey(NmsShapedRecipes recipe,NamespacedKey key)
	{
		recipe.setKey(NmsMinecraftKey.newInstance(key));
	}
	
	@Override
	public boolean register(NmsShapedRecipes recipe)
	{
		NmsCraftingManager.addRecipe(recipe.getKey0(),recipe);
		return true;
	}
	@Override
	public void unregister(NmsShapedRecipes recipe)
	{
		NmsCraftingManager.removeRecipe(NmsCraftingManager.getCraftingRecipes(),recipe.getKey());
	}
	
	@Override
	public NmsNBTTagCompound save(NmsShapedRecipes recipe)
	{
		return NmsNBTTagCompound.newInstance().set("key",recipe.getKey()).set("result",new ItemStackBuilder(recipe.getResult()).save()).set("group",recipe.getGroup()).set("width",recipe.getWidth()).set("height",recipe.getHeight()).set("ingredients",recipe.getIngredients().getRaw().stream().map(i->NmsNBTTagList.newInstance(WrappedObject.wrap(NmsRecipeItemStack.class,i))).collect(Collectors.toList()));
	}
	
	@Override
	public List<NmsShapedRecipes> getActives()
	{
		return NmsCraftingManager.getCraftingRecipes().values().stream().filter(r->WrappedObject.getRawClass(NmsShapedRecipes.class).isAssignableFrom(r.getClass())).map(r->WrappedObject.wrap(NmsShapedRecipes.class,r)).collect(Collectors.toList());
	}
	
	@Override
	public NmsShapedRecipes load(NmsNBTTagCompound nbt)
	{
		return NmsShapedRecipes.newInstance(nbt.getNamespacedKey("key"),nbt.getString("group"),nbt.getInt("width"),nbt.getInt("height"),nbt.getList("ingredients").values(NmsNBTTagList.class).stream().map(NmsNBTTagList::toRecipeItemStack).collect(Collectors.toList()),new ItemStackBuilder(nbt.getCompound("result")).get());
	}
	
	@Override
	public NmsShapedRecipes clone(NmsShapedRecipes data)
	{
		return NmsShapedRecipes.newInstance(data.getKey(),data.getGroup(),data.getWidth(),data.getHeight(),clone(data.getIngredients()),data.getResult(),inv->ObcItemStack.asBukkitCopy(data.getResult(WrappedObject.wrap(ObcInventory.class,inv).getNms().cast(NmsInventoryCrafting.class))));
	}
	
	@Override
	public void onSave(EditableRecipe<NmsShapedRecipes> recipe)
	{
		if(recipe.data.getWidth()<3||recipe.data.getHeight()<3)
			return;
		NmsNonNullList ings=recipe.data.getIngredients();
		int width=recipe.data.getWidth();
		int height=recipe.data.getHeight();
		for(int i=0;i<2;i++)
		{
			if(ings.get(0,NmsRecipeItemStack.class).getChoices0().getRaw().length==0 && ings.get(1,NmsRecipeItemStack.class).getChoices0().getRaw().length==0 && ings.get(2,NmsRecipeItemStack.class).getChoices0().getRaw().length==0)
			{
				ings.set(0,ings.get(3,NmsRecipeItemStack.class));
				ings.set(1,ings.get(4,NmsRecipeItemStack.class));
				ings.set(2,ings.get(5,NmsRecipeItemStack.class));
				ings.set(3,ings.get(6,NmsRecipeItemStack.class));
				ings.set(4,ings.get(7,NmsRecipeItemStack.class));
				ings.set(5,ings.get(8,NmsRecipeItemStack.class));
				height--;
			}
			if(ings.get(0,NmsRecipeItemStack.class).getChoices0().getRaw().length==0 && ings.get(3,NmsRecipeItemStack.class).getChoices0().getRaw().length==0 && ings.get(6,NmsRecipeItemStack.class).getChoices0().getRaw().length==0)
			{
				ings.set(0,ings.get(1,NmsRecipeItemStack.class));
				ings.set(3,ings.get(4,NmsRecipeItemStack.class));
				ings.set(6,ings.get(7,NmsRecipeItemStack.class));
				ings.set(1,ings.get(2,NmsRecipeItemStack.class));
				ings.set(4,ings.get(5,NmsRecipeItemStack.class));
				ings.set(7,ings.get(8,NmsRecipeItemStack.class));
				width--;
			}
		}
		a:while(width>0)
		{
			for(int i=0;i<height;i++)
			{
				if(ings.get(i*3+width-1,NmsRecipeItemStack.class).getChoices0().getRaw().length>0)
					break a;
			}
			width--;
		}
		b:while(height>0)
		{
			for(int i=0;i<width;i++)
			{
				if(ings.get(height*3+i-3,NmsRecipeItemStack.class).getChoices0().getRaw().length>0)
					break b;
			}
			height--;
		}
		NmsNonNullList n=NmsNonNullList.newInstance();
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				n.add(ings.get(i*3+j,NmsRecipeItemStack.class));
			}
		}
		recipe.data.setWidth(width);
		recipe.data.setHeight(height);
		recipe.data.setIngredients(n);
	}
	
	@Override
	public void edit(HumanEntity player,EditableRecipe<NmsShapedRecipes> recipe)
	{
		ViewList.get(player).go(new RecipeEditorUI<NmsShapedRecipes>(this,54,recipe)
		{
			@Override
			public void refresh()
			{
				clear();
				
				setRetButton(0);
				setKeySetButton(7);
				setExtra(this.recipe.data.getResult(),33);
				setSaveButton(35);
				
				setExtra(ItemStackBuilder.brownStainedGlassPane().setName("§0").get(),1,2,3,4,5,6);
				setExtra(ItemStackBuilder.blackStainedGlassPane().setName("§0").get(),9,10,11,12,13,14,15,16,17,18,23,24,25,26,27,32,34,36,41,43,44,45,46,47,48,49,50,51,52,53);
				setDescription(8,"mzlib.recipesEditor.types.shapedRecipe.description");
				setItem(42,p->ItemStackBuilder.limeStainedGlassPane().setName(LangUtil.getTranslated(p,"mzlib.recipesEditor.resultTips")).get());
				setExtra(p->ItemStackBuilder.whiteStainedGlassPane().setName(LangUtil.getTranslated(p,"mzlib.recipesEditor.types.shapedRecipe.rawTips")).get(),22,31,40);
				
				if(this.recipe.data.getWidth()<3||this.recipe.data.getHeight()<3)
				{
					NmsNonNullList n=NmsNonNullList.newInstance();
					for(int i=0;i<9;i++)
					{
						n.add(NmsRecipeItemStack.newInstance());
					}
					for(int i=0;i<this.recipe.data.getWidth();i++)
					{
						for(int j=0;j<this.recipe.data.getHeight();j++)
						{
							n.set(j*3+i,this.recipe.data.getIngredients().get(j*this.recipe.data.getWidth()+i,NmsRecipeItemStack.class));
						}
					}
					this.recipe.data.setIngredients(n);
					this.recipe.data.setWidth(3);
					this.recipe.data.setHeight(3);
				}
				for(int i=0;i<this.recipe.data.getWidth();i++)
				{
					for(int j=0;j<this.recipe.data.getHeight();j++)
					{
						int index=j*this.recipe.data.getWidth()+i;
						NmsRecipeItemStack ing=this.recipe.data.getIngredients().get(index,NmsRecipeItemStack.class);
						List<ItemStack> cs=ing.getChoices();
						setButton(j*9+i+19,cs.size()>0?cs.get(0):new ItemStack(Material.AIR),(t,p)->
						{
							ViewList.get(p).go(new IngredientEditor(module,ing,n->
							{
								this.recipe.data.getIngredients().set(index,n);
								this.refresh();
							}));
						});
					}
				}
			}
			
			@Override
			public void onClick(ClickType type,HumanEntity player,int slot)
			{
				super.onClick(type,player,slot);
				if(slot>=this.size)
				{
					ItemStack is=player.getOpenInventory().getItem(slot);
					if(!ItemStackBuilder.isAir(is))
					{
						this.recipe.data.setResult(ObcItemStack.asNMSCopy(is));
						refresh();
					}
				}
			}
		});
	}
}
