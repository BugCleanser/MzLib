package mz.lib.minecraft.recipe;

import com.google.common.collect.*;
import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.minecraft.bukkit.obc.*;
import mz.lib.minecraft.bukkitlegacy.itemstack.*;
import mz.lib.minecraft.bukkitlegacy.wrappedobc.*;
import mz.lib.wrapper.*;
import mz.lib.minecraft.Server;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

import java.util.*;
import java.util.stream.*;

public class ShapedRecipeEditor extends RecipeEditor<NmsShapedRecipes>
{
	public static ShapedRecipeEditor instance=new ShapedRecipeEditor();
	public ShapedRecipeEditor()
	{
		super(RecipeEditorRegistrar.instance);
	}
	
	@Override
	public NmsShapedRecipes newElement(HumanEntity player)
	{
		NmsShapedRecipes r=NmsShapedRecipes.newInstance(NmsMinecraftKey.random().toBukkit(),"",1,1,Lists.newArrayList(NmsRecipeItemStack.newInstance(Material.STICK)),new ItemStack(Material.STICK));
		if(Server.instance.version>=19.3f)
			r.setCategoryV193(NmsCraftingBookCategoryV193.getMisc());
		return r;
	}
	
	@Override
	public String getName()
	{
		return "shapedRecipe";
	}
	
	@Override
	public NamespacedKey getId(NmsShapedRecipes recipe)
	{
		return recipe.getKey();
	}
	
	@Override
	public Class<NmsShapedRecipes> getType()
	{
		return NmsShapedRecipes.class;
	}
	
	@Override
	public List<NmsShapedRecipes> getActiveRecipes()
	{
		return NmsCraftingManager.getCraftingRecipes().values().stream().filter(r->WrappedObject.getRawClass(NmsShapedRecipes.class).isAssignableFrom(r.getClass())).map(r->WrappedObject.wrap(NmsShapedRecipes.class,r)).collect(Collectors.toList());
	}
	
	@Override
	public boolean register(NmsShapedRecipes obj)
	{
		NmsCraftingManager.addRecipe(obj.getKey0(),obj);
		return true;
	}
	
	@Override
	public void unregister(NamespacedKey id)
	{
		NmsCraftingManager.removeRecipe(NmsCraftingManager.getCraftingRecipes(),id);
	}
	
	@Override
	public NmsShapedRecipes clone(NmsShapedRecipes recipe)
	{
		return recipe.clone0();
	}
	
	@Override
	public void initCase(RecipeEditorCase recipeEditorCase)
	{
		for(int i=0;i<recipeEditorCase.recipe.getHeight();i++)
			for(int j=0;j<recipeEditorCase.recipe.getWidth();j++)
				recipeEditorCase.getInventory().setItem(19+i*9+j,fromRecipeItemStack(recipeEditorCase.recipe.getIngredients().get(i*recipeEditorCase.recipe.getWidth()+j,NmsRecipeItemStack.class)));
		recipeEditorCase.getInventory().setItem(34,recipeEditorCase.recipe.getResult());
	}
	
	@Override
	public void setCaseSlots(RecipeEditorCase recipeEditorCase,HumanEntity player,NmsContainer container)
	{
		super.setCaseSlots(recipeEditorCase,player,container);
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				container.setReadWrite(19+i*9+j);
		container.setReadWrite(34);
	}
	
