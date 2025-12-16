package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.entity.player.EntityPlayer;

public class WindowAction
{
    EntityPlayer player;
    /**
     * the index of slot or -1 when click title bar with item or -999 when click outside
     */
    int index;
    WindowActionType type;
    /**
     * @see WindowActionType
     */
    int data;

    public WindowAction(EntityPlayer player, int index, WindowActionType type, int data)
    {
        this.player = player;
        this.index = index;
        this.type = type;
        this.data = data;
    }
    public WindowAction(EntityPlayer player, int index, WindowActionType type)
    {
        this(player, index, type, 0);
    }

    public EntityPlayer getPlayer()
    {
        return this.player;
    }

    public int getIndex()
    {
        return this.index;
    }

    public WindowActionType getType()
    {
        return this.type;
    }

    public int getData()
    {
        return this.data;
    }
}
