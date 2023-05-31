package mz.lib.minecraft.message.hoverevent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mz.lib.minecraft.*;
import mz.lib.minecraft.md5.message.*;
import mz.lib.minecraft.message.MessageComponent;
import mz.mzlib.*;

/**
 * 表示移动鼠标到消息上后显示的信息
 */
public abstract class HoverEvent
{
	public static HoverEvent parse(JsonObject json)
	{
		switch(MessageComponent.getString(json.get("action")).toLowerCase())
		{
			case "show_text":
				return new ShowTextOnMouse(json);
			case "show_item":
				return new ShowItemOnMouse(json);
			case "show_entity":
				return new ShowEntityOnMouse(json);
			default:
				throw new IllegalArgumentException("unknown action "+json.get("action")+" of hoverEvent");
		}
	}
	public static HoverEvent parse(net.md_5.bungee.api.chat.HoverEvent hoverEvent)
	{
		JsonObject json=new JsonObject();
		json.addProperty("action",hoverEvent.getAction().name());
		json.add("value",MessageMd5Util.parse(hoverEvent.getValue()).toJson());
		return parse(json);
	}
	public abstract String getAction();
	public abstract JsonElement getValue();
	public abstract JsonElement getContentsV16();
	@Override
	public String toString()
	{
		return toJson().toString();
	}
	
	public JsonObject toJson()
	{
		JsonObject r=new JsonObject();
		r.addProperty("action",getAction());
		if(Server.instance.version>=16)
			r.add("contents",getContentsV16());
		else
			r.add("value",getValue());
		return r;
	}
}
