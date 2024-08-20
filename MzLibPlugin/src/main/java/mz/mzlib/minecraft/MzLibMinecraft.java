package mz.mzlib.minecraft;

import mz.mzlib.event.EventListenerRegistrar;
import mz.mzlib.minecraft.packet.PacketListenerModule;
import mz.mzlib.module.MzModule;

public class MzLibMinecraft extends MzModule
{
    public static MzLibMinecraft instance = new MzLibMinecraft();

    @Override
    public void onLoad()
    {
        this.register(EventListenerRegistrar.instance);
        this.register(PacketListenerModule.instance);
    }
}
