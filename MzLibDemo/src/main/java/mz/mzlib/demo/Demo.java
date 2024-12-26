package mz.mzlib.demo;

import mz.mzlib.demo.games.ticactoe.Tictactoe;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.ui.window.UIWindowAnvilInput;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;

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
            UIWindowAnvilInput.invoke(context.sender.castTo(EntityPlayer::create), "test", Text.literal("§2一个输入框")).whenComplete((text, e) ->
            {
                if(e!=null)
                    throw RuntimeUtil.sneakilyThrow(e);
                UIStack.get(context.sender.castTo(EntityPlayer::create)).back();
                context.sender.sendMessage(Text.literal("§a你输入了: §r"+text));
            });
        }));
        
        this.register(Tictactoe.instance);
        this.register(DemoBookUi.instance);
        this.register(ExampleAsyncFunction.instance);
    }
}
