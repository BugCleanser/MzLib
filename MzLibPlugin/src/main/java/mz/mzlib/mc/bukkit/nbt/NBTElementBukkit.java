package mz.mzlib.mc.bukkit.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.bukkit.delegator.DelegatorBukkitClass;
import mz.mzlib.mc.nbt.NBTElement;
import mz.mzlib.util.delegator.AutoDelegator;
import mz.mzlib.util.delegator.Delegator;

@DelegatorBukkitClass({@VersionName(end=1700,name="nms.NBTBase"),@VersionName(begin=1700,name="net.minecraft.nbt.NBTBase")})
public interface NBTElementBukkit extends NBTElement, Delegator
{
	AutoDelegator<NBTElementBukkit> autoDelegator=new AutoDelegator<>(NBTElementBukkit.class,NBTCompoundBukkit.class);
}
