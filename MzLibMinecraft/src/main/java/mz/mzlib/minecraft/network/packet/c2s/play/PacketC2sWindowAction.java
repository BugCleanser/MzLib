package mz.mzlib.minecraft.network.packet.c2s.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.window.WindowActionType;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket", end=1400), @VersionName(name="net.minecraft.network.packet.c2s.play.ClickWindowC2SPacket", begin=1400, end=1500), @VersionName(name="net.minecraft.server.network.packet.ClickWindowC2SPacket", begin=1500, end=1502), @VersionName(name="net.minecraft.network.packet.c2s.play.ClickWindowC2SPacket", begin=1502, end=1604), @VersionName(name="net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket", begin=1604)})
public interface PacketC2sWindowAction extends Packet
{
    @WrapperCreator
    static PacketC2sWindowAction create(Object wrapped)
    {
        return WrapperObject.create(PacketC2sWindowAction.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="syncId"))
    int getSyncId();
    
    WindowActionType getActionType();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="actionType"))
    int getActionTypeIdV_1400();
    
    @SpecificImpl("getActionType")
    @VersionRange(end=1400)
    default WindowActionType getActionTypeV_1400()
    {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    @SpecificImpl("getActionType")
    @WrapMinecraftFieldAccessor(@VersionName(name="actionType", begin=1400))
    WindowActionType getActionTypeV1400();
    
    void setActionType(WindowActionType value);
    @WrapMinecraftFieldAccessor(@VersionName(name="actionType"))
    void setActionTypeIdV_1400(int value);
    @SpecificImpl("setActionType")
    @VersionRange(end=1400)
    default void setActionTypeV_1400(WindowActionType value)
    {
        // TODO
        throw new UnsupportedOperationException();
    }
    @SpecificImpl("setActionType")
    @WrapMinecraftFieldAccessor(@VersionName(name="actionType", begin=1400))
    void setActionTypeV1400(WindowActionType value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="slot"))
    int getSlotIndex();
    @WrapMinecraftFieldAccessor(@VersionName(name="slot"))
    void setSlotIndex(int value);
    
    @WrapMinecraftFieldAccessor({@VersionName(name="button", end=1600), @VersionName(name="clickData", begin=1600, end=1605), @VersionName(name="button", begin=1605)})
    int getData();
    @WrapMinecraftFieldAccessor({@VersionName(name="button", end=1600), @VersionName(name="clickData", begin=1600, end=1605), @VersionName(name="button", begin=1605)})
    void setData(int value);
}
