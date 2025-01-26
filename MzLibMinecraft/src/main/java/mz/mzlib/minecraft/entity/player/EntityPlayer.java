package mz.mzlib.minecraft.entity.player;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.MessageTypeV_1900;
import mz.mzlib.minecraft.network.ServerCommonNetworkHandlerV2002;
import mz.mzlib.minecraft.network.ServerPlayNetworkHandler;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(end=1400, name="net.minecraft.entity.player.ServerPlayerEntity"), @VersionName(begin=1400, name="net.minecraft.server.network.ServerPlayerEntity")})
public interface EntityPlayer extends WrapperObject, AbstractEntityPlayer
{
    @WrapperCreator
    static EntityPlayer create(Object wrapped)
    {
        return WrapperObject.create(EntityPlayer.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="networkHandler"))
    ServerPlayNetworkHandler getNetworkHandler();
    
    @WrapMinecraftFieldAccessor({@VersionName(name="language", end=1400), @VersionName(name="clientLanguage", begin=1400, end=1600), @VersionName(name="language", begin=2002)})
    String getLanguageV_1600__2002();
    
    default String getLanguage()
    {
        return MinecraftPlatform.instance.getLanguage(this);
    }
    
    @VersionRange(end=1600)
    @SpecificImpl("sendMessage")
    default void sendMessageV_1600(Text message)
    {
        this.sendMessageV_1600(message, MessageTypeV_1900.system());
    }
    
    @VersionRange(end=1600)
    @WrapMinecraftMethod({@VersionName(name="method_21277", end=1400), @VersionName(name="sendChatMessage", begin=1400)})
    void sendMessageV_1600(Text message, MessageTypeV_1900 type);
    
    void sendPacket(Packet packet);
    
    @SpecificImpl("sendPacket")
    @VersionRange(end=2002)
    default void sendPacketV_2002(Packet packet)
    {
        this.getNetworkHandler().sendPacketV_2002(packet);
    }
    
    @SpecificImpl("sendPacket")
    @VersionRange(begin=2002)
    default void sendPacketV2002(Packet packet)
    {
        this.getNetworkHandler().castTo(ServerCommonNetworkHandlerV2002::create).sendPacket(packet);
    }
    
    default void receivePacket(Packet packet)
    {
        this.getNetworkHandler().getConnection().getChannel().pipeline().fireChannelRead(packet.getWrapped());
    }
    
    default void openBook(ItemStack book)
    {
        this.closeInterface();
        int slot = 36+this.getInventory().getHandIndex();
        this.getCurrentWindow().sendSlotUpdate(this, slot, book);
        this.openBook0(book);
        this.getCurrentWindow().sendSlotUpdate(this, slot);
    }
    
    @WrapMinecraftMethod({@VersionName(name="refreshScreenHandler", end=1400), @VersionName(name="openContainer", begin=1400, end=1600), @VersionName(name="openHandledScreen", begin=1600, end=1604), @VersionName(name="refreshScreenHandler", begin=1604, end=1700)})
    void updateWindowV_1700(Window window);
}
