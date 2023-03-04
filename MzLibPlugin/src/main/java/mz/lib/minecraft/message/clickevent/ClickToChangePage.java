package mz.lib.minecraft.message.clickevent;

public class ClickToChangePage extends ClickEvent
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
