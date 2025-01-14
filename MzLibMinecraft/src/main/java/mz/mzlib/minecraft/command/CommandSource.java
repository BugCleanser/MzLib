package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
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
    
    @WrapMinecraftMethod(@VersionName(name="sendMessage"))
    void sendMessage(Text message);
    
    @WrapMinecraftFieldAccessor(@VersionName(name="entity"))
    Entity getEntity();
    
    default EntityPlayer getPlayer()
    {
        if(this.getEntity().isInstanceOf(EntityPlayer::create))
            return this.getEntity().castTo(EntityPlayer::create);
        return EntityPlayer.create(null);
    }
}
