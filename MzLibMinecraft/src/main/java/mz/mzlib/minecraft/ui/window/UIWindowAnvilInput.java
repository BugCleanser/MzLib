package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.player.async.EventAsyncPlayerDisplayItemInWindow;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.item.ItemStackBuilder;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UIStack;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class UIWindowAnvilInput extends UIWindowAnvil
{
    public static String prefixDefault = " ";
    public String prefix;
    public String initial;
    public String text;
    public UIWindowAnvilInput(String initial)
    {
        this(prefixDefault, initial);
    }
    public UIWindowAnvilInput(String prefix, String initial)
    {
        this.prefix = prefix;
        this.initial = initial;
        this.text = initial;
        
        this.putButton(0, player->new ItemStackBuilder("name_tag").setCustomName(Text.literal(this.prefix+this.initial)).setLore(MinecraftI18n.resolveText(player, "mzlib.ui.anvil_input.reset.lore")).get(), (player, actionType, data)->MinecraftServer.instance.execute(()->
        {
            player.getCurrentWindow().sendSlotUpdate(player, 0);
            player.getCurrentWindow().sendSlotUpdate(player, 2);
        }));
        this.putButton(1, player->new ItemStackBuilder("torch").setCustomName(MinecraftI18n.resolveText(player, "mzlib.ui.anvil_input.back")).get(), (player, actionType, data)->UIStack.get(player).back());
        this.putButton(2, player->new ItemStackBuilder("slime_ball").setCustomName(MinecraftI18n.resolveText(player, "mzlib.ui.anvil_input.done")).get(), (player, actionType, data)->this.done(player, this.text));
    }
    
    @Override
    public void onNameChanged(EntityPlayer player, String name)
    {
        if(name.startsWith(this.prefix))
            this.text = name.substring(this.prefix.length());
        else
        {
            Consumer<EventAsyncPlayerDisplayItemInWindow<?>> icon = this.icons.get(0);
            this.putIcon(0, p->ItemStack.empty());
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
        UIStack.get(player).go(new UIWindowAnvilInput(initial)
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
