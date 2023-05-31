package mz.lib.minecraft.bukkit.entity;

import mz.lib.minecraft.bukkit.wrappednms.NmsEntityTypes;
import mz.lib.minecraft.bukkit.wrappednms.NmsNBTTagCompound;
import mz.lib.minecraft.bukkit.wrappednms.NmsNBTTagLong;
import mz.lib.minecraft.bukkit.wrappednms.NmsWorld;
import mz.lib.minecraft.bukkit.wrappedobc.ObcEntity;
import mz.lib.minecraft.bukkit.wrappedobc.ObcWorld;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.UUID;
import java.util.function.Supplier;

public class EntityBuilder implements Supplier<Entity>
{
	public NmsNBTTagCompound nbt;
	public EntityBuilder(NmsNBTTagCompound nbt)
	{
		this.nbt=nbt;
	}
	public EntityBuilder(Entity entity)
	{
		this(NmsNBTTagCompound.newInstance());
		WrappedObject.wrap(ObcEntity.class,entity).getHandle().save(nbt);
	}
	
	public void set(Entity entity)
	{
		WrappedObject.wrap(ObcEntity.class,entity).getHandle().load(nbt);
	}
	@Override
	public Entity get()
	{
		return NmsEntityTypes.spawn(nbt,WrappedObject.wrap(NmsWorld.class,WrappedObject.wrap(ObcWorld.class,getWorld()).getHandle().getRaw())).getBukkitEntity();
	}
	public World getWorld()
	{
		return Bukkit.getWorld(getWorldUUID());
	}
	public EntityBuilder setWorld(World world)
	{
		setWorld(world.getUID());
		return this;
	}
	public EntityBuilder setWorld(UUID world)
	{
		nbt.getMap().put("WorldUUIDMost",NmsNBTTagLong.newInstance(world.getMostSignificantBits()));
		nbt.getMap().put("WorldUUIDLeast",NmsNBTTagLong.newInstance(world.getLeastSignificantBits()));
		return this;
	}
	public UUID getWorldUUID()
	{
		return new UUID(WrappedObject.wrap(NmsNBTTagLong.class,nbt.getMap().get("WorldUUIDMost")).getValue(),WrappedObject.wrap(NmsNBTTagLong.class,nbt.getMap().get("WorldUUIDLeast")).getValue());
	}
	public NmsNBTTagCompound nbt()
	{
		return nbt;
	}
}
