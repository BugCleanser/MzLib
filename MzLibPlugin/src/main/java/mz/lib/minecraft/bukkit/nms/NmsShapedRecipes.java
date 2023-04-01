package mz.lib.minecraft.bukkit.nms;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.lib.TypeUtil;
import mz.lib.minecraft.*;
import mz.lib.minecraft.mzlang.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.mzlang.*;
import mz.lib.minecraft.bukkit.obc.ObcItemStack;
import mz.lib.*;
import mz.lib.mzlang.*;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;
import mz.lib.mzlang.*;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Function;

@VersionalWrappedClass({@VersionalName(value="nms.ShapedRecipes",maxVer=17),@VersionalName(value="net.minecraft.world.item.crafting.ShapedRecipes",minVer=17)})
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
	interface CustomShapedRecipes extends VersionMzObject
	{
		@RefactorVersionSign({@VersionalName(maxVer=13,value="craftItem"),@VersionalName(minVer=13,value="a")})
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
	
	@VersionalWrappedFieldAccessor({@VersionalName("group"),@VersionalName(maxVer=13, value="e"),@VersionalName(minVer=17, value="f")})
	String getGroup();
	@VersionalWrappedFieldAccessor({@VersionalName("group"),@VersionalName(maxVer=13, value="e"),@VersionalName(minVer=17, value="f")})
	NmsShapedRecipes setGroup(String group);
	
	@VersionalWrappedFieldAccessor({@VersionalName("key"),@VersionalName(minVer=17, value="e")})
	NmsMinecraftKey getKey0();
	default NamespacedKey getKey()
	{
		return getKey0().toBukkit();
	}
	@VersionalWrappedFieldAccessor({@VersionalName("key"),@VersionalName(minVer=17, value="e")})
	NmsShapedRecipes setKey(NmsMinecraftKey key);
	
	@VersionalWrappedFieldAccessor({@VersionalName("width"),@VersionalName(minVer=17, value="a")})
	int getWidth();
	@VersionalWrappedFieldAccessor({@VersionalName("width"),@VersionalName(minVer=17, value="a")})
	NmsShapedRecipes setWidth(int width);
	
	@VersionalWrappedFieldAccessor({@VersionalName("height"),@VersionalName(minVer=17, value="b")})
	int getHeight();
	@VersionalWrappedFieldAccessor({@VersionalName("height"),@VersionalName(minVer=17, value="b")})
	NmsShapedRecipes setHeight(int height);
	
	@VersionalWrappedFieldAccessor({@VersionalName("items"),@VersionalName(minVer=17, value="c")})
	NmsNonNullList getIngredients();
	@VersionalWrappedFieldAccessor({@VersionalName("items"),@VersionalName(minVer=17, value="c")})
	NmsShapedRecipes setIngredients(NmsNonNullList items);
	
	@VersionalWrappedFieldAccessor({@VersionalName("result"),@VersionalName(minVer=17, value="d")})
	NmsShapedRecipes setResult(NmsItemStack result);
	
	@VersionalWrappedFieldAccessor(@VersionalName(value="@0",minVer=19.3f))
	NmsCraftingBookCategoryV193 getCategoryV193();
	
	@VersionalWrappedFieldAccessor(@VersionalName(value="@0",minVer=19.3f))
	NmsShapedRecipes setCategoryV193(NmsCraftingBookCategoryV193 category);
	
	@Override
	default NmsShapedRecipes clone0()
	{
		NmsShapedRecipes r;
		if(getRaw() instanceof CustomShapedRecipes)
			r=NmsShapedRecipes.newInstance(getKey(),getGroup(),getWidth(),getHeight(),getIngredients().toList(NmsRecipeItemStack.class),getResult(),((CustomShapedRecipes)getRaw()).getResultGetter());
		else
			r=NmsShapedRecipes.newInstance(getKey(),getGroup(),getWidth(),getHeight(),getIngredients().toList(NmsRecipeItemStack.class),getResult());
		if(Server.instance.version>=19.3f)
			r.setCategoryV193(this.getCategoryV193());
		return r;
	}
}
