package mz.lib.minecraft.message;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mz.lib.minecraft.message.clickevent.ClickEvent;
import mz.lib.minecraft.message.hoverevent.HoverEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 表示消息的一个段落
 */
public abstract class MessageComponent implements Cloneable
{
	public String color;
	public String fontV16;
	public Boolean bold=false;
	public Boolean italic=false;
	public Boolean underlined=false;
	public Boolean strikethrough=false;
	public Boolean obfuscated=false;
	public String insertion;
	public ClickEvent clickEvent;
	public HoverEvent hoverEvent;
	public List<MessageComponent> extra;
	
	public MessageComponent()
	{
	}
	public MessageComponent(JsonObject json)
	{
		if(json.has("extra"))
			extra=Lists.newArrayList(json.get("extra").getAsJsonArray().iterator()).stream().map(MessageComponent::parse).collect(Collectors.toList());
		if(json.has("color"))
			color=getString(json.get("color"));
		if(json.has("font"))
			fontV16=getString(json.get("font"));
		if(json.has("bold"))
			bold=getBoolean(json.get("bold"));
		if(json.has("italic"))
			italic=getBoolean(json.get("italic"));
		if(json.has("underlined"))
			underlined=getBoolean(json.get("underlined"));
		if(json.has("strikethrough"))
			strikethrough=getBoolean(json.get("strikethrough"));
		if(json.has("obfuscated"))
			obfuscated=getBoolean(json.get("obfuscated"));
		if(json.has("insertion"))
			insertion=getString(json.get("insertion"));
		if(json.has("hoverEvent"))
			hoverEvent=HoverEvent.parse(json.get("hoverEvent").getAsJsonObject());
		if(json.has("clickEvent"))
			clickEvent=ClickEvent.parse(json.get("clickEvent").getAsJsonObject());
	}
	public static MessageComponent parse(String json)
	{
		return parse(new JsonParser().parse(json));
	}
	public static List<MessageComponent> parseAll(JsonArray array)
	{
		List<MessageComponent> r=new ArrayList<>();
		for(JsonElement json:array)
			r.add(parse(json));
		return r;
	}
	public static MessageComponent parse(JsonElement json)
	{
		if(json.isJsonObject())
		{
			JsonObject jo=json.getAsJsonObject();
			if(jo.has("text"))
				return new TextMessageComponent(jo);
			else if(jo.has("translate"))
				return new TranslateMessageComponent(jo);
			else if(jo.has("score"))
				return new ScoreComponent(jo);
			else if(jo.has("selector"))
				return new SelectorMessageComponent(jo);
			else if(jo.has("keybind"))
				return new KeybindMessageComponent(jo);
			else if(jo.has("nbt"))
				return new NbtMessageComponent(jo);
		}
		else if(json.isJsonPrimitive())
			return new TextMessageComponent(json.getAsJsonPrimitive().getAsString());
		else if(json.isJsonArray())
			return merge(parseAll(json.getAsJsonArray()).toArray(new MessageComponent[0]));
		throw new IllegalArgumentException();
	}
	
	public static MessageComponent mergeLines(MessageComponent ...msgs)
	{
		List<MessageComponent> a=new ArrayList<>();
		for(int i=0;i<msgs.length;i++)
		{
			if(i!=0)
				a.add(new TextMessageComponent("\n"));
			a.add(msgs[i]);
		}
		return merge(a.toArray(new MessageComponent[0]));
	}
	public static MessageComponent merge(MessageComponent ...msgs)
	{
		if(msgs.length==0)
			return new TextMessageComponent("");
		MessageComponent r=msgs[0];
		if(msgs.length>1)
		{
			if(r.extra==null)
				r.extra=new ArrayList<>();
			for(int i=1;i<msgs.length;i++)
				r.extra.add(msgs[i]);
		}
		return r;
	}
	public JsonObject toJson()
	{
		JsonObject r=new JsonObject();
		if(insertion!=null)
			r.addProperty("insertion",insertion);
		if(bold!=null)
			r.addProperty("bold",bold);
		if(italic!=null)
			r.addProperty("italic",italic);
		if(underlined!=null)
			r.addProperty("underlined",underlined);
		if(strikethrough!=null)
			r.addProperty("strikethrough",strikethrough);
		if(obfuscated!=null)
			r.addProperty("obfuscated",obfuscated);
		if(color!=null)
			r.addProperty("color",color);
		if(hoverEvent!=null)
			r.add("hoverEvent",hoverEvent.toJson());
		if(clickEvent!=null)
			r.add("clickEvent",clickEvent.toJson());
		if(extra!=null&&!extra.isEmpty())
			r.add("extra",toJson(extra));
		return r;
	}
	public static JsonArray toJson(List<MessageComponent> messageComponents)
	{
		JsonArray r=new JsonArray();
		for(MessageComponent c:messageComponents)
		{
			r.add(c.toJson());
		}
		return r;
	}
	public MessageComponent setInsertion(String insertion)
	{
		this.insertion=insertion;
		return this;
	}
	
