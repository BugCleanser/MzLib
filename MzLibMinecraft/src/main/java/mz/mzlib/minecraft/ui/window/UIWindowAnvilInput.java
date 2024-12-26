package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.item.ItemStackBuilder;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UIStack;

import java.util.concurrent.CompletableFuture;

public abstract class UIWindowAnvilInput extends UIWindowAnvil
{
    public String text;
    public UIWindowAnvilInput(String original)
    {
        this.text=original;
        
        this.putButton(0, player->new ItemStackBuilder("name_tag").setDisplayName(Text.literal(original)).setLore(Text.literal(/*TODO i18n*/"§a点此重置输入框")).build(), (player, actionType, data)->MinecraftServer.instance.execute(()->
        {
            player.getCurrentWindow().sendSlotUpdate(player, 0);
            player.getCurrentWindow().sendSlotUpdate(player, 2);
        }));
        this.putButton(1, player->new ItemStackBuilder("torch").setDisplayName(Text.literal(/*TODO i18n*/"§5返回")).build(), (player, actionType, data)->UIStack.get(player).back());
        this.putButton(2, player->new ItemStackBuilder("slime_ball").setDisplayName(Text.literal(/*TODO i18n*/"§a完成")).build(), (player, actionType, data)->this.done(player, this.text));
    }
    
    @Override
    public void onNameChanged(EntityPlayer player, String name)
    {
        this.text=name;
        player.getCurrentWindow().sendSlotUpdate(player, 2);
    }
    
    public abstract void done(EntityPlayer player, String text);
    
    public static CompletableFuture<String> invoke(EntityPlayer player, String original, Text title)
    {
        CompletableFuture<String> result=new CompletableFuture<>();
        UIStack.get(player).go(new UIWindowAnvilInput(original)
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
            @Override
            public void onPlayerClose(EntityPlayer player)
            {
                result.complete(null);
            }
        });
        return result;
    }
}
