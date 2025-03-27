package mz.mzlib.minecraft.i18n;

import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.minecraft.entity.player.EntityPlayer;

import java.util.Map;

/**
 * @see MinecraftI18n
 */
@Deprecated
public class I18nMinecraft
{
    /**
     * @see MinecraftI18n#resolve(CommandSource, String)
     */
    public static String getTranslation(CommandSource player, String key)
    {
        return MinecraftI18n.resolve(player, key);
    }
    /**
     * @see MinecraftI18n#resolve(EntityPlayer, String)
     */
    public static String getTranslation(EntityPlayer player, String key)
    {
        return MinecraftI18n.resolve(player, key);
    }
    
    /**
     * @see MinecraftI18n#resolve(CommandSource, String, Object)
     */
    public static String getTranslationWithArgs(CommandSource commandSource, String key, Map<String, Object> args)
    {
        return MinecraftI18n.resolve(commandSource, key, args);
    }
    /**
     * @see MinecraftI18n#resolve(EntityPlayer, String, Object)
     */
    public static String getTranslationWithArgs(EntityPlayer commandSource, String key, Map<String, Object> args)
    {
        return MinecraftI18n.resolve(commandSource, key, args);
    }
}
