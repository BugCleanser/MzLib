package mz.mzlib.minecraft.text;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.EntityType;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.*;

import java.util.Optional;
import java.util.UUID;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.text.HoverEvent"))
public interface TextHoverEvent extends WrapperObject
{
    WrapperFactory<TextHoverEvent> FACTORY = WrapperFactory.of(TextHoverEvent.class);
    @Deprecated
    @WrapperCreator
    static TextHoverEvent create(Object wrapped)
    {
        return WrapperObject.create(TextHoverEvent.class, wrapped);
    }

    @WrapConstructor
    @VersionRange(end = 1600)
    TextHoverEvent static$newInstanceV_1600(Action action, Text value);

    static TextHoverEvent newInstanceV_1600(Action action, Text value)
    {
        return FACTORY.getStatic().static$newInstanceV_1600(action, value);
    }

    @WrapConstructor
    @VersionRange(begin = 1600, end = 2105)
    TextHoverEvent static$newInstanceV1600_2105(Action action, WrapperObject content);

    static TextHoverEvent newInstanceV1600_2105(Action action, WrapperObject content)
    {
        return FACTORY.getStatic().static$newInstanceV1600_2105(action, content);
    }

    static TextHoverEvent showText(Text text)
    {
        return FACTORY.getStatic().static$showText(text);
    }
    TextHoverEvent static$showText(Text text);
    @SpecificImpl("static$showText")
    @VersionRange(end = 1600)
    default TextHoverEvent static$showTextV_1600(Text text)
    {
        return newInstanceV_1600(Action.showText(), text);
    }
    @SpecificImpl("static$showText")
    @VersionRange(begin = 1600, end = 2105)
    default TextHoverEvent static$showTextV1600_2105(Text text)
    {
        return newInstanceV1600_2105(Action.showText(), text);
    }
    @SpecificImpl("static$showText")
    @VersionRange(begin = 2105)
    default TextHoverEvent static$showTextV2105(Text text)
    {
        return ShowText2105.newInstance(text);
    }

    static TextHoverEvent showItem(ItemStack is)
    {
        return FACTORY.getStatic().static$showItem(is);
    }
    TextHoverEvent static$showItem(ItemStack is);
    @SpecificImpl("static$showItem")
    @VersionRange(end = 1600)
    default TextHoverEvent static$showItemV_1600(ItemStack is)
    {
        return newInstanceV_1600(Action.showItem(), Text.literal(is.encode().getValue().unwrap().toString()));
    }
    @SpecificImpl("static$showItem")
    @VersionRange(begin = 1600, end = 2105)
    default TextHoverEvent static$showItemV1600_2105(ItemStack is)
    {
        return newInstanceV1600_2105(Action.showItem(), ContentItemStackV1600_2105.newInstance(is));
    }
    @SpecificImpl("static$showItem")
    @VersionRange(begin = 2105)
    default TextHoverEvent static$showItemV2105(ItemStack is)
    {
        return ShowItem2105.newInstance(is);
    }

    static TextHoverEvent showEntity(Identifier type, UUID uuid, Text name)
    {
        // TODO
        throw new UnsupportedOperationException();
    }

    @WrapMinecraftMethod(@VersionName(name = "getAction"))
    Action getAction();

    @VersionRange(end = 1600)
    @WrapMinecraftMethod(@VersionName(name = "getValue"))
    Text getValueV_1600();

    @VersionRange(begin = 1600, end = 2105)
    @WrapMinecraftMethod(@VersionName(name = "getValue"))
    WrapperObject getContent0V1600_2105(Action action);

    default WrapperObject getContentV1600_2105()
    {
        return this.getContent0V1600_2105(this.getAction());
    }

    TextHoverEvent.Entity getShowEntity();
    @SpecificImpl("getShowEntity")
    @VersionRange(end = 1600)
    default TextHoverEvent.Entity getShowEntityV_1600()
    {
        return new TextHoverEvent.EntityV_1600(
            NbtCompound.parse(this.getValueV_1600().castTo(TextLiteral.FACTORY).getLiteral()));
    }
    @SpecificImpl("getShowEntity")
    @VersionRange(begin = 1600, end = 2105)
    default TextHoverEvent.Entity getShowEntityV1600_2105()
    {
        return this.getContentV1600_2105().castTo(ContentEntityV1600.FACTORY);
    }
    @SpecificImpl("getShowEntity")
    @VersionRange(begin = 2105)
    default TextHoverEvent.Entity getShowEntityV2105()
    {
        return this.castTo(ShowEntity2105.FACTORY).getValue();
    }

