package mz.mzlib.minecraft;

import mz.mzlib.event.EventListenerRegistrar;
import mz.mzlib.minecraft.I18n.I18nMinecraft;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.MinecraftEventModule;
import mz.mzlib.minecraft.network.packet.PacketListenerModule;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;

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
            for (EntityPlayer player : MinecraftServer.instance.getPlayers())
            {
                player.sendMessage(Text.literal("awa").style(s->s.setBold(true)));
            }
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
