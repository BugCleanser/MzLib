package mz.mzlib.minecraft.item;


import com.google.gson.Gson;
import mz.mzlib.data.DataHandler;
import mz.mzlib.data.DataKey;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.component.ComponentKeyV2005;
import mz.mzlib.minecraft.component.type.WrittenBookContentComponentV2005;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.nbt.NbtElement;
import mz.mzlib.minecraft.nbt.NbtList;
import mz.mzlib.minecraft.nbt.NbtString;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.Option;
import mz.mzlib.util.Ref;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

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

    Item WRITTEN_BOOK = Item.fromId("written_book");

    int MAX_PAGE_LINES = 14;

    ComponentKeyV2005.Wrapper<WrittenBookContentComponentV2005> COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005 =
        MinecraftPlatform.instance.getVersion() < 2005 ?
            null :
            ComponentKeyV2005.fromId("written_book_content", WrittenBookContentComponentV2005.FACTORY);

    DataKey<ItemStack, String, Void> TITLE = new DataKey<>("title");
    DataKey<ItemStack, String, Void> AUTHOR = new DataKey<>("author");
    DataKey<ItemStack, Integer, Void> GENERATION = new DataKey<>("generation");
    DataKey<ItemStack, List<Text>, List<Text>> PAGES = new DataKey<>("pages");
    DataKey<ItemStack, Boolean, Void> RESOLVED = new DataKey<>("resolved");

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

    class Module extends MzModule
    {
        public static Module instance = new Module();

        @Override
        public void onLoad()
        {
            Predicate<ItemStack> checker = is -> is.getItem().equals(WRITTEN_BOOK);
            if(MinecraftPlatform.instance.getVersion() < 2005)
                DataHandler.builder(TITLE)
                    .checker(checker)
                    .getter(is -> is.getTagV_2005().flatMap(tag -> tag.getString("title")).unwrapOr(""))
                    .setter((is, value) ->
                    {
                        for(NbtCompound tag : CUSTOM_DATA.revise(is))
                        {
                            tag.put("title", value);
                        }
                    })
                    .register(this);
            else
                DataHandler.builder(TITLE)
                    .checker(checker)
                    .getter(is ->
                        is.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005)
                            .unwrapOrGet(WrittenBookContentComponentV2005::def).getTitle())
                    .setter((is, value) ->
                    {
                        for(Ref<Option<WrittenBookContentComponentV2005>> ref : is.getComponentsV2005()
                            .edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
                        {
                            ref.set(Option.some(
                                ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def).withTitle(value)));
                        }
                    })
                    .register(this);
            if(MinecraftPlatform.instance.getVersion() < 2005)
                DataHandler.builder(AUTHOR)
                    .checker(checker)
                    .getter(is -> is.getTagV_2005().flatMap(tag -> tag.getString("author")).unwrapOr(""))
                    .setter((is, value) ->
                    {
                        for(NbtCompound tag : CUSTOM_DATA.revise(is))
                        {
                            tag.put("author", value);
                        }
                    })
                    .register(this);
            else
                DataHandler.builder(AUTHOR)
                    .checker(checker)
                    .getter(is -> is.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005)
                        .unwrapOrGet(WrittenBookContentComponentV2005::def).getAuthor())
                    .setter((is, value) ->
                    {
                        for(Ref<Option<WrittenBookContentComponentV2005>> ref : is.getComponentsV2005()
                            .edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
                        {
                            ref.set(Option.some(
                                ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def).withAuthor(value)));
                        }
                    })
                    .register(this);
            if(MinecraftPlatform.instance.getVersion() < 2005)
                DataHandler.builder(GENERATION)
                    .checker(checker)
                    .getter(is -> is.getTagV_2005().flatMap(tag -> tag.getInt("generation")).unwrapOr(0))
                    .setter((is, value) ->
                    {
                        for(NbtCompound tag : CUSTOM_DATA.revise(is))
                        {
                            tag.put("generation", value);
                        }
                    })
                    .register(this);
            else
                DataHandler.builder(GENERATION)
                    .checker(checker)
                    .getter(is -> is.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005)
                        .unwrapOrGet(WrittenBookContentComponentV2005::def).getGeneration())
                    .setter((is, value) ->
                    {
                        for(Ref<Option<WrittenBookContentComponentV2005>> ref : is.getComponentsV2005()
                            .edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
                        {
                            ref.set(
                                Option.some(ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def)
                                    .withGeneration(value)));
                        }
                    })
                    .register(this);
            if(MinecraftPlatform.instance.getVersion() < 2005)
            {
                FunctionInvertible<NbtString, Text> functionPage = FunctionInvertible.of(
                    str -> Text.decode(str.getValue()),
                    text -> NbtString.newInstance(text.encode().toString())
                );
                DataHandler.builder(PAGES)
                    .checker(checker)
                    .getter(is -> new ListProxy<>(
                        is.tagV_2005().getNbtList("pages").map(pages -> pages.asList(NbtString.FACTORY))
                            .map(ArrayList::new).unwrapOrGet(ArrayList::new), // clone
                        functionPage
                    ))
                    .setter((is, value) ->
                    {
                        for(NbtCompound tag : CUSTOM_DATA.revise(is))
                        {
                            if(value instanceof ListProxy && ((ListProxy<?, ?>) value).getFunction() == functionPage)
                                tag.put(
                                    "pages", NbtList.newInstance(((ListProxy<Text, NbtString>) value).getDelegate()));
                            else
                                tag.put(
                                    "pages", NbtList.newInstance(
                                        value.stream()
                                            .map(page -> NbtString.newInstance(new Gson().toJson(page.encode())))
                                            .toArray(NbtElement[]::new)
                                    )
                                );
                        }
                    })
                    .reviserGetter(Function.identity())
                    .reviserApplier(Function.identity())
                    .register(this);
            }
            else
                DataHandler.builder(PAGES)
                    .checker(checker)
                    .getter(is -> is.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005)
                        .unwrapOrGet(WrittenBookContentComponentV2005::def).getPages())
                    .setter((is, value) ->
                    {
                        for(Ref<Option<WrittenBookContentComponentV2005>> ref : is.getComponentsV2005()
                            .edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
                        {
                            ref.set(Option.some(
                                ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def).withPages(value)));
                        }
                    })
                    .reviserGetter(Function.identity())
                    .reviserApplier(Function.identity())
                    .register(this);
            if(MinecraftPlatform.instance.getVersion() < 2005)
                DataHandler.builder(RESOLVED)
                    .checker(checker)
                    .getter(is -> is.getTagV_2005().flatMap(tag -> tag.getBoolean("resolved")).unwrapOr(false))
                    .setter((is, value) ->
                    {
                        for(NbtCompound tag : CUSTOM_DATA.revise(is))
                        {
                            tag.put("resolved", value);
                        }
                    })
                    .register(this);
            else
                DataHandler.builder(RESOLVED)
                    .checker(checker)
                    .getter(is -> is.getComponentsV2005().get(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005)
                        .unwrapOrGet(WrittenBookContentComponentV2005::def).isResolved())
                    .setter((is, value) ->
                    {
                        for(Ref<Option<WrittenBookContentComponentV2005>> ref : is.getComponentsV2005()
                            .edit(COMPONENT_KEY_WRITTEN_BOOK_CONTENT_V2005))
                        {
                            ref.set(Option.some(
                                ref.get().unwrapOrGet(WrittenBookContentComponentV2005::def).withResolved(value)));
                        }
                    })
                    .register(this);
        }
    }

    /**
     * @see #TITLE
     */
    @Deprecated
    static String getTitle(ItemStack book)
    {
        return TITLE.get(book);
    }
    /**
     * @see #TITLE
     */
    @Deprecated
    static void setTitle(ItemStack book, String title)
    {
        TITLE.set(book, title);
    }

    /**
     * @see #AUTHOR
     */
    @Deprecated
    static String getAuthor(ItemStack book)
    {
        return AUTHOR.get(book);
    }
    /**
     * @see #AUTHOR
     */
    @Deprecated
    static void setAuthor(ItemStack book, String author)
    {
        AUTHOR.set(book, author);
    }

    /**
     * @see #GENERATION
     */
    @Deprecated
    static int getGeneration(ItemStack book)
    {
        return GENERATION.get(book);
    }
    /**
     * @see #GENERATION
     */
    @Deprecated
    static void setGeneration(ItemStack book, int generation)
    {
        GENERATION.set(book, generation);
    }

    /**
     * @see #PAGES
     */
    @Deprecated
    static List<Text> getPages(ItemStack book)
    {
        return PAGES.get(book);
    }
    /**
     * @see #PAGES
     */
    @Deprecated
    static void setPages(ItemStack book, List<Text> pages)
    {
        PAGES.set(book, pages);
    }

    /**
     * @see #RESOLVED
     */
    @Deprecated
    static boolean isResolved(ItemStack book)
    {
        return RESOLVED.get(book);
    }
    /**
     * @see #RESOLVED
     */
    @Deprecated
    static void setResolved(ItemStack book, boolean resolved)
    {
        RESOLVED.set(book, resolved);
    }
}
