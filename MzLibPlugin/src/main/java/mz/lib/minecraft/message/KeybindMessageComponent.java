package mz.lib.minecraft.message;

import com.google.gson.JsonObject;

public class KeybindMessageComponent extends MessageComponent
{
	public String keybind;
	
	public KeybindMessageComponent(String keybind)
	{
		this.keybind=keybind;
	}
	public KeybindMessageComponent(JsonObject json)
	{
		super(json);
		keybind=getString(json.get("keybind"));
	}
	
	@Override
	public JsonObject toJson()
	{
		JsonObject r=super.toJson();
		r.addProperty("keybind",keybind);
		return r;
	}
	
	@Override
	public String toTextImpl(String locale)
	{
		return keybind;
	}
}
