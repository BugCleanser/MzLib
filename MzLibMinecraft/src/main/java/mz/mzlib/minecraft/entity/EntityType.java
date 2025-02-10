package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.projectile.EntityFishingBobber;
import mz.mzlib.minecraft.registry.EntityTypesV_1300;
import mz.mzlib.minecraft.registry.RegistriesV1300;
import mz.mzlib.minecraft.registry.Registry;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.HashMap;
import java.util.Map;

@WrapMinecraftClass({@VersionName(name="java.lang.Class", end=1300), @VersionName(name="net.minecraft.entity.EntityType", begin=1300)})
public interface EntityType extends WrapperObject
{
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
        if(id.equals(Identifier.ofMinecraft("fishing_bobber")))
            return create(EntityFishingBobber.create(null).staticGetWrappedClass());
        return EntityTypesV_1300.getByNameV_1100(V_1100.id2Name.get(id));
    }
    @SpecificImpl("staticFromId")
    @VersionRange(begin=1100, end=1300)
    default EntityType staticFromIdV1100_1300(Identifier id)
    {
        if(id.equals(Identifier.ofMinecraft("fishing_bobber")))
            return create(EntityFishingBobber.create(null).staticGetWrappedClass());
        return getRegistry1100().get(id).castTo(EntityType::create);
    }
    @SpecificImpl("staticFromId")
    @VersionRange(begin=1300)
    default EntityType staticFromIdV1300(Identifier id)
    {
        return getRegistry1100().get(id).castTo(EntityType::create);
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
    
    /**
     * @see <a href="https://zh.minecraft.wiki/w/16w32a">16w32a</a>
     */
    class V_1100
    {
        public static Map<Identifier, String> id2Name = new HashMap<>();
        public static Map<String, Identifier> name2Id = new HashMap<>();
        
        public static void register(Identifier id, String name)
        {
            id2Name.put(id, name);
            name2Id.put(name, id);
        }
        public static void register(String id, String name)
        {
            register(Identifier.newInstance(id), name);
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
