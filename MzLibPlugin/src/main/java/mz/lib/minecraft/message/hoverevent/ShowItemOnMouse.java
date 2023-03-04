package mz.lib.minecraft.message.hoverevent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.message.MessageComponent;
import mz.lib.minecraft.bukkit.nms.NmsNBTTagCompound;
import org.bukkit.inventory.ItemStack;

public class ShowItemOnMouse extends HoverEvent
{
	private ItemStack item;
	
	public ShowItemOnMouse(ItemStack item)
	{
		this.setItem(item);
	}
	public ShowItemOnMouse(JsonObject json)
	{
		if(json.has("contents"))
		{
			JsonObject contents=json.get("contents").getAsJsonObject();
			this.item=new ItemStackBuilder(MessageComponent.getString(contents.get("id"))).get();
			if(contents.has("count"))
				this.item.setAmount(contents.get("count").getAsShort());
			if(contents.has("tag"))
				new ItemStackBuilder(this.item).setTag(NmsNBTTagCompound.newInstance(MessageComponent.getString(contents.get("tag"))));
		}
		else
			this.item=new ItemStackBuilder(NmsNBTTagCompound.newInstance(MessageComponent.getString(json.get("value")))).get();
	}
	
	@Override
	public String getAction()
	{
		return "show_item";
	}
	@Override
	public JsonElement getValue()
	{
		return new JsonPrimitive(new ItemStackBuilder(item).toString());
	}
	
	@Override
	public JsonElement getContentsV16()
	{
		JsonObject r=new JsonObject();
		r.addProperty("id",new ItemStackBuilder(item).getId());
		r.addProperty("count",item.getAmount());
		r.addProperty("tag",new ItemStackBuilder(item).tag().toString());
		return r;
	}
	
	/**
	 * @return 物品
	 */
	public ItemStack getItem()
	{
		return item;
	}
	
	/**
	 * @param item item
	 */
	public ShowItemOnMouse setItem(ItemStack item)
	{
		this.item=item;
		return this;
	}
}
