package mz.lib.minecraft.ui.inventory;

import mz.lib.minecraft.MinecraftLanguages;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkitlegacy.module.IModule;
import mz.lib.minecraft.ui.UIStack;
import mz.lib.minecraft.bukkit.nms.NmsContainer;
import mz.lib.minecraft.bukkit.nms.NmsIChatBaseComponent;
import mz.lib.minecraft.bukkit.nms.NmsSlot;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class InputBox extends AnvilUI
{
	public Function<HumanEntity,String> errMsg=null;
	
	@SuppressWarnings("deprecation")
	public InputBox(IModule module,Function<Player,NmsIChatBaseComponent> titleGetter,String name)
	{
		super(module,"");
		this.name=name;
		this.titleModifier=(p,t)->t.set(titleGetter.apply(p));
	}
	
	@Override
	public void setSlots(HumanEntity player,NmsContainer container)
	{
		super.setSlots(player,container);
		container.setSlot(0,NmsSlot.showOnly(container.getSlot(0)));
		container.setSlot(1,NmsSlot.showOnly(container.getSlot(1)));
		container.setSlot(2,NmsSlot.showOnly(container.getSlot(2)));
	}
	
	@Override
	public void refresh()
	{
		this.setItem(0,new ItemStackBuilder(Material.PAPER).setName(getName()).get());
		this.setItem(1,p->ItemStackBuilder.returnArrow().setName(MinecraftLanguages.translate(p,"mzlib.menu.return")).get());
		this.setItem(2,p->ItemStackBuilder.checkmark().setLore(errMsg==null?new String[0]:new String[]{errMsg.apply(p)}).setName(MinecraftLanguages.translate(p,"mzlib.inputBox.OK")).get());
		this.refreshAfter();
	}
	
	public void setErrMsg(Function<HumanEntity,String> msg)
	{
		errMsg=msg;
		refresh();
	}
	
	@Override
	public void onClick(Player player,int slot)
	{
		switch(slot)
		{
			case 1:
				UIStack.get(player).ret();
				break;
			case 2:
				save(player);
				break;
		}
	}
	
	public void save(Player player)
	{
	}
}
