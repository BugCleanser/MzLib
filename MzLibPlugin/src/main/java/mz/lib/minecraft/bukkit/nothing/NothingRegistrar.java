package mz.lib.minecraft.bukkit.nothing;

import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.module.IRegistrar;
import mz.lib.minecraft.bukkit.module.RegistrarRegistrar;
import mz.lib.minecraft.bukkit.wrappednms.NmsEntity;
import mz.lib.minecraft.bukkit.wrappednms.NmsEntityFishingHook;
import mz.lib.minecraft.bukkit.wrappednms.NmsNetworkManager;
import mz.lib.minecraft.bukkit.wrappednms.NmsRecipeItemStack;
import mz.lib.nothing.Nothing;

public class NothingRegistrar extends AbsModule implements IRegistrar<Class<? extends Nothing>>
{
	public static NothingRegistrar instance=new NothingRegistrar();
	public NothingRegistrar()
	{
		super(MzLib.instance,RegistrarRegistrar.instance);
	}
	@Override
	public Class<Class<? extends Nothing>> getType()
	{
		return TypeUtil.cast(Class.class);
	}
	@Override
	public boolean register(Class<? extends Nothing> obj)
	{
		if(Nothing.class.isAssignableFrom(obj))
		{
			Nothing.install(TypeUtil.cast(obj));
			return true;
		}
		return false;
	}
	@Override
	public void unregister(Class<? extends Nothing> obj)
	{
		Nothing.uninstall(TypeUtil.cast(obj));
	}
	@Override
	public void onEnable()
	{
		Nothing.init();
		
		reg(NmsEntity.class);
		reg(NmsEntityFishingHook.class);
		reg(NmsNetworkManager.class);
		reg(NmsRecipeItemStack.class);
	}
	
	@Override
	public void onDisable()
	{
		Nothing.uninstallAll();
	}
}
