package mz.lib.minecraft.bukkit.message.clickmsgevent;

import com.google.gson.JsonObject;
import mz.lib.minecraft.bukkit.message.MessageComponent;
import net.md_5.bungee.api.chat.ClickEvent;

/**
 * 左键消息的作用
 */
public abstract class ClickMsgEvent
{
	@SuppressWarnings("deprecation")
	public static ClickMsgEvent parse(JsonObject json)
	{
		String value=MessageComponent.getString(json.get("value"));
		switch(MessageComponent.getString(json.get("action")).toLowerCase())
		{
			case "open_url":
				return new ClickToOpenUrl(value);
			case "open_file":
				return new ClickToOpenFile(value);
			case "run_command":
				return new ClickToSay(value);
			case "suggest_command":
				return new ClickToSetChat(value);
			case "change_page":
				return new ClickToChangePage(Integer.valueOf(value));
			case "copy_to_clipboard":
				return new ClickToCopyV16(value);
			default:
				throw new IllegalArgumentException("unknown action "+json.get("action")+" of clickEvent");
		}
	}
	public static ClickMsgEvent parse(ClickEvent clickEvent)
	{
		JsonObject json=new JsonObject();
		json.addProperty("action",clickEvent.getAction().name());
		json.addProperty("value",clickEvent.getValue());
		return parse(json);
	}
	/**
	 * @return json中的action值
	 */
	public abstract String getAction();
	/**
	 * @return json中的value值
	 */
	public abstract String getValue();
	@Override
	/**
	 * 得到json
	 */ public String toString()
	{
		return toJson().toString();
	}
	public JsonObject toJson()
	{
		JsonObject r=new JsonObject();
		r.addProperty("action",getAction());
		r.addProperty("value",getValue());
		return r;
	}
}
