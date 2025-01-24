package mz.mzlib.minecraft.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(end=1900)
@WrapMinecraftClass(@VersionName(name="net.minecraft.network.MessageType"))
public interface MessageTypeV_1900 extends WrapperObject
{
    @WrapperCreator
    static MessageTypeV_1900 create(Object wrapped)
    {
        return WrapperObject.create(MessageTypeV_1900.class, wrapped);
    }
    
    static MessageTypeV_1900 chat()
    {
        return create(null).staticChat();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="field_11737"))
    MessageTypeV_1900 staticChat();
    
    static MessageTypeV_1900 system()
    {
        return create(null).staticSystem();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="field_11735"))
    MessageTypeV_1900 staticSystem();
    
    static MessageTypeV_1900 actionBar()
    {
        return create(null).staticActionBar();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="field_11733"))
    MessageTypeV_1900 staticActionBar();
}
