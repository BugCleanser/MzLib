package mz.lib.minecraft.item;

import mz.lib.*;
import mz.lib.minecraft.*;
import mz.lib.minecraft.Server;
import mz.lib.minecraft.nbt.*;

import java.util.*;

public interface ItemStack
{
	static ItemStack newInstance(Item item,int amount)
	{
		return Factory.instance.newItemStack(item,amount);
	}
	static ItemStack newInstance(Item item)
	{
		return newInstance(item,1);
	}
	static ItemStack newInstance(String id)
	{
		return newInstance(Item.fromId(Identifier.newInstance(id)));
	}
	static ItemStack newInstance(String idV_13,int childIdV_13,String idV13)
	{
		if(Server.instance.v13)
			return newInstance(idV13);
		ItemStack r=newInstance(idV_13);
		r.setDamage(childIdV_13);
		return r;
	}
	
	Item getItem();
	void setItem(Item item);
	
	int getCount();
	void setCount(int count);
	
	int getDamage();
	void setDamage(int damage);
	
	NbtObject getTag();
	void setTag(NbtObject tag);
	default NbtObject tag()
	{
		if(TypeUtil.isNull(getTag()))
		{
			NbtObject r=NbtObject.newInstance();
			setTag(r);
			return r;
		}
		return getTag();
	}
	
	static ItemStack air()
	{
		return newInstance("air");
	}
	static ItemStack whiteStainedGlassPane()
	{
		return newInstance("stained_glass_pane",(short) 0,"white_stained_glass_pane");
	}
	static ItemStack orangeStainedGlassPane()
	{
		return newInstance("stained_glass_pane",(short) 1,"orange_stained_glass_pane");
	}
	static ItemStack lightBlueStainedGlassPane()
	{
		return newInstance("stained_glass_pane",(short) 3,"light_blue_stained_glass_pane");
	}
	static ItemStack yellowStainedGlassPane()
	{
		return newInstance("stained_glass_pane",(short) 4,"yellow_stained_glass_pane");
	}
	static ItemStack limeStainedGlassPane()
	{
		return newInstance("stained_glass_pane",(short) 5,"lime_stained_glass_pane");
	}
	static ItemStack grayStainedGlassPane()
	{
		return newInstance("stained_glass_pane",(short) 7,"gray_stained_glass_pane");
	}
	static ItemStack purpleStainedGlassPane()
	{
		return newInstance("stained_glass_pane",(short) 10,"purple_stained_glass_pane");
	}
	static ItemStack blueStainedGlassPane()
	{
		return newInstance("stained_glass_pane",(short) 11,"blue_stained_glass_pane");
	}
	static ItemStack brownStainedGlassPane()
	{
		return newInstance("stained_glass_pane",(short) 12,"brown_stained_glass_pane");
	}
	static ItemStack redStainedGlassPane()
	{
		return newInstance("stained_glass_pane",(short) 14,"red_stained_glass_pane");
	}
	static ItemStack blackStainedGlassPane()
	{
		return newInstance("stained_glass_pane",(short) 15,"black_stained_glass_pane");
	}
	static ItemStack grass()
	{
		return newInstance("tallgrass",(short) 1,"grass");
	}
	static ItemStack grassBlock()
	{
		return newInstance("grass",(short) 0,"grass_block");
	}
	static ItemStack expBottle()
	{
		return newInstance("exp_bottle",(short) 0,"experience_bottle");
	}
	static ItemStack sign()
	{
		return newInstance("sign",(short) 0,"oak_sign");
	}
	static ItemStack planks()
	{
		return newInstance("planks",(short) 0,"oak_planks");
	}
	static ItemStack clock()
	{
		return newInstance("clock");
	}
	static ItemStack enderEye()
	{
		return newInstance("ender_eye");
	}
	static ItemStack newSkull(String name,UUID id,String value)
	{
		ItemStack is = newInstance("skull",3,"player_head");
		is.tag().set("SkullOwner",NbtObject.newInstance(new MapEntry<>("Id",Server.instance.version<16?NbtString.newInstance(id.toString()):NbtIntArray.newInstance(id)),new MapEntry<>("Properties",NbtObject.newInstance(new MapEntry<>("textures",NbtList.newInstance(NbtObject.newInstance(new MapEntry<>("Value",NbtString.newInstance(value)))))))));
		if(name!=null)
			((NbtObject)is.tag().get("SkullOwner")).set("Name",NbtString.newInstance(name));
		return is;
	}
	static ItemStack craftingTable()
	{
		return newInstance("crafting_table");
	}
	static ItemStack newSkull(String url)
	{
		return newSkull(null,UUID.nameUUIDFromBytes(url.getBytes(StringUtil.UTF8)),Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\""+url+"\"}}}").getBytes(StringUtil.UTF8)));
	}
	static ItemStack questionMark()
	{
		return newSkull("http://textures.minecraft.net/texture/65b95da1281642daa5d022adbd3e7cb69dc0942c81cd63be9c3857d222e1c8d9");
	}
	static ItemStack returnArrow()
	{
		return newSkull("http://textures.minecraft.net/texture/d9ed8bcbafbe99787325239048b8099407a098e7077c9b4c3b478b289b9149fd");
	}
	static ItemStack leftArrow()
	{
		return newSkull("http://textures.minecraft.net/texture/3866a889e51ca79c5d200ea6b5cfd0a655f32fea38b8138598c72fb200b97b9");
	}
	static ItemStack rightArrow()
	{
		return newSkull("http://textures.minecraft.net/texture/dfbf1402a04064cebaa96b77d5455ee93b685332e264c80ca36415df992fb46c");
	}
	static ItemStack checkmark()
	{
		return newSkull("http://textures.minecraft.net/texture/ce2a530f42726fa7a31efab8e43dadee188937cf824af88ea8e4c93a49c57294");
	}
}
