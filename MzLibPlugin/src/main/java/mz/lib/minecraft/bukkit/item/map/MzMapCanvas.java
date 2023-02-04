package mz.lib.minecraft.bukkit.item.map;

import mz.lib.minecraft.bukkit.*;
import org.bukkit.map.MapPalette;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

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
	
	public void setPixel(int x,int y,Color pixel)
	{
		setPixel0(x,y,matchColor(pixel));
	}
	
	public static byte matchColor(Color rgb)
	{
		int index = 1;
		double minDist = Double.MAX_VALUE;
		Color[] colors=WrappedMapPalette.getColors();
		for (int i=1;i<colors.length;i++)
		{
			Color color=colors[i];
			double dist=WrappedMapPalette.getDistance(rgb, color);
			if (dist < minDist)
			{
				minDist=dist;
				index=i;
			}
		}
		
		double[] probabilities = new double[colors.length];
		double sum = 0;
		for (int i = 1; i < colors.length; i++)
		{
			double dist=WrappedMapPalette.getDistance(rgb,colors[i]);
			double probability = Math.exp(-0.5 * Math.pow(dist/minDist,2));
			sum+=probability;
			probabilities[i]=sum;
		}
		
		for (int i=1;i<colors.length;i++)
			probabilities[i]/=sum;
		
		double randomValue=MzLib.rand.nextDouble();
		for (int i=1;i<colors.length; i++)
		{
			if (randomValue<probabilities[i])
				return (byte)i;
		}
		
		return (byte) index;
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
