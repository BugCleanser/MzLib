package mz.lib.minecraft.message;

import com.google.gson.JsonObject;

public class SelectorMessageComponent extends MessageComponent
{
	public String selector;
	public String separator;
	
	public SelectorMessageComponent(String selector,String separator)
	{
		this.selector=selector;
		this.separator=separator;
	}
	public SelectorMessageComponent(JsonObject json)
	{
		super(json);
		selector=getString(json.get("selector"));
		if(json.has("separator"))
			separator=getString(json.get("separator"));
	}
	
	@Override
	public JsonObject toJson()
	{
		JsonObject r=super.toJson();
		r.addProperty("selector",selector);
		if(separator!=null)
			r.addProperty("separator",separator);
		return r;
	}
}
