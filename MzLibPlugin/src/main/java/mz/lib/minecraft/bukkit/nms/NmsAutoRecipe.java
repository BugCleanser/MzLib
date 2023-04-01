package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkit.obc.*;
import mz.lib.minecraft.bukkitlegacy.*;
import mz.lib.minecraft.bukkitlegacy.itemstack.*;
import mz.lib.minecraft.nothing.*;
import mz.lib.minecraft.bukkit.obc.*;
import mz.lib.minecraft.nothing.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.nothing.*;
import mz.lib.wrapper.*;
import mz.lib.*;
import mz.lib.minecraft.bukkit.obc.*;
import mz.lib.wrapper.*;
import org.bukkit.entity.*;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.Optional;

@VersionalWrappedClass({@VersionalName(value="nms.AutoRecipe",maxVer=17),@VersionalName(value="net.minecraft.recipebook.AutoRecipe",minVer=17)})
public interface NmsAutoRecipe extends VersionalWrappedObject, VersionalNothing
{
	@VersionalWrappedMethod(@VersionalName(value="@0",minVer=13))
	NmsContainerRecipeBookV13 getCraftContainerV13();
	
	@VersionalNothingInject(name = @VersionalName("a"), args = {NmsEntityPlayer.class,NmsIRecipe.class,boolean.class}, location = NothingLocation.FRONT)
	default Optional<Void> fillIngredientsFront(@LocalVar(1) NmsEntityPlayer player, @LocalVar(2) NmsIRecipe recipe, @LocalVar(3) boolean craftAll)
	{
		if(recipe.isNull())
			return Nothing.doReturn(null);
		NmsContainer craftContainer;
		if(Server.instance.v13)
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
