package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.window.WindowActionType;
import mz.mzlib.minecraft.window.WindowType;
import mz.mzlib.util.*;
import mz.mzlib.util.proxy.ListProxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @see mz.mzlib.minecraft.ui.window.control.UiWindowList
 */
@Deprecated
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
        public StepButton<T> button(String id)
        {
            return new StepButton<>(this, id);
        }
        public StepButton<T> buttonBack()
        {
            return this.button("back");
        }
        public StepButton<T> buttonNew()
        {
            return this.button("new");
        }
        public StepButton<T> buttonPrev()
        {
            return this.button("prev");
        }
        public StepButton<T> buttonNext()
        {
            return this.button("next");
        }
        public StepButton<T> buttonAppend()
        {
            return this.button("append");
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
        public Builder<T> insertable(boolean value)
        {
            this.style.insertable = value;
            return this;
        }

        public UiWindowList<T> build()
        {
            return new UiWindowList<>(this);
        }

        public static class StepButton<T>
        {
            Builder<T> builder;
            String id;
            Style.Button data;
            public StepButton(Builder<T> builder, String id)
            {
                this.builder = builder;
                this.id = id;
                this.data = this.builder.style.buttons.get(id);
                if(this.data != null)
                    this.data = this.data.clone();
                else
                    this.data = new Style.Button(false, 0, null, null);
            }

            public StepButton<T> inBottomBar(boolean value)
            {
                this.data.inButtonBar = value;
                return this;
            }
            public StepButton<T> index(int value)
            {
                this.data.index = value;
                return this;
            }
            public StepButton<T> iconGetter(Function<EntityPlayer, ItemStack> value)
            {
                this.data.iconGetter = value;
                return this;
            }
            public StepButton<T> onClick(ButtonHandler<T> value)
            {
                this.data.handler = RuntimeUtil.cast(value);
                return this;
            }

            public Builder<T> remove()
            {
                this.builder.style.buttons.remove(this.id);
                return this.builder;
            }
            public Builder<T> finish()
            {
                if(this.data.iconGetter == null)
                    throw new IllegalStateException("iconGetter is not set");
                if(this.data.handler == null)
                    throw new IllegalStateException("onClick is not set");
                this.builder.style.buttons.put(this.id, this.data);
                return this.builder;
            }
        }
    }
    static class Style extends SimpleCloneable<Style>
    {
        static Style DEFAULT;

        int rows;
        int topBar, bottomBar;
        Function<EntityPlayer, ItemStack> topBarBackground, bottomBarBackground;
        boolean buttonPrevFirst, buttonNextLast;
        boolean insertable;
        Map<String, Button> buttons;

        static class Button extends SimpleCloneable<Button>
        {
            boolean inButtonBar;
            int index;
            Function<EntityPlayer, ItemStack> iconGetter;
            ButtonHandler<?> handler;
            Button(
                boolean inBottomBar,
                int index,
                Function<EntityPlayer, ItemStack> iconGetter,
                ButtonHandler<?> handler)
            {
                this.inButtonBar = inBottomBar;
                this.index = index;
                this.iconGetter = iconGetter;
                this.handler = handler;
            }
        }

        @Override
        public Style clone()
        {
            Style result = super.clone();
            result.buttons = new HashMap<>(result.buttons);
            return result;
        }
        static
        {
            DEFAULT = new Style();
            DEFAULT.rows = 6;
            DEFAULT.topBar = DEFAULT.bottomBar = 1;
            DEFAULT.buttons = new HashMap<>();
            DEFAULT.buttons.put(
                "new", new Button(
                    false, 7, player -> ItemStack.builder().fromId("nether_star")
                    .customName(MinecraftI18n.resolveText(player, "mzlib.ui.list.new")).build(),
                    (ui, player, action, arg) -> ui.adder.visit(RuntimeUtil.cast(ui), player, ui.getList().size())
                )
            );
            DEFAULT.buttons.put(
                "prev", new Button(
                    true, 2, player -> ItemStack.builder().playerHead().texturesUrl(
                        "https://textures.minecraft.net/texture/69ea1d86247f4af351ed1866bca6a3040a06c68177c78e42316a1098e60fb7d3")
                    .customName(MinecraftI18n.resolveText(player, "mzlib.ui.list.prev")).build(),
                    (ui, player, action, arg) ->
                    {
                        if(ui.pageNumber > 0)
                            ui.pageNumber--;
                        ui.update();
                    }
                )
            );
            DEFAULT.buttons.put(
                "next", new Button(
                    true, 6, player -> ItemStack.builder().playerHead().texturesUrl(
                        "https://textures.minecraft.net/texture/8271a47104495e357c3e8e80f511a9f102b0700ca9b88e88b795d33ff20105eb")
                    .customName(MinecraftI18n.resolveText(player, "mzlib.ui.list.next")).build(),
                    (ui, player, action, arg) ->
                    {
                        if(ui.pageNumber < ui.getPageCount() - 1)
                            ui.pageNumber++;
                        ui.update();
                    }
                )
            );
            DEFAULT.buttons.put(
                "append", new Button(
                    false, -1, player -> ItemStack.builder().fromId("nether_star")
                    .customName(MinecraftI18n.resolveText(player, "mzlib.ui.list.append")).build(),
                    (ui, player, action, arg) -> ui.adder.visit(RuntimeUtil.cast(ui), player, ui.getList().size())
                )
            );
            DEFAULT.buttonPrevFirst = DEFAULT.buttonNextLast = true;
            DEFAULT.topBarBackground = DEFAULT.bottomBarBackground = it -> ItemStack.builder().stainedGlassPane().blue()
                .emptyName().build();
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
        if(this.adder == null)
        {
            this.style.buttons.remove("new");
            this.style.buttons.remove("append");
        }
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

    void putButton(Style.Button value)
    {
        this.putButton(
            (value.inButtonBar ? this.getWindowType().getSize() - this.style.bottomBar * 9 : 0) + value.index,
            value.iconGetter,
            (player, action, arg) -> value.handler.onClick(RuntimeUtil.cast(this), player, action, arg)
        );
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
                        for(List<Text> lore : Item.LORE.revise(icon))
                        {
                            if(this.viewer != null)
                                lore.add(MinecraftI18n.resolveText(player, "mzlib.ui.list.lore.view"));
                            if(this.remover != null)
                                lore.add(MinecraftI18n.resolveText(player, "mzlib.ui.list.lore.remove"));
                            if(this.adder != null && this.style.insertable)
                                lore.add(MinecraftI18n.resolveText(player, "mzlib.ui.list.lore.insert"));
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
                                    if(this.adder != null && this.style.insertable)
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
                Style.Button buttonAppend;
                if(index == this.getList().size() && (buttonAppend = this.style.buttons.get("append")) != null)
                {
                    buttonAppend = buttonAppend.clone();
                    buttonAppend.inButtonBar = false;
                    buttonAppend.index = iconIndex;
                    this.putButton(buttonAppend);
                }
                else
                    this.putIconEmpty(iconIndex);
            }
        }
        for(Map.Entry<String, Style.Button> entry : this.style.buttons.entrySet())
        {
            switch(entry.getKey())
            {
                case "append":
                    continue;
                case "prev":
                    if(!this.style.buttonPrevFirst && this.pageNumber <= 0)
                        continue;
                    break;
                case "next":
                    if(!this.style.buttonNextLast && this.pageNumber >= this.getPageCount() - 1)
                        continue;
                    break;
                default:
                    break;
            }
            this.putButton(entry.getValue());
        }
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

    @FunctionalInterface
    public interface ButtonHandler<T>
    {
        void onClick(UiWindowList<T> ui, EntityPlayer player, WindowActionType actionType, int arg);

        static <T> ButtonHandler<T> of(UiWindow.ButtonHandler handler)
        {
            return (ui, player, actionType, arg) -> handler.onClick(player, actionType, arg);
        }
    }
}
