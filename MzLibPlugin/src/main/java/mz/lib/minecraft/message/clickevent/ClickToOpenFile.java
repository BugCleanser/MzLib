package mz.lib.minecraft.message.clickevent;

@Deprecated
public class ClickToOpenFile extends ClickEvent
{
	String fileName;
	public ClickToOpenFile(String fileName)
	{
		this.fileName=fileName;
	}
	
	@Override
	public String getAction()
	{
		return "open_file";
	}
	
	@Override
	public String getValue()
	{
		return fileName;
	}
}
