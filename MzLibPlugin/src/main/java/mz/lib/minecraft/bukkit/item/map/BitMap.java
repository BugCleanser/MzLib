package mz.lib.minecraft.bukkit.item.map;

import mz.lib.*;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.wrappednms.*;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import java.nio.charset.StandardCharsets;
import java.util.*;

public interface BitMap extends MzMap
{
	@Override
	default NamespacedKey getKey()
	{
		return new NamespacedKey(MzLib.instance,"bit_map");
	}
	
	@Override
	default void init()
	{
		try
		{
			byte[] bits=new byte[128*128];
			Arrays.fill(bits,(byte)128);
			setBits(bits);
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	default void setBits(byte[] bits)
	{
		if(mzTag().containsKey("mapData"))
			mzTag().remove("mapData");
		if(mzTag().containsKey("mapDataCompressed"))
			mzTag().remove("mapDataCompressed");
		//mzTag().set("mapDataCompressed",WrappedObject.allocInstance(NmsNBTTagByteArray.class).setData(FileUtil.deflate(bits)));
		setBits(new String(Base64.getEncoder().encode(FileUtil.deflate(bits)),StandardCharsets.ISO_8859_1));
	}
	default void setBits(String base64)
	{
		mzTag().set("mapDataBase64",NmsNBTTagString.newInstance(base64));
	}
	default byte[] getBits()
	{
		if(mzTag().containsKey("mapDataBase64"))
			return FileUtil.inflate(Base64.getDecoder().decode(mzTag().get("mapDataBase64",NmsNBTTagString.class).getValue()));
		if(mzTag().containsKey("mapData"))
			return mzTag().get("mapData",NmsNBTTagByteArray.class).getData();
		if(mzTag().containsKey("mapDataCompressed"))
			return FileUtil.inflate(mzTag().get("mapDataCompressed",NmsNBTTagByteArray.class).getData());
		return new byte[128*128];
	}
	
	@Override
	default void onRedraw(Player player,MzMapCanvas canvas,int x,int y,int width,int height)
	{
		MzMapCanvas src=new MzMapCanvas(getBits(),128);
		for(int i=0;i<width;i++)
		{
			for(int j=0;j<height;j++)
				canvas.setPixel0(x+i,y+j,src.getPixel0(x+i,y+j));
		}
	}
}
