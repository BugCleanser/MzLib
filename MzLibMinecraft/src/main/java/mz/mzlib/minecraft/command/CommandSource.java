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

@WrapMinecraftClass({@VersionName(name="net.minecraft.command.CommandSource", end=1300), @VersionName(name="net.minecraft.class_3915", begin=1300, end=1400), @VersionName(name="net.minecraft.server.command.ServerCommandSource", begin=1400)})
public interface CommandSource extends WrapperObject
{
    @WrapperCreator
    static CommandSource create(Object wrapped)
    {
        return WrapperObject.create(CommandSource.class, wrapped);
    }
    
    boolean isSilent();
    
    @SpecificImpl("isSilent")
    @VersionRange(end=1300)
    default boolean isSilentV_1300()
    {
        return false;
    }
    
    @SpecificImpl("isSilent")
    @VersionRange(begin=1300)
    @WrapMinecraftFieldAccessor({@VersionName(name="field_19283", end=1400), @VersionName(name="silent", begin=1400)})
    boolean isSilentV1300();
    
    CommandOutput getOutput();
    
    @SpecificImpl("getOutput")
    @VersionRange(end=1300)
    default CommandOutput getOutputV_1300()
    {
        return this.castTo(CommandOutput::create);
    }
    
    @SpecificImpl("getOutput")
    @VersionRange(begin=1300)
    @WrapMinecraftFieldAccessor({@VersionName(name="field_19276", end=1400), @VersionName(name="output", begin=1400)})
    CommandOutput getOutputV1300();
    
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
    
    Entity getEntity();
    
    @SpecificImpl("getEntity")
    @VersionRange(end=1300)
    default Entity getEntityV_1300()
    {
        if(this.isInstanceOf(Entity::create))
            return this.castTo(Entity::create);
        else
            return Entity.create(null);
    }
    
    @SpecificImpl("getEntity")
    @VersionRange(begin=1300)
    @WrapMinecraftFieldAccessor({@VersionName(name="field_19284", end=1400), @VersionName(name="entity", begin=1400)})
    Entity getEntityV1300();
    
    default EntityPlayer getPlayer()
    {
        if(this.getEntity().isInstanceOf(EntityPlayer::create))
            return this.getEntity().castTo(EntityPlayer::create);
        return EntityPlayer.create(null);
    }
}
