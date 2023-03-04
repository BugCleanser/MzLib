package mz.lib.minecraft.message;

import com.google.gson.JsonObject;
import mz.lib.minecraft.bukkitlegacy.*;

import java.util.List;

public class TranslateMessageComponent extends MessageComponent
{
	public String translate;
	public List<MessageComponent> with;
	
	public TranslateMessageComponent(JsonObject json)
	{
		super(json);
		translate=getString(json.get("translate"));
		if(json.has("with"))
			with=parseAll(json.get("with").getAsJsonArray());
	}
	public TranslateMessageComponent(String translate)
	{
		this(translate,null);
	}
	public TranslateMessageComponent(String translate,List<MessageComponent> with)
	{
		this.translate=translate;
		this.with=with;
	}
	
	public TranslateMessageComponent setTranslate(String translate)
	{
		this.translate=translate;
		return this;
	}
	
	@Override
	public JsonObject toJson()
	{
		JsonObject r=super.toJson();
		r.addProperty("translate",translate);
		if(with!=null&&with.size()>0)
			r.add("with",toJson(with));
		return r;
	}
	
	@Override
	public String toTextImpl(String locale)
	{
		Object[] args;
		if(with!=null)
			args=with.stream().map(m->m.toText(locale)).toArray(String[]::new);
		else
			args=new Object[0];
		return String.format(LangUtil.getTranslated(locale,translate),args);
	}
}
