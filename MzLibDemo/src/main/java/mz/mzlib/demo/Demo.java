package mz.mzlib.demo;

import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.command.CommandBuilder;
import mz.mzlib.module.MzModule;

public class Demo extends MzModule
{
    public static Demo instance = new Demo();
    
    public Command commandDemo=new CommandBuilder("mzlibDemo".toLowerCase()).build();
    
    @Override
    public void onLoad()
    {
        this.register(commandDemo);
        
        this.register(Inventory10Slots.instance);
    }
}
