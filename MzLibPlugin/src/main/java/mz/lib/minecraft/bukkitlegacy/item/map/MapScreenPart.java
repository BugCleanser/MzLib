package mz.lib.minecraft.bukkitlegacy.item.map;

import mz.lib.minecraft.bukkit.nms.NmsMapIcon;
import mz.lib.minecraft.bukkit.nms.NmsNBTTagCompound;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public interface MapScreenPart extends MzMap
{
	MapScreen getScreen();
	default NmsNBTTagCompound mapScreenPartTag()
	{
		if(!mzTag().containsKey("MapScreenPart"))
			mzTag().set("MapScreenPart",NmsNBTTagCompound.newInstance());
		return mzTag().getCompound("MapScreenPart");
	}
	default int getScreenPartX()
	{
		return mapScreenPartTag().getInt("x");
	}
	default void setScreenPartX(int x)
	{
		mapScreenPartTag().set("x",x);
	}
	default int getScreenPartY()
	{
		return mapScreenPartTag().getInt("y");
	}
	default void setScreenPartY(int y)
	{
		mapScreenPartTag().set("y",y);
	}
	
	@Override
	default List<NmsMapIcon> getIcons(Player player)
	{
		int x=getScreenPartX()*128;
		int y=getScreenPartY()*128;
		return getScreen().icons.stream().filter(i->i.x>=x&&i.x<x+128&&i.y>=y&&i.y<y+128).map(i->NmsMapIcon.newInstance().setType(i.type).setRotation(i.rotation).setX((byte)(i.x-x-128)).setY((byte)(i.y-y-128))).collect(Collectors.toList());
	}
	
	@Override
	default void onRedraw(Player player,MzMapCanvas canvas,int x,int y,int width,int height)
	{
		MzMapCanvas c=getScreen().canvas;
		int px=getScreenPartX()*128;
		int py=getScreenPartY()*128;
		for(int i=0;i!=width;i++)
		{
			for(int j=0;j!=height;j++)
			{
				canvas.setPixel0(x+i,y+j,c.getPixel0(px+x+i,py+y+j));
			}
		}
	}
}
