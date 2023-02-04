package mz.lib.minecraft.bukkit.message.showonmouse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import mz.lib.minecraft.bukkit.message.MessageComponent;
import mz.lib.minecraft.bukkit.message.TextMessageComponent;
import mz.lib.minecraft.bukkit.wrappednms.NmsNBTTagCompound;
import org.bukkit.entity.Entity;

public class ShowEntityOnMouse extends ShowOnMouse
{
	public String name;
	public String type;
	public String id;
	
	@SuppressWarnings("deprecation")
	public ShowEntityOnMouse(Entity entity)
	{
		name=new TextMessageComponent(entity.getName()).toString();
		type=entity.getType().getName();
		id=entity.getUniqueId().toString();
	}
	public ShowEntityOnMouse(String name,String type,String id)
	{
		this.name=new TextMessageComponent(name).toString();
		this.type=type;
		this.id=id;
	}
	public ShowEntityOnMouse(MessageComponent name,String type,String id)
	{
		this.type=type;
		this.name=name.toString();
		this.id=id;
	}
	public ShowEntityOnMouse(JsonObject json)
	{
		if(json.has("contents"))
		{
			JsonObject contents=json.get("contents").getAsJsonObject();
			if(contents.has("name"))
				this.name=contents.get("name").toString();
			this.type=MessageComponent.getString(contents.get("type"));
			this.id=MessageComponent.getString(contents.get("id"));
		}
		else
		{
			NmsNBTTagCompound nbt=NmsNBTTagCompound.newInstance(MessageComponent.getString(json.get("value")));
			if(nbt.containsKey("name"))
				this.name=nbt.getString("name");
			if(nbt.containsKey("type"))
				this.type=nbt.getString("type");
			if(nbt.containsKey("id"))
				this.id=nbt.getString("id");
		}
	}
	
	@Override
	public String getAction()
	{
		return "show_entity";
	}
	
	@Override
	public JsonPrimitive getValue()
	{
		NmsNBTTagCompound r=NmsNBTTagCompound.newInstance();
		if(name!=null)
			r.set("name",name);
		if(type!=null)
			r.set("type",type);
		if(id!=null)
			r.set("id",id);
		return new JsonPrimitive(r.toString());
	}
	
	@Override
	public JsonObject getContentsV16()
	{
		JsonObject r=new JsonObject();
		if(name!=null)
			r.add("name",new JsonParser().parse(name));
		r.addProperty("type",type);
		r.addProperty("id",id);
		return r;
	}
}
