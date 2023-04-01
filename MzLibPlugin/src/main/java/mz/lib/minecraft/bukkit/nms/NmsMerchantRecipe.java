package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.MerchantRecipe",maxVer=17),@VersionalName(value="net.minecraft.world.item.trading.MerchantRecipe",minVer=17)})
public interface NmsMerchantRecipe extends VersionalWrappedObject
{
	static NmsMerchantRecipe newInstance(NmsNBTTagCompound nbt)
	{
		return WrappedObject.getStatic(NmsMerchantRecipe.class).staticNewInstance(nbt);
	}
	@WrappedConstructor
	NmsMerchantRecipe staticNewInstance(NmsNBTTagCompound nbt);
	@VersionalWrappedFieldAccessor({@VersionalName("buyingItem1"),@VersionalName(minVer=17, value="a")})
	NmsItemStack getBuyingItem1();
	@VersionalWrappedFieldAccessor({@VersionalName("buyingItem1"),@VersionalName(minVer=17, value="a")})
	NmsMerchantRecipe setBuyingItem1(NmsItemStack item);
	@VersionalWrappedFieldAccessor({@VersionalName("buyingItem2"),@VersionalName(minVer=17, value="b")})
	NmsItemStack getBuyingItem2();
	@VersionalWrappedFieldAccessor({@VersionalName("buyingItem2"),@VersionalName(minVer=17, value="b")})
	NmsMerchantRecipe setBuyingItem2(NmsItemStack item);
	@VersionalWrappedFieldAccessor({@VersionalName("sellingItem"),@VersionalName(minVer=17, value="c")})
	NmsItemStack getSellingItem();
	@VersionalWrappedFieldAccessor({@VersionalName("sellingItem"),@VersionalName(minVer=17, value="c")})
	NmsMerchantRecipe setSellingItem(NmsItemStack item);
	
	@VersionalWrappedMethod({@VersionalName(maxVer=13, value="k"),@VersionalName(value="k",minVer=13,maxVer=14),@VersionalName(minVer=14,maxVer=15, value="s"),@VersionalName(minVer=15,maxVer=15, value="t"),@VersionalName(minVer=16,maxVer=16, value="s"),@VersionalName(minVer=16, value="t")})
	NmsNBTTagCompound save();
	
	default NmsMerchantRecipe copy()
	{
		return NmsMerchantRecipe.newInstance(this.save());
	}
}
