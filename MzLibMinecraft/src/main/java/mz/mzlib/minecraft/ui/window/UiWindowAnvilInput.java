package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.player.async.EventAsyncPlayerDisplayItemInWindow;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UiStack;
import mz.mzlib.util.Option;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class UiWindowAnvilInput extends UiWindowAnvil
{
    public static String prefixDefault = " ";
    public String prefix;
    public String initial;
    public String text;
    public UiWindowAnvilInput(String initial)
    {
        this(prefixDefault, initial);
    }
    public UiWindowAnvilInput(String prefix, String initial)
    {
        this.prefix = prefix;
        this.initial = initial;
        this.text = initial;

        this.putButton(
            0, player -> ItemStack.builder().fromId("name_tag").data(
                    Item.CUSTOM_NAME,
                    Option.some(Text.literal(this.prefix + this.initial))
                )
                .data(
                    Item.LORE, Option.some(
                        Collections.singletonList(MinecraftI18n.resolveText(player, "mzlib.ui.anvil_input.reset.lore")))
                ).build(),
            (player, actionType, data) -> MinecraftServer.instance.execute(() ->
            {
                player.getCurrentWindow().sendSlotUpdate(player, 0);
                player.getCurrentWindow().sendSlotUpdate(player, 2);
            })
        );
        this.putButton(
            1, player -> ItemStack.builder().fromId("torch").data(
                Item.CUSTOM_NAME,
                Option.some(MinecraftI18n.resolveText(player, "mzlib.ui.anvil_input.back"))
            ).build(),
            (player, actionType, data) -> UiStack.get(player).back()
        );
        this.putButton(
            2, player -> ItemStack.builder().fromId("slime_ball").data(
                Item.CUSTOM_NAME,
                Option.some(MinecraftI18n.resolveText(player, "mzlib.ui.anvil_input.done"))
            ).build(),
            (player, actionType, data) -> this.done(player, this.text)
        );
    }

    @Override
    public void onNameChanged(EntityPlayer player, String name)
    {
        if(name.startsWith(this.prefix))
            this.text = name.substring(this.prefix.length());
        else
        {
            Consumer<EventAsyncPlayerDisplayItemInWindow<?>> icon = this.icons.get(0);
            this.putIcon(0, p -> ItemStack.empty());
            player.getCurrentWindow().sendSlotUpdate(player, 0);
            this.putIcon0(0, icon);
            player.getCurrentWindow().sendSlotUpdate(player, 0);
        }
        player.getCurrentWindow().sendSlotUpdate(player, 2);
    }

    public abstract void done(EntityPlayer player, String text);

    public static CompletableFuture<String> invoke(EntityPlayer player, String initial, Text title)
    {
        CompletableFuture<String> result = new CompletableFuture<>();
        UiStack.get(player).go(new UiWindowAnvilInput(initial)
        {
            @Override
            public void done(EntityPlayer player, String text)
            {
                result.complete(text);
            }
            @Override
            public Text getTitle(EntityPlayer player)
            {
                return title;
            }
        });
        return result;
    }
}