	@Override
	public void refreshCase(RecipeEditorCase recipeEditorCase)
	{
		recipeEditorCase.setRetButton(0);
		recipeEditorCase.setSaveButton(50);
		
		recipeEditorCase.setExtra(ItemStackBuilder.purpleStainedGlassPane().setName("§0").get(),1,2,3,4,5,6,7,8);
		recipeEditorCase.setExtra(ItemStackBuilder.brownStainedGlassPane().setName("§0").get(),9,10,12,13,18,22,36,40,45,46,48,49);
		recipeEditorCase.setExtra(ItemStackBuilder.orangeStainedGlassPane().setName("§0").get(),11,27,31,47);
		recipeEditorCase.setExtra(ItemStackBuilder.grass().setName("§0").get(),14,15,16,17,23,41,51,52,53);
		recipeEditorCase.setExtra(ItemStackBuilder.newSkull(null,"http://textures.minecraft.net/texture/6b46f03b108af6651fbbf49025564d3332dd3419128c8041af6a6e1434083cb0").setName("§0").get(),32);
		recipeEditorCase.setExtra(ItemStackBuilder.newSkull(null,"http://textures.minecraft.net/texture/4154ffc35ff916d369ff3afad90f35209b30bc8c6447613d385553d622e53678").setName("§0").get(),24);
		recipeEditorCase.setExtra(ItemStackBuilder.newSkull(null,"http://textures.minecraft.net/texture/93223097237e99ceb72019173e093d78d317fac6fd34ab0b9212e76f8767c0f7").setName("§0").get(),25);
		recipeEditorCase.setExtra(ItemStackBuilder.newSkull(null,"http://textures.minecraft.net/texture/7cd24b4805400a16b91e6b8d2c2497641152416e854ab42b26aafa9ea65d408a").setName("§0").get(),26);
		recipeEditorCase.setExtra(ItemStackBuilder.newSkull(null,"http://textures.minecraft.net/texture/d03ae658bf49d32be0192fee2a49ac0b67c6770dd0f638e918f7686125a94da3").setName("§0").get(),33);
		recipeEditorCase.setExtra(ItemStackBuilder.newSkull(null,"http://textures.minecraft.net/texture/4fe1f805c10bad0f65570479113427a0634de7ad5c0e064b5004e401f0d7ec1c").setName("§0").get(),35);
		recipeEditorCase.setExtra(ItemStackBuilder.newSkull(null,"http://textures.minecraft.net/texture/3cc67ad1db8393d2567870268e5ca5ed86ebdb458c81e1ee2a2b1b8efa3317da").setName("§0").get(),42);
		recipeEditorCase.setExtra(ItemStackBuilder.newSkull(null,"http://textures.minecraft.net/texture/d783d3d8240c421dc83f8126c9781fc4e6a597d9bdb9d222e309763b4f146fd9").setName("§0").get(),43);
		recipeEditorCase.setExtra(ItemStackBuilder.newSkull(null,"http://textures.minecraft.net/texture/f1fa3f69ed4e91c6c2f057ea3b227d9e6607066c83c5bde0ea832b8339e2a6a5").setName("§0").get(),44);
	}
	
	@Override
	public void save(RecipeEditorCase recipeEditorCase,HumanEntity player)
	{
		ItemStack[][] raws=new ItemStack[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				raws[i][j]=recipeEditorCase.getInventory().getItem(19+j*9+i);
		for(int i=0;i<2;i++)
			if(ItemStackBuilder.isAir(raws[0][0])&&ItemStackBuilder.isAir(raws[0][1])&&ItemStackBuilder.isAir(raws[0][2]))
			{
				raws[0][0]=raws[1][0];
				raws[0][1]=raws[1][1];
				raws[0][2]=raws[1][2];
				raws[1][0]=raws[2][0];
				raws[1][1]=raws[2][1];
				raws[1][2]=raws[2][2];
			}
		for(int i=0;i<2;i++)
			if(ItemStackBuilder.isAir(raws[0][0])&&ItemStackBuilder.isAir(raws[1][0])&&ItemStackBuilder.isAir(raws[2][0]))
			{
				raws[0][0]=raws[0][1];
				raws[1][0]=raws[1][1];
				raws[2][0]=raws[2][1];
				raws[0][1]=raws[0][2];
				raws[1][1]=raws[1][2];
				raws[2][1]=raws[2][2];
			}
		int width=3,height=3;
		if(ItemStackBuilder.isAir(raws[2][0])&&ItemStackBuilder.isAir(raws[2][1])&&ItemStackBuilder.isAir(raws[2][2]))
		{
			width--;
			if(ItemStackBuilder.isAir(raws[1][0])&&ItemStackBuilder.isAir(raws[1][1])&&ItemStackBuilder.isAir(raws[1][2]))
				width--;
		}
		if(ItemStackBuilder.isAir(raws[0][2])&&ItemStackBuilder.isAir(raws[1][2])&&ItemStackBuilder.isAir(raws[2][2]))
		{
			height--;
			if(ItemStackBuilder.isAir(raws[0][1])&&ItemStackBuilder.isAir(raws[1][1])&&ItemStackBuilder.isAir(raws[2][1]))
				height--;
		}
		recipeEditorCase.recipe.setWidth(width);
		recipeEditorCase.recipe.setHeight(height);
		NmsNonNullList ings=NmsNonNullList.newInstance();
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++)
				ings.add(getRecipeItemStack(raws[j][i]));
		recipeEditorCase.recipe.setIngredients(ings);
		recipeEditorCase.recipe.setResult(ObcItemStack.ensure(recipeEditorCase.getInventory().getItem(34)).getHandle());
	}
	
	@Override
	public ItemStack getIcon()
	{
		return ItemStackBuilder.craftingTable().get();
	}
}
