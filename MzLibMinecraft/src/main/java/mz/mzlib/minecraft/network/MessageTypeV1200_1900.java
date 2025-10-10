package mz.mzlib.minecraft.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1200, end=1900)
@WrapMinecraftClass({@VersionName(name="net.minecraft.util.ChatMessageType", end=1400), @VersionName(name="net.minecraft.network.MessageType", begin=1400)})
public interface MessageTypeV1200_1900 extends WrapperObject
{
    WrapperFactory<MessageTypeV1200_1900> FACTORY = WrapperFactory.of(MessageTypeV1200_1900.class);
    @Deprecated
    @WrapperCreator
    static MessageTypeV1200_1900 create(Object wrapped)
    {
        return WrapperObject.create(MessageTypeV1200_1900.class, wrapped);
    }
    
    static MessageTypeV1200_1900 chat()
    {
        return create(null).staticChat();
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="CHAT", end=1400), @VersionName(name="field_11737", begin=1400)})
    MessageTypeV1200_1900 staticChat();
    
    static MessageTypeV1200_1900 system()
    {
        return create(null).staticSystem();
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="SYSTEM", end=1400), @VersionName(name="field_11735", begin=1400)})
    MessageTypeV1200_1900 staticSystem();
    
    static MessageTypeV1200_1900 actionBar()
    {
        return create(null).staticActionBar();
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="GAME_INFO", end=1400), @VersionName(name="field_11733", begin=1400)})
    MessageTypeV1200_1900 staticActionBar();
}
