package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.projectile.EntityFishingBobber;
import mz.mzlib.minecraft.registry.EntityTypesV_1300;
import mz.mzlib.minecraft.registry.RegistriesV1300;
import mz.mzlib.minecraft.registry.Registry;
import mz.mzlib.minecraft.world.AbstractWorld;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.InvertibleMap;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.ThrowableFunction;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@WrapMinecraftClass({@VersionName(name="java.lang.Class", end=1300), @VersionName(name="net.minecraft.entity.EntityType", begin=1300)})
public interface EntityType extends WrapperObject
{
    WrapperFactory<EntityType> FACTORY = WrapperFactory.of(EntityType.class);
    @Deprecated
    @WrapperCreator
    static EntityType create(Object wrapped)
    {
        return WrapperObject.create(EntityType.class, wrapped);
    }
    
    static EntityType fromId(String id)
    {
        return fromId(Identifier.newInstance(id));
    }
    
    static EntityType fromId(Identifier id)
    {
        return create(null).staticFromId(id);
    }
    
    EntityType staticFromId(Identifier id);
    
    @SpecificImpl("staticFromId")
    @VersionRange(end=1100)
    default EntityType staticFromIdV_1100(Identifier id)
    {
        if(id.equals(Identifier.minecraft("fishing_bobber")))
            return create(EntityFishingBobber.FACTORY.getWrappedClass());
        return EntityTypesV_1300.getByNameV_1100(V_1100.names.get(id));
    }
    
    @SpecificImpl("staticFromId")
    @VersionRange(begin=1100, end=1300)
    default EntityType staticFromIdV1100_1300(Identifier id)
    {
        if(id.equals(Identifier.minecraft("fishing_bobber")))
            return create(EntityFishingBobber.FACTORY.getWrappedClass());
        return getRegistry1100().get(id).castTo(EntityType.FACTORY);
    }
    
    @SpecificImpl("staticFromId")
    @VersionRange(begin=1300)
    default EntityType staticFromIdV1300(Identifier id)
    {
        return getRegistry1100().get(id).castTo(EntityType.FACTORY);
    }
    
    default Identifier getIdV1300()
    {
        return getRegistry1100().getIdV1300(this);
    }
    
    static Registry getRegistry1100()
    {
        return create(null).staticGetRegistryV1100();
    }
    
    Registry staticGetRegistryV1100();
    
    @VersionRange(begin=1100, end=1300)
    @SpecificImpl("staticGetRegistryV1100")
    default Registry staticRegistryV1100_1300()
    {
        return EntityTypesV_1300.registryV1100();
    }
    
    @VersionRange(begin=1300)
    @SpecificImpl("staticGetRegistryV1100")
    default Registry staticRegistryV1300()
    {
        return RegistriesV1300.entityType();
    }
    
    Entity newEntity(AbstractWorld world);
    
