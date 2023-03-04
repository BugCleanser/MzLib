package mz.lib.minecraft.message.clickevent;

/**
 * 设置点击消息的玩家的聊天栏
 */
public class ClickToSetChat extends ClickEvent
{
	/**
	 * 设置的聊天栏
	 */
	public String chat;
	
	/**
	 * @param chat 设置的聊天栏
	 */
	public ClickToSetChat(String chat)
	{
		this.chat=chat;
	}
	
	@Override
	public String getAction()
	{
		return "suggest_command";
	}
	
	@Override
	public String getValue()
	{
		return chat;
	}
}
