package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UiStack;
import mz.mzlib.minecraft.window.WindowActionType;
import mz.mzlib.minecraft.window.WindowType;
import mz.mzlib.util.*;
import mz.mzlib.util.proxy.ListProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class UiWindowList<T> extends UiWindow
{
    public static <T> Builder<T> builder(List<T> list)
    {
        return new Builder<>(list);
    }
    public static class Builder<T>
    {
        List<T> list;
        Style style = Style.DEFAULT.clone();
        BiFunction<UiWindowList<T>, EntityPlayer, Text> titleGetter = (ui, player) -> Text.literal(
            String.format("untitled ( %d / %d )", ui.getPageNumber() + 1, ui.getPageCount()));
        BiFunction<? super T, EntityPlayer, ItemStack> iconGetter;
        Visitor<T> viewer;
        Visitor<T> remover;
        Visitor<T> adder;
        Consumer<UiWindowList<T>> extra = ThrowableConsumer.nothing();

        public Builder(List<T> list)
        {
            this.list = list;
        }

        public Builder<T> titleGetter(BiFunction<UiWindowList<T>, EntityPlayer, Text> value)
        {
            this.titleGetter = value;
            return this;
        }
        public Builder<T> iconGetter(BiFunction<? super T, EntityPlayer, ItemStack> value)
        {
            this.iconGetter = value;
            return this;
        }
        public Builder<T> viewer(Visitor<T> value)
        {
            this.viewer = value;
            return this;
        }
        public Builder<T> remover(Visitor<T> value)
        {
            this.remover = value;
            return this;
        }
        public Builder<T> remover()
        {
            return this.remover((ui, player, index) ->
            {
                list.remove(index);
                ui.update();
            });
        }
        public Builder<T> adder(Visitor<T> value)
        {
            this.adder = value;
            return this;
        }
        public Builder<T> adder(Supplier<T> supplier)
        {
            return this.adder((ui, player, index) ->
            {
                list.add(index, supplier.get());
                ui.update();
            });
        }

        public Builder<T> extra(Consumer<UiWindowList<T>> value)
        {
            this.extra = value;
            return this;
        }

        public Builder<T> rows(int value)
        {
            this.style.rows = value;
            return this;
        }
        public Builder<T> topBar(int value)
        {
            this.style.topBar = value;
            return this;
        }
        public Builder<T> bottomBar(int value)
        {
            this.style.bottomBar = value;
            return this;
        }
        public Builder<T> topBarBackground(Function<EntityPlayer, ItemStack> value)
        {
            this.style.topBarBackground = value;
            return this;
        }
        public Builder<T> bottomBarBackground(Function<EntityPlayer, ItemStack> value)
        {
            this.style.bottomBarBackground = value;
            return this;
        }
        public Builder<T> buttonBack(Function<EntityPlayer, ItemStack> value)
        {
            this.style.buttonBack = value;
            return this;
        }
        public Builder<T> buttonBackIndex(int value)
        {
            this.style.buttonBackIndex = value;
            return this;
        }
        public Builder<T> buttonNew(Function<EntityPlayer, ItemStack> value)
        {
            this.style.buttonNew = value;
            return this;
        }
        public Builder<T> buttonNewIndex(int value)
        {
            this.style.buttonNewIndex = value;
            return this;
        }
        public Builder<T> buttonPrev(Function<EntityPlayer, ItemStack> value)
        {
            this.style.buttonPrev = value;
            return this;
        }
        public Builder<T> buttonNext(Function<EntityPlayer, ItemStack> value)
        {
            this.style.buttonNext = value;
            return this;
        }
        public Builder<T> buttonPrevIndex(int value)
        {
            this.style.buttonPrevIndex = value;
            return this;
        }
        public Builder<T> buttonNextIndex(int value)
        {
            this.style.buttonNextIndex = value;
            return this;
        }
        public Builder<T> buttonPrevFirst(boolean value)
        {
            this.style.buttonPrevFirst = value;
            return this;
        }
        public Builder<T> buttonNextLast(boolean value)
        {
            this.style.buttonNextLast = value;
            return this;
        }
        public Builder<T> buttonAppend(Function<EntityPlayer, ItemStack> value)
        {
            this.style.buttonAppend = value;
            return this;
        }
        public Builder<T> insertable(boolean value)
        {
            this.style.insertable = value;
            return this;
        }

        public UiWindowList<T> build()
        {
            return new UiWindowList<>(this);
        }
    }
    static class Style extends SimpleCloneable<Style>
    {
        int rows;
        int topBar, bottomBar;
        Function<EntityPlayer, ItemStack> topBarBackground, bottomBarBackground;
        Function<EntityPlayer, ItemStack> buttonBack;
        int buttonBackIndex;
        Function<EntityPlayer, ItemStack> buttonNew;
        int buttonNewIndex;
        Function<EntityPlayer, ItemStack> buttonPrev, buttonNext;
        int buttonPrevIndex, buttonNextIndex;
        boolean buttonPrevFirst, buttonNextLast;
        Function<EntityPlayer, ItemStack> buttonAppend;
        boolean insertable;

        static Style DEFAULT;

        static
        {
            DEFAULT = new Style();
            DEFAULT.rows = 6;
            DEFAULT.topBar = DEFAULT.bottomBar = 1;
            DEFAULT.buttonBackIndex = 0;
            DEFAULT.buttonNewIndex = 7;
            DEFAULT.buttonPrevIndex = 2;
            DEFAULT.buttonNextIndex = 6;
            DEFAULT.buttonPrevFirst = DEFAULT.buttonNextLast = true;
            DEFAULT.topBarBackground = DEFAULT.bottomBarBackground = it -> ItemStack.builder().stainedGlassPane().blue()
                .emptyName().build();
            DEFAULT.buttonBack = player -> ItemStack.builder().playerHead().texturesUrl(
                    "https://textures.minecraft.net/texture/47e50591f4118b9ae44755f7b485699b4b917f00d65f5ea8553ee48826d234c7")
                .customName(MinecraftI18n.resolveText(player, "mzlib.ui.back"))
                .build();
            DEFAULT.buttonPrev = player -> ItemStack.builder().playerHead().texturesUrl(
                    "https://textures.minecraft.net/texture/69ea1d86247f4af351ed1866bca6a3040a06c68177c78e42316a1098e60fb7d3")
                .customName(MinecraftI18n.resolveText(player, "mzlib.ui.list.prev"))
                .build();
            DEFAULT.buttonNext = player -> ItemStack.builder().playerHead().texturesUrl(
                    "https://textures.minecraft.net/texture/8271a47104495e357c3e8e80f511a9f102b0700ca9b88e88b795d33ff20105eb")
                .customName(MinecraftI18n.resolveText(player, "mzlib.ui.list.next"))
                .build();
            DEFAULT.buttonNew = player -> ItemStack.builder().fromId("nether_star")
                .customName(MinecraftI18n.resolveText(player, "mzlib.ui.list.new")).build();
            DEFAULT.buttonAppend = player -> ItemStack.builder().fromId("nether_star")
                .customName(MinecraftI18n.resolveText(player, "mzlib.ui.list.append")).build();
            DEFAULT.insertable = true;
        }
    }

    List<T> list;
    Style style;
    BiFunction<UiWindowList<T>, EntityPlayer, Text> titleGetter;
    BiFunction<? super T, EntityPlayer, ItemStack> iconGetter;
    Visitor<T> viewer;
    Visitor<T> remover;
    Visitor<T> adder;
    Consumer<UiWindowList<T>> extra;
    public UiWindowList(Builder<T> builder)
    {
        super(WindowType.generic9x(builder.style.rows));
        if(builder.iconGetter == null)
            throw new IllegalStateException("iconGetter is not set");
        this.titleGetter = builder.titleGetter;
        this.list = builder.list;
        this.style = builder.style.clone();
        this.iconGetter = builder.iconGetter;
        this.viewer = builder.viewer;
        this.remover = builder.remover;
        this.adder = builder.adder;
        this.extra = builder.extra;
        this.update();
    }

    int pageNumber = 0;
    public int getPageNumber()
    {
        return this.pageNumber;
    }
    public int getPageCount()
    {
        return 1 + (this.getList().size() + RuntimeUtil.castBooleanToByte(this.adder != null) - 1) /
            ((this.style.rows - this.style.topBar - this.style.bottomBar) * 9);
    }

    public List<T> getList()
    {
        return this.list;
    }

    public void update()
    {
        this.clear();
        int size = this.getWindowType().getSize();
        for(int i = 0; i < this.style.topBar; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                this.putIcon(i * 9 + j, this.style.topBarBackground);
            }
        }
        for(int i = 0; i < this.style.bottomBar; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                this.putIcon(size + (-this.style.bottomBar + i) * 9 + j, this.style.bottomBarBackground);
            }
        }
        for(int capacity = (this.style.rows - this.style.topBar - this.style.bottomBar) * 9, i = 0; i < capacity; i++)
        {
            int index = this.pageNumber * capacity + i;
            int iconIndex = this.style.topBar * 9 + i;
            if(index < this.getList().size())
            {
                this.putButton(
                    iconIndex, player ->
                    {
                        ItemStack icon = this.iconGetter.apply(this.getList().get(index), player).clone0();
                        List<Text> extra = new ArrayList<>();
                        if(this.viewer != null)
                            extra.add(MinecraftI18n.resolveText(player, "mzlib.ui.list.lore.view"));
                        if(this.remover != null)
                            extra.add(MinecraftI18n.resolveText(player, "mzlib.ui.list.lore.remove"));
                        if(this.adder != null && this.style.insertable)
                            extra.add(MinecraftI18n.resolveText(player, "mzlib.ui.list.lore.insert"));
                        for(List<Text> lore : Item.LORE.revise(icon))
                        {
                            lore.addAll(0, extra);
                        }
                        return icon;
                    },
                    (player, action, arg) ->
                    {
                        Visitor<T> visitor = null;
                        if(action.equals(WindowActionType.click()))
                        {
                            switch(arg)
                            {
                                case 0:
                                    if(this.viewer != null)
                                        visitor = this.viewer;
                                    break;
                                case 1:
                                    if(this.adder != null)
                                        visitor = this.adder;
                                    break;
                                default:
                                    break;
                            }
                        }
                        else if(action.equals(WindowActionType.shiftClick()))
                        {
                            if(this.remover != null)
                                visitor = this.remover;
                        }
                        if(visitor == null)
                            return;
                        visitor.visit(this, player, index);
                    }
                );
            }
            else
            {
                if(index == this.getList().size() && this.adder != null && this.style.buttonAppend != null)
                    this.putButton(
                        iconIndex, this.style.buttonAppend,
                        (player, action, arg) -> this.adder.visit(this, player, index)
                    );
                else
                    this.putIconEmpty(iconIndex);
            }
        }
        if(this.style.buttonNew != null)
        {
            this.putButton(
                this.style.buttonNewIndex, this.style.buttonNew,
                (player, action, arg) -> this.adder.visit(this, player, this.getList().size())
            );
        }
        if(this.style.buttonBack != null)
        {
            this.putButton(
                this.style.buttonBackIndex, this.style.buttonBack,
                (player, action, arg) -> UiStack.get(player).back()
            );
        }
        if(this.style.buttonPrev != null)
            if(this.style.buttonPrevFirst || this.pageNumber > 0)
            {
                this.putButton(
                    size - this.style.bottomBar * 9 + this.style.buttonPrevIndex, this.style.buttonPrev,
                    (player, action, arg) ->
                    {
                        if(this.pageNumber > 0)
                            this.pageNumber--;
                        this.update();
                    }
                );
            }
        if(this.style.buttonNext != null)
            if(this.style.buttonNextLast || this.pageNumber < this.getPageCount() - 1)
            {
                this.putButton(
                    size - this.style.bottomBar * 9 + this.style.buttonNextIndex, this.style.buttonNext,
                    (player, action, arg) ->
                    {
                        if(this.pageNumber < this.getPageCount() - 1)
                            this.pageNumber++;
                        this.update();
                    }
                );
            }
        this.extra.accept(this);
        this.reopen();
    }

    @Override
    public Text getTitle(EntityPlayer player)
    {
        return this.titleGetter.apply(this, player);
    }

    public List<T> proxy()
    {
        return new ListProxy<>(
            this.getList(), FunctionInvertible.identity(),
            new ModifyMonitor.Simple(ThrowableRunnable.nothing(), this::update)
        );
    }

    @FunctionalInterface
    public interface Visitor<T>
    {
        void visit(UiWindowList<T> ui, EntityPlayer player, int index);
    }
}
