package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.EntityType;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.InvertibleMap;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.UUID;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket"))
public interface PacketS2cEntitySpawn extends Packet
{
    WrapperFactory<PacketS2cEntitySpawn> FACTORY = WrapperFactory.find(PacketS2cEntitySpawn.class);
    @Deprecated
    @WrapperCreator
    static PacketS2cEntitySpawn create(Object wrapped)
    {
        return WrapperObject.create(PacketS2cEntitySpawn.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="id", end=2100), @VersionName(name="entityId", begin=2100)})
    int getEntityId();
    
    @VersionRange(begin=900)
    @WrapMinecraftFieldAccessor(@VersionName(name="uuid"))
    UUID getUuidV900();
    
    EntityType getEntityType();
    
    @VersionRange(end=1400)
    @WrapMinecraftFieldAccessor(@VersionName(name="type"))
    int getEntityTypeIdV_1400();
    
    @SpecificImpl("getEntityType")
    @VersionRange(end=1400)
    default EntityType getEntityTypeV_1400()
    {
        return V_1400.fromId(this.getEntityTypeIdV_1400());
    }
    
    @SpecificImpl("getEntityType")
    @VersionRange(begin=1400)
    @WrapMinecraftFieldAccessor({@VersionName(name="entityTypeId", end=1903), @VersionName(name="entityType", begin=1903)})
    EntityType getEntityTypeV1400();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="x"))
    double getX();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="y"))
    double getY();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="z"))
    double getZ();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="yaw"))
    byte getScaledYaw();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="pitch"))
    byte getScaledPitch();
    
    @VersionRange(begin=1900)
    @WrapMinecraftFieldAccessor(@VersionName(name="headYaw"))
    byte getScaledHeadYawV1900();
    
    default Vec3d getPosition()
    {
        return Vec3d.newInstance(this.getX(), this.getY(), this.getZ());
    }
    
    class V_1400
    {
        public static InvertibleMap<EntityType, Integer> typeIds = new InvertibleMap<>();
        public static void register(String type, int id)
        {
            typeIds.put(EntityType.fromId(type), id);
        }
        static
        {
            register("boat", 1);
            register("item", 2);
            if(MinecraftPlatform.instance.getVersion()>=900)
                register("area_effect_cloud", 3);
            register("minecart", 10);
            register("tnt", 50);
            register(MinecraftPlatform.instance.getVersion()<1300 ? "ender_crystal" : "end_crystal", 51);
            register("arrow", 60);
            register("snowball", 61);
            register("egg", 62);
            register("fireball", 63);
            register("small_fireball", 64);
            register("ender_pearl", 65);
            register("wither_skull", 66);
            if(MinecraftPlatform.instance.getVersion()>=900)
                register("shulker_bullet", 67);
            if(MinecraftPlatform.instance.getVersion()>=1100)
                register("llama_spit", 68);
            register("falling_block", 70);
            register("item_frame", 71);
            register(MinecraftPlatform.instance.getVersion()<1300 ? "eye_of_ender_signal" : "eye_of_ender", 72);
            register("potion", 73);
            register(MinecraftPlatform.instance.getVersion()<1300 ? "xp_bottle" : "experience_bottle", 75);
            register(MinecraftPlatform.instance.getVersion()<1300 ? "fireworks_rocket" : "firework_rocket", 76);
            register("leash_knot", 77);
            register("armor_stand", 78);
            if(MinecraftPlatform.instance.getVersion()>=1100)
                register(MinecraftPlatform.instance.getVersion()<1300 ? "evocation_fangs" : "evoker_fangs", 79);
            register("fishing_bobber", 90);
            if(MinecraftPlatform.instance.getVersion()>=900)
                register("spectral_arrow", 91);
            if(MinecraftPlatform.instance.getVersion()>=900)
                register("dragon_fireball", 93);
            if(MinecraftPlatform.instance.getVersion()>=1300)
                register("trident", 94);
        }
        
        public static int toId(EntityType type)
        {
            return typeIds.get(type);
        }
        public static EntityType fromId(int id)
        {
            return typeIds.inverse().get(id);
        }
    }
}
