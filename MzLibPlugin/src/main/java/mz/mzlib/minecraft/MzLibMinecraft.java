package mz.mzlib.minecraft;

import mz.mzlib.event.EventListenerRegistrar;
import mz.mzlib.minecraft.I18n.I18nMinecraft;
import mz.mzlib.minecraft.bukkit.item.BukkitItemUtil;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.MinecraftEventModule;
import mz.mzlib.minecraft.network.packet.PacketListenerModule;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.text.TextHoverEvent;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class MzLibMinecraft extends MzModule
{
    public static MzLibMinecraft instance = new MzLibMinecraft();

    @Override
    public void onLoad()
    {
        try
        {
            this.register(I18nMinecraft.instance);

            this.register(NothingMinecraftServer.class);

            this.register(EventListenerRegistrar.instance);
            this.register(PacketListenerModule.instance);

            this.register(MinecraftEventModule.instance);

            // TODO test
//            ItemStack is=new ItemStack(Material.ICE);
//            ItemMeta im = is.getItemMeta();
//            im.setDisplayName("我超，冰！");
//            im.setLore(Collections.singletonList("OwO"));
//            is.setItemMeta(im);
//            for (EntityPlayer player : MinecraftServer.instance.getPlayers())
//            {
//                player.sendMessage(Text.literal("awa").style(s->
//                        s.setHoverEvent(TextHoverEvent.showItemV_1300(BukkitItemUtil.fromBukkit(is)))
//                ));
//            }
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
