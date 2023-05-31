package mz.lib.minecraft.message.legacy;

import com.google.gson.JsonObject;

public class ScoreComponent extends MessageComponent
{
	public String name;
	public String objective;
	
	public ScoreComponent(String name,String objective)
	{
		this.name=name;
		this.objective=objective;
	}
	public ScoreComponent(JsonObject json)
	{
		super(json);
		this.name=getString(json.get("score").getAsJsonObject().get("name"));
		this.objective=getString(json.get("score").getAsJsonObject().get("objective"));
	}
	
	@Override
	public JsonObject toJson()
	{
		JsonObject r=super.toJson();
		r.addProperty("name",name);
		r.addProperty("objective",objective);
		return r;
	}
}
