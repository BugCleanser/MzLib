package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkit.obc.ObcItemStack;
import mz.lib.wrapper.WrappedMethod;
import mz.mzlib.*;
import mz.mzlib.wrapper.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

@VersionalWrappedClass({@VersionalName(value="nms.IRecipe",maxVer=17),@VersionalName(value="net.minecraft.world.item.crafting.IRecipe",minVer=17)})
public interface NmsIRecipe extends VersionalWrappedObject,Recipe
{
	@WrappedMethod("toBukkitRecipe")
	Recipe toBukkitRecipe();
	
	@VersionalWrappedMethod({@VersionalName("getResult"),@VersionalName(maxVer=13, value="b"),@VersionalName(value="d",minVer=13,maxVer=14),@VersionalName(minVer=14,maxVer=16, value="c"),@VersionalName(minVer=18, value="c")})
	NmsItemStack getResult0();
	
	@VersionalWrappedMethod(@VersionalName(maxVer=13, value="craftItem"))
	NmsItemStack getResultV_13(NmsInventoryCrafting inv);
	@VersionalWrappedMethod({@VersionalName(value="craftItem",minVer=13,maxVer=14),@VersionalName(minVer=14, value="a")})
	NmsItemStack getResultV13(NmsIInventory inv);
	default NmsItemStack getResult(NmsIInventory inv)
	{
		if(Server.instance.v13)
			return getResultV13(inv);
		else
			return getResultV_13(inv.cast(NmsInventoryCrafting.class));
	}
	
	@Override
	default ItemStack getResult()
	{
		return ObcItemStack.asBukkitCopy(getResult0());
	}
}
