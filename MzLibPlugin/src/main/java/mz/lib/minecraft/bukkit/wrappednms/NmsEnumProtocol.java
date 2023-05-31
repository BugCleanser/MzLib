package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;

@WrappedBukkitClass({@VersionName(maxVer=17,value="nms.EnumProtocol"),@VersionName(minVer=17,value="net.minecraft.network.EnumProtocol")})
public interface NmsEnumProtocol extends WrappedBukkitObject
{
}
