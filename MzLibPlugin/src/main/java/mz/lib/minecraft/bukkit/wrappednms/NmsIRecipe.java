package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrappedobc.ObcItemStack;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedMethod;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

@WrappedBukkitClass({@VersionName(value="nms.IRecipe",maxVer=17),@VersionName(value="net.minecraft.world.item.crafting.IRecipe",minVer=17)})
public interface NmsIRecipe extends WrappedBukkitObject,Recipe
{
	@WrappedMethod("toBukkitRecipe")
	Recipe toBukkitRecipe();
	
	@WrappedBukkitMethod({@VersionName("getResult"),@VersionName(maxVer=13, value="b"),@VersionName(value="d",minVer=13,maxVer=14),@VersionName(minVer=14,maxVer=16, value="c"),@VersionName(minVer=18, value="c")})
	NmsItemStack getResult0();
	
	@WrappedBukkitMethod(@VersionName(maxVer=13, value="craftItem"))
	NmsItemStack getResultV_13(NmsInventoryCrafting inv);
	@WrappedBukkitMethod({@VersionName(value="craftItem",minVer=13,maxVer=14),@VersionName(minVer=14, value="a")})
	NmsItemStack getResultV13(NmsIInventory inv);
	default NmsItemStack getResult(NmsIInventory inv)
	{
		if(BukkitWrapper.v13)
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
