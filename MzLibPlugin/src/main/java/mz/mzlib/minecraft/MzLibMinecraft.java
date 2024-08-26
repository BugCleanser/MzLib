package mz.mzlib.minecraft;

import mz.mzlib.event.EventListenerRegistrar;
import mz.mzlib.minecraft.I18n.I18nMinecraft;
import mz.mzlib.minecraft.event.MinecraftEventModule;
import mz.mzlib.minecraft.network.packet.PacketListenerModule;
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

            this.register(EventListenerRegistrar.instance);
            this.register(PacketListenerModule.instance);

            this.register(MinecraftEventModule.instance);
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
