package mz.mzlib.minecraft.ui.book;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.item.ItemStackBuilder;
import mz.mzlib.minecraft.item.ItemWrittenBook;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UI;

import java.util.List;

public interface UIWrittenBook extends UI
{
    List<Text> getPages(EntityPlayer player);
    
    @Override
    default void open(EntityPlayer player)
    {
        ItemStack book=new ItemStackBuilder("written_book").build();
        ItemWrittenBook.setPages(book, getPages(player));
        player.openBook(book);
    }
    
    @Override
    default void close(EntityPlayer player)
    {
        // TODO ?
    }
}
