package mz.lib.minecraft.message.clickevent;

/**
 * 点击消息的玩家自动发送消息
 */
public class ClickToSay extends ClickEvent
{
	/**
	 * 消息
	 */
	public String text;
	
	/**
	 * @param text 消息
	 */
	public ClickToSay(String text)
	{
		this.text=text;
	}
	
	@Override
	/**
	 * @return run_command
	 */ public String getAction()
	{
		return "run_command";
	}
	
	@Override
	/**
	 * @return text
	 */ public String getValue()
	{
		return text;
	}
}
