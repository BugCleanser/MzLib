package mz.lib.minecraft.message.hoverevent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mz.lib.minecraft.message.MessageComponent;
import mz.lib.minecraft.message.TextMessageComponent;

public class ShowTextOnMouse extends HoverEvent
{
	public MessageComponent text;
	
	public ShowTextOnMouse(MessageComponent text)
	{
		this.text=text;
	}
	public ShowTextOnMouse(String text)
	{
		this(new TextMessageComponent(text));
	}
	public ShowTextOnMouse(JsonObject json)
	{
		if(json.has("contents"))
			this.text=MessageComponent.parse(json.get("contents"));
		else
			this.text=MessageComponent.parse(json.get("value"));
	}
	
	@Override
	public String getAction()
	{
		return "show_text";
	}
	
	@Override
	public JsonElement getValue()
	{
		return text.toJson();
	}
	
	@Override
	public JsonElement getContentsV16()
	{
		return getValue();
	}
}
