package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.EntityType;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket"))
public interface PacketS2cEntitySpawn extends Packet
{
    @WrapperCreator
    static PacketS2cEntitySpawn create(Object wrapped)
    {
        return WrapperObject.create(PacketS2cEntitySpawn.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="id", end=2100), @VersionName(name="entityId", begin=2100)})
    int getEntityId();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="uuid"))
    UUID getUuid();
    
    EntityType getEntityType();
    
    @VersionRange(end=1400)
    @WrapMinecraftFieldAccessor(@VersionName(name="type"))
    int getEntityTypeIdV_1400();
    
    @SpecificImpl("getEntityType")
    @VersionRange(end=1400)
    default EntityType getEntityTypeV_1400()
    {
        return TypeIdMapV_1400.fromId(this.getEntityTypeIdV_1400());
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
    
    class TypeIdMapV_1400
    {
        public static Map<EntityType, Integer> typeIdsV_1400 = new HashMap<>();
        public static Map<Integer, EntityType> typesV_1400 = new HashMap<>();
        public static void register(EntityType type, int id)
        {
            typeIdsV_1400.put(type, id);
            typesV_1400.put(id, type);
        }
        public static void register(String type, int id)
        {
            register(EntityType.fromId(type), id);
        }
        static
        {
            register("item", 2);
            register("xp_orb", 7);
            register("area_effect_cloud", 3);
            register("painting", 9);
            register("arrow", 10);
            register("snowball", 61);
            register("small_fireball", 13);
            register("ender_pearl", 14);
            register("eye_of_ender_signal", 15);
            register("potion", 73);
            register("xp_bottle", 75);
            register("item_frame", 71);
            register("wither_skull", 66);
            register("tnt", 50);
            register("falling_block", 70);
            register("fireworks_rocket", 76);
            register("spectral_arrow", 91);
            register("shulker_bullet", 67);
            register("dragon_fireball", 93);
            register("armor_stand", 78);
            register("ender_crystal", 51);
        }
        
        public static int toId(EntityType type)
        {
            return typeIdsV_1400.get(type);
        }
        public static EntityType fromId(int id)
        {
            return typesV_1400.get(id);
        }
    }
}
