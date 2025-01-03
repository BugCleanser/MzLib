package mz.mzlib.minecraft.text;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.EntityType;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.nbt.NbtElement;
import mz.mzlib.minecraft.nbt.NbtString;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.*;

import java.util.Optional;
import java.util.UUID;

@WrapMinecraftClass({@VersionName(name="net.minecraft.text.HoverEvent", end=1400), @VersionName(name="net.minecraft.network.chat.HoverEvent", begin=1400, end=1403), @VersionName(name="net.minecraft.text.HoverEvent", begin=1403)})
public interface TextHoverEvent extends WrapperObject
{
    @WrapperCreator
    static TextHoverEvent create(Object wrapped)
    {
        return WrapperObject.create(TextHoverEvent.class, wrapped);
    }
    
    @WrapConstructor
    @VersionRange(end=1600)
    TextHoverEvent staticNewInstanceV_1600(Action action, Text value);
    
    static TextHoverEvent newInstanceV_1600(Action action, Text value)
    {
        return create(null).staticNewInstanceV_1600(action, value);
    }
    
    @WrapConstructor
    @VersionRange(begin=1600)
    TextHoverEvent staticNewInstanceV1600(Action action, WrapperObject content);
    
    static TextHoverEvent newInstanceV1600(Action action, WrapperObject content)
    {
        return create(null).staticNewInstanceV1600(action, content);
    }
    
    TextHoverEvent staticShowText(Text text);
    
    static TextHoverEvent showText(Text text)
    {
        return create(null).staticShowText(text);
    }
    
    @SpecificImpl("staticShowText")
    @VersionRange(end=1600)
    default TextHoverEvent staticShowTextV_1600(Text text)
    {
        return newInstanceV_1600(Action.showText(), text);
    }
    
    @SpecificImpl("staticShowText")
    @VersionRange(begin=1600)
    default TextHoverEvent staticShowTextV1600(Text text)
    {
        return newInstanceV1600(Action.showText(), text);
    }
    
    TextHoverEvent staticShowItem(ItemStack is);
    
    static TextHoverEvent showItem(ItemStack is)
    {
        return create(null).staticShowItem(is);
    }
    
    @SpecificImpl("staticShowItem")
    @VersionRange(end=1600)
    default TextHoverEvent staticShowItemV_1600(ItemStack is)
    {
        return newInstanceV_1600(Action.showItem(), Text.literal(is.encode().toString()));
    }
    
    @SpecificImpl("staticShowItem")
    @VersionRange(begin=1600)
    default TextHoverEvent staticShowItemV1600(ItemStack is)
    {
        return newInstanceV1600(Action.showItem(), ContentsItemStackV1600.newInstance(is));
    }
    
