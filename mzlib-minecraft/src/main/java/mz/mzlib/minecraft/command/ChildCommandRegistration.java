package mz.mzlib.minecraft.command;

import mz.mzlib.module.MzModule;
import mz.mzlib.module.Registrable;

public class ChildCommandRegistration implements Registrable
{
    public Command parent;
    public Command child;

    public ChildCommandRegistration(Command parent, Command child)
    {
        this.parent = parent;
        this.child = child;
    }

    @Override
    public void onRegister(MzModule module)
    {
        this.parent.addChild(this.child);
    }
    @Override
    public void onUnregister(MzModule module)
    {
        this.parent.removeChild(this.child);
    }
}
