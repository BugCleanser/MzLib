package mz.lib.minecraft.bukkitlegacy.item.map;

import org.bukkit.map.MapCursor;

import java.util.List;

public abstract class MapScreen
{
	public static class MapScreenIcon
	{
		MapCursor.Type type;
		int x;
		int y;
		byte rotation;
		public MapScreenIcon(MapCursor.Type type,int x,int y,byte rotation)
		{
			this.type=type;
			this.x=x;
			this.y=y;
			this.rotation=rotation;
		}
	}
	
	public MapScreenPart[][] parts;
	public MzMapCanvas lastCanvas;
	public MzMapCanvas canvas;
	public List<MapScreenIcon> icons;
	
	public MapScreen(int xMaps,int yMaps)
	{
		parts=new MapScreenPart[xMaps][yMaps];
		canvas=new MzMapCanvas(xMaps*128,yMaps*128);
		lastCanvas=canvas.clone();
		for(int i=0;i!=xMaps;i++)
		{
			for(int j=0;j!=yMaps;j++)
			{
				parts[i][j]=createPart(i,j);
			}
		}
	}
	
	public abstract MapScreenPart createPart(int x,int y);
	
	public void refresh()
	{
		for(int i=0;i!=parts.length;i++)
		{
			for(int j=0;j!=parts[i].length;j++)
			{
				int xb=0,yb=0,xe=0,ye=0;
				for(int k=0;k!=128;k++)
				{
					for(int l=0;l!=128;l++)
					{
						if(lastCanvas.getPixel0(i*128+k,j*128+l)!=canvas.getPixel0(i*128+k,j*128+l))
						{
							if(xe==0)
							{
								xb=k;
								yb=l;
								xe=k+1;
								ye=l+1;
							}
							else
							{
								if(k<xb)
									xb=k;
								if(l<yb)
									yb=l;
								if(k>=xe)
									xe=k+1;
								if(l>=ye)
									ye=l+1;
							}
						}
					}
				}
				parts[i][j].redraw(xb,yb,xe-xb,ye-yb);
			}
		}
		lastCanvas=canvas.clone();
	}
}
