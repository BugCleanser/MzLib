package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.server.command.ServerCommandSource"))
public interface CommandSource extends WrapperObject
{
    @WrapperCreator
    static CommandSource create(Object wrapped)
    {
        return WrapperObject.create(CommandSource.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="silent"))
    boolean isSilent();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="output"))
    CommandOutput getOutput();
    
    void sendMessage(Text message);
    @VersionRange(end=1901)
    @SpecificImpl("sendMessage")
    default void sendMessageV_1901(Text message)
    {
        if(this.isSilent())
            return;
        this.getOutput().sendMessage(message);
    }
    @VersionRange(begin=1901)
    @SpecificImpl("sendMessage")
    @WrapMinecraftMethod(@VersionName(name="sendMessage"))
    void sendMessageV1901(Text message);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="entity"))
    Entity getEntity();
    
    default EntityPlayer getPlayer()
    {
        if(this.getEntity().isInstanceOf(EntityPlayer::create))
            return this.getEntity().castTo(EntityPlayer::create);
        return EntityPlayer.create(null);
    }
}
