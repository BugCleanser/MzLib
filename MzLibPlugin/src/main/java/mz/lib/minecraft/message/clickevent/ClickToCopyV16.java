package mz.lib.minecraft.message.clickevent;

public class ClickToCopyV16 extends ClickEvent
{
	public String text;
	
	public ClickToCopyV16(String text)
	{
		this.text=text;
	}
	
	@Override
	public String getAction()
	{
		return "copy_to_clipboard";
	}
	
	@Override
	public String getValue()
	{
		return text;
	}
}
