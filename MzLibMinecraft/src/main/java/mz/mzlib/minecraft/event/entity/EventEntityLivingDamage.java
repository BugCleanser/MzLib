package mz.mzlib.minecraft.event.entity;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.EntityLiving;
import mz.mzlib.minecraft.entity.damage.DamageSource;
import mz.mzlib.minecraft.world.World;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.Wrapper_boolean;
import mz.mzlib.util.wrapper.basic.Wrapper_float;

public class EventEntityLivingDamage extends EventEntity
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
        return (EntityLiving)super.getEntity();
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
        super.call();
    }
    
    public static class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @WrapSameClass(EntityLiving.class)
        public interface NothingEntityLiving extends EntityLiving, Nothing
        {
            @VersionRange(end=2102)
            @NothingInject(wrapperMethodName="damageV_2102", wrapperMethodParams={DamageSource.class, float.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
            default Wrapper_boolean damageBeforeV_2102(@LocalVar(1) DamageSource source, @LocalVar(2) Wrapper_float damage, @CustomVar("event") WrapperObject event)
            {
                EventEntityLivingDamage e = new EventEntityLivingDamage(this, source, damage.getWrapped());
                e.call();
                if(e.isCancelled())
                {
                    e.finish();
                    return Wrapper_boolean.create(false);
                }
                source.setWrappedFrom(e.getSource());
                damage.setWrapped(e.getDamage());
                event.setWrapped(e);
                return Nothing.notReturn();
            }
            
            @VersionRange(begin=2102)
            @NothingInject(wrapperMethodName="damageV2102", wrapperMethodParams={World.class, DamageSource.class, float.class}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
            default Wrapper_boolean damageBeforeV2102(@LocalVar(2) DamageSource source, @LocalVar(3) Wrapper_float damage, @CustomVar("event") WrapperObject event)
            {
                return this.damageBeforeV_2102(source, damage, event);
            }
            
            static void locateDamageAfter(NothingInjectLocating locating)
            {
                locating.allLater(AsmUtil.insnReturn(boolean.class).getOpcode());
                assert !locating.locations.isEmpty();
            }
            
            @VersionRange(end=2102)
            @NothingInject(wrapperMethodName="damageV_2102", wrapperMethodParams={DamageSource.class, float.class}, locateMethod="locateDamageAfter", type=NothingInjectType.INSERT_BEFORE)
            default Wrapper_boolean damageAfterV_2102(@CustomVar("event") WrapperObject event)
            {
                ((EventEntityLivingDamage)event.getWrapped()).finish();
                return Nothing.notReturn();
            }
            
            @VersionRange(begin=2102)
            @NothingInject(wrapperMethodName="damageV2102", wrapperMethodParams={World.class, DamageSource.class, float.class}, locateMethod="locateDamageAfter", type=NothingInjectType.INSERT_BEFORE)
            default Wrapper_boolean damageAfterV2102(@CustomVar("event") WrapperObject event)
            {
                return this.damageAfterV_2102(event);
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
