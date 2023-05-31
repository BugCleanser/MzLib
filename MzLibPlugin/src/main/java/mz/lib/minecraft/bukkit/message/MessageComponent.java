package mz.lib.minecraft.bukkit.message;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mz.lib.minecraft.bukkit.message.clickmsgevent.ClickMsgEvent;
import mz.lib.minecraft.bukkit.message.showonmouse.ShowOnMouse;
import mz.lib.minecraft.bukkit.wrappednms.NmsIChatBaseComponent;
import mz.lib.minecraft.bukkit.wrappednms.NmsICommandListener;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 表示消息的一个段落
 */
public abstract class MessageComponent implements Cloneable
{
	public List<MessageComponent> extra;
	public ChatColor color;
	public String fontV16;
	public Boolean bold=false;
	public Boolean italic=false;
	public Boolean underlined=false;
	public Boolean strikethrough=false;
	public Boolean obfuscated=false;
	public String insertion;
	public ClickMsgEvent cme;
	public ShowOnMouse som;
	
	public MessageComponent()
	{
	}
	public MessageComponent(JsonObject json)
	{
		if(json.has("extra"))
			extra=Lists.newArrayList(json.get("extra").getAsJsonArray().iterator()).stream().map(MessageComponent::parse).collect(Collectors.toList());
		if(json.has("color"))
		{
			if(BukkitWrapper.version<16)
				color=ChatColor.valueOf(getString(json.get("color")).toUpperCase());
			else
				color=WrappedChatColor.ofV16(getString(json.get("color")));
		}
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
			som=ShowOnMouse.parse(json.get("hoverEvent").getAsJsonObject());
		if(json.has("clickEvent"))
			cme=ClickMsgEvent.parse(json.get("clickEvent").getAsJsonObject());
	}
	public static MessageComponent parse(NmsIChatBaseComponent nms)
	{
		return parse(NmsIChatBaseComponent.NmsChatSerializer.toJson(nms));
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
	public static MessageComponent parse(BaseComponent[] md5)
	{
		if(md5.length==0)
			return new TextMessageComponent("");
		MessageComponent r=parse(md5[0]);
		if(r.extra==null)
			r.extra=new ArrayList<>();
		for(int i=1;i<md5.length;i++)
		{
			r.extra.add(parse(md5[i]));
		}
		return r;
	}
	public static MessageComponent parse(BaseComponent md5)
	{
		MessageComponent r;
		if(md5 instanceof TextComponent)
		{
			r=new TextMessageComponent(((TextComponent) md5).getText());
		}
		else if(md5 instanceof TranslatableComponent)
		{
			r=new TranslateMessageComponent(((TranslatableComponent) md5).getTranslate());
			if(((TranslatableComponent) md5).getWith()!=null)
			{
				((TranslateMessageComponent)r).with=((TranslatableComponent) md5).getWith().stream().map(m->parse(m)).collect(Collectors.toList());
			}
		}
		else
			return new TextMessageComponent(md5.toLegacyText());
		if(md5.getExtra()!=null)
			r.extra=md5.getExtra().stream().map(e->parse(e)).collect(Collectors.toList());
		r.color=md5.getColor();
		if(BukkitWrapper.version>=16)
		{
			try{
				r.fontV16=mz.lib.TypeUtil.cast(md5.getClass().getMethod("getFont").invoke(md5));
			}catch(Throwable t){
				mz.lib.TypeUtil.throwException(t);
				return null;
			}
		}
		r.bold=md5.isBold();
		r.italic=md5.isItalic();
		r.underlined=md5.isUnderlined();
		r.strikethrough=md5.isStrikethrough();
		r.obfuscated=md5.isObfuscated();
		r.insertion=md5.getInsertion();
		if(md5.getHoverEvent()!=null)
			r.som=ShowOnMouse.parse(md5.getHoverEvent());
		if(md5.getClickEvent()!=null)
			r.cme=ClickMsgEvent.parse(md5.getClickEvent());
		return r;
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
			r.addProperty("color",color.getName());
		if(som!=null)
			r.add("hoverEvent",som.toJson());
		if(cme!=null)
			r.add("clickEvent",cme.toJson());
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
	
	public ShowOnMouse getShowOnMouse()
	{
		return som;
	}
	/**
	 * 设置移动鼠标到消息上后显示的信息
	 *
	 * @param som 移动鼠标到消息上后显示的信息
	 * @return this
	 */
	public MessageComponent setShowOnMouse(ShowOnMouse som)
	{
		this.som=som;
		return this;
	}
	
	public ClickMsgEvent getClickMsgEvent()
	{
		return cme;
	}
	/**
	 * 设置点击消息的作用
	 *
	 * @param cme 点击消息的作用
	 * @return this
	 */
	public MessageComponent setClickMsgEvent(ClickMsgEvent cme)
	{
		this.cme=cme;
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
	
	public void setColor(ChatColor color)
	{
		if(color!=null&&color.ordinal()>16)
		{
			throw new IllegalArgumentException("the arg can only be color or null rather than "+color.name());
		}
		this.color=color;
	}
	
	/**
	 * 发送这个消息给玩家
	 *
	 * @param senders 要接受信息的玩家
	 *               异常： 选择器无法找到目标
	 */
	public void send(CommandSender... senders)
	{
		if(senders.length==0)
			return;
		NmsIChatBaseComponent nms=this.toNms();
		for(CommandSender sender: senders)
		{
			NmsICommandListener.fromBukkit(sender).sendMessage(nms);
		}
	}
	public NmsIChatBaseComponent toNms()
	{
		return NmsIChatBaseComponent.NmsChatSerializer.jsonToComponent(this.toString());
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
