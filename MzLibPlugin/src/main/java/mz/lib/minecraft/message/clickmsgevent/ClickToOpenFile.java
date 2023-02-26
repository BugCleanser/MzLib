package mz.lib.minecraft.message.clickmsgevent;

@Deprecated
public class ClickToOpenFile extends ClickMsgEvent
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
