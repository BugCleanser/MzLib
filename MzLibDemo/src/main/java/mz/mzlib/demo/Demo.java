package mz.mzlib.demo;

import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.item.ItemStackBuilder;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.ui.window.UIWindowAnvil;
import mz.mzlib.module.MzModule;

public class Demo extends MzModule
{
    public static Demo instance = new Demo();
    
    public Permission permission=new Permission("mzlibdemo.command.mzlibdemo");
    public Command command;
    
    @Override
    public void onLoad()
    {
        this.register(this.permission);
        this.register(this.command =new Command("mzlibDemo".toLowerCase()).setNamespace("mzlibDemo".toLowerCase()).setPermissionChecker(sender->Command.checkPermission(sender, this.permission)).setHandler(context ->
        {
            if(!context.successful || !context.doExecute)
                return;
            if(!context.sender.isInstanceOf(EntityPlayer::create))
                return;
            UIStack.get(context.sender.castTo(EntityPlayer::create)).start(new UIWindowAnvil()
            {
                {
                    this.inventory.setItemStack(0, new ItemStackBuilder("name_tag").setDisplayName(Text.literal("")).build());
                }
                @Override
                public Text getTitle(EntityPlayer player)
                {
                    return Text.literal("§2我是一个输入框");
                }
                
                @Override
                public void onTextChange(EntityPlayer player, String text)
                {
                    player.sendMessage(Text.literal(text));
                }
            });
        }));
        
        this.register(Tictactoe.instance);
        this.register(DemoBookUi.instance);
        this.register(ExampleAsyncFunction.instance);
    }
}
