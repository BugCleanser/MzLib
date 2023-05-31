package mz.lib.minecraft.message.clickmsgevent;

public class ClickToChangePage extends ClickMsgEvent
{
	int page;
	
	public ClickToChangePage(int page)
	{
		this.page=page;
	}
	
	@Override
	public String getAction()
	{
		return "change_page";
	}
	@Override
	public String getValue()
	{
		return page+"";
	}
}
