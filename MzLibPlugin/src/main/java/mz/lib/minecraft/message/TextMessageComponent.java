package mz.lib.minecraft.message;

import com.google.gson.JsonObject;
import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.minecraft.MinecraftLanguages;
import mz.lib.minecraft.message.hoverevent.ShowTextOnMouse;
import org.bukkit.command.CommandSender;

public class TextMessageComponent extends MessageComponent
{
	public String text;
	
	public TextMessageComponent(JsonObject json)
	{
		super(json);
		text=getString(json.get("text"));
	}
	
	public TextMessageComponent(String text)
	{
		this.text=text;
	}
	public static TextMessageComponent textCopy(String text,CommandSender sender,String value)
	{
		return textCopy(text,MinecraftLanguages.getLang(sender),value);
	}
	public static TextMessageComponent textCopy(String text,String locale,String value)
	{
		return (TextMessageComponent)new TextMessageComponent(text).setInsertion(value).setHoverEvent(new ShowTextOnMouse(StringUtil.replaceStrings(MinecraftLanguages.get(locale,"mzlib.chat.shiftCopy"),new MapEntry<>("%\\{value\\}",value))));
	}
	public TextMessageComponent setText(String text)
	{
		this.text=text;
		return this;
	}
	
	@Override
	public JsonObject toJson()
	{
		JsonObject r=super.toJson();
		r.addProperty("text",text);
		return r;
	}
	
	@Override
	public String toTextImpl(String locale)
	{
		return text;
	}
}
