package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedObject;

import java.util.List;

@WrappedBukkitClass({@VersionName(value="nms.Container",maxVer=17),@VersionName(value="net.minecraft.world.inventory.Container",minVer=17)})
public interface NmsContainer extends WrappedBukkitObject
{
	@WrappedBukkitFieldAccessor({@VersionName(value="slots",maxVer=17),@VersionName(value="@0",maxVer=17)})
	List<Object> getSlotsV_17();
	@WrappedBukkitFieldAccessor({@VersionName(value="slots",minVer=17),@VersionName(value="@1",minVer=17)})
	NmsNonNullList getSlotsV17();
	default List<Object> getSlots()
	{
		if(BukkitWrapper.v17)
			return getSlotsV17().getRaw();
		else
			return getSlotsV_17();
	}
	default NmsSlot getSlot(int index)
	{
		return WrappedObject.wrap(NmsSlot.class,getSlots().get(index));
	}
	default void setSlot(int index,NmsSlot slot)
	{
		getSlots().set(index,slot.getRaw());
	}
	
	default void setReadWrite(int index)
	{
		setSlot(index,NmsSlot.readWrite(getSlot(index)));
	}
	default void setReadOnly(int index)
	{
		setSlot(index,NmsSlot.readOnly(getSlot(index)));
	}
	default void setWriteOnly(int index)
	{
		setSlot(index,NmsSlot.writeOnly(getSlot(index)));
	}
	default void setShowOnly(int index)
	{
		setSlot(index,NmsSlot.showOnly(getSlot(index)));
	}
	
	@WrappedBukkitFieldAccessor({@VersionName("windowId"),@VersionName(minVer=17, value="j")})
	int getWindowId();
	
	@WrappedBukkitMethod({@VersionName(minVer=17,value="incrementStateId"),@VersionName(minVer=18, value="k")})
	int incrementStateIdV17();
}
