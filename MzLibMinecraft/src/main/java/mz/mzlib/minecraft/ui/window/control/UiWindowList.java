package mz.mzlib.minecraft.ui.window.control;


import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.ui.window.UiWindowUtil;
import mz.mzlib.minecraft.window.WindowAction;
import mz.mzlib.util.Option;
import mz.mzlib.util.RuntimeUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class UiWindowList<T> extends UiWindowControlReadOnly
{
    List<T> list;
    Function<? super Entry<T>, ? extends ItemStack> iconGetter;
    Option<Consumer<Entry<T>>> viewer;
    Option<Consumer<Entry<T>>> adder;
    List<Consumer<UiWindowList<T>>> listenersPageChanged;

    public UiWindowList(Builder<T> builder)
    {
        super(builder.bounds);
        this.list = builder.list;
        this.iconGetter = builder.iconGetter;
        this.viewer = builder.viewer;
        this.adder = builder.adder;
        this.setBackground(builder.background);
        this.listenersPageChanged = builder.listenersPageChanged;
        this.setPage(0);
    }

    public List<T> getList()
    {
        return this.list;
    }

    public List<Consumer<UiWindowList<T>>> getListenersPageChanged()
    {
        return this.listenersPageChanged;
    }
    public void listenPageChanged(Consumer<UiWindowList<T>> listener)
    {
        this.listenersPageChanged.add(listener);
    }

    int page;
    public int getPage()
    {
        return this.page;
    }
    public void setPage(int value)
    {
        this.page = value;
        this.invalidate();
        for(Consumer<UiWindowList<T>> listener : this.getListenersPageChanged())
        {
            listener.accept(this);
        }
    }

    public int getPageCount()
    {
        return 1 + (this.getList().size() + RuntimeUtil.castBooleanToByte(this.adder.isSome()) - 1) /
            (this.getSize().height * 9);
    }

    Option<Option<Integer>> point2index(Point point)
    {
        int i = this.getSize().width * this.getSize().height * this.page + point.y * this.getSize().width + point.x;
        if(i < 0 || i > this.list.size())
            return Option.none();
        if(i == this.list.size())
            return Option.some(Option.none());
        return Option.some(Option.some(i));
    }

    @Override
    public Option<ItemStack> getIcon(EntityPlayer player, Point point)
    {
        for(Option<Integer> indexOrNone : this.point2index(point))
        {
            for(int index : indexOrNone)
            {
                return Option.some(this.iconGetter.apply(new Entry<>(this, player, index)));
            }
            if(this.adder.isSome())
                return Option.some(ItemStack.builder().fromId("nether_star")
                    .customName(MinecraftI18n.resolveText(player, "mzlib.ui.list.append")).build());
        }
        return super.getIcon(player, point);
    }
    @Override
    public void onAction(WindowAction action, Point point)
    {
        for(Consumer<Entry<T>> viewer : this.viewer)
        {
            for(Option<Integer> indexOrNone : this.point2index(point))
            {
                for(int index : indexOrNone)
                {
                    viewer.accept(new Entry<>(this, action.getPlayer(), index));
                    return;
                }
                for(Consumer<Entry<T>> adder : this.adder)
                {
                    adder.accept(new Entry<>(this, action.getPlayer(), this.list.size()));
                }
            }
        }
    }

    public static <T> Builder<T> builder(List<T> list)
    {
        return new Builder<>(list);
    }
    @SuppressWarnings("UnusedReturnValue")
    public static class Builder<T>
    {
        Rectangle bounds = new Rectangle();
        List<T> list;
        Function<? super Entry<T>, ? extends ItemStack> iconGetter;
        Option<Consumer<Entry<T>>> viewer = Option.none();
        Option<Consumer<Entry<T>>> adder = Option.none();
        Function<EntityPlayer, ItemStack> background = player -> ItemStack.empty();
        List<Consumer<UiWindowList<T>>> listenersPageChanged = new ArrayList<>();
        public Builder(List<T> list)
        {
            this.list = list;
        }
        public Builder<T> bounds(Rectangle value)
        {
            this.bounds = value;
            return this;
        }
        public Builder<T> location(Point value)
        {
            this.bounds.setLocation(value);
            return this;
        }
        public Builder<T> size(Dimension value)
        {
            this.bounds.setSize(value);
            return this;
        }
        public Builder<T> viewer(Consumer<Entry<T>> value)
        {
            this.viewer = Option.some(value);
            return this;
        }
        public Builder<T> adder(Consumer<Entry<T>> value)
        {
            this.adder = Option.some(value);
            return this;
        }
        public Builder<T> adder(Supplier<T> value)
        {
            return this.adder(entry -> entry.getControl().getList().add(value.get()));
        }
        public Builder<T> iconGetter(Function<? super Entry<T>, ? extends ItemStack> value)
        {
            this.iconGetter = value;
            return this;
        }
        public Builder<T> background(Function<EntityPlayer, ItemStack> background)
        {
            this.background = background;
            return this;
        }
        public Builder<T> listenerPageChanged(Consumer<UiWindowList<T>> value)
        {
            this.listenersPageChanged.add(value);
            return this;
        }
        public UiWindowList<T> build()
        {
            return new UiWindowList<>(this);
        }
    }

    public static <T> OverlappedBuilder<T> overlappedBuilder(List<T> list)
    {
        return new OverlappedBuilder<>(list);
    }
    public static class OverlappedBuilder<T>
    {
        Rectangle bounds = new Rectangle();
        Builder<T> controlBuilder;
        Function<EntityPlayer, ItemStack> background;
        boolean buttonPrevFirst = true, buttonNextLast = true;
        public OverlappedBuilder(List<T> list)
        {
            this.controlBuilder = UiWindowList.builder(list);
            this.background = it -> ItemStack.builder().stainedGlassPane().blue().emptyName().build();
        }
        public OverlappedBuilder<T> bounds(Rectangle value)
        {
            this.bounds = value;
            return this;
        }
        public OverlappedBuilder<T> location(Point value)
        {
            this.bounds.setLocation(value);
            return this;
        }
        public OverlappedBuilder<T> size(Dimension value)
        {
            this.bounds.setSize(value);
            return this;
        }
        public OverlappedBuilder<T> viewer(Consumer<Entry<T>> value)
        {
            this.controlBuilder.viewer(value);
            return this;
        }
        public OverlappedBuilder<T> adder(Consumer<Entry<T>> value)
        {
            this.controlBuilder.adder(value);
            return this;
        }
        public OverlappedBuilder<T> adder(Supplier<T> value)
        {
            this.controlBuilder.adder(value);
            return this;
        }
        public OverlappedBuilder<T> iconGetter(Function<? super Entry<T>, ? extends ItemStack> value)
        {
            this.controlBuilder.iconGetter(value);
            return this;
        }
        public OverlappedBuilder<T> listenerPageChanged(Consumer<UiWindowList<T>> value)
        {
            this.controlBuilder.listenerPageChanged(value);
            return this;
        }
        public OverlappedBuilder<T> background(Function<EntityPlayer, ItemStack> background)
        {
            this.background = background;
            return this;
        }
        public OverlappedBuilder<T> buttonPrevFirst(boolean value)
        {
            this.buttonPrevFirst = value;
            return this;
        }
        public OverlappedBuilder<T> buttonNextLast(boolean value)
        {
            this.buttonNextLast = value;
            return this;
        }
        public Overlapped<T> build()
        {
            return new Overlapped<>(this);
        }
    }
    public static class Overlapped<T> extends UiWindowControlReadOnly
    {
        public UiWindowButton buttonBack, buttonPrev, buttonNext;
        public UiWindowList<T> controlList;
        public Overlapped(OverlappedBuilder<T> builder)
        {
            super(builder.bounds);
            this.setBackground(builder.background);
            this.addChild(this.buttonBack = UiWindowUtil.buttonBack(new Point(0, 0)));
            builder.controlBuilder.bounds(new Rectangle(0, 1, builder.bounds.width, builder.bounds.height - 2));
            this.addChild(this.buttonPrev = new UiWindowButton(
                new Point(2, builder.bounds.height - 1),
                player -> ItemStack.builder().playerHead().texturesUrl(
                        "https://textures.minecraft.net/texture/69ea1d86247f4af351ed1866bca6a3040a06c68177c78e42316a1098e60fb7d3")
                    .customName(MinecraftI18n.resolveText(player, "mzlib.ui.list.prev")).build(),
                action -> controlList.setPage(Math.max(0, controlList.getPage() - 1))
            ));
            this.addChild(this.buttonNext = new UiWindowButton(
                new Point(6, builder.bounds.height - 1),
                player -> ItemStack.builder().playerHead().texturesUrl(
                        "https://textures.minecraft.net/texture/8271a47104495e357c3e8e80f511a9f102b0700ca9b88e88b795d33ff20105eb")
                    .customName(MinecraftI18n.resolveText(player, "mzlib.ui.list.next")).build(),
                action -> controlList.setPage(Math.min(controlList.getPageCount() - 1, controlList.getPage() + 1))
            ));
            if(!builder.buttonPrevFirst)
                builder.controlBuilder.listenerPageChanged(
                    control -> this.buttonPrev.setVisible(control.getPage() > 0));
            if(!builder.buttonNextLast)
                builder.controlBuilder.listenerPageChanged(
                    control -> this.buttonNext.setVisible(control.getPage() < control.getPageCount() - 1));
            this.addChild(this.controlList = builder.controlBuilder.build());
        }
    }

    public static class Entry<T>
    {
        UiWindowList<T> control;
        EntityPlayer player;
        int index;
        Entry(UiWindowList<T> control, EntityPlayer player, int index)
        {
            this.control = control;
            this.player = player;
            this.index = index;
        }

        public UiWindowList<T> getControl()
        {
            return this.control;
        }
        public EntityPlayer getPlayer()
        {
            return this.player;
        }
        public int getIndex()
        {
            return this.index;
        }
        public T getElement()
        {
            return this.getControl().getList().get(this.getIndex());
        }
    }
}
