package mz.mzlib.minecraft.vanilla;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.util.RuntimeUtil;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.platform.PlayerAdapter;
import net.luckperms.api.util.Tristate;

public class PermissionHelpLuckPerms extends PermissionRegistry
{
    public LuckPerms provider;
    public PlayerAdapter<?> playerAdapter;

    {
        this.provider = LuckPermsProvider.get();
        this.playerAdapter = this.provider.getPlayerAdapter(EntityPlayer.FACTORY.getStatic().static$getWrappedClass());
    }

    public User getUser(EntityPlayer player)
    {
        return this.playerAdapter.getUser(RuntimeUtil.cast(player.getWrapped()));
    }

    @Override
    public boolean check(EntityPlayer player, String permission)
    {
        Tristate tristate = this.getUser(player).getCachedData().getPermissionData().checkPermission(permission);
        if(Tristate.UNDEFINED.equals(tristate))
            return super.check(player, permission);
        else
            return tristate.asBoolean();
    }
}
