package mz.mzlib.demo;

import mz.mzlib.minecraft.command.Command;
import mz.mzlib.module.MzModule;

public class Demo extends MzModule
{
    public static Demo instance = new Demo();
    
    public Command commandDemo=new Command("mzlibDemo".toLowerCase());
    
    @Override
    public void onLoad()
    {
        this.register(commandDemo);
        
        this.register(Inventory10Slots.instance);
        this.register(ExampleAsyncFunction.instance);
    }
}
