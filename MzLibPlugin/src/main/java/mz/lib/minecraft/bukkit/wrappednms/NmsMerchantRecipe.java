package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.MerchantRecipe",maxVer=17),@VersionName(value="net.minecraft.world.item.trading.MerchantRecipe",minVer=17)})
public interface NmsMerchantRecipe extends WrappedBukkitObject
{
	static NmsMerchantRecipe newInstance(NmsNBTTagCompound nbt)
	{
		return WrappedObject.getStatic(NmsMerchantRecipe.class).staticNewInstance(nbt);
	}
	@WrappedConstructor
	NmsMerchantRecipe staticNewInstance(NmsNBTTagCompound nbt);
	@WrappedBukkitFieldAccessor({@VersionName("buyingItem1"),@VersionName(minVer=17, value="a")})
	NmsItemStack getBuyingItem1();
	@WrappedBukkitFieldAccessor({@VersionName("buyingItem1"),@VersionName(minVer=17, value="a")})
	NmsMerchantRecipe setBuyingItem1(NmsItemStack item);
	@WrappedBukkitFieldAccessor({@VersionName("buyingItem2"),@VersionName(minVer=17, value="b")})
	NmsItemStack getBuyingItem2();
	@WrappedBukkitFieldAccessor({@VersionName("buyingItem2"),@VersionName(minVer=17, value="b")})
	NmsMerchantRecipe setBuyingItem2(NmsItemStack item);
	@WrappedBukkitFieldAccessor({@VersionName("sellingItem"),@VersionName(minVer=17, value="c")})
	NmsItemStack getSellingItem();
	@WrappedBukkitFieldAccessor({@VersionName("sellingItem"),@VersionName(minVer=17, value="c")})
	NmsMerchantRecipe setSellingItem(NmsItemStack item);
	
	@WrappedBukkitMethod({@VersionName(maxVer=13, value="k"),@VersionName(value="k",minVer=13,maxVer=14),@VersionName(minVer=14,maxVer=15, value="s"),@VersionName(minVer=15,maxVer=15, value="t"),@VersionName(minVer=16,maxVer=16, value="s"),@VersionName(minVer=16, value="t")})
	NmsNBTTagCompound save();
	
	default NmsMerchantRecipe copy()
	{
		return NmsMerchantRecipe.newInstance(this.save());
	}
}
