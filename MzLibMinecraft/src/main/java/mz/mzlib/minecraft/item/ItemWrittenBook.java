package mz.mzlib.minecraft.item;


import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.component.ComponentKeyV2005;
import mz.mzlib.minecraft.item.component.ComponentWrittenBookContentV2005;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.ArrayList;
import java.util.List;

@WrapMinecraftClass(@VersionName(name="net.minecraft.item.WrittenBookItem"))
public interface ItemWrittenBook extends Item
{
    @WrapperCreator
    static ItemWrittenBook create(Object wrapped)
    {
        return WrapperObject.create(ItemWrittenBook.class, wrapped);
    }
    
    int MAX_PAGE_LINES=14;
    
    static List<Text> makePages(List<Text> lines)
    {
        List<Text> result=new ArrayList<>();
        for(Text i: lines)
        {
            if(result.isEmpty() || result.get(result.size()-1).getExtra().size()==MAX_PAGE_LINES)
                result.add(Text.literal("").addExtra());
            result.get(result.size()-1).addExtra(i);
        }
        return result;
    }
    
    ComponentKeyV2005 componentKeyWrittenBookContentV2005 = MinecraftPlatform.instance.getVersion()<2005?null:ComponentKeyV2005.fromId("written_book_content");
    
    static void setPages(ItemStack book, List<Text> pages)
    {
        create(null).staticSetPages(book, pages);
    }
    void staticSetPages(ItemStack book, List<Text> pages);
    @SpecificImpl("staticSetPages")
    @VersionRange(end=2005)
    default void staticSetPagesV_2005(ItemStack book, List<Text> pages)
    {
        throw new UnsupportedOperationException(); // TODO
    }
    @SpecificImpl("staticSetPages")
    @VersionRange(begin=2005)
    default void staticSetPagesV2005(ItemStack book, List<Text> pages)
    {
        ComponentWrittenBookContentV2005 last = book.getComponentsV2005().get(componentKeyWrittenBookContentV2005, ComponentWrittenBookContentV2005::create);
        if(!last.isPresent())
            last=ComponentWrittenBookContentV2005.empty();
        book.setComponentV2005(componentKeyWrittenBookContentV2005, last.withPagesReplaced(pages));
    }
}
