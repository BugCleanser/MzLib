package mz.mzlib.minecraft.event.entity;

import mz.mzlib.module.MzModule;

public class ModuleEventEntity extends MzModule
{
    public static ModuleEventEntity instance=new ModuleEventEntity();

    @Override
    public void onLoad()
    {
        this.register(EventEntityLivingDamage.Module.instance);
    }
}
