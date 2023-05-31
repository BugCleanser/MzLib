package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.ProtocolUtil;
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
import mz.lib.nothing.Nothing;
import mz.lib.nothing.NothingLocation;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.entity.Player;

import java.util.Optional;

@WrappedBukkitClass({@VersionName(value="nms.AutoRecipe",maxVer=17),@VersionName(value="net.minecraft.recipebook.AutoRecipe",minVer=17)})
public interface NmsAutoRecipe extends WrappedBukkitObject, NothingBukkit
{
	@WrappedBukkitMethod(@VersionName(value="@0",minVer=13))
	NmsContainerRecipeBookV13 getCraftContainerV13();
	
	@NothingBukkitInject(name = @VersionName("a"), args = {NmsEntityPlayer.class,NmsIRecipe.class,boolean.class}, location = NothingLocation.FRONT)
	default Optional<Void> fillIngredientsFront(@LocalVar(1) NmsEntityPlayer player,@LocalVar(2) NmsIRecipe recipe,@LocalVar(3) boolean craftAll)
	{
		if(recipe.isNull())
			return Nothing.doReturn(null);
		NmsContainer craftContainer;
		if(BukkitWrapper.v13)
			craftContainer=getCraftContainerV13();
		else
			craftContainer=player.getOpenContainer();
		if(fillIngredients0(craftContainer,player,recipe))
		{
			if(craftAll)
				while(fillIngredients0(craftContainer,player,recipe))
					continue;
		}
		else
			ProtocolUtil.sendPacket((Player)player.getBukkitEntity(),NmsPacketPlayOutAutoRecipe.newInstance(craftContainer.getWindowId(),recipe));
		return Nothing.doReturn(null);
	}
	
	default boolean fillIngredients0(NmsContainer craftContainer,NmsEntityPlayer player,NmsIRecipe recipe)
	{
		for(Object s:craftContainer.getSlots())
		{
			if(!ItemStackBuilder.isAir(ObcItemStack.asCraftMirror(WrappedObject.wrap(NmsSlot.class,s).getItem()).getRaw()))
			{
			}
		}
		return false;
	}
}
