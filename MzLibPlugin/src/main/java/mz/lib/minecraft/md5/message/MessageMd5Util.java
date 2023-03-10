package mz.lib.minecraft.md5.message;

import com.google.gson.*;
import mz.lib.minecraft.*;
import mz.lib.minecraft.message.*;
import mz.lib.minecraft.message.clickevent.ClickEvent;
import mz.lib.minecraft.message.hoverevent.HoverEvent;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;

import java.util.*;
import java.util.stream.*;

public class MessageMd5Util
{
	public static void setColor(MessageComponent msg,ChatColor color)
	{
		if(color!=null)
			msg.color=color.getName();
		else
			msg.color=null;
	}
	
	public static MessageComponent cast(BaseComponent[] md5)
	{
		if(md5.length==0)
			return new TextMessageComponent("");
		MessageComponent r=cast(md5[0]);
		if(r.extra==null)
			r.extra=new ArrayList<>();
		for(int i=1;i<md5.length;i++)
		{
			r.extra.add(cast(md5[i]));
		}
		return r;
	}
	public static MessageComponent cast(BaseComponent md5)
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
				((TranslateMessageComponent)r).with=((TranslatableComponent) md5).getWith().stream().map(m->cast(m)).collect(Collectors.toList());
			}
		}
		else
			return new TextMessageComponent(md5.toLegacyText());
		if(md5.getExtra()!=null)
			r.extra=md5.getExtra().stream().map(e->cast(e)).collect(Collectors.toList());
		setColor(r,md5.getColor());
		if(Server.instance.version>=16)
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
			r.hoverEvent=cast(md5.getHoverEvent());
		if(md5.getClickEvent()!=null)
			r.clickEvent=ClickEvent.parse(md5.getClickEvent());
		return r;
	}
	
	public static HoverEvent cast(net.md_5.bungee.api.chat.HoverEvent hoverEvent)
	{
		JsonObject json=new JsonObject();
		json.addProperty("action",hoverEvent.getAction().name());
		json.add("value",cast(hoverEvent.getValue()).toJson());
		return HoverEvent.parse(json);
	}
}
