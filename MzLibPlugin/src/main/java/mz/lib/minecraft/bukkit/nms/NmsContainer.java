package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.WrappedObject;
import mz.lib.*;
import mz.lib.wrapper.*;

import java.util.List;

@VersionalWrappedClass({@VersionalName(value="nms.Container",maxVer=17),@VersionalName(value="net.minecraft.world.inventory.Container",minVer=17)})
public interface NmsContainer extends VersionalWrappedObject
{
	@VersionalWrappedFieldAccessor({@VersionalName(value="slots",maxVer=17),@VersionalName(value="@0",maxVer=17)})
	List<Object> getSlotsV_17();
	@VersionalWrappedFieldAccessor({@VersionalName(value="slots",minVer=17),@VersionalName(value="@1",minVer=17)})
	NmsNonNullList getSlotsV17();
	default List<Object> getSlots()
	{
		if(Server.instance.v17)
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
	
	@VersionalWrappedFieldAccessor({@VersionalName("windowId"),@VersionalName(minVer=17, value="j")})
	int getWindowId();
	
	@VersionalWrappedMethod({@VersionalName(minVer=17,value="incrementStateId"),@VersionalName(minVer=18, value="k")})
	int incrementStateIdV17();
}
