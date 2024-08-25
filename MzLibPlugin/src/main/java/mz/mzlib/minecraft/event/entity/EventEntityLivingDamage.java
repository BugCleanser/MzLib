package mz.mzlib.minecraft.event.entity;

import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.entity.EntityLiving;
import mz.mzlib.minecraft.entity.damage.DamageSource;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.Wrapper_boolean;
import mz.mzlib.util.wrapper.basic.Wrapper_float;

public class EventEntityLivingDamage extends EntityEvent
{
    public DamageSource source;
    public float damage;
    public EventEntityLivingDamage(EntityLiving entity, DamageSource source, float damage)
    {
        super(entity);
        this.source = source;
        this.damage = damage;
    }

    @Override
    public EntityLiving getEntity()
    {
        return (EntityLiving) super.getEntity();
    }

    public DamageSource getSource()
    {
        return this.source;
    }

    public void setSource(DamageSource source)
    {
        this.source = source;
    }

    public float getDamage()
    {
        return this.damage;
    }

    public void setDamage(float damage)
    {
        this.damage = damage;
    }

    @Override
    public void call()
    {
    }

    public static class Module extends MzModule
    {
        public static Module instance=new Module();

        @WrapSameClass(EntityLiving.class)
        public interface NothingEntityLiving extends EntityLiving, Nothing
        {
            static void locateDamageBefore(NothingInjectLocating locating)
            {
            }
            @NothingInject(wrapperMethod = "damage", locateMethod = "locateDamageBefore", type = NothingInjectType.INSERT_BEFORE)
            default Wrapper_boolean damageBefore(@LocalVar(1) DamageSource source, @LocalVar(2) Wrapper_float damage, @CustomVar("event") WrapperObject event)
            {
                EventEntityLivingDamage e = new EventEntityLivingDamage(this, source, damage.getWrapped());
                e.call();
                if(e.isCancelled())
                {
                    e.complete();
                    return Wrapper_boolean.create(false); // TODO: ?
                }
                source.setWrappedFrom(e.getSource());
                damage.setWrapped(e.getDamage());
                event.setWrapped(e);
                return Nothing.notReturn();
            }
            static void locateDamageAfter(NothingInjectLocating locating)
            {
                locating.allAfter(AsmUtil.insnReturn(boolean.class).getOpcode());
                assert !locating.locations.isEmpty();
            }
            @NothingInject(wrapperMethod = "damage", locateMethod = "locateDamageAfter", type = NothingInjectType.INSERT_BEFORE)
            default Wrapper_boolean damageAfter(@CustomVar("event") WrapperObject event)
            {
                ((EventEntityLivingDamage) event.getWrapped()).complete();
                return Nothing.notReturn();
            }
        }

        @Override
        public void onLoad()
        {
            this.register(EventEntityLivingDamage.class);
            this.register(NothingEntityLiving.class);
        }
    }
}
