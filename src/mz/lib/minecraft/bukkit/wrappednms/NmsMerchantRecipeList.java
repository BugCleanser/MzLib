package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;

import java.util.Iterator;
import java.util.List;

@WrappedBukkitClass({@VersionName(value="nms.MerchantRecipeList",maxVer=17),@VersionName(value="net.minecraft.world.item.trading.MerchantRecipeList",minVer=17)})
public interface NmsMerchantRecipeList extends WrappedBukkitObject, Iterable<NmsMerchantRecipe>
{
	static NmsMerchantRecipeList newInstance()
	{
		return WrappedObject.getStatic(NmsMerchantRecipeList.class).staticNewInstance();
	}
	@WrappedBukkitMethod(@VersionName("a"))
	void write(NmsPacketDataSerializer data);
	@WrappedConstructor
	NmsMerchantRecipeList staticNewInstance();
	@Override
	List<Object> getRaw();
	
	default NmsMerchantRecipeList add(int index,NmsMerchantRecipe e)
	{
		getRaw().add(index,e.getRaw());
		return this;
	}
	default NmsMerchantRecipeList add(NmsMerchantRecipe e)
	{
		getRaw().add(e.getRaw());
		return this;
	}
	
	@Override
	default Iterator<NmsMerchantRecipe> iterator()
	{
		Iterator<Object> r=getRaw().iterator();
		return new Iterator<NmsMerchantRecipe>()
		{
			public NmsMerchantRecipe next()
			{
				return WrappedObject.wrap(NmsMerchantRecipe.class,r.next());
			}
			public boolean hasNext()
			{
				return r.hasNext();
			}
		};
	}
}
