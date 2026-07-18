package mz.mzlib.minecraft.entity.player;

import mz.mzlib.minecraft.*;
import mz.mzlib.minecraft.bukkit.entity.BukkitEntityUtil;
import mz.mzlib.minecraft.incomprehensible.network.WindowSyncHandlerV1700;
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
import mz.mzlib.util.wrapper.Impl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({
    @VersionName(end = 1400, name = "net.minecraft.entity.player.ServerPlayerEntity"),
    @VersionName(begin = 1400, name = "net.minecraft.server.network.ServerPlayerEntity")
})
public interface EntityPlayer extends WrapperObject, EntityPlayerAbstract
{
    WrapperFactory<EntityPlayer> FACTORY = WrapperFactory.of(EntityPlayer.class);
    default Player toPlayer()
    {
        return Player.of(this.getUuid());
    }

    boolean isOp();
    @Impl("isOp")
    @MinecraftPlatform.Disabled(MinecraftPlatform.Tag.BUKKIT)
    default boolean isOp0()
    {
        return MinecraftServer.instance.getPlayerManager().isOp(this);
    }
    @Impl("isOp")
    @MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
    default boolean isOpBukkit()
    {
        return BukkitEntityUtil.toBukkit(this).isOp();
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "networkHandler"))
    ServerPlayNetworkHandler getNetworkHandler();

    @VersionRange(end = 1600)
    @VersionRange(begin = 2002)
    @WrapMinecraftFieldAccessor({
        @VersionName(name = "language", end = 1400),
        @VersionName(name = "clientLanguage", begin = 1400, end = 1600),
        @VersionName(name = "language", begin = 2002)
    })
    String getLanguageV_1600__2002();

    default String getLanguage()
    {
        return MinecraftPlatform.instance.getLanguage(this);
    }

    void sendMessage(Text message);

    void sendPacket(Packet packet);

    @Impl("sendPacket")
    @VersionRange(end = 2002)
    default void sendPacketV_2002(Packet packet)
    {
        this.getNetworkHandler().sendPacketV_2002(packet);
    }

    @Impl("sendPacket")
    @VersionRange(begin = 2002)
    default void sendPacketV2002(Packet packet)
    {
        this.getNetworkHandler().castTo(ServerCommonNetworkHandlerV2002.FACTORY).sendPacket(packet);
    }

    default void receivePacket(Packet packet)
    {
        this.getNetworkHandler().getConnection().getChannel().pipeline().fireChannelRead(packet.getWrapped());
    }

    default void openBook(ItemStack book)
    {
        this.closeInterface();
        int slot = 36 + this.getInventory().getHandIndex();
        this.getCurrentWindow().sendSlotUpdate(this, slot, book);
        this.openBook0(book);
        this.getCurrentWindow().sendSlotUpdate(this, slot);
    }

    void updateWindow();
    @VersionRange(end = 1700)
    @WrapMinecraftMethod({
        @VersionName(name = "refreshScreenHandler", end = 1400),
        @VersionName(name = "openContainer", begin = 1400, end = 1600),
        @VersionName(name = "openHandledScreen", begin = 1600, end = 1604),
        @VersionName(name = "refreshScreenHandler", begin = 1604)
    })
    void updateWindowV_1700(Window window);
    @Impl("updateWindow")
    @VersionRange(end = 1700)
    default void updateWindowV_1700()
    {
        this.updateWindowV_1700(this.getCurrentWindow());
    }
    @Impl("updateWindow")
    @VersionRange(begin = 1700)
    default void updateWindowV1700()
    {
        this.getCurrentWindow().updateV1700();
    }

    @VersionRange(begin = 1700)
    @WrapMinecraftFieldAccessor(@VersionName(name = "screenHandlerSyncHandler"))
    WindowSyncHandlerV1700 getWindowSyncHandlerV1700();


    @Impl("sendMessage")
    @VersionRange(end = 1100)
    default void sendMessage$implV_1100(Text message)
    {
        sendMessageV_1100(message);
    }
    @Impl("sendMessage")
    @VersionRange(begin = 1100, end = 1300)
    @VersionRange(begin = 1600, end = 2610)
    default void sendMessage$implV1100_1300__1600_2610(Text message)
    {
        this.sendMessageV1100_1300__1600_2610(message, false);
    }
    @Impl("sendMessage")
    @VersionRange(begin = 1300, end = 1600)
    default void sendMessage$implV1300_1600(Text message)
    {
        this.sendMessageV1300_1600(message, MessageTypeV1200_1900.system());
    }
    @Impl("sendMessage")
    @VersionRange(begin = 2610)
    default void sendMessage$implV2610(Text message)
    {
        this.sendMessageV2610(message);
    }

    @VersionRange(end = 1100)
    @WrapMinecraftMethod(@VersionName(name = "sendMessage"))
    void sendMessageV_1100(Text message);
    @VersionRange(begin = 1300, end = 1600)
    @WrapMinecraftMethod({
        @VersionName(name = "method_21277", end = 1400),
        @VersionName(name = "sendChatMessage", begin = 1400)
    })
    void sendMessageV1300_1600(Text message, MessageTypeV1200_1900 type);
}