    ItemStack getShowItem();
    @SpecificImpl("getShowItem")
    @VersionRange(end = 1600)
    default ItemStack getShowItemV_1600()
    {
        throw new UnsupportedOperationException(); // TODO
    }
    @SpecificImpl("getShowItem")
    @VersionRange(begin = 1600, end = 2105)
    default ItemStack getShowItemV1600_2105()
    {
        return this.getContentV1600_2105().castTo(ContentItemStackV1600_2105.FACTORY).getItemStack();
    }
    @SpecificImpl("getShowItem")
    @VersionRange(begin = 2105)
    default ItemStack getShowItemV2105()
    {
        return this.castTo(ShowItem2105.FACTORY).getValue();
    }

    Text getShowText();
    @SpecificImpl("getShowText")
    @VersionRange(end = 1600)
    default Text getShowTextV_1600()
    {
        return this.getValueV_1600();
    }
    @SpecificImpl("getShowText")
    @VersionRange(begin = 1600, end = 2105)
    default Text getShowTextV1600_2105()
    {
        return this.getContentV1600_2105().castTo(Text.FACTORY);
    }
    @SpecificImpl("getShowText")
    @VersionRange(begin = 2105)
    default Text getShowTextV2105()
    {
        return this.castTo(ShowText2105.FACTORY).getValue();
    }

    @WrapMinecraftInnerClass(outer = TextHoverEvent.class, name = @VersionName(name = "Action"))
    interface Action extends WrapperObject
    {
        WrapperFactory<Action> FACTORY = WrapperFactory.of(Action.class);
        @Deprecated
        @WrapperCreator
        static Action create(Object wrapped)
        {
            return WrapperObject.create(Action.class, wrapped);
        }

        @WrapMinecraftFieldAccessor({
            @VersionName(name = "SHOW_TEXT", end = 1400),
            @VersionName(name = "field_11762", begin = 1400, end = 1600),
            @VersionName(name = "field_24342", begin = 1600)
        })
        Action static$showText();

        static Action showText()
        {
            return Action.FACTORY.getStatic().static$showText();
        }

        @WrapMinecraftFieldAccessor({
            @VersionName(name = "SHOW_ITEM", end = 1400),
            @VersionName(name = "field_11757", begin = 1400, end = 1600),
            @VersionName(name = "field_24343", begin = 1600)
        })
        Action static$showItem();

        static Action showItem()
        {
            return Action.FACTORY.getStatic().static$showItem();
        }

        @WrapMinecraftFieldAccessor({
            @VersionName(name = "SHOW_ENTITY", end = 1400),
            @VersionName(name = "field_11761", begin = 1400, end = 1600),
            @VersionName(name = "field_24344", begin = 1600)
        })
        Action static$showEntity();

        static Action showEntity()
        {
            return Action.FACTORY.getStatic().static$showEntity();
        }
    }

    @VersionRange(begin = 1600, end = 2105)
    @WrapMinecraftInnerClass(outer = TextHoverEvent.class, name = @VersionName(name = "ItemStackContent"))
    interface ContentItemStackV1600_2105 extends WrapperObject
    {
        WrapperFactory<ContentItemStackV1600_2105> FACTORY = WrapperFactory.of(ContentItemStackV1600_2105.class);
        @Deprecated
        @WrapperCreator
        static ContentItemStackV1600_2105 create(Object wrapped)
        {
            return WrapperObject.create(ContentItemStackV1600_2105.class, wrapped);
        }

        @WrapMinecraftFieldAccessor(@VersionName(name = "item"))
        Item getItem();

        @WrapMinecraftFieldAccessor(@VersionName(name = "count"))
        int getCount();

        @WrapMinecraftFieldAccessor({
            @VersionName(name = "tag", end = 1700),
            @VersionName(name = "nbt", begin = 1700, end = 2003)
        })
        NbtCompound getTag0V_2003();
        default Option<NbtCompound> getTagV_2003()
        {
            return Option.fromNullable(this.getTag0V_2003());
        }

        @WrapConstructor
        ContentItemStackV1600_2105 static$newInstance(ItemStack is);

        static ContentItemStackV1600_2105 newInstance(ItemStack is)
        {
            return ContentItemStackV1600_2105.FACTORY.getStatic().static$newInstance(is);
        }

        ItemStack getItemStack();

        @SpecificImpl("getItemStack")
        @VersionRange(end = 1700)
        default ItemStack getItemStackV_1700()
        {
            ItemStack result = ItemStack.newInstance(this.getItem(), this.getCount());
            result.setTagV_2005(this.getTagV_2003());
            return result;
        }

