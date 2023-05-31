package mz.lib;

import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class RGB
{
	int r,g,b;
	public RGB(int r,int g,int b)
	{
		this.r=r;
		this.g=g;
		this.b=b;
	}
	public RGB(Color color)
	{
		this(color.getRed(),color.getGreen(),color.getBlue());
	}
	public RGB(int color)
	{
		this(new Color(color));
	}
	
	public static RGB zero()
	{
		return new RGB(0,0,0);
	}
	public RGB add(RGB rgb)
	{
		return new RGB(r+rgb.r,g+rgb.g,b+rgb.b);
	}
	public RGB subtract(RGB rgb)
	{
		return new RGB(r-rgb.r,g-rgb.g,b-rgb.b);
	}
	public RGB multiply(double num)
	{
		return new RGB((int)(r*num),(int)(g*num),(int)(b*num));
	}
	public RGB divide(double num)
	{
		return new RGB((int)(r/num),(int)(g/num),(int)(b/num));
	}
	public Color toColor()
	{
		return new Color(max(0,min(255,r)),max(0,min(255,g)),max(0,min(255,b)));
	}
}
