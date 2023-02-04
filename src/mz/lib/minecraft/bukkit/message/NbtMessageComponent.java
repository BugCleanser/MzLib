package mz.lib.minecraft.bukkit.message;

import com.google.gson.JsonObject;

public class NbtMessageComponent extends MessageComponent
{
	public String nbt;
	public Boolean interpret;
	public MessageComponent separator;
	public String block;
	public String entity;
	public String storage;
	
	public NbtMessageComponent(String nbt,Boolean interpret,MessageComponent separator,String block,String entity,String storage)
	{
		this.nbt=nbt;
		this.interpret=interpret;
		this.separator=separator;
		this.block=block;
		this.entity=entity;
		this.storage=storage;
	}
	public NbtMessageComponent(JsonObject json)
	{
		super(json);
		nbt=MessageComponent.getString(json.get("nbt"));
		if(json.has("interpret"))
			interpret=MessageComponent.getBoolean(json.get("interpret"));
		if(json.has("separator"))
			separator=MessageComponent.parse(json.get("separator"));
		if(json.has("block"))
			block=MessageComponent.getString(json.get("block"));
		if(json.has("entity"))
			entity=MessageComponent.getString(json.get("entity"));
		if(json.has("storage"))
			storage=MessageComponent.getString(json.get("storage"));
	}
	
	@Override
	public JsonObject toJson()
	{
		JsonObject r=super.toJson();
		r.addProperty("nbt",nbt);
		if(interpret!=null)
			r.addProperty("interpret",interpret);
		if(separator!=null)
			r.add("separator",separator.toJson());
		if(block!=null)
			r.addProperty("block",block);
		if(entity!=null)
			r.addProperty("entity",entity);
		if(storage!=null)
			r.addProperty("storage",storage);
		return r;
	}
}
