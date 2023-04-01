package mz.lib.minecraft.ui;

import mz.lib.module.MzModule;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class UI
{
	public MzModule module;
	public UI(MzModule module)
	{
		this.module=module;
		if(module!=null)
		{
			if(!module.getAllDepends().contains(UIStack.Module.instance))
				System.out.println("Module "+module.getClass().getName()+" should depend module "+UIStack.Module.class.getName());
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
	
	public UI ret(HumanEntity player)
	{
		return UIStack.get(player).ret();
	}
}
