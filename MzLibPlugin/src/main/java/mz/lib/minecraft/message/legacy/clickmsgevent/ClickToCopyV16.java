package mz.lib.minecraft.message.legacy.clickmsgevent;

public class ClickToCopyV16 extends ClickMsgEvent
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
