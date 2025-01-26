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

@WrapMinecraftClass({@VersionName(name="net.minecraft.network.packet.c2s.play.ClickWindowC2SPacket", end=1604), @VersionName(name="net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket", begin=1604)})
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
    
    @VersionRange(end=1300)
    @WrapMinecraftFieldAccessor(@VersionName(name="actionType", begin=1400))
    int getActionTypeIdV_1300();
    
    @SpecificImpl("getActionType")
    @VersionRange(end=1300)
    default WindowActionType getActionTypeV_1300()
    {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    @SpecificImpl("getActionType")
    @VersionRange(begin=1300)
    @WrapMinecraftFieldAccessor({@VersionName(name="field_13798", end=1400), @VersionName(name="actionType", begin=1400)})
    WindowActionType getActionTypeV1300();
    
    void setActionType(WindowActionType value);
    
    @VersionRange(end=1300)
    @WrapMinecraftFieldAccessor(@VersionName(name="actionType"))
    void setActionTypeIdV_1300(int value);
    
    @SpecificImpl("setActionType")
    @VersionRange(end=1300)
    default void setActionTypeV_1300(WindowActionType value)
    {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    @SpecificImpl("setActionType")
    @VersionRange(begin=1300)
    @WrapMinecraftFieldAccessor({@VersionName(name="field_13798", end=1400), @VersionName(name="actionType", begin=1400)})
    void setActionTypeV1300(WindowActionType value);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="slot"))
    int getSlotIndex();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="slot"))
    void setSlotIndex(int value);
    
    @WrapMinecraftFieldAccessor({@VersionName(name="button", end=1600), @VersionName(name="clickData", begin=1600, end=1605), @VersionName(name="button", begin=1605)})
    int getData();
    
    @WrapMinecraftFieldAccessor({@VersionName(name="button", end=1600), @VersionName(name="clickData", begin=1600, end=1605), @VersionName(name="button", begin=1605)})
    void setData(int value);
}
