package mz.lib.minecraft.bukkit.entity;

import mz.lib.minecraft.bukkit.wrappednms.NmsEntityTypes;
import mz.lib.minecraft.bukkit.wrappednms.NmsIRegistry;
import mz.lib.minecraft.bukkit.wrappednms.NmsMinecraftKey;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import org.bukkit.entity.EntityType;

public class EntityTypeUtil
{
	public static EntityType tridentV13=fromId("trident");
	public static EntityType glowItemFrameV17=fromId("glow_item_frame");
	
	
	@SuppressWarnings("deprecation")
	public static EntityType fromId(int id)
	{
		if(BukkitWrapper.v13)
			return fromNmsV13(NmsIRegistry.getEntityTypesV13().fromId(id,NmsEntityTypes.class));
		else
			return EntityType.fromId(id);
	}
	public static EntityType fromNmsV13(NmsEntityTypes type)
	{
		return fromId(NmsIRegistry.getEntityTypesV13().getKey(type));
	}
	public static NmsEntityTypes toNmsV13(EntityType type)
	{
		return NmsIRegistry.getEntityTypesV13().get(NmsMinecraftKey.newInstance(type.getName()),NmsEntityTypes.class);
	}
	@SuppressWarnings("deprecation")
	public static EntityType fromId(NmsMinecraftKey id)
	{
		if(!"minecraft".equals(id.getNamespace()))
			return null;
		return EntityType.fromName(id.getKey());
	}
	public static EntityType fromId(String id)
	{
		return fromId(NmsMinecraftKey.newInstance(id));
	}
	public static EntityType forFlattening(String idV_13,String idV13)
	{
		if(BukkitWrapper.v13)
			return fromId(idV13);
		else
			return fromId(idV_13);
	}
}
