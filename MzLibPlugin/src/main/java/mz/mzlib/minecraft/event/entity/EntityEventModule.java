package mz.mzlib.minecraft.event.entity;

import mz.mzlib.module.MzModule;

public class EntityEventModule extends MzModule
{
    public static EntityEventModule instance=new EntityEventModule();

    @Override
    public void onLoad()
    {
        this.register(EventEntityLivingDamage.Module.instance);
    }
}
