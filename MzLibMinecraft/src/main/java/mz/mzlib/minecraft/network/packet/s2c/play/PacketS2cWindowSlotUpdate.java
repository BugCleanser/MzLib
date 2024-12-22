package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket", end=1400), @VersionName(name="net.minecraft.network.packet.s2c.play.ContainerSlotUpdateS2CPacket", begin=1400, end=1600), @VersionName(name="net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket", begin=1600)})
public interface PacketS2cWindowSlotUpdate extends Packet
{
    @WrapperCreator
    static PacketS2cWindowSlotUpdate create(Object wrapped)
    {
        return WrapperObject.create(PacketS2cWindowSlotUpdate.class, wrapped);
    }
    
    static PacketS2cWindowSlotUpdate newInstanceV_1701(int syncId, int slot, ItemStack itemStack)
    {
        return create(null).staticNewInstanceV_1701(syncId, slot, itemStack);
    }
    @WrapConstructor
    @VersionRange(end=1701)
    PacketS2cWindowSlotUpdate staticNewInstanceV_1701(int syncId, int slot, ItemStack itemStack);
    
    static PacketS2cWindowSlotUpdate newInstanceV1701(int syncId, int revision, int slot, ItemStack itemStack)
    {
        return create(null).staticNewInstanceV1701(syncId, revision, slot, itemStack);
    }
    @WrapConstructor
    @VersionRange(begin=1701)
    PacketS2cWindowSlotUpdate staticNewInstanceV1701(int syncId, int revision, int slot, ItemStack itemStack);
}
