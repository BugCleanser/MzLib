package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.nothing.NothingBukkit;
import mz.lib.minecraft.bukkit.nothing.NothingBukkitInject;
import mz.lib.minecraft.bukkit.wrappedobc.ObcItemStack;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.nothing.LocalVar;
import mz.lib.nothing.NothingLocation;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WrappedBukkitClass({@VersionName(value="nms.TileEntityFurnace",maxVer=17),@VersionName(value="net.minecraft.world.level.block.entity.TileEntityFurnace",minVer=17)})
public interface NmsTileEntityFurnace extends WrappedBukkitObject, NothingBukkit
{
	@WrappedBukkitMethod(@VersionName(minVer=13, value="f"))
	Map<Object,Integer> staticGetFuelMap0V13();
	static Map<Object,Integer> getFuelMap0V13()
	{
		return WrappedObject.getStatic(NmsTileEntityFurnace.class).staticGetFuelMap0V13();
	}
	
	Map<NmsRecipeItemStack,Integer> fuelMap=new HashMap<NmsRecipeItemStack,Integer>()
	{
		{
			if(BukkitWrapper.v13)
				getFuelMap0V13().forEach((i,t)->
				{
					this.put(NmsRecipeItemStack.newInstance(ItemStackBuilder.getData(ObcItemStack.asBukkitCopy(NmsItemStack.newInstance(WrappedObject.wrap(NmsItem.class,i))))),t);
				});
			else
			{
				this.put(NmsRecipeItemStack.newInstance(Material.WOOD_STEP),150);
				this.put(NmsRecipeItemStack.newInstance(Material.WOOL),100);
				this.put(NmsRecipeItemStack.newInstance(Material.CARPET),67);
				this.put(NmsRecipeItemStack.newInstance(Material.LADDER),300);
				this.put(NmsRecipeItemStack.newInstance(Material.WOOD_BUTTON),100);
				this.put(NmsRecipeItemStack.newInstance(Material.WOOD),200);
				this.put(NmsRecipeItemStack.newInstance(Material.LOG),300);
				this.put(NmsRecipeItemStack.newInstance(Material.LOG_2),300);
				this.put(NmsRecipeItemStack.newInstance(Material.COAL_BLOCK),16000);
				this.put(NmsRecipeItemStack.newInstance(Material.WOOD_SWORD,Material.WOOD_AXE,Material.WOOD_PICKAXE,Material.WOOD_HOE,Material.WOOD_SPADE),200);
				this.put(NmsRecipeItemStack.newInstance(Material.STICK),100);
				this.put(NmsRecipeItemStack.newInstance(Material.SIGN),200);
				this.put(NmsRecipeItemStack.newInstance(Material.COAL),1600);
				this.put(NmsRecipeItemStack.newInstance(Material.LAVA_BUCKET),20000);
				this.put(NmsRecipeItemStack.newInstance(Material.BLAZE_ROD),2400);
				this.put(NmsRecipeItemStack.newInstance(Material.SAPLING),100);
				this.put(NmsRecipeItemStack.newInstance(Material.BOWL),100);
				this.put(NmsRecipeItemStack.newInstance(Material.BOW,Material.FISHING_ROD),300);
				this.put(NmsRecipeItemStack.newInstance(Material.DARK_OAK_DOOR_ITEM,Material.ACACIA_DOOR_ITEM,Material.BIRCH_DOOR_ITEM,Material.JUNGLE_DOOR_ITEM,Material.SPRUCE_DOOR_ITEM,Material.WOOD_DOOR,Material.TRAP_DOOR),200);
				this.put(NmsRecipeItemStack.newInstance(Material.BOAT,Material.BOAT_ACACIA,Material.BOAT_BIRCH,Material.BOAT_DARK_OAK,Material.BOAT_JUNGLE,Material.BOAT_SPRUCE),400);
			}
		}
	};
	
	@NothingBukkitInject(name = {@VersionName(maxVer = 17, value = "fuelTime"),@VersionName(minVer = 18, value = "a")}, args = {NmsItemStack.class}, location = NothingLocation.FRONT)
	static Optional<Integer> fuelTimeOverwrite(@LocalVar(0) NmsItemStack item)
	{
		for(Map.Entry<NmsRecipeItemStack,Integer> e:fuelMap.entrySet())
		{
			if(e.getKey().test(ObcItemStack.asBukkitCopy(item)))
				return Optional.ofNullable(e.getValue());
		}
		return Optional.of(0);
	}
	@WrappedBukkitMethod(value={@VersionName(maxVer=18, value="fuelTime"),@VersionName(minVer=18, value="a")})
	int fuelTime(NmsItemStack item);
	default int fuelTime(ItemStack item)
	{
		return fuelTime(ObcItemStack.asNMSCopy(item));
	}
	
	@NothingBukkitInject(name = {@VersionName("isFuel"),@VersionName(minVer = 18, value = "b")}, args = {NmsItemStack.class}, location = NothingLocation.FRONT)
	static Optional<Boolean> isFuelOverwrite(@LocalVar(0) NmsItemStack item)
	{
		for(NmsRecipeItemStack i:fuelMap.keySet())
		{
			if(i.test(ObcItemStack.asBukkitCopy(item)))
				return Optional.of(true);
		}
		return Optional.of(false);
	}
	@WrappedBukkitMethod({@VersionName("isFuel"),@VersionName(minVer=18, value="b")})
	boolean staticIsFuel(NmsItemStack item);
	static boolean isFuel(NmsItemStack item)
	{
		return WrappedObject.getStatic(NmsTileEntityFurnace.class).staticIsFuel(item);
	}
	static boolean isFuel(ItemStack item)
	{
		return isFuel(ObcItemStack.asNMSCopy(item));
	}
}
