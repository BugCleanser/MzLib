package mz.mzlib.minecraft.item;


import com.google.gson.Gson;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.component.ComponentKeyV2005;
import mz.mzlib.minecraft.component.type.WrittenBookContentComponentV2005;
import mz.mzlib.minecraft.nbt.*;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.Option;
import mz.mzlib.util.Ref;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WrapMinecraftClass(@VersionName(name="net.minecraft.item.WrittenBookItem"))
public interface ItemWrittenBook extends Item
{
    WrapperFactory<ItemWrittenBook> FACTORY = WrapperFactory.find(ItemWrittenBook.class);
    @Deprecated
    @WrapperCreator
    static ItemWrittenBook create(Object wrapped)
    {
        return WrapperObject.create(ItemWrittenBook.class, wrapped);
    }
    
    int MAX_PAGE_LINES = 14;
    
    ComponentKeyV2005.Specialized<WrittenBookContentComponentV2005> COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005 = MinecraftPlatform.instance.getVersion()<2005 ? null : ComponentKeyV2005.fromId("written_book_content").specialized(WrittenBookContentComponentV2005.FACTORY);
    
    static List<Text> makePages(List<Text> lines)
    {
        List<Text> result = new ArrayList<>();
        for(Text i: lines)
        {
            if(result.isEmpty() || result.get(result.size()-1).getExtra().size()==MAX_PAGE_LINES)
                result.add(Text.literal("").addExtra());
            result.get(result.size()-1).addExtra(i);
        }
        return result;
    }
    
    static String getTitle(ItemStack book)
    {
        return create(null).staticGetTitle(book);
    }
    
    String staticGetTitle(ItemStack book);
    
    @SpecificImpl("staticGetTitle")
    @VersionRange(end=2005)
    default String staticGetTitleV_2005(ItemStack book)
    {
        for(String s: book.tagV_2005().getString("title"))
            return s;
        return "";
    }
    
