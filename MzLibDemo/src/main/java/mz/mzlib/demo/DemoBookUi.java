package mz.mzlib.demo;

import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.item.ItemWrittenBook;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.text.TextColor;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.ui.book.UIWrittenBook;
import mz.mzlib.module.MzModule;

import java.util.ArrayList;
import java.util.List;

public class DemoBookUi extends MzModule
{
    public static final DemoBookUi instance = new DemoBookUi();

    public Command command;

    @Override
    public void onLoad()
    {
        Demo.instance.command.addChild(
            this.command = new Command("book").setPermissionChecker(Command::checkPermissionSenderPlayer)
                .setHandler(context ->
                {
                    if(context.argsReader.hasNext())
                        return;
                    if(!context.successful || !context.doExecute)
                        return;
                    UIStack.get(context.getSource().getPlayer().unwrap()).start(new UIDemoBook());
                }));
    }

    public static class UIDemoBook extends UIWrittenBook
    {
        int button0, button1;

        public UIDemoBook()
        {
            this.button0 = this.newButton(
                player -> player.sendMessage(Text.literal("Button 0").setBold(true).setColor(TextColor.DARK_BLUE)));
            this.button1 = this.newButton(player -> player.sendMessage(Text.literal("Button 1")));
        }

        @Override
        public List<Text> getPages(EntityPlayer player)
        {
            List<Text> lines = new ArrayList<>();
            lines.add(Text.literal("awa\n").setBold(true).setClickEvent(buttonClickEvent(button0)));
            lines.add(Text.literal("qwq\n").setClickEvent(buttonClickEvent(button1)));
            return ItemWrittenBook.makePages(lines);
        }
    }
}