    static TextHoverEvent showEntity(Identifier type, UUID uuid, Text name)
    {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    @WrapMinecraftMethod(@VersionName(name="getAction"))
    Action getAction();
    
    @WrapMinecraftMethod(@VersionName(name="getValue", end=1600))
    Text getValueV_1600();
    
    @WrapMinecraftMethod(@VersionName(name="getValue", begin=1600))
    WrapperObject getContents0V1600(Action action);
    
    default WrapperObject getContentsV1600()
    {
        return this.getContents0V1600(this.getAction());
    }
    
    Text getShowedText();
    
    @SpecificImpl("getShowedText")
    @VersionRange(end=1600)
    default Text getShowedTextV_1600()
    {
        return this.getValueV_1600();
    }
    
    @SpecificImpl("getShowedText")
    @VersionRange(begin=1600)
    default Text getShowedTextV1600()
    {
        WrapperObject result = this.getContentsV1600();
        if(!result.isInstanceOf(Text::create))
            return null;
        return result.castTo(Text::create);
    }
    
    ItemStack getShowedItem();
    
    @SpecificImpl("getShowedItem")
    @VersionRange(end=1600)
    default ItemStack getShowedItemV_1600()
    {
        return null; // TODO
    }
    
    @SpecificImpl("getShowedItem")
    @VersionRange(begin=1600)
    default ItemStack getShowedItemV1600()
    {
        WrapperObject result = this.getContentsV1600();
        if(!result.isInstanceOf(ContentsItemStackV1600::create))
            return null;
        return result.castTo(ContentsItemStackV1600::create).getItemStack();
    }
    
    TextHoverEvent.Entity getEntity();
    
    @SpecificImpl("getEntity")
    @VersionRange(end=1600)
    default TextHoverEvent.Entity getEntityV_1600()
    {
        return new TextHoverEvent.EntityV_1600(NbtCompound.parse(this.getValueV_1600().getLiteral()));
    }
    
    @SpecificImpl("getEntity")
    @VersionRange(begin=1600)
    default TextHoverEvent.Entity getEntityV1600()
    {
        return this.getContentsV1600().castTo(TextHoverEvent.ContentsEntityV1600::create);
    }
    
    @WrapMinecraftInnerClass(outer=TextHoverEvent.class, name=@VersionName(name="Action"))
    interface Action extends WrapperObject
    {
        @WrapperCreator
        static Action create(Object wrapped)
        {
            return WrapperObject.create(Action.class, wrapped);
        }
        
        @WrapMinecraftFieldAccessor({@VersionName(name="SHOW_TEXT", end=1400), @VersionName(name="field_11762", begin=1400, end=1600), @VersionName(name="field_24342", begin=1600)})
        Action staticShowText();
        
        static Action showText()
        {
            return Action.create(null).staticShowText();
        }
        
        @WrapMinecraftFieldAccessor({@VersionName(name="SHOW_ITEM", end=1400), @VersionName(name="field_11757", begin=1400, end=1600), @VersionName(name="field_24343", begin=1600)})
        Action staticShowItem();
        
        static Action showItem()
        {
            return Action.create(null).staticShowItem();
        }
        
        @WrapMinecraftFieldAccessor({@VersionName(name="SHOW_ENTITY", end=1400), @VersionName(name="field_11761", begin=1400, end=1600), @VersionName(name="field_24344", begin=1600)})
        Action staticShowEntity();
        
        static Action showEntity()
        {
            return Action.create(null).staticShowEntity();
        }
    }
    
    @WrapMinecraftInnerClass(outer=TextHoverEvent.class, name=@VersionName(name="ItemStackContent", begin=1600))
    interface ContentsItemStackV1600 extends WrapperObject
    {
        @WrapperCreator
        static ContentsItemStackV1600 create(Object wrapped)
        {
            return WrapperObject.create(ContentsItemStackV1600.class, wrapped);
        }
        
        @WrapMinecraftFieldAccessor(@VersionName(name="item"))
        Item getItem();
        
        @WrapMinecraftFieldAccessor(@VersionName(name="count"))
        int getCount();
        
        @WrapMinecraftFieldAccessor({@VersionName(name="tag", end=1700), @VersionName(name="nbt", begin=1700, end=2003)})
        NbtCompound getTagV_2003();
        
        @WrapConstructor
        ContentsItemStackV1600 staticNewInstance(ItemStack is);
        
        static ContentsItemStackV1600 newInstance(ItemStack is)
        {
            return ContentsItemStackV1600.create(null).staticNewInstance(is);
        }
        
        ItemStack getItemStack();
        
        @SpecificImpl("getItemStack")
        @VersionRange(end=1700)
        default ItemStack getItemStackV_1700()
        {
            ItemStack result = ItemStack.newInstance(this.getItem(), this.getCount());
            result.setTagV_2005(this.getTagV_2003());
            return result;
        }
        
        @SpecificImpl("getItemStack")
        @WrapMinecraftMethod(@VersionName(name="asStack", begin=1700))
        ItemStack getItemStackV1700();
    }
    
    interface Entity
    {
        Identifier getType();
        
        UUID getId();
        
        Text getName();
    }
    
    class EntityV_1600 implements TextHoverEvent.Entity
    {
        public NbtCompound nbt;
        public EntityV_1600(NbtCompound nbt)
        {
            this.nbt = nbt;
        }
        @Override
        public Identifier getType()
        {
            NbtElement result = this.nbt.get("type");
            if(!result.isPresent())
                return null;
            return Identifier.newInstance(result.castTo(NbtString::create).getValue());
        }
        @Override
        public UUID getId()
        {
            NbtElement result = this.nbt.get("id");
            if(!result.isPresent())
                return null;
            return UUID.fromString(result.castTo(NbtString::create).getValue());
        }
        @Override
        public Text getName()
        {
            NbtElement result = this.nbt.get("name");
            if(!result.isPresent())
                return null;
            return Text.decode(new Gson().fromJson(result.castTo(NbtString::create).getValue(), JsonObject.class));
        }
    }
    
    @WrapMinecraftInnerClass(outer=TextHoverEvent.class, name=@VersionName(name="EntityContent", begin=1600))
    interface ContentsEntityV1600 extends WrapperObject, TextHoverEvent.Entity
    {
        @WrapperCreator
        static TextHoverEvent.ContentsEntityV1600 create(Object wrapped)
        {
            return WrapperObject.create(TextHoverEvent.ContentsEntityV1600.class, wrapped);
        }
        
        @WrapConstructor
        ContentsEntityV1600 staticNewInstance(EntityType type, UUID id, Text name);
        
        static TextHoverEvent.ContentsEntityV1600 newInstance(EntityType type, UUID id, Text name)
        {
            return ContentsEntityV1600.create(null).staticNewInstance(type, id, name);
        }
        
        @WrapMinecraftFieldAccessor(@VersionName(name="entityType"))
        EntityType getType0();
        
        default Identifier getType()
        {
            return this.getType0().getId();
        }
        
        @Override
        @WrapMinecraftFieldAccessor(@VersionName(name="uuid"))
        UUID getId();
        
        @WrapMinecraftFieldAccessor(@VersionName(name="name"))
        Optional<?> getName0();
        
        @Override
        default Text getName()
        {
            return this.getName0().map(Text::create).orElse(null);
        }
    }
}