    Map<Class<?>, MethodHandle> cacheV_1300 = MinecraftPlatform.instance.getVersion()<1300 ? new HashMap<>() : null;
    @SpecificImpl("newEntity")
    @VersionRange(end=1300)
    default Entity newEntityV_1300(AbstractWorld world)
    {
        try
        {
            return Entity.create((Object)cacheV_1300.computeIfAbsent((Class<?>)this.getWrapped(), ThrowableFunction.of(c-> //
                            ClassUtil.findConstructor(c, AbstractWorld.create(null).staticGetWrappedClass()).asType(MethodType.methodType(Object.class, Object.class)))) //
                    .invokeExact((Object)world.getWrapped()));
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
    
    @VersionRange(begin=1300, end=1400)
    @WrapMinecraftFieldAccessor(@VersionName(name="entityFactory"))
    Function<Object, ?> getFactoryV1300_1400();
    
    @SpecificImpl("newEntity")
    @VersionRange(begin=1300, end=1400)
    default Entity newEntityV1300_1400(AbstractWorld world)
    {
        return Entity.create(this.getFactoryV1300_1400().apply(world.getWrapped()));
    }
    
    @VersionRange(begin=1400)
    @WrapMinecraftFieldAccessor(@VersionName(name="factory"))
    EntityFactoryV1400 getFactoryV1400();
    
    @SpecificImpl("newEntity")
    @VersionRange(begin=1400)
    default Entity newEntityV1400(AbstractWorld world)
    {
        return getFactoryV1400().create(this, world);
    }
    
    @VersionRange(begin=1400)
    @WrapMinecraftInnerClass(outer=EntityType.class, name=@VersionName(name="EntityFactory"))
    interface EntityFactoryV1400 extends WrapperObject
    {
        WrapperFactory<EntityFactoryV1400> FACTORY = WrapperFactory.of(EntityFactoryV1400.class);
        @Deprecated
        @WrapperCreator
        static EntityFactoryV1400 create(Object wrapped)
        {
            return WrapperObject.create(EntityFactoryV1400.class, wrapped);
        }
        
        @WrapMinecraftMethod(@VersionName(name="create"))
        Entity create(EntityType type, AbstractWorld world);
    }
    
    /**
     * @see <a href="https://zh.minecraft.wiki/w/16w32a">16w32a</a>
     */
    class V_1100
    {
        public static InvertibleMap<Identifier, String> names = new InvertibleMap<>();
        
        public static void register(String id, String name)
        {
            names.put(Identifier.newInstance(id), name);
        }
        
        static
        {
            register("area_effect_cloud", "AreaEffectCloud");
            register("armor_stand", "ArmorStand");
            register("cave_spider", "CaveSpider");
            register("chest_minecart", "MinecartChest");
            register("commandblock_minecart", "MinecartCommandBlock");
            register("dragon_fireball", "DragonFireball");
            register("egg", "ThrownEgg");
            register("ender_crystal", "EnderCrystal");
            register("ender_dragon", "EnderDragon");
            register("ender_pearl", "ThrownEnderpearl");
            register("eye_of_ender_signal", "EyeOfEnderSignal");
            register("falling_block", "FallingSand");
            register("fireworks_rocket", "FireworksRocketEntity");
            register("furnace_minecart", "MinecartFurnace");
            register("hopper_minecart", "MinecartHopper");
            register("horse", "EntityHorse");
            register("item_frame", "ItemFrame");
            register("leash_knot", "LeashKnot");
            register("lightning_bolt", "LightningBolt");
            register("magma_cube", "LavaSlime");
            register("minecart", "MinecartRideable");
            register("mooshroom", "MushroomCow");
            register("ocelot", "Ozelot");
            register("polar_bear", "PolarBear");
            register("shulker_bullet", "ShulkerBullet");
            register("small_fireball", "SmallFireball");
            register("spectral_arrow", "SpectralArrow");
            register("potion", "ThrownPotion");
            register("spawner_minecart", "MinecartSpawner");
            register("tnt", "PrimedTnt");
            register("tnt_minecart", "MinecartTNT");
            register("villager_golem", "VillagerGolem");
            register("wither", "WitherBoss");
            register("wither_skull", "WitherSkull");
            register("xp_bottle", "ThrownExpBottle");
            register("xp_orb", "XPOrb");
            register("zombie_pigman", "PigZombie");
            
            register("item", "Item");
            register("painting", "Painting");
            register("arrow", "Arrow");
            register("snowball", "Snowball");
            register("fireball", "Fireball");
            register("boat", "Boat");
            register("creeper", "Creeper");
            register("skeleton", "Skeleton");
            register("spider", "Spider");
            register("giant", "Giant");
            register("zombie", "Zombie");
            register("slime", "Slime");
            register("ghast", "Ghast");
            register("enderman", "Enderman");
            register("silverfish", "Silverfish");
            register("blaze", "Blaze");
            register("bat", "Bat");
            register("witch", "Witch");
            register("endermite", "Endermite");
            register("guardian", "Guardian");
            register("shulker", "Shulker");
            register("pig", "Pig");
            register("sheep", "Sheep");
            register("cow", "Cow");
            register("chicken", "Chicken");
            register("squid", "Squid");
            register("wolf", "Wolf");
            register("snowman", "SnowMan");
            register("rabbit", "Rabbit");
            register("villager", "Villager");
        }
    }
}
