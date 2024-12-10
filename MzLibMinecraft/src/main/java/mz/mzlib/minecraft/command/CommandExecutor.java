package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.GameObject;
import mz.mzlib.minecraft.text.Text;

public interface CommandExecutor
{
	Text execute(GameObject sender, String command, String args);
}
