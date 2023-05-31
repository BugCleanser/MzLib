package mz.lib.minecraft.bukkit.gui;

import mz.lib.minecraft.bukkit.module.IModule;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class GUI
{
	public IModule module;
	public GUI(IModule module)
	{
		this.module=module;
		if(module!=null)
		{
			if(!module.getAllDepends().contains(ViewList.Module.instance))
				System.out.println("Module "+module.getClass().getName()+" should depend module "+ViewList.Module.class.getName());
		}
	}
	
	public IModule getModule()
	{
		return module;
	}
	
	public abstract void open(HumanEntity player);
	public abstract void close(HumanEntity player);
	public abstract List<HumanEntity> getViewers();
	public void closeAll()
	{
		for(HumanEntity v:new ArrayList<>(getViewers()))
		{
			close(v);
		}
	}
	
	public void refresh()
	{
	}
	
	public GUI ret(HumanEntity player)
	{
		return ViewList.get(player).ret();
	}
}
