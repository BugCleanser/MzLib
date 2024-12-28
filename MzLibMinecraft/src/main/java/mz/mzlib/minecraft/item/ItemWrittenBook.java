package mz.mzlib.minecraft.item;


import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.component.ComponentKeyV2005;
import mz.mzlib.minecraft.item.component.ComponentWrittenBookContentV2005;
import mz.mzlib.minecraft.nbt.*;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WrapMinecraftClass(@VersionName(name="net.minecraft.item.WrittenBookItem"))
public interface ItemWrittenBook extends Item
{
    @WrapperCreator
    static ItemWrittenBook create(Object wrapped)
    {
        return WrapperObject.create(ItemWrittenBook.class, wrapped);
    }
    
    int MAX_PAGE_LINES = 14;
    
    ComponentKeyV2005 componentKeyWrittenBookContentV2005 = MinecraftPlatform.instance.getVersion()<2005 ? null : ComponentKeyV2005.fromId("written_book_content");
    
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
        return book.tagV_2005().getString("title");
    }
    
    @SpecificImpl("staticGetTitle")
    @VersionRange(begin=2005)
    default String staticGetTitleV2005(ItemStack book)
    {
        ComponentWrittenBookContentV2005 component = book.getComponentsV2005().get(componentKeyWrittenBookContentV2005, ComponentWrittenBookContentV2005::create);
        if(!component.isPresent())
            component = ComponentWrittenBookContentV2005.def();
        return component.getTitle();
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
        ComponentWrittenBookContentV2005 component = book.getComponentsV2005().get(componentKeyWrittenBookContentV2005, ComponentWrittenBookContentV2005::create);
        if(!component.isPresent())
            component = ComponentWrittenBookContentV2005.def();
        book.setComponentV2005(componentKeyWrittenBookContentV2005, component.withTitle(title));
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
        return book.tagV_2005().getString("author");
    }
    
    @SpecificImpl("staticGetAuthor")
    @VersionRange(begin=2005)
    default String staticGetAuthorV2005(ItemStack book)
    {
        ComponentWrittenBookContentV2005 component = book.getComponentsV2005().get(componentKeyWrittenBookContentV2005, ComponentWrittenBookContentV2005::create);
        if(!component.isPresent())
            component = ComponentWrittenBookContentV2005.def();
        return component.getAuthor();
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
        ComponentWrittenBookContentV2005 component = book.getComponentsV2005().get(componentKeyWrittenBookContentV2005, ComponentWrittenBookContentV2005::create);
        if(!component.isPresent())
            component = ComponentWrittenBookContentV2005.def();
        book.setComponentV2005(componentKeyWrittenBookContentV2005, component.withAuthor(author));
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
        return book.tagV_2005().getInt("generation");
    }
    
    @SpecificImpl("staticGetGeneration")
    @VersionRange(begin=2005)
    default int staticGetGenerationV2005(ItemStack book)
    {
        ComponentWrittenBookContentV2005 component = book.getComponentsV2005().get(componentKeyWrittenBookContentV2005, ComponentWrittenBookContentV2005::create);
        if(!component.isPresent())
            component = ComponentWrittenBookContentV2005.def();
        return component.getGeneration();
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
        ComponentWrittenBookContentV2005 component = book.getComponentsV2005().get(componentKeyWrittenBookContentV2005, ComponentWrittenBookContentV2005::create);
        if(!component.isPresent())
            component = ComponentWrittenBookContentV2005.def();
        book.setComponentV2005(componentKeyWrittenBookContentV2005, component.withGeneration(generation));
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
        return book.tagV_2005().getNBTList("pages").asList().stream().map(nbt->Text.decode(nbt.castTo(NbtString::create).getValue())).collect(Collectors.toList());
    }
    
    @SpecificImpl("staticGetPages")
    @VersionRange(begin=2005)
    default List<Text> staticGetPagesV2005(ItemStack book)
    {
        ComponentWrittenBookContentV2005 component = book.getComponentsV2005().get(componentKeyWrittenBookContentV2005, ComponentWrittenBookContentV2005::create);
        if(!component.isPresent())
            component = ComponentWrittenBookContentV2005.def();
        return component.getPages();
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
        book.tagV_2005().put("pages", NbtList.newInstance(pages.stream().map(page->NbtString.newInstance(page.encode().toString())).toArray(NbtElement[]::new)));
    }
    
    @SpecificImpl("staticSetPages")
    @VersionRange(begin=2005)
    default void staticSetPagesV2005(ItemStack book, List<Text> pages)
    {
        ComponentWrittenBookContentV2005 component = book.getComponentsV2005().get(componentKeyWrittenBookContentV2005, ComponentWrittenBookContentV2005::create);
        if(!component.isPresent())
            component = ComponentWrittenBookContentV2005.def();
        book.setComponentV2005(componentKeyWrittenBookContentV2005, component.withPages(pages));
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
        return book.tagV_2005().getBoolean("resolved");
    }
    
    @SpecificImpl("staticIsResolved")
    @VersionRange(begin=2005)
    default boolean staticIsResolvedV2005(ItemStack book)
    {
        ComponentWrittenBookContentV2005 component = book.getComponentsV2005().get(componentKeyWrittenBookContentV2005, ComponentWrittenBookContentV2005::create);
        if(!component.isPresent())
            component = ComponentWrittenBookContentV2005.def();
        return component.isResolved();
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
        ComponentWrittenBookContentV2005 component = book.getComponentsV2005().get(componentKeyWrittenBookContentV2005, ComponentWrittenBookContentV2005::create);
        if(!component.isPresent())
            component = ComponentWrittenBookContentV2005.def();
        book.setComponentV2005(componentKeyWrittenBookContentV2005, component.withResolved(resolved));
    }
}