        @SpecificImpl("getItemStack")
        @WrapMinecraftMethod(@VersionName(name = "asStack", begin = 1700))
        ItemStack getItemStackV1700();
    }

    interface Entity
    {
        Option<Identifier> getType();

        Option<UUID> getId();

        Option<Text> getName();
    }

    class EntityV_1600 implements TextHoverEvent.Entity
    {
        public NbtCompound nbt;
        public EntityV_1600(NbtCompound nbt)
        {
            this.nbt = nbt;
        }
        @Override
        public Option<Identifier> getType()
        {
            return this.nbt.getString("type").map(Identifier::of);
        }
        @Override
        public Option<UUID> getId()
        {
            return this.nbt.getString("id").map(UUID::fromString);
        }
        @Override
        public Option<Text> getName()
        {
            return this.nbt.getString("name").map(Text::decode);
        }
    }

    @WrapMinecraftInnerClass(outer = TextHoverEvent.class, name = @VersionName(name = "EntityContent", begin = 1600))
    interface ContentEntityV1600 extends WrapperObject, TextHoverEvent.Entity
    {
        WrapperFactory<ContentEntityV1600> FACTORY = WrapperFactory.of(ContentEntityV1600.class);
        @Deprecated
        @WrapperCreator
        static ContentEntityV1600 create(Object wrapped)
        {
            return WrapperObject.create(ContentEntityV1600.class, wrapped);
        }

        @WrapConstructor
        ContentEntityV1600 static$newInstance(EntityType type, UUID id, Text name);

        static ContentEntityV1600 newInstance(EntityType type, UUID id, Text name)
        {
            return FACTORY.getStatic().static$newInstance(type, id, name);
        }

        @WrapMinecraftFieldAccessor(@VersionName(name = "entityType"))
        EntityType getType0();

        default Option<Identifier> getType()
        {
            return Option.some(this.getType0().getIdV1300());
        }

        @WrapMinecraftFieldAccessor(@VersionName(name = "uuid"))
        UUID getId0();
        @Override
        default Option<UUID> getId()
        {
            return Option.some(this.getId0());
        }

        @WrapMinecraftFieldAccessor(@VersionName(name = "name"))
        Optional<?> getName0();
        @Override
        default Option<Text> getName()
        {
            return Option.fromOptional(this.getName0().map(Text.FACTORY::create));
        }
    }

    @VersionRange(begin = 2105)
    @WrapMinecraftInnerClass(outer = TextHoverEvent.class, name = @VersionName(name = "ShowEntity"))
    interface ShowEntity2105 extends WrapperObject, TextHoverEvent
    {
        WrapperFactory<ShowEntity2105> FACTORY = WrapperFactory.of(ShowEntity2105.class);

        static ShowEntity2105 newInstance(ContentEntityV1600 value)
        {
            return FACTORY.getStatic().static$newInstance(value);
        }
        @WrapConstructor
        ShowEntity2105 static$newInstance(ContentEntityV1600 value);

        @WrapMinecraftFieldAccessor(@VersionName(name = "comp_3508"))
        ContentEntityV1600 getValue();
    }

    @VersionRange(begin = 2105)
    @WrapMinecraftInnerClass(outer = TextHoverEvent.class, name = @VersionName(name = "ShowItem"))
    interface ShowItem2105 extends WrapperObject, TextHoverEvent
    {
        WrapperFactory<ShowItem2105> FACTORY = WrapperFactory.of(ShowItem2105.class);

        static ShowItem2105 newInstance(ItemStack value)
        {
            return FACTORY.getStatic().static$newInstance(value);
        }
        @WrapConstructor
        ShowItem2105 static$newInstance(ItemStack value);

        @WrapMinecraftFieldAccessor(@VersionName(name = "comp_3509"))
        ItemStack getValue();
    }

    @VersionRange(begin = 2105)
    @WrapMinecraftInnerClass(outer = TextHoverEvent.class, name = @VersionName(name = "ShowText"))
    interface ShowText2105 extends WrapperObject, TextHoverEvent
    {
        WrapperFactory<ShowText2105> FACTORY = WrapperFactory.of(ShowText2105.class);

        static ShowText2105 newInstance(Text value)
        {
            return FACTORY.getStatic().static$newInstance(value);
        }
        @WrapConstructor
        ShowText2105 static$newInstance(Text value);

        @WrapMinecraftFieldAccessor(@VersionName(name = "comp_3510"))
        Text getValue();
    }
}
