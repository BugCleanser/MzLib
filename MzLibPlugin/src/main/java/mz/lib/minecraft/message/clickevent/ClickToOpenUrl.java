package mz.lib.minecraft.message.clickevent;

/**
 * 左键消息将会打开url
 */
public class ClickToOpenUrl extends ClickEvent
{
	/**
	 * 打开的url
	 */
	public String url;
	
	/**
	 * @param url 将会打开的url，如"http://www.baidu.com"
	 */
	public ClickToOpenUrl(String url)
	{
		this.url=url;
	}
	
	@Override
	public String getAction()
	{
		return "open_url";
	}
	
	@Override
	public String getValue()
	{
		return url;
	}
}
