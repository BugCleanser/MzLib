package mz.lib.minecraft.bukkit.item.map;

import mz.lib.*;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.entity.*;
import mz.lib.minecraft.bukkit.wrappednms.*;
import mz.lib.mzlang.*;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.awt.*;
import java.awt.image.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

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
	
	static List<BitMap> fromImage(Image image,int x,int y)
	{
		BufferedImage tar=new BufferedImage(x*128,y*128,BufferedImage.TYPE_INT_RGB);
		Graphics2D g=tar.createGraphics();
		g.drawImage(image.getScaledInstance(x*128,y*128,Image.SCALE_SMOOTH),0,0,x*128,y*128,null);
		g.dispose();
		List<BitMap> r=new ArrayList<>();
		RGB[][] temp=new RGB[x*128][y*128];
		for(int i=0;i<x*128;i++)
			for(int j=0;j<y*128;j++)
				temp[i][j]=new RGB(new Color(tar.getRGB(i,j)));
		for(int j=0;j<y*128;j++)
			for(int i=0;i<x*128;i++)
			{
				int n=WrappedMapPalette.matchColor(temp[i][j].toColor());
				if(n<0)
					n+=256;
				RGB err=temp[i][j].subtract(new RGB(WrappedMapPalette.getColors()[n]));
				if(i+1<x*128)
				{
					temp[i+1][j]=temp[i+1][j].add(err.multiply(7./16));
					err=err.multiply(9./16);
				}
				if(j+1<y*128)
				{
					if(i>0)
					{
						temp[i-1][j+1]=temp[i-1][j+1].add(err.multiply(3./9));
						err=err.multiply(6./9);
					}
					temp[i][j+1]=temp[i][j+1].add(err.multiply(5./6));
					err=err.multiply(1./6);
					if(i+1<x*128)
						temp[i+1][j+1]=temp[i+1][j+1].add(err);
				}
			}
		for(int j=0;j!=y;j++)
			for(int i=0;i!=x;i++)
			{
				BitMap item=MzObject.newInstance(BitMap.class);
				MzMapCanvas canvas=new MzMapCanvas(128,128);
				for(int l=0;l<128;l++)
					for(int k=0;k<128;k++)
						canvas.setPixel0(k,l,WrappedMapPalette.matchColor(temp[i*128+k][j*128+l].toColor()));
				item.setBits(canvas.data);
				r.add(item);
			}
		return r;
	}
}
