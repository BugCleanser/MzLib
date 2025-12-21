package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.ui.UiStack;
import mz.mzlib.minecraft.ui.window.control.UiWindowButton;

import java.awt.*;

public class UiWindowUtil
{
    public static UiWindowButton buttonBack(Point location)
    {
        return new UiWindowButton(
            location,
            player -> ItemStack.builder().playerHead().texturesUrl(
                    "https://textures.minecraft.net/texture/47e50591f4118b9ae44755f7b485699b4b917f00d65f5ea8553ee48826d234c7")
                .customName(MinecraftI18n.resolveText(player, "mzlib.ui.button.back")).build(),
            action -> UiStack.get(action.getPlayer()).back()
        );
    }
}
