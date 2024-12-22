package mz.mzlib.demo;

import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.ui.book.UIWrittenBook;
import mz.mzlib.module.MzModule;

import java.util.Arrays;
import java.util.List;

public class DemoBookUi extends MzModule
{
    public static final DemoBookUi instance = new DemoBookUi();
    
    public Command command;
    
    @Override
    public void onLoad()
    {
        Demo.instance.command.addChild(this.command=new Command("book").setPermissionChecker(Command::checkPermissionSenderPlayer).setHandler(context->
        {
            if(!context.successful || !context.doExecute)
                return;
            UIStack.get(context.sender.castTo(EntityPlayer::create)).start(new UIDemoBook());
        }));
    }
    
    public static class UIDemoBook implements UIWrittenBook
    {
        @Override
        public List<Text> getPages(EntityPlayer player)
        {
            return Arrays.asList(Text.literal("Hello World"), Text.literal("awa"));
        }
    }
}
