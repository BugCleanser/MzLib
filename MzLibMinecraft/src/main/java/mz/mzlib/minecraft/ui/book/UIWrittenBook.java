package mz.mzlib.minecraft.ui.book;

import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.command.argument.ArgumentParserInt;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.item.ItemWrittenBook;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.text.TextClickEvent;
import mz.mzlib.minecraft.ui.UI;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.module.MzModule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class UIWrittenBook implements UI
{
    public abstract List<Text> getPages(EntityPlayer player);

    public List<Consumer<EntityPlayer>> buttons = new ArrayList<>();

    public int newButton(Consumer<EntityPlayer> handle)
    {
        this.buttons.add(handle);
        return this.buttons.size() - 1;
    }

    public static TextClickEvent buttonClickEvent(int button)
    {
        return TextClickEvent.runCommand(
            "/" + MzLibMinecraft.instance.command.name + " " + Module.instance.command.name + " " + button);
    }

    public void clear()
    {
        this.buttons.clear();
    }

    @Override
    public void open(EntityPlayer player)
    {
        player.openBook(ItemStack.factory()
            .fromId("written_book")
            .data(ItemWrittenBook.TITLE, "UIWrittenBook")
            .data(ItemWrittenBook.AUTHOR, "UIWrittenBook")
            .data(ItemWrittenBook.PAGES, this.getPages(player))
            .data(ItemWrittenBook.RESOLVED, true)
            .build());
    }

    @Deprecated
    @Override
    public void onPlayerClose(EntityPlayer player)
    {
    }

    public static class Module extends MzModule
    {
        public static Module instance = new Module();

        public Command command;

        @Override
        public void onLoad()
        {
            MzLibMinecraft.instance.command.addChild(
                this.command = new Command("book_click").setPermissionCheckers(
                    Command::checkPermissionSenderPlayer,
                    source -> UIStack.get(source.getPlayer().unwrap()).top() instanceof UIWrittenBook ?
                        null :
                        MinecraftI18n.resolveText(source, "mzlib.commands.mzlib.book_click.error.not_opening")
                ).setHandler(context ->
                {
                    Integer button = new ArgumentParserInt("button").handle(context);
                    if(context.argsReader.hasNext())
                        context.successful = false;
                    if(!context.successful || !context.doExecute)
                        return;
                    EntityPlayer sender = context.getSource().getPlayer().unwrap();
                    List<Consumer<EntityPlayer>> buttons = ((UIWrittenBook) UIStack.get(sender).top()).buttons;
                    if(button < 0 || button >= buttons.size())
                    {
                        context.successful = false;
                        context.getSource().sendMessage(MinecraftI18n.resolveText(
                            context.getSource(),
                            "mzlib.commands.mzlib.book_click.error.invalid_button_index"
                        ));
                        UIStack.get(sender).top().open(sender);
                        return;
                    }
                    buttons.get(button).accept(sender);
                }));
        }

        @Override
        public void onUnload()
        {
            MzLibMinecraft.instance.command.removeChild(this.command);
        }
    }
}
