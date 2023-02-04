package mz.lib.minecraft.bukkit.wrappednms;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.lib.ClassUtil;
import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.mzlang.*;
import mz.lib.minecraft.bukkit.wrappedobc.ObcItemStack;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.mzlang.*;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@WrappedBukkitClass({@VersionName(value="nms.ShapedRecipes",maxVer=17),@VersionName(value="net.minecraft.world.item.crafting.ShapedRecipes",minVer=17)})
public interface NmsShapedRecipes extends NmsIRecipe, Keyed
{
	static NmsShapedRecipes newInstance(NamespacedKey key,String group,int width,int height,List<NmsRecipeItemStack> ingredients,ItemStack result)
	{
		try
		{
			return WrappedObject.wrap(NmsShapedRecipes.class,Root.getUnsafe().allocateInstance(WrappedObject.getRawClass(NmsShapedRecipes.class))).setKey(NmsMinecraftKey.newInstance(key)).setGroup(group).setWidth(width).setHeight(height).setIngredients(NmsNonNullList.newInstance(ingredients)).setResult(ObcItemStack.asNMSCopy(result));
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	
	@Extends(NmsShapedRecipes.class)
	interface CustomShapedRecipes extends BukkitMzObject
	{
		@RefactorBukkitSign({@VersionName(maxVer=13,value="craftItem"),@VersionName(minVer=13,value="a")})
		default NmsItemStack craftItem(NmsIInventory inv)
		{
			return ObcItemStack.asNMSCopy(getResultGetter().apply(inv.getViewers().get(0).getOpenInventory().getTopInventory()));
		}
		
		static CustomShapedRecipes newInstance(Function<Inventory,ItemStack> resultGetter)
		{
			return MzObject.newInstance(CustomShapedRecipes.class).setResultGetter(resultGetter);
		}
		
		@PropAccessor("resultGetter")
		Function<Inventory,ItemStack> getResultGetter();
		@PropAccessor("resultGetter")
		CustomShapedRecipes setResultGetter(Function<Inventory,ItemStack> resultGetter);
		default NmsShapedRecipes wrap()
		{
			return WrappedObject.wrap(NmsShapedRecipes.class,this);
		}
	}
	static NmsShapedRecipes newInstance(NamespacedKey key,String group,int width,int height,List<NmsRecipeItemStack> ingredients,ItemStack result,Function<Inventory,ItemStack> resultGetter)
	{
		return CustomShapedRecipes.newInstance(resultGetter).wrap().setKey(NmsMinecraftKey.newInstance(key)).setGroup(group).setWidth(width).setHeight(height).setIngredients(NmsNonNullList.newInstance(ingredients)).setResult(ObcItemStack.asNMSCopy(result));
	}
	
	@WrappedBukkitFieldAccessor({@VersionName("group"),@VersionName(maxVer=13, value="e"),@VersionName(minVer=17, value="f")})
	String getGroup();
	@WrappedBukkitFieldAccessor({@VersionName("group"),@VersionName(maxVer=13, value="e"),@VersionName(minVer=17, value="f")})
	NmsShapedRecipes setGroup(String group);
	
	@WrappedBukkitFieldAccessor({@VersionName("key"),@VersionName(minVer=17, value="e")})
	NmsMinecraftKey getKey0();
	default NamespacedKey getKey()
	{
		return getKey0().toBukkit();
	}
	@WrappedBukkitFieldAccessor({@VersionName("key"),@VersionName(minVer=17, value="e")})
	NmsShapedRecipes setKey(NmsMinecraftKey key);
	
	@WrappedBukkitFieldAccessor({@VersionName("width"),@VersionName(minVer=17, value="a")})
	int getWidth();
	@WrappedBukkitFieldAccessor({@VersionName("width"),@VersionName(minVer=17, value="a")})
	NmsShapedRecipes setWidth(int width);
	
	@WrappedBukkitFieldAccessor({@VersionName("height"),@VersionName(minVer=17, value="b")})
	int getHeight();
	@WrappedBukkitFieldAccessor({@VersionName("height"),@VersionName(minVer=17, value="b")})
	NmsShapedRecipes setHeight(int height);
	
	@WrappedBukkitFieldAccessor({@VersionName("items"),@VersionName(minVer=17, value="c")})
	NmsNonNullList getIngredients();
	@WrappedBukkitFieldAccessor({@VersionName("items"),@VersionName(minVer=17, value="c")})
	NmsShapedRecipes setIngredients(NmsNonNullList items);
	
	@WrappedBukkitFieldAccessor({@VersionName("result"),@VersionName(minVer=17, value="d")})
	NmsShapedRecipes setResult(NmsItemStack result);
	
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",minVer=19.3f))
	NmsCraftingBookCategoryV193 getCategoryV193();
	
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",minVer=19.3f))
	NmsShapedRecipes setCategoryV193(NmsCraftingBookCategoryV193 category);
	
	@Override
	default NmsShapedRecipes clone0()
	{
		NmsShapedRecipes r;
		if(getRaw() instanceof CustomShapedRecipes)
			r=NmsShapedRecipes.newInstance(getKey(),getGroup(),getWidth(),getHeight(),getIngredients().toList(NmsRecipeItemStack.class),getResult(),((CustomShapedRecipes)getRaw()).getResultGetter());
		else
			r=NmsShapedRecipes.newInstance(getKey(),getGroup(),getWidth(),getHeight(),getIngredients().toList(NmsRecipeItemStack.class),getResult());
		if(BukkitWrapper.version>=19.3f)
			r.setCategoryV193(this.getCategoryV193());
		return r;
	}
}