	public HoverEvent getHoverEvent()
	{
		return hoverEvent;
	}
	/**
	 * 设置移动鼠标到消息上后显示的信息
	 *
	 * @param hoverEvent 移动鼠标到消息上后显示的信息
	 * @return this
	 */
	public MessageComponent setHoverEvent(HoverEvent hoverEvent)
	{
		this.hoverEvent=hoverEvent;
		return this;
	}
	
	public ClickEvent getClickEvent()
	{
		return clickEvent;
	}
	/**
	 * 设置点击消息的作用
	 *
	 * @param clickEvent 点击消息的作用
	 * @return this
	 */
	public MessageComponent setClickEvent(ClickEvent clickEvent)
	{
		this.clickEvent=clickEvent;
		return this;
	}
	
	public MessageComponent addExtra(List<MessageComponent> mcs)
	{
		this.extra.addAll(mcs);
		return this;
	}
	public MessageComponent addExtra(MessageComponent... mcs)
	{
		return addExtra(Lists.newArrayList(mcs));
	}
	
	public boolean isBold()
	{
		return bold;
	}
	public MessageComponent setBold(boolean bold)
	{
		this.bold=bold;
		return this;
	}
	
	public boolean isItalic()
	{
		return italic;
	}
	public MessageComponent setItalic(boolean italic)
	{
		this.italic=italic;
		return this;
	}
	
	public boolean isUnderlined()
	{
		return underlined;
	}
	public MessageComponent setUnderlined(boolean underlined)
	{
		this.underlined=underlined;
		return this;
	}
	
	public boolean isStrikethrough()
	{
		return strikethrough;
	}
	public MessageComponent setStrikethrough(boolean strikethrough)
	{
		this.strikethrough=strikethrough;
		return this;
	}
	
	public boolean isObfuscated()
	{
		return obfuscated;
	}
	public MessageComponent setObfuscated(boolean obfuscated)
	{
		this.obfuscated=obfuscated;
		return this;
	}
	
	public abstract String toTextImpl(String locale);
	public String toText(String locale)
	{
		StringBuilder r=new StringBuilder();
		switch(color.toLowerCase())
		{
			case "black":
				r.append("§0");
				break;
			case "dark_blue":
				r.append("§1");
				break;
			case "dark_green":
				r.append("§2");
				break;
			case "dark_aqua":
				r.append("§3");
				break;
			case "dark_red":
				r.append("§4");
				break;
			case "dark_purple":
				r.append("§5");
				break;
			case "gold":
				r.append("§6");
				break;
			case "gray":
				r.append("§7");
				break;
			case "dark_gray":
				r.append("§8");
				break;
			case "blue":
				r.append("§9");
				break;
			case "green":
				r.append("§a");
				break;
			case "aqua":
				r.append("§b");
				break;
			case "red":
				r.append("§c");
				break;
			case "light_purple":
				r.append("§d");
				break;
			case "yellow":
				r.append("§e");
				break;
			case "white":
				r.append("§f");
				break;
			case "reset":
				r.append("§r");
				break;
		}
		if(bold)
			r.append("§l");
		if(italic)
			r.append("§o");
		if(underlined)
			r.append("§n");
		if(strikethrough)
			r.append("§m");
		if(obfuscated)
			r.append("§k");
		r.append(toTextImpl(locale));
		if(extra!=null)
			for(MessageComponent e:extra)
				r.append(e.toText(locale));
		return r.toString();
	}
	
	@Override
	public String toString()
	{
		return toJson().toString();
	}
	
	@Override
	public MessageComponent clone()
	{
		return parse(this.toJson());
	}
	
	public static String getString(JsonElement json)
	{
		if(json.isJsonPrimitive())
			return json.getAsJsonPrimitive().getAsString();
		return ((TextMessageComponent)MessageComponent.parse(json)).text;
	}
	public static short getShort(JsonElement json)
	{
		if(json.isJsonPrimitive()&&json.getAsJsonPrimitive().isNumber())
			return json.getAsShort();
		return Short.parseShort(getString(json));
	}
	public static boolean getBoolean(JsonElement json)
	{
		if(json.isJsonPrimitive()&&json.getAsJsonPrimitive().isBoolean())
			return json.getAsJsonPrimitive().getAsBoolean();
		return Boolean.parseBoolean(getString(json));
	}
}
