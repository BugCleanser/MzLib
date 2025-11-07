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

@WrapMinecraftClass(@VersionName(name = "net.minecraft.item.WrittenBookItem"))
public interface ItemWrittenBook extends Item
{
    WrapperFactory<ItemWrittenBook> FACTORY = WrapperFactory.of(ItemWrittenBook.class);
    @Deprecated
    @WrapperCreator
    static ItemWrittenBook create(Object wrapped)
    {
        return WrapperObject.create(ItemWrittenBook.class, wrapped);
    }

    int MAX_PAGE_LINES = 14;

    ComponentKeyV2005.Wrapper<WrittenBookContentComponentV2005> COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005 =
        MinecraftPlatform.instance.getVersion() < 2005 ?
            null :
            ComponentKeyV2005.fromId("written_book_content", WrittenBookContentComponentV2005.FACTORY);

    static List<Text> makePages(List<Text> lines)
    {
        List<Text> result = new ArrayList<>();
        for(Text i : lines)
        {
            if(result.isEmpty() || result.get(result.size() - 1).getExtra().size() == MAX_PAGE_LINES)
                result.add(Text.literal("").addExtra());
            result.get(result.size() - 1).addExtra(i);
        }
        return result;
    }

    static String getTitle(ItemStack book)
    {
        return FACTORY.getStatic().static$getTitle(book);
    }
    String static$getTitle(ItemStack book);
    @SpecificImpl("static$getTitle")
    @VersionRange(end = 2005)
    default String static$getTitleV_2005(ItemStack book)
    {
        for(String s : book.tagV_2005().getString("title"))
        {
            return s;
        }
        return "";
    }
    @SpecificImpl("static$getTitle")
    @VersionRange(begin = 2005)
    default String static$getTitleV2005(ItemStack book)
    {
        return book.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005)
            .unwrapOrGet(WrittenBookContentComponentV2005::def).getTitle();
    }

    static void setTitle(ItemStack book, String title)
    {
        FACTORY.getStatic().static$setTitle(book, title);
    }
    void static$setTitle(ItemStack book, String title);
    @SpecificImpl("static$setTitle")
    @VersionRange(end = 2005)
    default void static$setTitleV_2005(ItemStack book, String title)
    {
        for(NbtCompound tag : Item.reviseCustomData(book))
        {
            tag.put("title", NbtString.newInstance(title));
        }
    }
    @SpecificImpl("static$setTitle")
    @VersionRange(begin = 2005)
    default void static$setTitleV2005(ItemStack book, String title)
    {
        for(Ref<Option<WrittenBookContentComponentV2005>> ref : book.getComponentsV2005()
            .edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
        {
            ref.set(Option.some(ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def).withTitle(title)));
        }
    }

    static String getAuthor(ItemStack book)
    {
        return FACTORY.getStatic().static$getAuthor(book);
    }
    String static$getAuthor(ItemStack book);
    @SpecificImpl("static$getAuthor")
    @VersionRange(end = 2005)
    default String static$getAuthorV_2005(ItemStack book)
    {
        for(String author : book.tagV_2005().getString("author"))
        {
            return author;
        }
        return "";
    }
    @SpecificImpl("static$getAuthor")
    @VersionRange(begin = 2005)
    default String static$getAuthorV2005(ItemStack book)
    {
        return book.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005)
            .unwrapOrGet(WrittenBookContentComponentV2005::def).getAuthor();
    }

    static void setAuthor(ItemStack book, String author)
    {
        FACTORY.getStatic().static$setAuthor(book, author);
    }
    void static$setAuthor(ItemStack book, String author);
    @SpecificImpl("static$setAuthor")
    @VersionRange(end = 2005)
    default void static$setAuthorV_2005(ItemStack book, String author)
    {
        for(NbtCompound tag : Item.reviseCustomData(book))
        {
            tag.put("author", NbtString.newInstance(author));
        }
    }
    @SpecificImpl("static$setAuthor")
    @VersionRange(begin = 2005)
    default void static$setAuthorV2005(ItemStack book, String author)
    {
        for(Ref<Option<WrittenBookContentComponentV2005>> ref : book.getComponentsV2005()
            .edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
        {
            ref.set(Option.some(ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def).withAuthor(author)));
        }
    }

    static int getGeneration(ItemStack book)
    {
        return FACTORY.getStatic().static$getGeneration(book);
    }
    int static$getGeneration(ItemStack book);
    @SpecificImpl("static$getGeneration")
    @VersionRange(end = 2005)
    default int static$getGenerationV_2005(ItemStack book)
    {
        for(Integer generation : book.tagV_2005().getInt("generation"))
        {
            return generation;
        }
        return 0;
    }
    @SpecificImpl("static$getGeneration")
    @VersionRange(begin = 2005)
    default int static$getGenerationV2005(ItemStack book)
    {
        return book.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005)
            .unwrapOrGet(WrittenBookContentComponentV2005::def).getGeneration();
    }

    static void setGeneration(ItemStack book, int generation)
    {
        FACTORY.getStatic().static$setGeneration(book, generation);
    }
    void static$setGeneration(ItemStack book, int generation);
    @SpecificImpl("static$setGeneration")
    @VersionRange(end = 2005)
    default void static$setGenerationV_2005(ItemStack book, int generation)
    {
        for(NbtCompound tag : Item.reviseCustomData(book))
        {
            tag.put("generation", NbtInt.newInstance(generation));
        }
    }
    @SpecificImpl("static$setGeneration")
    @VersionRange(begin = 2005)
    default void static$setGenerationV2005(ItemStack book, int generation)
    {
        for(Ref<Option<WrittenBookContentComponentV2005>> ref : book.getComponentsV2005()
            .edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
        {
            ref.set(
                Option.some(ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def).withGeneration(generation)));
        }
    }

    static List<Text> getPages(ItemStack book)
    {
        return FACTORY.getStatic().static$getPages(book);
    }
    List<Text> static$getPages(ItemStack book);

    @SpecificImpl("static$getPages")
    @VersionRange(end = 2005)
    default List<Text> static$getPagesV_2005(ItemStack book)
    {
        return book.tagV_2005().getOr("pages", NbtList.FACTORY, NbtList::newInstance).asList().stream()
            .map(nbt -> Text.decode(nbt.castTo(NbtString.FACTORY).getValue())).collect(Collectors.toList());
    }
    @SpecificImpl("static$getPages")
    @VersionRange(begin = 2005)
    default List<Text> static$getPagesV2005(ItemStack book)
    {
        return book.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005)
            .unwrapOrGet(WrittenBookContentComponentV2005::def).getPages();
    }

    static void setPages(ItemStack book, List<Text> pages)
    {
        FACTORY.getStatic().static$setPages(book, pages);
    }
    void static$setPages(ItemStack book, List<Text> pages);
    @SpecificImpl("static$setPages")
    @VersionRange(end = 2005)
    default void static$setPagesV_2005(ItemStack book, List<Text> pages)
    {
        for(NbtCompound tag : Item.reviseCustomData(book))
        {
            tag.put(
                "pages", NbtList.newInstance(
                    pages.stream().map(page -> NbtString.newInstance(new Gson().toJson(page.encode())))
                        .toArray(NbtElement[]::new)
                )
            );
        }
    }
    @SpecificImpl("static$setPages")
    @VersionRange(begin = 2005)
    default void static$setPagesV2005(ItemStack book, List<Text> pages)
    {
        for(Ref<Option<WrittenBookContentComponentV2005>> ref : book.getComponentsV2005()
            .edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
        {
            ref.set(Option.some(ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def).withPages(pages)));
        }
    }

    // TODO revise pages

    static boolean isResolved(ItemStack book)
    {
        return FACTORY.getStatic().static$isResolved(book);
    }
    boolean static$isResolved(ItemStack book);
    @SpecificImpl("static$isResolved")
    @VersionRange(end = 2005)
    default boolean static$isResolvedV_2005(ItemStack book)
    {
        for(boolean resolved : book.tagV_2005().getBoolean("resolved"))
        {
            return resolved;
        }
        return false;
    }
    @SpecificImpl("static$isResolved")
    @VersionRange(begin = 2005)
    default boolean static$isResolvedV2005(ItemStack book)
    {
        return book.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005)
            .unwrapOrGet(WrittenBookContentComponentV2005::def).isResolved();
    }

    static void setResolved(ItemStack book, boolean resolved)
    {
        FACTORY.getStatic().static$setResolved(book, resolved);
    }
    void static$setResolved(ItemStack book, boolean resolved);
    @SpecificImpl("static$setResolved")
    @VersionRange(end = 2005)
    default void static$setResolvedV_2005(ItemStack book, boolean resolved)
    {
        for(NbtCompound tag : Item.reviseCustomData(book))
        {
            tag.put("resolved", NbtByte.newInstance(resolved));
        }
    }
    @SpecificImpl("static$setResolved")
    @VersionRange(begin = 2005)
    default void static$setResolvedV2005(ItemStack book, boolean resolved)
    {
        for(Ref<Option<WrittenBookContentComponentV2005>> ref : book.getComponentsV2005()
            .edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
        {
            ref.set(Option.some(ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def).withResolved(resolved)));
        }
    }
}