    @SpecificImpl("staticGetTitle")
    @VersionRange(begin=2005)
    default String staticGetTitleV2005(ItemStack book)
    {
        return book.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005).unwrapOrGet(WrittenBookContentComponentV2005::def).getTitle();
    }
    
    static void setTitle(ItemStack book, String title)
    {
        create(null).staticSetTitle(book, title);
    }
    
    void staticSetTitle(ItemStack book, String title);
    
    @SpecificImpl("staticSetTitle")
    @VersionRange(end=2005)
    default void staticSetTitleV_2005(ItemStack book, String title)
    {
        book.tagV_2005().put("title", NbtString.newInstance(title));
    }
    
    @SpecificImpl("staticSetTitle")
    @VersionRange(begin=2005)
    default void staticSetTitleV2005(ItemStack book, String title)
    {
        for(Ref<Option<WrittenBookContentComponentV2005>> ref: book.getComponentsV2005().edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
            ref.set(Option.some(ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def).withTitle(title)));
    }
    
    static String getAuthor(ItemStack book)
    {
        return create(null).staticGetAuthor(book);
    }
    
    String staticGetAuthor(ItemStack book);
    
    @SpecificImpl("staticGetAuthor")
    @VersionRange(end=2005)
    default String staticGetAuthorV_2005(ItemStack book)
    {
        for(String author: book.tagV_2005().getString("author"))
            return author;
        return "";
    }
    
    @SpecificImpl("staticGetAuthor")
    @VersionRange(begin=2005)
    default String staticGetAuthorV2005(ItemStack book)
    {
        return book.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005).unwrapOrGet(WrittenBookContentComponentV2005::def).getAuthor();
    }
    
    static void setAuthor(ItemStack book, String author)
    {
        create(null).staticSetAuthor(book, author);
    }
    
    void staticSetAuthor(ItemStack book, String author);
    
    @SpecificImpl("staticSetAuthor")
    @VersionRange(end=2005)
    default void staticSetAuthorV_2005(ItemStack book, String author)
    {
        book.tagV_2005().put("author", NbtString.newInstance(author));
    }
    
    @SpecificImpl("staticSetAuthor")
    @VersionRange(begin=2005)
    default void staticSetAuthorV2005(ItemStack book, String author)
    {
        for(Ref<Option<WrittenBookContentComponentV2005>> ref: book.getComponentsV2005().edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
            ref.set(Option.some(ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def).withAuthor(author)));
    }
    
    static int getGeneration(ItemStack book)
    {
        return create(null).staticGetGeneration(book);
    }
    
    int staticGetGeneration(ItemStack book);
    
    @SpecificImpl("staticGetGeneration")
    @VersionRange(end=2005)
    default int staticGetGenerationV_2005(ItemStack book)
    {
        for(Integer generation: book.tagV_2005().getInt("generation"))
            return generation;
        return 0;
    }
    
    @SpecificImpl("staticGetGeneration")
    @VersionRange(begin=2005)
    default int staticGetGenerationV2005(ItemStack book)
    {
        return book.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005).unwrapOrGet(WrittenBookContentComponentV2005::def).getGeneration();
    }
    
    static void setGeneration(ItemStack book, int generation)
    {
        create(null).staticSetGeneration(book, generation);
    }
    
    void staticSetGeneration(ItemStack book, int generation);
    
    @SpecificImpl("staticSetGeneration")
    @VersionRange(end=2005)
    default void staticSetGenerationV_2005(ItemStack book, int generation)
    {
        book.tagV_2005().put("generation", NbtInt.newInstance(generation));
    }
    
    @SpecificImpl("staticSetGeneration")
    @VersionRange(begin=2005)
    default void staticSetGenerationV2005(ItemStack book, int generation)
    {
        for(Ref<Option<WrittenBookContentComponentV2005>> ref: book.getComponentsV2005().edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
            ref.set(Option.some(ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def).withGeneration(generation)));
    }
    
    static List<Text> getPages(ItemStack book)
    {
        return create(null).staticGetPages(book);
    }
    
    List<Text> staticGetPages(ItemStack book);
    
    @SpecificImpl("staticGetPages")
    @VersionRange(end=2005)
    default List<Text> staticGetPagesV_2005(ItemStack book)
    {
        return book.tagV_2005().getOrPut("pages", NbtList.FACTORY, NbtList::newInstance).asList().stream().map(nbt->Text.decode(nbt.castTo(NbtString.FACTORY).getValue())).collect(Collectors.toList());
    }
    
    @SpecificImpl("staticGetPages")
    @VersionRange(begin=2005)
    default List<Text> staticGetPagesV2005(ItemStack book)
    {
        return book.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005).unwrapOrGet(WrittenBookContentComponentV2005::def).getPages();
    }
    
    static void setPages(ItemStack book, List<Text> pages)
    {
        create(null).staticSetPages(book, pages);
    }
    
    void staticSetPages(ItemStack book, List<Text> pages);
    
    @SpecificImpl("staticSetPages")
    @VersionRange(end=2005)
    default void staticSetPagesV_2005(ItemStack book, List<Text> pages)
    {
        book.tagV_2005().put("pages", NbtList.newInstance(pages.stream().map(page->NbtString.newInstance(new Gson().toJson(page.encode()))).toArray(NbtElement[]::new)));
    }
    
    @SpecificImpl("staticSetPages")
    @VersionRange(begin=2005)
    default void staticSetPagesV2005(ItemStack book, List<Text> pages)
    {
        for(Ref<Option<WrittenBookContentComponentV2005>> ref: book.getComponentsV2005().edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
            ref.set(Option.some(ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def).withPages(pages)));
    }
    
    static boolean isResolved(ItemStack book)
    {
        return create(null).staticIsResolved(book);
    }
    
    boolean staticIsResolved(ItemStack book);
    
    @SpecificImpl("staticIsResolved")
    @VersionRange(end=2005)
    default boolean staticIsResolvedV_2005(ItemStack book)
    {
        for(boolean resolved: book.tagV_2005().getBoolean("resolved"))
            return resolved;
        return false;
    }
    
    @SpecificImpl("staticIsResolved")
    @VersionRange(begin=2005)
    default boolean staticIsResolvedV2005(ItemStack book)
    {
        return book.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005).unwrapOrGet(WrittenBookContentComponentV2005::def).isResolved();
    }
    
    static void setResolved(ItemStack book, boolean resolved)
    {
        create(null).staticSetResolved(book, resolved);
    }
    
    void staticSetResolved(ItemStack book, boolean resolved);
    
    @SpecificImpl("staticSetResolved")
    @VersionRange(end=2005)
    default void staticSetResolvedV_2005(ItemStack book, boolean resolved)
    {
        book.tagV_2005().put("resolved", NbtByte.newInstance(resolved));
    }
    
    @SpecificImpl("staticSetResolved")
    @VersionRange(begin=2005)
    default void staticSetResolvedV2005(ItemStack book, boolean resolved)
    {
        for(Ref<Option<WrittenBookContentComponentV2005>> ref: book.getComponentsV2005().edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
            ref.set(Option.some(ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def).withResolved(resolved)));
    }
}
