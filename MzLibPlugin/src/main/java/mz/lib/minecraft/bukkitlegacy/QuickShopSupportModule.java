package mz.lib.minecraft.bukkitlegacy;

import com.google.common.collect.Lists;
import mz.lib.module.MzModule;
import mz.lib.module.MzModule;
import mz.lib.nothing.NothingRegistrar;
import mz.lib.nothing.LocalVar;
import mz.lib.nothing.Nothing;
import mz.lib.nothing.NothingInject;
import mz.lib.nothing.NothingLocation;
import mz.lib.wrapper.WrappedClass;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class QuickShopSupportModule extends MzModule
{
	public static QuickShopSupportModule instance=new QuickShopSupportModule();
	
	@WrappedClass("org.maxgamer.quickshop.Util.NMS")
	public interface FengjianQSNMS extends WrappedObject, Nothing
	{
		@NothingInject(name="rename", args={ItemStack.class}, location=NothingLocation.FRONT)
		static Optional<Void> renameOverwrite(@LocalVar(0) ItemStack is)
		{
			ItemMeta im=is.getItemMeta();
			im.setLore(Lists.newArrayList("MzLibQuickShopSupport"));
			is.setItemMeta(im);
			return Nothing.doReturn(null);
		}
	}
	
	@WrappedClass("org.maxgamer.quickshop.Listeners.DisplayProtectionListener")
	public static interface FengjianQSDisplayProtectionListener extends WrappedObject,Nothing
	{
		@NothingInject(name="itemStackCheck", args={ItemStack.class}, location=NothingLocation.FRONT)
		default Optional<Boolean> itemStackCheckOverwrite(@LocalVar(1) ItemStack is)
		{
			if(is.hasItemMeta())
			{
				if(is.getItemMeta().getLore().contains("MzLibQuickShopSupport"))
					return Nothing.doReturn(true);
			}
			return Nothing.doReturn(false);
		}
	}
	
	@Override
	public void onLoad()
	{
		depend(NothingRegistrar.instance);
		if(MzLib.instance.getConfig().getBoolean("func.QuickShopSupport",true)&& Bukkit.getPluginManager().getPlugin("QuickShop")!=null)
		{
			try
			{
				reg(FengjianQSNMS.class);
				reg(FengjianQSDisplayProtectionListener.class);
			}
			catch(IllegalArgumentException e)
			{
			}
		}
	}
}
