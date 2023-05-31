package mz.lib.minecraft.bukkit.item.map;

import org.bukkit.map.MapPalette;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MzMapCanvas implements Cloneable
{
	public byte[] data;
	public int width;
	
	public MzMapCanvas(byte[] data,int width)
	{
		this.data=data;
		this.width=width;
	}
	public MzMapCanvas(BufferedImage image)
	{
		this(image.getWidth(),image.getHeight());
		for(int i=0;i!=getWidth();i++)
		{
			for(int j=0;j!=getHeight();j++)
			{
				setPixel(i,j,new Color(image.getRGB(i,j),true));
			}
		}
	}
	
	public MzMapCanvas(int width,int height)
	{
		this(new byte[width*height],width);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return data.length/getWidth();
	}
	
	public byte getPixel0(int x,int y)
	{
		return data[width*y+x];
	}
	
	public void setPixel0(int x,int y,byte pixel)
	{
		data[width*y+x]=pixel;
	}
	
	public Color getPixel(int x,int y)
	{
		return MapPalette.getColor(getPixel0(x,y));
	}
	
	@Deprecated
	public void setPixel(int x,int y,Color pixel)
	{
		setPixel0(x,y,WrappedMapPalette.matchColor(pixel));
	}
	
	public void sub(MzMapCanvas sub,int x,int y)
	{
		int subHeight=getHeight();
		for(int i=0;i!=sub.width;i++)
		{
			for(int j=0;j!=subHeight;j++)
				sub.setPixel0(i,j,getPixel0(x+i,y+j));
		}
	}
	
	public MzMapCanvas getSub(int x,int y,int width,int height)
	{
		MzMapCanvas r=new MzMapCanvas(width,height);
		sub(r,x,y);
		return r;
	}
	
	@Override
	public MzMapCanvas clone()
	{
		return getSub(0,0,getWidth(),getHeight());
	}
}
