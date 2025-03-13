package mz.mzlib.minecraft.entity.player;

import mz.mzlib.minecraft.*;
import mz.mzlib.minecraft.bukkit.BukkitDisabled;
import mz.mzlib.minecraft.bukkit.BukkitEnabled;
import mz.mzlib.minecraft.bukkit.entity.BukkitEntityUtil;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.MessageTypeV1200_1900;
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
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(end=1400, name="net.minecraft.entity.player.ServerPlayerEntity"), @VersionName(begin=1400, name="net.minecraft.server.network.ServerPlayerEntity")})
public interface EntityPlayer extends WrapperObject, AbstractEntityPlayer
{
    WrapperFactory<EntityPlayer> FACTORY = WrapperFactory.find(EntityPlayer.class);
    @Deprecated
    @WrapperCreator
    static EntityPlayer create(Object wrapped)
    {
        return WrapperObject.create(EntityPlayer.class, wrapped);
    }
    
    default Player toPlayer()
    {
        return Player.of(this.getUuid());
    }
    
    boolean isOp();
    @SpecificImpl("isOp")
    @BukkitDisabled
    default boolean isOpNonBukkit()
    {
        return MinecraftServer.instance.getPlayerManager().isOp(this.getGameProfile());
    }
    @SpecificImpl("isOp")
    @BukkitEnabled
    default boolean isOpBukkit()
    {
        return BukkitEntityUtil.toBukkit(this).isOp();
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="networkHandler"))
    ServerPlayNetworkHandler getNetworkHandler();
    
    @WrapMinecraftFieldAccessor({@VersionName(name="language", end=1400), @VersionName(name="clientLanguage", begin=1400, end=1600), @VersionName(name="language", begin=2002)})
    String getLanguageV_1600__2002();
    
    default String getLanguage()
    {
        return MinecraftPlatform.instance.getLanguage(this);
    }
    
    @SpecificImpl("sendMessage")
    @VersionRange(end=1100)
    @WrapMinecraftMethod(@VersionName(name="sendMessage"))
    void sendMessageV_1100(Text message);
    
    @VersionRange(begin=1300, end=1600)
    @WrapMinecraftMethod({@VersionName(name="method_21277", end=1400), @VersionName(name="sendChatMessage", begin=1400)})
    void sendMessageV1300_1600(Text message, MessageTypeV1200_1900 type);
    
    @VersionRange(begin=1300, end=1600)
    @SpecificImpl("sendMessage")
    default void sendMessageV1300_1600(Text message)
    {
        this.sendMessageV1300_1600(message, MessageTypeV1200_1900.system());
    }
    
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
    
    void updateWindow();
    @VersionRange(end=1700)
    @WrapMinecraftMethod({@VersionName(name="refreshScreenHandler", end=1400), @VersionName(name="openContainer", begin=1400, end=1600), @VersionName(name="openHandledScreen", begin=1600, end=1604), @VersionName(name="refreshScreenHandler", begin=1604)})
    void updateWindowV_1700(Window window);
    @SpecificImpl("updateWindow")
    @VersionRange(end=1700)
    default void updateWindowV_1700()
    {
        this.updateWindowV_1700(this.getCurrentWindow());
    }
    @SpecificImpl("updateWindow")
    @VersionRange(begin=1700)
    default void updateWindowV1700()
    {
        this.getCurrentWindow().updateV1700();
    }
}
