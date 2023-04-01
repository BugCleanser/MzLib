package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;
import mz.lib.wrapper.*;

import java.util.Iterator;
import java.util.List;

@VersionalWrappedClass({@VersionalName(value="nms.MerchantRecipeList",maxVer=17),@VersionalName(value="net.minecraft.world.item.trading.MerchantRecipeList",minVer=17)})
public interface NmsMerchantRecipeList extends VersionalWrappedObject, Iterable<NmsMerchantRecipe>
{
	static NmsMerchantRecipeList newInstance()
	{
		return WrappedObject.getStatic(NmsMerchantRecipeList.class).staticNewInstance();
	}
	@VersionalWrappedMethod(@VersionalName("a"))
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
